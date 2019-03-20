import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SelectShapeState implements State {

	private DocumentModel model;
	private Canvas canvas;
	
	public SelectShapeState(DocumentModel model,Canvas canvas) {
		this.model = model;
		this.canvas = canvas;
	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		GraphicalObject selObj = this.model
				.findSelectedGraphicalObject(mousePoint);
		if (selObj != null) {
			selObj.setSelected(true);
			if (ctrlDown) {			
				this.model.addObjectToSelection(selObj);
			} else {
				this.model.clearSelObj();
				this.model.addObjectToSelection(selObj);
			}
		}
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		GraphicalObject obj = model.findSelectedGraphicalObject(mousePoint);
		if(obj!= null){
			for(int i=0;i<obj.getNumberOfHotPoints();i++){
				obj.setHotPointSelected(i, false);
			}
		}
	}

	@Override
	public void mouseDragged(Point mousePoint) {
		if(this.model.getSelectedObjects().size() ==1){
			GraphicalObject obj = this.model.getSelectedObjects().get(0);
			for(int i=0;i<obj.getNumberOfHotPoints();i++){
				if(obj.isHotPointSelected(i)){
					obj.setHotPoint(i, mousePoint);
					canvas.repaint();
					return;
				}
			}
			int index = model.findSelectedHotPoint(obj, mousePoint);
			if(index != -1){
				obj.setHotPointSelected(index, true);
			}
			
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		switch(keyCode){
			case KeyEvent.VK_UP : moveSelectedObjects(new Point(0,-1));
									break;
			case KeyEvent.VK_DOWN : moveSelectedObjects(new Point(0,1));
									break;
			case KeyEvent.VK_LEFT : moveSelectedObjects(new Point(-1,0));
									break;
			case KeyEvent.VK_RIGHT : moveSelectedObjects(new Point(1,0));
									break;
			case KeyEvent.VK_P : if (model.getSelectedObjects().size() == 1){
										GraphicalObject obj = model.getSelectedObjects().get(0);
										model.increaseZ(obj);
									}
									break;
			case KeyEvent.VK_M : if (model.getSelectedObjects().size() == 1){
										GraphicalObject obj = model.getSelectedObjects().get(0);
										model.decreaseZ(obj);
									}
									break;
			case KeyEvent.VK_G : if(model.getSelectedObjects().size() > 0){
										CompositeShape comp = new CompositeShape(model.getSelectedObjects());
										comp.setSelected(true);
										List<GraphicalObject> objs = new ArrayList<GraphicalObject>(model.getSelectedObjects());
										for(GraphicalObject obj : objs){
											model.removeGraphicalObject(obj);
										}
										model.clearSelObj();
										model.addGraphicalObject(comp);
									}
									break;
			case KeyEvent.VK_U : if(model.getSelectedObjects().size() == 1){
									GraphicalObject obj = model.getSelectedObjects().get(0);
									if( obj instanceof CompositeShape){
										model.removeGraphicalObject(obj);
									
										for(GraphicalObject chObj : ((CompositeShape)obj).getChildren()){
											chObj.setSelected(true);
											model.addGraphicalObject(chObj);
										}
									}
								}
		}
		canvas.repaint();
	}
	
	private void moveSelectedObjects(Point p){
		for(GraphicalObject obj : model.getSelectedObjects()){
			obj.translate(p);
		}
	}

	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {

	}

	@Override
	public void afterDraw(Renderer r) {
		for(GraphicalObject obj : this.model.getSelectedObjects()){
			Rectangle rect = obj.getBoundingBox();
			Point point1 = new Point(rect.getX(),rect.getY());
			Point point2 = point1.translate(new Point(rect.getWidth(),0));
			Point point3 = point2.translate(new Point(0,rect.getHeight()));
			Point point4 = point3.translate(new Point(-rect.getWidth(),0));
			r.drawLine(point1, point2);
			r.drawLine(point2, point3);
			r.drawLine(point3, point4);
			r.drawLine(point4, point1);
		}
		if(this.model.getSelectedObjects().size() ==1){
			GraphicalObject obj = this.model.getSelectedObjects().get(0);
			for(int i=0;i<obj.getNumberOfHotPoints();i++){
				Point hp = obj.getHotPoint(i);
				r.drawLine(hp.translate(new Point(-3,-3)),hp.translate(new Point(3,-3)));
				r.drawLine(hp.translate(new Point(3,-3)),hp.translate(new Point(3,3)));
				r.drawLine(hp.translate(new Point(3,3)),hp.translate(new Point(-3,3)));
				r.drawLine(hp.translate(new Point(-3,3)),hp.translate(new Point(-3,-3)));
			}
		}
	}

	@Override
	public void onLeaving() {
		model.clearSelObj();
	}

}
