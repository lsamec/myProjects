import java.util.List;
import java.util.Stack;



public class Oval extends AbstractGraphicalObject {
	
	public Point getCenter() {
		return this.hotPoints[0].translate(new Point(this.hotPoints[1].getX()-this.hotPoints[0].getX(),0));
	}

	public Oval(Point right, Point down) {
		super(new Point[2]);
		this.hotPoints[0] = right;
		this.hotPoints[1] = down;
	}
	
	public Oval() {
		super(new Point[2]);
		this.hotPoints[0] = new Point(50,80);
		this.hotPoints[1] = new Point(70,50);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		int xdist = this.hotPoints[1].getX()-this.hotPoints[0].getX();
		int ydist = this.hotPoints[0].getY()-this.hotPoints[1].getY();
		return new Rectangle(this.hotPoints[1].getX()-xdist, this.hotPoints[0].getY()-ydist, 2*xdist, 2*ydist);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		Point center = this.getCenter();
		return GeometryUtil.distanceFromPoint(mousePoint, center) - Math.max(GeometryUtil.distanceFromPoint(center, this.hotPoints[0]),GeometryUtil.distanceFromPoint(center, this.hotPoints[1]));
	}

	@Override
	public String getShapeName() {
		return "Oval";
	}

	@Override
	public GraphicalObject duplicate() {
		return new Oval(new Point(this.hotPoints[0].getX(),this.hotPoints[0].getY()), new Point(this.hotPoints[1].getX(),this.hotPoints[1].getY()));
	}

	@Override
	public void render(Renderer r) {
		Point center = this.getCenter();
		double radius1 = GeometryUtil.distanceFromPoint(center, this.hotPoints[0]);
		double radius2 = GeometryUtil.distanceFromPoint(center, this.hotPoints[1]);
		Point[] points = new Point[360];
		for(int ang=0;ang<360;ang++){			
			double rad = (Math.PI/180.0)*(double)ang;
			double radius = (radius1*radius2)/Math.sqrt( Math.pow(radius1, 2.0)*Math.pow(Math.sin(rad),2.0) +  Math.pow(radius2, 2.0)*Math.pow(Math.cos(rad),2.0)  );
			points[ang] = center.translate(new Point((int)Math.round(radius*Math.cos(rad)),(int)Math.round(radius*Math.sin(rad))));
		}
		r.fillPolygon(points);
	}

	@Override
	public String getShapeID() {
		return "@OVAL";
	}

	@Override
	public void save(List<String> rows) {
		String line = this.getShapeID()+ " "+ this.hotPoints[0].getX() + " " + this.hotPoints[0].getY() + " "+ this.hotPoints[1].getX() + " "+ this.hotPoints[1].getY();
		rows.add(line);		
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		String[] parts = data.split(" ");
		Oval oval = new Oval(new Point(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])), new Point(Integer.parseInt(parts[2]),Integer.parseInt(parts[3])));
		stack.push(oval);
		
	}

}
