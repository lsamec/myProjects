
import java.util.List;


public class Main2 {
	
	public static void main(String[] args) {
		AlgNSGA2 algNSGA2 = new AlgNSGA2(0.0, 1.0,1, 2,300, 50,
				new EvaluateOp1());
		List<Individual> archive = algNSGA2.run();
		try{
			AlgNSGA2.printFrontIntoFile(archive);
		}catch(Exception e){
			
		}

	}
	
}

