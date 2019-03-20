import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.w3c.dom.views.AbstractView;


public class Triangle extends AbstractGraphicalObject{
	
	public Triangle(){
		super(new Point[3]);
		this.hotPoints[0] = new Point(50, 50);
		this.hotPoints[1] = new Point(100,100);
		this.hotPoints[2] = new Point(50, 100);
	}
	
	public Triangle(Point[] points){
		super(points);
		this.hotPoints[0] = points[0];
		this.hotPoints[1] = points[1];
		this.hotPoints[2] = points[2];
	}
	
	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(Renderer r) {
		r.drawLine(this.getHotPoint(0), this.getHotPoint(1));
		r.drawLine(this.getHotPoint(1), this.getHotPoint(2));
		r.drawLine(this.getHotPoint(2), this.getHotPoint(0));		
	}

	@Override
	public String getShapeName() {
		// TODO Auto-generated method stub
		return "Triangle";
	}

	@Override
	public GraphicalObject duplicate() {
		Point[] p = new Point[3];
		p[0] = new Point(this.getHotPoint(0).getX(),this.getHotPoint(0).getY());
		p[1] = new Point(this.getHotPoint(1).getX(),this.getHotPoint(1).getY());
		p[2] = new Point(this.getHotPoint(2).getX(),this.getHotPoint(2).getY());
		return new Triangle(p);
	}

	@Override
	public String getShapeID() {

		return "@TRIANGLE";
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		String[] parts = data.split(" ");
		Point[] p = new Point[3];
		p[0] = new Point(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
		p[1] = new Point(Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
		p[2] = new Point(Integer.parseInt(parts[4]),Integer.parseInt(parts[5]));
		Triangle tr = new Triangle(p);
		stack.push(tr);
		
	}

	@Override
	public void save(List<String> rows) {
		String line = this.getShapeID()+ " "+ this.hotPoints[0].getX() + " " + this.hotPoints[0].getY() + " "+ this.hotPoints[1].getX() + " "+ this.hotPoints[1].getY()+ " "+ this.hotPoints[2].getX() + " "+ this.hotPoints[2].getY();
		rows.add(line);
		
	}


	
	

}
