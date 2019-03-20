import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jmetal.qualityIndicator.Hypervolume;

public class AlgNSGA2 {

	private Double uBound;
	private Double lBound;
	private Integer dimentsionOfValues;
	private Integer dimentsionOfResult;
	private Integer noOfGenerations;
	private Integer populationSize;
	private Integer tournamentSize = 2;
	private Double liveParentsAfterSelectionPercentage = 0.4;
	private Double numberOfMutatedPercentage;
	private List<Individual> populationR;
	private List<Individual> populationP;
	private List<Individual> populationQ;
	private EvaluateOp evaluateOp;
	private Map<Integer, List<Individual>> fronts = new HashMap<Integer, List<Individual>>();
	private Random rand = new Random();

	public AlgNSGA2(Double lBound, Double uBound, Integer dimentsionOfValues,
			Integer dimentsionOfResult, Integer noOfGenerations,
			Integer populationSize, EvaluateOp evaluateOp) {
		super();
		this.uBound = uBound;
		this.lBound = lBound;
		this.dimentsionOfValues = dimentsionOfValues;
		this.dimentsionOfResult = dimentsionOfResult;
		this.noOfGenerations = noOfGenerations;
		this.populationSize = populationSize;
		this.evaluateOp = evaluateOp;
		this.populationR = new ArrayList<Individual>();
		this.populationP = new ArrayList<Individual>();
		this.populationQ = new ArrayList<Individual>();
		this.numberOfMutatedPercentage = 1.0 / this.dimentsionOfValues;
	}

	public List<Individual> run() {
		firstGenerationFunction();
		for (int i = 0; i < noOfGenerations; i++) {
			advanceGeneration();
		}
		return populationP;
	}

	public static void printFrontIntoFile(List<Individual> population)
			throws IOException {

		File fout = new File("paretoOptimal.txt");
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (Individual ind : population) {
			for (Double value : ind.values) {
				bw.write(value + " ");
			}
			bw.write("-> ");
			for (Fitness fitness : ind.results) {
				bw.write(fitness.value + " ");
			}
			bw.newLine();
		}

		bw.close();
	}

	public static Double calcualteHypervolume(List<Individual> front, Integer M) {
		int frontSize = front.size();

		double[][] frontArray = new double[frontSize][M];
		for (int i = 0; i < frontSize; i++) {
			Individual ind = front.get(i);
			for (int j = 0; j < M; j++) {
				frontArray[i][j] = ind.results.get(j).value;
			}
		}
		Hypervolume hv = new Hypervolume();

		return hv.calculateHypervolume(frontArray, frontSize, M);
	}

	public boolean allPositive(List<Individual> list) {
		for (Individual ind : list) {
			for (int i = 0; i < this.dimentsionOfResult; i++) {
				if (ind.results.get(i).value < 0) {
					return false;
				}
			}

		}
		return true;
	}

