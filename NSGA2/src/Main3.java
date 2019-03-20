import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main3 {
	public static void main(String[] args) {
		Integer k = 3;
		Integer M = 7;
		AlgNSGA2 algNSGA2 = new AlgNSGA2(0.0, 1.0, M - 1 + k, M, 150,getPartitonWhenNoOfPopulationOverN(100,M).getElement1(),
				new EvaluateOpDTLZ2(M, k));
		List<Individual> firstFront = algNSGA2.run();
		/*
		for(Individual ind : firstFront){
			System.out.println(ind);
		}
		*/
		System.out.println(AlgNSGA2.calcualteHypervolume(firstFront, M));

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
