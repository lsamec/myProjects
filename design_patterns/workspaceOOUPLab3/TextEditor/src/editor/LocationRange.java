package editor;

public class LocationRange {
	private Location begin;
	private Location end;
	
	public LocationRange() {
		super();
		this.begin = new Location();
		this.end = new Location();
	}
	
	public LocationRange(Location begin, Location end) {
		super();
		this.begin = begin;
		this.end = end;
	}
	public Location getBegin() {
		return begin;
	}
	public void setBegin(Location begin) {
		this.begin = begin;
	}
	public Location getEnd() {
		return end;
	}
	public void setEnd(Location end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "LocationRange [begin=" + begin + ", end=" + end + "]";
	}
}
