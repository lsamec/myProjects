import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Individual {
	
	Long id;
	List<Double> values;
	List<Fitness> results;
	List<Individual> dominates;
	Integer ni;
	Integer rank;
	Double crowdingDistance;
	private static Random rand = new Random();
	
	public Individual(List<Double> values) {
		super();
		this.id = rand.nextLong();
		this.values = values;
		this.dominates = new ArrayList<Individual>();
		this.ni = 0;
		this.rank = 0;
		this.crowdingDistance = 0.0;
	}
	
	public static Individual copy(Individual ind){
		Individual newInd = new Individual(ind.values);
		newInd.results = new ArrayList<Fitness>(ind.results);
		return newInd;
	}
	
	public void reset(Individual ind){
		this.dominates = new ArrayList<Individual>();
		this.ni = 0;
		this.rank = 0;
		this.crowdingDistance = 0.0;
	}

	@Override
	public String toString() {
		return "Individual [values=" + values + ", results=" + results + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		Individual other = (Individual) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}
	
}
