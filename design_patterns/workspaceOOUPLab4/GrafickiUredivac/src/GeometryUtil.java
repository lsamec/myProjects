
public class GeometryUtil {

	public static double distanceFromPoint(Point point1, Point point2) {
		// izraèunaj euklidsku udaljenost izmeðu dvije toèke ...
		return Math.sqrt(Math.pow(point1.getX()-point2.getX(),2.0)+Math.pow(point1.getY()-point2.getY(),2.0));
	}
	
	public static double distanceFromLineSegment(Point s, Point e, Point p) {
		// Izraèunaj koliko je toèka P udaljena od linijskog segmenta odreðenog
		// poèetnom toèkom S i završnom toèkom E. Uoèite: ako je toèka P iznad/ispod
		// tog segmenta, ova udaljenost je udaljenost okomice spuštene iz P na S-E.
		// Ako je toèka P "prije" toèke S ili "iza" toèke E, udaljenost odgovara
		// udaljenosti od P do poèetne/konaène toèke segmenta.
		double a = distanceFromPoint(p, s);
		double b = distanceFromPoint(p, e);
		double c = distanceFromPoint(s, e);
		if (Math.abs( Math.abs(a-b) - c) < 5.0){
			return Math.min(a, b);
		}else{
			Point va = p.difference(s);
			Point vc = e.difference(s);
			double y= Math.sqrt(va.getX()*va.getX()+va.getY()*va.getY());
			double x =y* ((double)(va.getX()*vc.getX()+va.getY()*vc.getY()))/(Math.sqrt(va.getX()*va.getX()+va.getY()*va.getY())*Math.sqrt(vc.getX()*vc.getX()+vc.getY()*vc.getY()));
			return Math.sqrt(y*y-x*x);
			
		}
	}
}
