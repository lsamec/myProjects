package genPaket;
import static genPaket.Earth.lastEatenHighestMeasure;
import static genPaket.Earth.numberOfStates;

import java.util.List;
import java.util.Random;



public class Animal {
	public List<ActionNewState> chromosome;
	public Integer orientation;
	public Integer state;
	public Integer initialState;
	public Point position;
	public Integer whenLastEaten;
	public Integer seesPlant;
	public Integer seesAnimal;
	public Integer eaten;
	Animal(){
		Random rand = new Random();
		this.orientation=rand.nextInt(4)+1;
		this.state=rand.nextInt(numberOfStates)+1;
		this.initialState=this.state;
		this.whenLastEaten= lastEatenHighestMeasure;
		this.eaten=0;
		this.seesAnimal=0;
		this.seesPlant=0;
	}
	Animal(List<ActionNewState> chromosome){
		this();
		this.chromosome=chromosome;
	}
	@Override
	public String toString() {
		return "Animal [eaten=" + eaten + "]";
	}

	public String toStringWithStates() {
		return "Animal [whenLastEaten="
				+ whenLastEaten + ", seesPlant=" + seesPlant + ", seesAnimal="
				+ seesAnimal + ", state=" + state+ ", eaten=" + eaten + "]";
	}
}
	