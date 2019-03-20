import java.util.ArrayList;
import java.util.List;


public abstract class AbstractGraphicalObject implements GraphicalObject {
	protected Point[] hotPoints;
	protected boolean[] hotPointsSelected;
	protected boolean selected;
	protected List<GraphicalObjectListener> listeners = new ArrayList<GraphicalObjectListener>();
	
	public AbstractGraphicalObject(Point[] points) {
		this.hotPoints = points;
		int len = this.hotPoints.length;
		this.hotPointsSelected = new boolean[len];
		for(int i=0;i<len;i++){
			this.hotPointsSelected[i] = false;
		}
		this.selected = false;
	}
	
	@Override
	public boolean isSelected() {
		return selected;
	}
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		
	}
	@Override
	public int getNumberOfHotPoints() {
		return this.hotPoints.length;
	}
	@Override
	public Point getHotPoint(int index) {
		return this.hotPoints[index];
	}
	@Override
	public void setHotPoint(int index, Point point) {
		this.hotPoints[index] = point;
		
	}
	@Override
	public boolean isHotPointSelected(int index) {
		return this.hotPointsSelected[index];
	}
	@Override
	public void setHotPointSelected(int index, boolean selected) {
		this.hotPointsSelected[index] = selected;
		
	}
	@Override
	public double getHotPointDistance(int index, Point mousePoint) {
		return GeometryUtil.distanceFromPoint(this.hotPoints[index], mousePoint);
	}
	@Override
	public void translate(Point delta) {
		int len = this.hotPoints.length;
		for(int i=0;i<len;i++){
			this.hotPoints[i] = this.hotPoints[i].translate(delta);
		}
		
	}
	@Override
	public void addGraphicalObjectListener(GraphicalObjectListener l) {
		this.listeners.add(l);
		
	}
	@Override
	public void removeGraphicalObjectListener(GraphicalObjectListener l) {
		this.listeners.remove(l);		
	}
	
	public void notifyListeners(){
		for(GraphicalObjectListener l : this.listeners){
			l.graphicalObjectChanged(this);
		}
	}
	
	public void notifySelectionListeners(){
		for(GraphicalObjectListener l : this.listeners){
			l.graphicalObjectSelectionChanged(this);
		}
	}
	

}
