package editor;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class UndoManager {
	public Stack<EditAction> undoStack;
	public Stack<EditAction> redoStack;
	public List<UndoObserver> undoObservers;
	
	public void attachUO(UndoObserver obs){
		undoObservers.add(obs);
	}
	
	public void dettachUO(UndoObserver obs){
		undoObservers.remove(obs);
	}
	
	public void notifyObs(){
		for(UndoObserver obs : undoObservers){
			obs.updateUndo();
		}
	}
	
	private static UndoManager instance;
	
	private UndoManager(){
		this.undoStack = new Stack<EditAction>();
		this.redoStack = new Stack<EditAction>();
		this.undoObservers = new ArrayList<UndoObserver>();
	}
	
	public void undo(){
		EditAction editAction = undoStack.pop();
		editAction.executeUndo();
		redoStack.push(editAction);
		notifyObs();
	}
	
	public void push(EditAction editAction){
		redoStack.clear();
		undoStack.push(editAction);
		notifyObs();
	}
	
	public void redo(){
		EditAction editAction = redoStack.pop();
		editAction.executeDo();
		undoStack.push(editAction);
		notifyObs();
	}
	
	public static UndoManager instance(){
		if(instance == null){
			instance = new UndoManager();
		}
		return instance;
	}
	
}
