import java.util.Random;


public class UsualMatrix{
	
	private int[][] field;
	private int rows, columns;
	
	public UsualMatrix(int i, int j){
		
		rows = i;
		columns = j;
		field = new int[rows][columns];
	}
	
	public void setValue(int i, int j, int value){
		
		if(i < 0 || i >= getRows() || j < 0 || j >= getColumns())
			throw new RuntimeException("Wrong I and J");
		
		field[i][j] = value;
	}
	
	public int getValue(int i, int j){
		
		if(i < 0 || i >= getRows() || j < 0 || j >= getColumns())
			throw new RuntimeException("Wrong I and J");
		
		return field[i][j];
	}
	
	public int getRows(){
		return rows;
	}
	
	public int getColumns(){
		return columns;
	}
	
	public void fillMatrixRand(int maxNumber){
		
		Random rand = new Random();
		
		for(int i = 0; i < getRows(); i++){
			for(int j = 0; j < getColumns(); j++){
				field[i][j] = rand.nextInt(maxNumber);
			}
		}
	}
	
	public UsualMatrix add(UsualMatrix other){
		
		if(this.getRows() != other.getRows() || this.getColumns() != other.getColumns())
			throw new RuntimeException("Wrong sides!");
		
		UsualMatrix res = new UsualMatrix(this.getRows(), this.getColumns());
		
		for(int i = 0; i < res.getRows(); i++){
			for(int j = 0; j < res.getColumns(); j++){
				res.setValue(i, j, (this.getValue(i, j) + other.getValue(i, j)));
			}
		}
		return res;
	}
	
	public UsualMatrix product(UsualMatrix other){
		
		if(this.getColumns() != other.getRows())
			throw new RuntimeException("Wrong sides!");
		
		UsualMatrix res = new UsualMatrix(this.getRows(), other.getColumns());
		
		for(int i = 0; i < this.getRows(); i++){
			for(int j = 0; j < other.getColumns(); j++){
				for(int k = 0; k < this.getColumns(); k++){
					res.setValue(i, j, res.getValue(i, j) + this.getValue(i, k) * other.getValue(k, j));
				}
			}
		}
		return res;
	}
	
	public boolean equals(Object o){
		
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(!(o instanceof UsualMatrix))
			return false;
		
		UsualMatrix um = (UsualMatrix) o;
		
		if(getRows() == um.getRows() && getColumns() == um.getColumns()){
			for(int i = 0; i < getRows(); i++){
				for(int j = 0; j < getColumns(); j++){
					if(getValue(i,j) != um.getValue(i, j))
						return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < getRows(); i++, sb.append("\n")){
			for(int j = 0; j < getColumns(); j++){
				sb.append(getValue(i, j) + " ");
			}
		}
		return sb.toString();
	}
	
}


