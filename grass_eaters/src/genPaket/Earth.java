package genPaket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Earth {
	public static Integer animalNumber = 20;

	// for dist of plants with attraction
	public static Integer biggestPlantSquare = 7;

	public static Integer percentageOfMapCoveredWithPlants = 50;
	public static Integer width = 30;
	public static Integer height = 30;
	public static Integer numberOfStates = 15;
	public static Integer lastEatenHighestMeasure = 12;
	public static Integer yearLength = 200;
	public static Integer numberOfYears = 200;
	public static Integer probabilityForEachAnimalToMutateInEveryYearInPromil = 50;
	public static Integer intensityOfMutationCalcInPercentageOfRandomMutatedPartsOfChromosome = 20;
	public static Set<Point> plants = new HashSet<>();
	public static List<Animal> animals = new ArrayList<>();
	public static Map<GenomPartOfFunction, Integer> genomFunction = new HashMap<GenomPartOfFunction, Integer>();
	public static Integer seesBooleanSet = 2;
	public static Integer numberOfFunctionParts = numberOfStates
			* lastEatenHighestMeasure * seesBooleanSet * seesBooleanSet;
	public static Integer plantNumber = Math
			.round((((float) percentageOfMapCoveredWithPlants) / 100)
					* (width * height));
	public static Random rand = new Random();
	public static Integer numberOfMutInYear = 0;

	static {
		generateGenomFuncion();
		//removeOldPlantsMakeNewPlantsUniform();
		removeOldPlantsMakeNewPlantsWithAttraction();
		makeInitialAnimals();
		calculateAllAnimalPerception();
	}

	public static void calculateAnimalPerception(Animal animal) {
		Point pos = animal.position;
		switch (animal.orientation) {
		case 1:
			if (plants
					.contains(transformCoordinates(new Point(pos.x, pos.y - 1)))) {
				animal.seesPlant = 1;
			} else {
				animal.seesPlant = 0;
			}
			if (animals.contains(transformCoordinates(new Point(pos.x,
					pos.y - 1)))) {
				animal.seesAnimal = 1;
			} else {
				animal.seesAnimal = 0;
			}
			break;
		case 2:
			if (plants
					.contains(transformCoordinates(new Point(pos.x + 1, pos.y)))) {
				animal.seesPlant = 1;
			} else {
				animal.seesPlant = 0;
			}
			if (animals.contains(new Point(pos.x + 1, pos.y))) {
				animal.seesAnimal = 1;
			} else {
				animal.seesAnimal = 0;
			}
			break;
		case 3:
			if (plants
					.contains(transformCoordinates(new Point(pos.x, pos.y + 1)))) {
				animal.seesPlant = 1;
			} else {
				animal.seesPlant = 0;
			}
			if (animals.contains(transformCoordinates(new Point(pos.x,
					pos.y + 1)))) {
				animal.seesAnimal = 1;
			} else {
				animal.seesAnimal = 0;
			}
			break;
		case 4:
			if (plants
					.contains(transformCoordinates(new Point(pos.x - 1, pos.y)))) {
				animal.seesPlant = 1;
			} else {
				animal.seesPlant = 0;
			}
			if (animals.contains(transformCoordinates(new Point(pos.x - 1,
					pos.y)))) {
				animal.seesAnimal = 1;
			} else {
				animal.seesAnimal = 0;
			}
			break;
		}
	}

	public static void calculateAllAnimalPerception() {
		for (Animal animal : animals) {
			calculateAnimalPerception(animal);
		}
	}

	public static void generateGenomFuncion() {
		Integer partPos = 0;
		for (int i = 1; i <= numberOfStates; i++) {
			for (int j = 1; j <= lastEatenHighestMeasure; j++) {
				for (int k = 0; k < seesBooleanSet; k++) {
					for (int l = 0; l < seesBooleanSet; l++) {

						genomFunction.put(new GenomPartOfFunction(i, j, k, l),
								partPos);
						partPos++;
					}
				}

			}
		}
	}

	public static void makeInitialAnimals() {
		for (int i = 0; i < animalNumber; i++) {
			Animal animal = new Animal();
			List<ActionNewState> chromosome = new ArrayList<ActionNewState>();
			for (int j = 0; j < numberOfFunctionParts; j++) {
				chromosome.add(new ActionNewState(
						actionFromInt(rand.nextInt(3) + 1), rand
								.nextInt(numberOfStates) + 1));
			}
			animal.chromosome = chromosome;
			Point pos = null;
			do {
				pos = new Point(rand.nextInt(width + 1),
						rand.nextInt(height + 1));
			} while (plants.contains(pos) || animals.contains(pos));
			animal.position = pos;
			animals.add(animal);
		}
	}

	public static void removeOldPlantsMakeNewPlantsUniform() {
		plants.clear();
		for (int i = 0; i < plantNumber; i++) {
			Point pos = null;
			do {
				pos = new Point(rand.nextInt(width + 1),
						rand.nextInt(height + 1));
			} while (plants.contains(pos));
			plants.add(pos);
		}
	}

	public static void removeOldPlantsMakeNewPlantsWithAttraction() {
		plants.clear();
		boolean reachedPlantNumber = false;
		while (true) {
			Point pos = null;
			do {
				pos = new Point(rand.nextInt(width + 1),
						rand.nextInt(height + 1));
			} while (plants.contains(pos));
			Integer bushSize = rand.nextInt(biggestPlantSquare) + 1;
			for (int j = 0; j < bushSize; j++) {
				for (int k = 0; k < bushSize; k++) {
					if (plants.size() == plantNumber) {
						reachedPlantNumber = true;
						break;
					}
					plants.add(transformCoordinates(new Point(pos.x + j, pos.y
							+ k)));
				}
				if (reachedPlantNumber)
					break;
			}
			if (reachedPlantNumber)
				break;
		}
	}

	public static String actionFromInt(Integer num) {
		String action = null;
		switch (num) {
		case 1:
			action = "L";
			break;
		case 2:
			action = "D";
			break;
		case 3:
			action = "N";
			break;
		default:
			throw new IllegalStateException();

		}
		return action;
	}

	public static void performAnimalAction(Animal animal,
			ActionNewState actionNewState) {
		Point pos = animal.position;
		switch (actionNewState.action) {
		case "L":
			switch (animal.orientation) {
			case 1:
				animal.orientation = 4;
				break;
			case 2:
				animal.orientation = 1;
				break;
			case 3:
				animal.orientation = 2;
				break;
			case 4:
				animal.orientation = 3;
				break;
			}
			break;
		case "D":
			switch (animal.orientation) {
			case 1:
				animal.orientation = 2;
				break;
			case 2:
				animal.orientation = 3;
				break;
			case 3:
				animal.orientation = 4;
				break;
			case 4:
				animal.orientation = 1;
				break;
			}
			break;
		case "N":
			boolean standStill = false;
			switch (animal.orientation) {
			case 1:
				if (animals.contains(transformCoordinates(new Point(pos.x,
						pos.y - 1)))) {
					standStill = true;
				}
				break;
			case 2:
				if (animals.contains(transformCoordinates(new Point(pos.x + 1,
						pos.y)))) {
					standStill = true;
				}
				break;
			case 3:
				if (animals.contains(transformCoordinates(new Point(pos.x,
						pos.y + 1)))) {
					standStill = true;
				}
				break;
			case 4:
				if (animals.contains(transformCoordinates(new Point(pos.x - 1,
						pos.y)))) {
					standStill = true;
				}
				break;
			}

			if (!standStill) {
				switch (animal.orientation) {
				case 1:
					animal.position = transformCoordinates(new Point(pos.x,
							pos.y - 1));
					break;
				case 2:
					animal.position = transformCoordinates(new Point(pos.x + 1,
							pos.y));
					break;
				case 3:
					animal.position = transformCoordinates(new Point(pos.x,
							pos.y + 1));
					break;
				case 4:
					animal.position = transformCoordinates(new Point(pos.x - 1,
							pos.y));
					break;
				}
			}
			break;
		}
		animal.state = actionNewState.newState;
	}

	public static void eatPlantWhereStanding(Animal animal) {
		if (plants.contains(animal.position)) {
			animal.eaten++;
			animal.whenLastEaten = 1;
			plants.remove(animal.position);
		} else {
			animal.whenLastEaten++;
			if (animal.whenLastEaten > lastEatenHighestMeasure) {
				animal.whenLastEaten = lastEatenHighestMeasure;
			}
		}
	}

	public static void selection() {
		Collections.sort(animals, new Comparator<Animal>() {
			@Override
			public int compare(Animal animal1, Animal animal2) {

				return animal1.eaten.compareTo(animal2.eaten);
			}
		});
		Iterator<Animal> it = animals.iterator();
		Integer size = animals.size();
		Integer counter = 0;
		while (it.hasNext()) {
			it.next();
			it.remove();
			counter++;
			if (counter.equals(size / 2)) {
				break;
			}
		}
	}

	public static void crossovering() {
		List<Animal> newAnimals = new ArrayList<Animal>();
		while (animals.size() + newAnimals.size() < animalNumber) {
			Animal animal1 = animals.remove(rand.nextInt(animals.size()));
			Animal animal2 = animals.remove(rand.nextInt(animals.size()));
			animals.add(animal1);
			animals.add(animal2);
			List<ActionNewState> newMixedChromosome = new LinkedList<ActionNewState>();
			List<Integer> listOfPartIds = new ArrayList<Integer>();
			Map<Integer, ActionNewState> mapOfParts = new HashMap<Integer, ActionNewState>();
			for (int i = 0; i < numberOfFunctionParts; i++) {
				listOfPartIds.add(i);
			}
			for (int i = 1; i <= numberOfFunctionParts / 2; i++) {
				Integer id = listOfPartIds.remove(rand.nextInt(listOfPartIds
						.size()));
				mapOfParts.put(id, animal1.chromosome.get(id));
			}
			for (int i = numberOfFunctionParts / 2 + 1; i <= numberOfFunctionParts; i++) {
				Integer id = listOfPartIds.remove(rand.nextInt(listOfPartIds
						.size()));
				mapOfParts.put(id, animal2.chromosome.get(id));
			}
			for (int i = 0; i < numberOfFunctionParts; i++) {
				newMixedChromosome.add(mapOfParts.get(i));
			}
			Animal newAnimal = new Animal(newMixedChromosome);
			newAnimal.initialState = animal1.initialState;
			newAnimals.add(newAnimal);
		}
		animals.addAll(newAnimals);
	}

	public static void mutation() {
		Integer numberOfMutatedParts = Math
				.round((((float) intensityOfMutationCalcInPercentageOfRandomMutatedPartsOfChromosome) / 100))
				* numberOfFunctionParts;
		for (Animal animal : animals) {
			if (rand.nextInt(1000) + 1 <= probabilityForEachAnimalToMutateInEveryYearInPromil) {
				numberOfMutInYear++;
				for (int i = 0; i < numberOfMutatedParts; i++) {
					ActionNewState actionNewState = animal.chromosome.get(rand
							.nextInt(numberOfFunctionParts));
					actionNewState.action = actionFromInt(rand.nextInt(3) + 1);
					actionNewState.newState = rand.nextInt(numberOfStates) + 1;
				}
			}
		}
	}

	public static void newYearFunction() {
		//removeOldPlantsMakeNewPlantsUniform();
		removeOldPlantsMakeNewPlantsWithAttraction();
		 newYearForAnimals();
	}

	public static void newYearForAnimals() {
		for (Animal animal : animals) {
			animal.state = animal.initialState;
			animal.eaten = 0;
			animal.whenLastEaten = lastEatenHighestMeasure;
			animal.orientation = rand.nextInt(4) + 1;
			animal.seesAnimal = 0;
			animal.seesPlant = 0;
			Point pos = null;
			do {
				pos = new Point(rand.nextInt(width + 1),
						rand.nextInt(height + 1));
			} while (plants.contains(pos) || animals.contains(pos));
			animal.position = pos;
		}
	}

	public static Double calculateAverageEaten() {
		Double average = 0.0;
		for (Animal animal : animals) {
			average += Double.parseDouble(animal.eaten.toString());
		}
		average = average / animalNumber;
		return average;
	}

	public static void printBestAndWorstAnimal() {
		Collections.sort(animals, new Comparator<Animal>() {
			@Override
			public int compare(Animal animal1, Animal animal2) {

				return animal1.eaten.compareTo(animal2.eaten);
			}
		});
		System.out.println("bestAnimal: "
				+ animals.get(animals.size() - 1).eaten);
		System.out.println("worstAnmimal: " + animals.get(0).eaten);
	}

	public static void printMapForAllAnimals() {
		for (int y = 0; y <= height; y++) {
			for (int x = 0; x <= width; x++) {
				Point point = new Point(x, y);
				if (plants.contains(point)) {
					System.out.print("*");
				} else {
					Boolean written = false;
					for (Animal animal : animals) {
						if (animal.position.equals(point)) {
							switch (animal.orientation) {
							case 1:
								System.out.print("^");
								written = true;
								break;
							case 2:
								System.out.print(">");
								written = true;
								break;
							case 3:
								System.out.print("v");
								written = true;
								break;
							case 4:
								System.out.print("<");
								written = true;
								break;
							}
						}
					}
					if (written.equals(false)) {
						System.out.print("-");
					}
				}

			}
			System.out.println();
		}
	}

	public static void printMapForOneAnimal(Animal animal) {
		for (int y = 0; y <= height; y++) {
			for (int x = 0; x <= width; x++) {
				Point point = new Point(x, y);
				if (plants.contains(point)) {
					System.out.print("*");
				} else {
					Boolean written = false;
					if (animal.position.equals(point)) {
						switch (animal.orientation) {
						case 1:
							System.out.print("^");
							written = true;
							break;
						case 2:
							System.out.print(">");
							written = true;
							break;
						case 3:
							System.out.print("v");
							written = true;
							break;
						case 4:
							System.out.print("<");
							written = true;
							break;
						}
					}
					if (written.equals(false)) {
						System.out.print("-");
					}
				}

			}
			if (y == 0) {
				System.out.print("     " + animal.toStringWithStates());
			}
			System.out.println();
		}
	}

	public static Animal getBestAnimal() {
		Collections.sort(animals, new Comparator<Animal>() {
			@Override
			public int compare(Animal animal1, Animal animal2) {

				return animal1.eaten.compareTo(animal2.eaten);
			}
		});
		return animals.get(animals.size() - 1);
	}

	public static void simulateOneAnimal(Animal animal, Long waitInMS) {
		 removeOldPlantsMakeNewPlantsWithAttraction();
		Point pos = null;
		do {
			pos = new Point(rand.nextInt(width + 1), rand.nextInt(height + 1));
		} while (plants.contains(pos));
		animal.position = pos;

		for (int day = 1; day <= yearLength; day++) {

			calculateAnimalPerception(animal);
			Integer genomActivation = genomFunction
					.get(new GenomPartOfFunction(animal.state,
							animal.whenLastEaten, animal.seesPlant,
							animal.seesAnimal));
			ActionNewState actionNewState = animal.chromosome
					.get(genomActivation);
			printMapForOneAnimal(animal);
			performAnimalAction(animal, actionNewState);
			eatPlantWhereStanding(animal);
			System.out.println();
			try {
				Thread.sleep(waitInMS);
			} catch (InterruptedException e) {
			}
		}
	}

	public static Point transformCoordinates(Point point) {
		Point newPos = point;
		if (point.x > width) {
			newPos.x = point.x - width - 1;
		}
		if (point.x < 0) {
			newPos.x = point.x + width + 1;
		}
		if (point.y > height) {
			newPos.y = point.y - height - 1;
		}
		if (point.y < 0) {
			newPos.y = point.y + height + 1;
		}
		return newPos;
	}
}