	private void advanceGeneration() {
		fronts.clear();
		rPopulationMakeup();
		clearData();
		nonDominatedSortingFast();

		BufferedWriter bw = null;
		if (allPositive(fronts.get(1))) {
			try {
				// APPEND MODE SET HERE
				bw = new BufferedWriter(new FileWriter(
						"C:\\Users\\leonx64\\Desktop\\rezultati\\NSGA2\\konv"
								+ this.evaluateOp.getClass().hashCode() + "_"
								+ this.dimentsionOfResult + "_re2.txt", true));

			} catch (IOException ioe) {
			} // always close the file
			try {

				bw.write(Double.toString(calcualteHypervolume(fronts.get(1),
						this.dimentsionOfResult) / Math.pow(10.0, 6.0)));
				bw.newLine();
				bw.flush();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally { // always close the file
				if (bw != null)
					try {
						bw.close();
					} catch (IOException ioe2) {
						// just ignore it
					}
			}

		}

		newGenerationPopulationMakeUp();
		qPopulationMakeUp();
	}

	private void clearData() {
		for (Individual ind : populationR) {
			ind.dominates = new ArrayList<Individual>();
			ind.ni = 0;
			ind.rank = 0;
			ind.crowdingDistance = 0.0;
		}
		populationP.clear();
		populationQ.clear();
	}

	private void firstGenerationFunction() {
		for (int i = 0; i < populationSize; i++) {
			List<Double> genotype = new ArrayList<Double>();
			for (int j = 0; j < dimentsionOfValues; j++) {
				Double value = lBound + rand.nextDouble() * (uBound - lBound);
				genotype.add(value);
			}
			Individual ind = new Individual(genotype);
			ind.results = evaluateOp.evaluate(ind.values);
			populationP.add(ind);
		}
		qPopulationMakeUp();
	}

	private Boolean firstIsDominatingSecond(List<Fitness> results1,
			List<Fitness> results2) {
		for (int i = 0; i < dimentsionOfResult; i++) {
			if (results2.get(i).isBetterThan(results1.get(i))) {
				return false;
			}
		}
		for (int i = 0; i < dimentsionOfResult; i++) {
			if (results1.get(i).isBetterThan(results2.get(i))) {
				return true;
			}
		}

		return false;
	}

	private void nonDominatedSortingFast() {
		List<Individual> populationCopy = new ArrayList<Individual>(populationR);
		for (int i = 0; i < (2 * populationSize); i++) {
			for (int j = 0; j < (2 * populationSize); j++) {
				if (j == i)
					continue;
				Individual unit1 = populationCopy.get(i);
				Individual unit2 = populationCopy.get(j);
				if (firstIsDominatingSecond(unit1.results, unit2.results)) {
					unit1.dominates.add(unit2);
				}
				if (firstIsDominatingSecond(unit2.results, unit1.results)) {
					unit1.ni++;
				}
			}
		}
		Integer noOfFront = 1;
		while (true) {
			List<Individual> front = new ArrayList<Individual>();
			for (Individual unit : populationCopy) {
				if (unit.ni == 0) {
					unit.rank = noOfFront;
					front.add(unit);
				}
			}
			if (front.size() > 0) {
				for (Individual unit : front) {
					for (Individual unit1 : unit.dominates) {
						unit1.ni--;
					}
				}
				fronts.put(noOfFront, front);
				populationCopy.removeAll(front);
				noOfFront++;
			} else {
				break;
			}
		}
	}

	private void newGenerationPopulationMakeUp() {
		// crowding selection scheme
		Integer noOfFront = 1;
		while (true) {
			List<Individual> front = fronts.get(noOfFront);
			for (int i = 0; i < dimentsionOfResult; i++) {
				final Integer iFinal = new Integer(i);
				Collections.sort(front, new Comparator<Individual>() {
					@Override
					public int compare(Individual o1, Individual o2) {
						return o1.results.get(iFinal).value
								.compareTo(o2.results.get(iFinal).value);
					}
				});
				Integer frontSize = front.size();
				Double fe1 = front.get(0).results.get(i).value;
				Double fe2 = front.get(frontSize - 1).results.get(i).value;
				for (int j = 0; j < frontSize; j++) {
					Individual ind = front.get(j);
					if (j == 0 || j == frontSize - 1) {
						ind.crowdingDistance = Double.MAX_VALUE;
					} else {
						Individual beforeInd = front.get(j - 1);
						Individual afterInd = front.get(j + 1);
						if (!ind.crowdingDistance.equals(Double.MAX_VALUE)) {
							ind.crowdingDistance += (Math.abs(afterInd.results
									.get(i).value
									- beforeInd.results.get(i).value))
									/ (Math.abs(fe2 - fe1));
						}
					}
				}
			}

			if (populationP.size() + front.size() >= populationSize) {
				break;
			} else {
				// at the same time adds to populationP and gives crowding
				// distance
				populationP.addAll(front);
				noOfFront++;
			}
		}
		Integer rest = populationSize - populationP.size();
		if (rest == 0)
			return;
		List<Individual> crowdingFront = fronts.get(noOfFront);
		Collections.sort(crowdingFront, new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				return Double.compare(o2.crowdingDistance, o1.crowdingDistance);
			}
		});
		for (int i = 0; i < rest; i++) {
			populationP.add(crowdingFront.get(i));
		}
	}

	private void qPopulationMakeUp() {
		// selection,mutation,crossover
		Integer liveParentsAfterSelection = (int) (liveParentsAfterSelectionPercentage * populationSize);
		Integer numberOfMutated = (int) (numberOfMutatedPercentage * populationSize);
		List<Individual> tournamentList = new ArrayList<Individual>();
		List<Individual> winnerList = new ArrayList<Individual>();
		for (int i = 0; i < liveParentsAfterSelection; i++) {
			tournamentList.clear();
			for (int k = 0; k < tournamentSize; k++) {
				tournamentList
						.add(populationP.get(rand.nextInt(populationSize)));
			}
			Individual winner = tournamentList.get(0);
			for (int j = 1; j < tournamentSize; j++) {
				Individual competitor = tournamentList.get(j);
				if (competitor.rank > winner.rank) {
					winner = competitor;
				} else if (competitor.rank == winner.rank
						&& competitor.crowdingDistance > winner.crowdingDistance) {
					winner = competitor;
				}
			}
			Individual winnerCopy = Individual.copy(winner);
			winnerList.add(winnerCopy);
			populationQ.add(winnerCopy);
		}

		Integer numberOfCreatedChildren = populationSize
				- liveParentsAfterSelection;
		List<Individual> childrenPopulation = new ArrayList<Individual>();
		for (int i = 0; i < numberOfCreatedChildren; i++) {
			Individual parent1 = populationQ.get(rand.nextInt(populationQ
					.size()));
			Individual parent2 = populationQ.get(rand.nextInt(populationQ
					.size()));
			Individual child = new Individual(crossover(parent1.values,
					parent2.values));
			child.results = evaluateOp.evaluate(child.values);
			childrenPopulation.add(child);
		}
		populationQ.addAll(childrenPopulation);
		// for(int i=0;i<numberOfMutated;i++){
		// Individual ind = populationQ.get(rand.nextInt(populationSize));
		// ind.values = mutate(ind.values);
		// ind.results = evaluateOp.evaluate(ind.values);
		// }
		// mutate only winners, they are duplicates
		for (Individual ind : winnerList) {
			ind.values = mutate(ind.values);
			ind.results = evaluateOp.evaluate(ind.values);
		}

	}

	private void rPopulationMakeup() {
		populationR.clear();
		populationR.addAll(populationP);
		populationR.addAll(populationQ);
	}

	private List<Double> crossover(List<Double> inputList1,
			List<Double> inputList2) {
		List<Double> crossoverInputList = new ArrayList<Double>();
		Integer size = inputList1.size();
		for (int i = 0; i < size; i++) {
			Double greater;
			Double lower;
			Double arg1 = inputList1.get(i);
			Double arg2 = inputList2.get(i);
			if (arg1 > arg2) {
				greater = arg1;
				lower = arg2;
			} else {
				greater = arg2;
				lower = arg1;
			}
			Double result = lower + rand.nextDouble() * (greater - lower);
			crossoverInputList.add(result);
		}
		return crossoverInputList;
	}

	private List<Double> mutate(List<Double> inputList) {
		List<Double> mutatedInputList = new ArrayList<Double>();
		for (Double input : inputList) {

			Integer mutPow = rand.nextInt(60) - 30;
			Double delta = 0.0;
			if (mutPow >= 0) {
				delta = (((double) mutPow) / 100.0) * (uBound - input);
			} else {
				delta = (((double) mutPow) / 100.0) * (input - lBound);
			}
			Double mutatedInput = input + delta;
			mutatedInputList.add(mutatedInput);
		}
		return mutatedInputList;
	}
}
