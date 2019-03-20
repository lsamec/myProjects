
public class AddShapeState implements State {
	
	private GraphicalObject prototype;
	private DocumentModel model;
	
	public AddShapeState(DocumentModel model, GraphicalObject prototype) {
		this.prototype = prototype;
		this.model = model;
	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		GraphicalObject obj = prototype.duplicate();
		Point trans = mousePoint.difference(prototype.getHotPoint(0));
		for(int i=0; i<obj.getNumberOfHotPoints();i++){
			if(i ==0){
				obj.setHotPoint(i, mousePoint);
			}else{
				obj.setHotPoint(i, obj.getHotPoint(i).translate(trans));
			}
		}
		model.addGraphicalObject(obj);
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		
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
		
	}

	@Override
	public void onLeaving() {
		
	}
	
}