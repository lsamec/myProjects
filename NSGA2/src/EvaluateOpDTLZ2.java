import java.util.ArrayList;
import java.util.List;


public class EvaluateOpDTLZ2 implements EvaluateOp {
	
	Integer M; // no of objectives / no of position parameters + 1 (yi)
	Integer k; // no of distance parameters (zi)
	
	
	public EvaluateOpDTLZ2(Integer M, Integer k) {
		this.M =M;
		this.k = k;
	}
	
	@Override
	public List<Fitness> evaluate(List<Double> values) {
		List<Fitness> codomain = new ArrayList<Fitness>();
		
		List<Double> yiList = new ArrayList<Double>();
		List<Double> ziList = new ArrayList<Double>();
		for(int i=0;i<M-1;i++){
			yiList.add(values.get(i));
		}
		for(int i=M-1;i<M-1+k;i++){
			ziList.add(values.get(i));
		}
		
		Double g = 0.0;
		for(int i=0;i<k;i++){
			Double zi = ziList.get(i);
			g+= (zi-0.5)*(zi-0.5);
		}
	
		Fitness f1 = new FitnessMax();
		f1.value = 1.0;
		for(int i=0;i<M-1;i++){
			Double yi = yiList.get(i);
			f1.value*=Math.cos(yi*(Math.PI/2));;
		}
		f1.value =- (1+g)*f1.value + 10;
		
		codomain.add(f1);
		
		for(int m=2;m<=M-1;m++){
			Fitness fm = new FitnessMax();
			fm.value = 1.0;
			for(int i=0;i<M-m;i++){
				Double yi = yiList.get(i);
				fm.value*=Math.cos(yi*(Math.PI/2));
			}
			fm.value =- (1+g)*(Math.sin(yiList.get(M-m+1-1)*(Math.PI/2)))*fm.value + 10;
			codomain.add(fm);
		}
		
		Fitness fM = new FitnessMax();
		fM.value =- (1+g)*(Math.sin(yiList.get(0)*(Math.PI/2))) + 10;
		codomain.add(fM);
		
		return codomain;
	}

}
