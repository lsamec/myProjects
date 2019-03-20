package editor;

public class Location {
	private Integer row;
	private Integer column;
	
	public Location() {
		super();
		this.row = 0;
		this.column = 0;
	}
	
	@Override
	public String toString() {
		return "Location [row=" + row + ", column=" + column + "]";
	}

	public Location(Integer row, Integer column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public Location(Location loc) {
		super();
		this.row = loc.getRow();
		this.column = loc.getColumn();
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((row == null) ? 0 : row.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}
	
	public boolean isLessThan(Location loc){
		if (this.getRow()< loc.getRow() || (this.getRow()== loc.getRow() && this.getColumn() < loc.getColumn())){
			return true;
		}
		return false;
	}
	
	public boolean isLowerColumnAndSameRow(Location loc){
		if (this.getRow()== loc.getRow() && this.getColumn() < loc.getColumn()){
			return true;
		}
		return false;
	}
	
	public boolean isNotSameRow(Location loc){
		if (this.getRow() != loc.getRow()){
			return true;
		}
		return false;
	}
	
	public Location giveLeft(){
		Location newLoc = new Location(this);
		newLoc.setColumn(newLoc.getColumn()-1);
		return newLoc;
	}
	public Location giveRight(){
		Location newLoc = new Location(this);
		newLoc.setColumn(newLoc.getColumn()+1);
		return newLoc;
	}
	
}
