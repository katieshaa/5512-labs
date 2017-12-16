package matrix;
import java.lang.StringBuilder;

public class UsualMatrix {
	private int[][] matrix;
	
	public UsualMatrix(int Rows, int Columns) {
		matrix = new int[Rows][Columns];
	}
	
	public UsualMatrix(UsualMatrix Matr) {
		matrix = new int[Matr.getRows()][Matr.getColumns()];
		
		for(int i = 0; i < this.getRows(); i++) {
			for(int j = 0; j < this.getColumns(); j++) {
				this.setElement(i, j, Matr.getElement(i, j));
			}
		}
	}
		
	public int getRows() {
		return matrix.length;
	}
	
	public int getColumns() {
		return matrix[0].length;
	}
	
	public int getElement(int Row, int Column) {
		if(Row >= getRows() || Row < 0 || Column >= getColumns() || Column < 0) {
			throw new RuntimeException("Wrong number of column or row");
		}
		
		return matrix[Row][Column];
	}
	
	public void setElement(int Row, int Column, int Value) {
		if(Row >= getRows() || Row < 0 || Column >= getColumns() || Column < 0) {
			throw new RuntimeException("Wrong number of column or row");
		}
		
		matrix[Row][Column] = Value;
	}
	
	public UsualMatrix sum(UsualMatrix Matr) {
		if(this.getRows() != Matr.getRows() || this.getColumns() != Matr.getColumns()) {
			throw new RuntimeException("Number of columns or rows are not equals");
		}
		
		UsualMatrix result = new UsualMatrix(this.getRows(), this.getColumns());
		
		for(int i = 0; i < result.getRows(); i++) {
			for(int j = 0; j < result.getColumns(); j++) {
				result.setElement(i, j, this.getElement(i, j) + Matr.getElement(i, j));
			}
		}
		
		return result;
	}
	
	
	public UsualMatrix product(UsualMatrix Matr) {
		if(this.getColumns() != Matr.getRows()) {
			throw new RuntimeException("Number of columns or rows are not equals");
		}
		
		UsualMatrix result = new UsualMatrix(this.getRows(), Matr.getColumns());
		
		for(int i = 0; i < result.getRows(); i++) {
			for(int j = 0; j < result.getColumns(); j++) {
				for(int k = 0; k < this.getColumns(); k++) {
					result.setElement(i, j, result.getElement(i, j) + (this.getElement(i, k) * Matr.getElement(k, j)));
				}
			}
		}
		
		return result;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder(getRows() * getColumns() * 3);
		
		int maxValue = Math.abs(getElement(0, 0));
		boolean negative = false;
		
		for(int i = 0; i < getRows(); i++) {
			for(int j = 0; j < getColumns(); j++) {
				int number = getElement(i, j);
				if(!negative && number < 0) negative = true;
				
				if(Math.abs(number) > maxValue) {
					maxValue = Math.abs(number);
				}
			}
		}
		
		int length = ("" + maxValue).length() + ((negative) ? 1 : 0);
		for(int i = 0; i < getRows(); i++) {
			result.append('[');
			for(int j = 0; j < getColumns(); j++) {
				result.append(String.format("%" + length + "d", getElement(i, j)));
				
				if(j < getColumns() - 1) {
					result.append(',');
					result.append(' ');
				}
			}
			result.append(']');
			result.append('\n');
		}
		
		return result.toString();
	}
	
	public boolean equals(Object OtherObject) {
		if(OtherObject == null) {
			return false;
		}
		
		if(OtherObject == this) {
			return true;
		}
		
		if(!(OtherObject instanceof UsualMatrix)) {
			return false;
		}
		
		UsualMatrix Other = (UsualMatrix) OtherObject;
		
		if(this.getRows() != Other.getRows() || this.getColumns() != Other.getColumns()) {
			return false;
		}
		
		for(int i = 0; i < this.getRows(); i++) {
			for(int j = 0; j < this.getColumns(); j++) {
				if(this.getElement(i, j) != Other.getElement(i, j)) {
					return false;
				}
			}
		}
		
		return true;
	}
}