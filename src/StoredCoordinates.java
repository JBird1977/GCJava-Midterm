
public class StoredCoordinates {

    private int row = 0;
    private int column = 0;
    
    public StoredCoordinates() {}

	public StoredCoordinates(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	
	public void setRow() {
		this.row = row;
	}
	
	public void setColumn() {
		this.column = column;
	}
	
	
	@Override
	public String toString() {
		return "Row = " + row + "Column = " + column;
	}
}