import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class CompositeShape extends AbstractGraphicalObject {

	private List<GraphicalObject> children;
	private Integer xmin;
	private Integer xmax;
	private Integer ymin;
	private Integer ymax;
	
	public CompositeShape(){
		super(new Point[0]);
	}
	
	public CompositeShape(List<GraphicalObject> objs) {
		super(new Point[0]);
		children = new ArrayList<GraphicalObject>(objs);
		List<Integer> xList = new ArrayList<Integer>();
		List<Integer> yList = new ArrayList<Integer>();
		for(GraphicalObject obj : children){
			for(int i=0;i<obj.getNumberOfHotPoints();i++){
				Rectangle rect = obj.getBoundingBox();
				Point p1 = new Point(rect.getX(),rect.getY());
				Point p2 = new Point(p1.getX()+rect.getWidth(),p1.getY());
				Point p3 = new Point(p2.getX(),p2.getY()+rect.getHeight());
				Point p4 = new Point(p3.getX()-rect.getWidth(),p3.getY());
				xList.add(p1.getX());
				xList.add(p2.getX());
				xList.add(p3.getX());
				xList.add(p4.getX());
				yList.add(p1.getY());
				yList.add(p2.getY());
				yList.add(p3.getY());
				yList.add(p4.getY());
			}
		}
		xmin = Collections.min(xList);
		ymin = Collections.min(yList);
		xmax = Collections.max(xList);
		ymax = Collections.max(yList);
	}
	
	@Override
	public void translate(Point p){
		for(GraphicalObject obj : children){
			obj.translate(p);
		}
		List<Integer> xList = new ArrayList<Integer>();
		List<Integer> yList = new ArrayList<Integer>();
		for(GraphicalObject obj : children){
			for(int i=0;i<obj.getNumberOfHotPoints();i++){
				Rectangle rect = obj.getBoundingBox();
				Point p1 = new Point(rect.getX(),rect.getY());
				Point p2 = new Point(p1.getX()+rect.getWidth(),p1.getY());
				Point p3 = new Point(p2.getX(),p2.getY()+rect.getHeight());
				Point p4 = new Point(p3.getX()-rect.getWidth(),p3.getY());
				xList.add(p1.getX());
				xList.add(p2.getX());
				xList.add(p3.getX());
				xList.add(p4.getX());
				yList.add(p1.getY());
				yList.add(p2.getY());
				yList.add(p3.getY());
				yList.add(p4.getY());
			}
		}
		xmin = Collections.min(xList);
		ymin = Collections.min(yList);
		xmax = Collections.max(xList);
		ymax = Collections.max(yList);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(xmin, ymin, xmax-xmin, ymax-ymin);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		if(mousePoint.getX()>xmin && mousePoint.getY()>ymin && mousePoint.getX()<xmax && mousePoint.getY()<ymax){
			return 0.0;			
		}else{
			return 100.0;
		}
	}

	public List<GraphicalObject> getChildren() {
		return children;
	}

	public void setChildren(List<GraphicalObject> children) {
		this.children = children;
	}

	@Override
	public void render(Renderer r) {
		for(GraphicalObject obj : children){
			obj.render(r);
		}
	}

	@Override
	public String getShapeName() {
		return "Kompozit";
	}

	@Override
	public GraphicalObject duplicate() {
		return null;
	}

	@Override
	public String getShapeID() {
		return "@COMP";
	}

	@Override
	public void save(List<String> rows) {
		for(GraphicalObject obj : this.children){
			obj.save(rows);
		}
		String line = this.getShapeID() + " " + this.children.size();
		rows.add(line);
		
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		Integer noOfObjs = Integer.parseInt(data);
		List<GraphicalObject> objs = new ArrayList<GraphicalObject>();
		for(int i=0;i<noOfObjs;i++){
			objs.add(stack.pop());
		}
		CompositeShape comp = new CompositeShape(objs);
		stack.push(comp);
	}

}
