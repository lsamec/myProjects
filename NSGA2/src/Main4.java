import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main4 {

	public static void main(String[] args) {
		List<KritGenPopOp> argsOpt = new ArrayList<KritGenPopOp>();
		/*
		argsOpt.add(new KritGenPopOp(2, 250, 100, new EvaluateOpDTLZ1(2, 3),
				Math.pow(10.0, 5.0)));
		argsOpt.add(new KritGenPopOp(3, 350, 200, new EvaluateOpDTLZ1(3, 3),
				Math.pow(10.0, 8.0)));
		argsOpt.add(new KritGenPopOp(5, 220, 250, new EvaluateOpDTLZ1(5, 3),
				Math.pow(10.0, 13.0)));
		
		argsOpt.add(new KritGenPopOp(7, 150, 100, new EvaluateOpDTLZ1(7, 3),
				Math.pow(10.0, 18.0)));
*/
		argsOpt.add(new KritGenPopOp(2, 250, 100, new EvaluateOpDTLZ2(2, 3),
				Math.pow(10.0, 1.0)));
		argsOpt.add(new KritGenPopOp(3, 350, 200, new EvaluateOpDTLZ2(3, 3),
				Math.pow(10.0, 2.0)));
				/*
		argsOpt.add(new KritGenPopOp(5, 220, 250, new EvaluateOpDTLZ2(5, 3),
				Math.pow(10.0, 4.0)));
		
		argsOpt.add(new KritGenPopOp(7, 150, 100, new EvaluateOpDTLZ2(7, 3),
				Math.pow(10.0, 6.0)));
				*/
		for (KritGenPopOp kgpo : argsOpt) {
			//for (int i = 0; i < 15; i++) {
				Integer k = 3;
				Integer M = kgpo.krit;
				AlgNSGA2 algNSGA2 = new AlgNSGA2(0.0, 1.0, M - 1 + k, M,
						kgpo.gen, getPartitonWhenNoOfPopulationOverN(kgpo.pop,
								M).getElement1(), kgpo.op);
				List<Individual> firstFront = algNSGA2.run();
				/*
				BufferedWriter bw = null;
				try {
					// APPEND MODE SET HERE
					bw = new BufferedWriter(new FileWriter(
							"C:\\Users\\leonx64\\Desktop\\rezultati\\NSGA2\\konv"
									+ kgpo.op.getClass().hashCode() + "_"
									+ kgpo.krit + "_re2.txt", true));

				} catch (IOException ioe) {
				} // always close the file
				try {

					bw.write(Double.toString(AlgNSGA2.calcualteHypervolume(
							firstFront, M) / kgpo.scale));
					bw.newLine();
					bw.flush();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} finally { // always close the file
					if (bw != null)
						try {
							bw.close();
						} catch (IOException ioe2) {
							// just ignore it
						}
				}
				*/ 
			}
			
		}
	//}

	// Pair<p,n>; might return lower if integer overflows; purpose-equalising
	// NSGA-2 and NSGA-3 population size
	public static Pair<Integer, Integer> getPartitonWhenNoOfPopulationOverN(
			Integer N, Integer M) {
		Integer n = 0;
		Integer p = 0;
		while (n < N) {
			p++;
			Integer temp = (factoriel(M + p - 1))
					/ (factoriel(p) * factoriel(M - 1));
			if (temp < n) {
				p--;
				break;
			}
			n = temp;
			n = getFirstNumThatIsMultipleOfFour(n);
		}
		return new Pair<Integer, Integer>(p, n);
	}

	public static int factoriel(int nmbr) {
		int factoriel = 1;
		for (int i = 1; i <= nmbr; i++) {
			factoriel *= i;
		}
		return factoriel;
	}

	public static Integer getFirstNumThatIsMultipleOfFour(Integer num) {
		if (num % 4 == 0)
			return num;
		if ((num + 1) % 4 == 0)
			return num + 1;
		if ((num + 2) % 4 == 0)
			return num + 2;
		if ((num + 3) % 4 == 0)
			return num + 3;
		return -1;
	}
}
