import java.util.List;
import java.util.Stack;



public class LineSegment extends AbstractGraphicalObject {

	public LineSegment(Point start, Point end) {
		super(new Point[2]);
		this.hotPoints[0] = start;
		this.hotPoints[1] = end;
	}
	
	public LineSegment() {
		super(new Point[2]);
		this.hotPoints[0] = new Point(20,20);
		this.hotPoints[1] = new Point(40,40);
	}

	@Override
	public Rectangle getBoundingBox() {
		int startx = Math.min(this.hotPoints[0].getX(), this.hotPoints[1].getX());
		int starty = Math.min(this.hotPoints[0].getY(), this.hotPoints[1].getY());
		int width = Math.abs(this.hotPoints[0].getX()- this.hotPoints[1].getX());
		int height = Math.abs(this.hotPoints[0].getY()- this.hotPoints[1].getY());
		return new Rectangle(startx, starty, width, height);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		return GeometryUtil.distanceFromLineSegment(this.hotPoints[0], this.hotPoints[1], mousePoint);
	}

	@Override
	public String getShapeName() {
		return "Linija";
	}

	@Override
	public GraphicalObject duplicate() {
		return new LineSegment(new Point(this.hotPoints[0].getX(),this.hotPoints[0].getY()), new Point(this.hotPoints[1].getX(),this.hotPoints[1].getY()));
	}

	@Override
	public void render(Renderer r) {
		r.drawLine(this.hotPoints[0], this.hotPoints[1]);		
	}

	@Override
	public String getShapeID() {
		return "@LINE";
	}

	@Override
	public void save(List<String> rows) {
		String line = this.getShapeID()+ " "+ this.hotPoints[0].getX() + " " + this.hotPoints[0].getY() + " "+ this.hotPoints[1].getX() + " "+ this.hotPoints[1].getY();
		rows.add(line);
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		String[] parts = data.split(" ");
		LineSegment line = new LineSegment(new Point(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])), new Point(Integer.parseInt(parts[2]),Integer.parseInt(parts[3])));
		stack.push(line);
	}

}
