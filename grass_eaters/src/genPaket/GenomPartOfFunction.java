package genPaket;

public class GenomPartOfFunction {
	Integer state;
	Integer whenLastEaten;
	Integer seesPlant;
	Integer seesAnimal;
	public GenomPartOfFunction(Integer state, Integer whenLastEaten,
			Integer seesPlant, Integer seesAnimal) {
		super();
		this.state = state;
		this.whenLastEaten = whenLastEaten;
		this.seesPlant = seesPlant;
		this.seesAnimal = seesAnimal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((seesAnimal == null) ? 0 : seesAnimal.hashCode());
		result = prime * result
				+ ((seesPlant == null) ? 0 : seesPlant.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((whenLastEaten == null) ? 0 : whenLastEaten.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenomPartOfFunction other = (GenomPartOfFunction) obj;
		if (seesAnimal == null) {
			if (other.seesAnimal != null)
				return false;
		} else if (!seesAnimal.equals(other.seesAnimal))
			return false;
		if (seesPlant == null) {
			if (other.seesPlant != null)
				return false;
		} else if (!seesPlant.equals(other.seesPlant))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (whenLastEaten == null) {
			if (other.whenLastEaten != null)
				return false;
		} else if (!whenLastEaten.equals(other.whenLastEaten))
			return false;
		return true;
	}
	
}
