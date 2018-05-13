package genPaket;

import static genPaket.Earth.*;

public class Main {
	public static Animal bestAnimal;
	public static Boolean firstIter = true;

	public static void main(String[] args) {

		for (int year = 1; year <= numberOfYears; year++) {
			numberOfMutInYear=0;
			for (int day = 1; day <= yearLength; day++) {

				for (Animal animal : animals) {

					calculateAnimalPerception(animal);
					Integer genomActivation = genomFunction
							.get(new GenomPartOfFunction(animal.state,
									animal.whenLastEaten, animal.seesPlant,
									animal.seesAnimal));
					ActionNewState actionNewState = animal.chromosome
							.get(genomActivation);
					performAnimalAction(animal, actionNewState);
					eatPlantWhereStanding(animal);
				}
				if(year == 1 || year == (numberOfYears * 3) / 4){
					printMapForAllAnimals();
					System.out.println();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}

			if (year == numberOfYears) {
				bestAnimal = getBestAnimal();
			}

			System.out.println("avg: "+calculateAverageEaten()+ ", numOfYear: "+ (year-1));
			printBestAndWorstAnimal();
			selection();
			crossovering();
			mutation();
			newYearFunction();
			System.out.println("nmbrOfMut: "+numberOfMutInYear);
			System.out.println();
		}
	// simulateOneAnimal(bestAnimal, 500L);
	}

}
