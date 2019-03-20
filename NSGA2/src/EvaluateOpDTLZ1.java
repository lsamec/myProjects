import java.util.ArrayList;
import java.util.List;


public class EvaluateOpDTLZ1 implements EvaluateOp {
	
	Integer M; // no of objectives / no of position parameters + 1 (yi)
	Integer k; // no of distance parameters (zi)
	
	
	public EvaluateOpDTLZ1(Integer M, Integer k) {
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
			g+= (zi-0.5)*(zi-0.5)-Math.cos(20*Math.PI*(zi-0.5));
		}
		g+=k;
		g =100*g;
		
		Fitness f1 = new FitnessMax();
		f1.value = 1.0;
		for(int i=0;i<M-1;i++){
			Double yi = yiList.get(i);
			f1.value*=yi;
		}
		f1.value =- (1+g)*0.5*f1.value + 500;
		
		codomain.add(f1);
		
		for(int m=2;m<=M-1;m++){
			Fitness fm = new FitnessMax();
			fm.value = 1.0;
			for(int i=0;i<M-m;i++){
				Double yi = yiList.get(i);
				fm.value*=yi;
			}
			fm.value =- (1+g)*0.5*(1-yiList.get(M-m+1-1))*fm.value + 500;
			codomain.add(fm);
		}
		
		Fitness fM = new FitnessMax();
		fM.value =- (1+g)*0.5*(1-yiList.get(0)) + 500;
		codomain.add(fM);
		
		return codomain;
	}

}
