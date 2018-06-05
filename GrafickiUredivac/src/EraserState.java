import java.util.ArrayList;
import java.util.List;


public class EraserState implements State {
	
	private List<Point> curve;
	private DocumentModel model;
	private Canvas canvas;
			
	public EraserState(DocumentModel model, Canvas canvas){
		this.model = model;
		this.canvas = canvas;
		this.curve = new ArrayList<Point>();
	}
	
	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		curve.clear();
		curve.add(mousePoint);
		canvas.repaint();
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		curve.add(mousePoint);
		canvas.repaint();
		Point p1 = curve.get(0);
		Point p2 = curve.get(1);
		Integer xmin = Math.min(p1.getX(), p2.getX());
		Integer xmax = Math.max(p1.getX(), p2.getX());
		Integer ymin = Math.min(p1.getY(), p2.getY());
		Integer ymax = Math.max(p1.getY(), p2.getY());
		Integer deltaX = xmax-xmin;
		Integer deltaY = ymax-ymin;
		if(deltaX>deltaY){
			double step = ((double)deltaY/(double)deltaX);
			if(p1.equals(new Point(xmin,ymin))|| p2.equals(new Point(xmin,ymin))){
				for(int x=xmin;x<=xmax;x++){
					Point p = new Point(x,ymin+(int)(step*(x-xmin)));
					GraphicalObject obj = model.findSelectedGraphicalObject(p);
					if(obj!= null){
						model.removeGraphicalObject(obj);
					}
				}
			}else{
				for(int x=xmin;x<=xmax;x++){
					Point p = new Point(x,ymax-(int)(step*(x-xmin)));
					GraphicalObject obj = model.findSelectedGraphicalObject(p);
					if(obj!= null){
						model.removeGraphicalObject(obj);
					}
				}				
			}
		}else{
			double step = ((double)deltaX/(double)deltaY);
			if(p1.equals(new Point(xmin,ymin))|| p2.equals(new Point(xmin,ymin))){
				for(int y=ymin;y<=ymax;y++){
					Point p = new Point(xmin + (int)(step*(y-ymin)),y);
					GraphicalObject obj = model.findSelectedGraphicalObject(p);
					if(obj!= null){
						model.removeGraphicalObject(obj);
					}
				}
			}else{
				for(int y=ymin;y<=ymax;y++){
					Point p = new Point(xmax - (int)(step*(y-ymin)),y);
					GraphicalObject obj = model.findSelectedGraphicalObject(p);
					if(obj!= null){
						model.removeGraphicalObject(obj);
					}
				}				
			}
			
		}
	}

	@Override
	public void mouseDragged(Point mousePoint) {
		
	}

	@Override
	public void keyPressed(int keyCode) {
		
	}

	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {
		
	}

	@Override
	public void afterDraw(Renderer r) {
		if(curve.size()==2){			
			r.drawLine(curve.get(0), curve.get(1));			
		}
	}

	@Override
	public void onLeaving() {
		
	}

}
