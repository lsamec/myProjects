package plugins;

import animal.Animal;

public class Tiger extends Animal {

	private String animalName;

	public Tiger(String name) {
		this.animalName = name;
	}

	@Override
	public String name() {
		return this.animalName;
	}

	@Override
	public String greet() {
		return "Mijau!";
	}

	@Override
	public String menu() {
		return "mlako mlijeko";
	}

}
