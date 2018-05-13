package genPaket;

public class ActionNewState {
	public String action;
	public Integer newState;
	public ActionNewState(String action, Integer newState) {
		super();
		this.action = action;
		this.newState = newState;
	}
	@Override
	public String toString() {
		return "["+ action + "," + newState
				+"]";
	}
	

}
