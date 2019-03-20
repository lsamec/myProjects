package animal;

public class Main {
	public static void main(String[] args) {
		Animal animal1 = AnimalFactory.newInstance("Parrot", "Stef");
		Animal animal2 = AnimalFactory.newInstance("Tiger", "Joza");
		
		animal1.animalPrintGreeting();
		animal2.animalPrintGreeting();
		
		animal1.animalPrintMenu();
		animal2.animalPrintMenu();

	}
}
