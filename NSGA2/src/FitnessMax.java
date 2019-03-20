
public class FitnessMax extends Fitness{

	@Override
	public Boolean isBetterThan(Fitness other) {
		if (this.value > other.value){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		return "FitnessMax [value=" + value + "]";
	}

}
