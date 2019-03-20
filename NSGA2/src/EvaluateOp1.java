import java.util.ArrayList;
import java.util.List;


public class EvaluateOp1 implements EvaluateOp {

	@Override
	public List<Fitness> evaluate(List<Double> values) {
		
		List<Fitness> codomain = new ArrayList<Fitness>();
		
		Double value1 = values.get(0);
		
		Fitness function1 = new FitnessMax();
		function1.value = - (Math.pow(value1 + 3.0, 2) - 10);
		Fitness function2 = new FitnessMax();
		function2.value = - (Math.pow(value1 - 3.0, 2) + 10);
		
		codomain.add(function1);
		codomain.add(function2);
		
		return codomain;
	}

}
