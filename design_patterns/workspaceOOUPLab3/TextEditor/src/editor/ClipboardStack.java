package editor;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ClipboardStack {
	List<ClipboardObserver> clipObservers;
	Stack<String> stack;
	
	public void attach(ClipboardObserver obs){
		clipObservers.add(obs);
	}
	
	public void dettach(ClipboardObserver obs){
		clipObservers.remove(obs);
	}

	public ClipboardStack() {
		super();
		this.clipObservers = new ArrayList<ClipboardObserver>();
		this.stack = new Stack<String>();
	}
	
	public void notifyObs(){
		for(ClipboardObserver obs : clipObservers){
			obs.updateClipboard();
		}
	}
}
