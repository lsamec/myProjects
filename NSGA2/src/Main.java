import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class Main {
	public static void main(String[] args) {
		System.out.println("NSGA2");
		List<Integer> Ms = new ArrayList<Integer>();
		Ms.add(2);
		Ms.add(3);
		Ms.add(5);
		Ms.add(7);
		Ms.add(9);
		Integer k = 3;
		Integer NRuns = 10;
		Map<Integer,Double> MwithMinHV = new HashMap<Integer,Double>(); 
		for (Integer M : Ms) {
			List<Double> hvInRuns = new ArrayList<Double>();
			for(int i=0;i<NRuns;i++){
				AlgNSGA2 algNSGA2 = new AlgNSGA2(0.0, 1.0, M - 1 + k, M, 300, getPartitonWhenNoOfPopulationOverN(50, M).getElement1(),
						new EvaluateOpDTLZ1(M, k));
				List<Individual> firstFront = algNSGA2.run();
				hvInRuns.add(AlgNSGA2.calcualteHypervolume(firstFront, M));
			}
			Double min = Collections.min(hvInRuns);
			MwithMinHV.put(M,min);
			System.out.println(M+":  "+min);
		}		
		System.out.println("\nNoOfObjectives:  minHvInNRuns");
		for(Entry<Integer,Double> entry : MwithMinHV.entrySet()){
			System.out.println(entry.getKey()+":  "+entry.getValue());
		}
		
	}
	//Pair<p,n>; might return lower if integer overflows; purpose-equalising NSGA-2 and NSGA-3 population size
	public static Pair<Integer,Integer> getPartitonWhenNoOfPopulationOverN(Integer N,Integer M){
		Integer n=0;
		Integer p=0;
		while(n<N){
			p++;
			Integer temp =(factoriel(M + p - 1)) / (factoriel(p) * factoriel(M - 1));
			if(temp < n){
				p--;
				break;
			}
			n=temp;
			n= getFirstNumThatIsMultipleOfFour(n);		
		}		
		return new Pair<Integer,Integer>(p,n);
	}
	
	public static int factoriel(int nmbr) {
		int factoriel = 1;
		for (int i = 1; i <= nmbr; i++) {
			factoriel *= i;
		}
		return factoriel;
	}
	
	public static Integer getFirstNumThatIsMultipleOfFour(Integer num){
		if(num%4==0)return num;
		if((num+1)%4==0) return num+1;
		if((num+2)%4==0) return num+2;
		if((num+3)%4==0) return num+3;
		return -1;
	}
}