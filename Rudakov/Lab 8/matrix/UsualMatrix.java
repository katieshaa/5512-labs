package matrix;

public class UsualMatrix {
    protected int[][] data;
    protected int rows;
    protected int columns;
    public UsualMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        data = new int[rows][columns];
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                data[i][j] = 0;
            }
        }
    }
    public UsualMatrix(UsualMatrix a) {
        rows = a.rows;
        columns = a.columns;
        data = new int[rows][columns];
        for(int i = 0; i < rows; ++i) {
            System.arraycopy(a.data[i], 0, data[i], 0, columns);
        }
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    public void setElement(int row, int column, int value) {
        if(row >= rows || column >= columns) {
            throw new RuntimeException("Element does not exist\n");
        }
        data[row][column] = value;
    }
    public int getElement(int row, int column) {
        if(row >= rows || column >= columns) {
            throw new RuntimeException("Element does not exist\n");
        }
        return data[row][column];
    }
    public UsualMatrix add(UsualMatrix a) {
        if(a.getRows() != this.getRows() || a.getColumns() != this.getColumns()) {

            throw new RuntimeException("Invalid dimensions for addition\n");
        }
        UsualMatrix result = new UsualMatrix(a.getRows(), a.getColumns());
        for(int i = 0;i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                result.setElement(i, j, this.getElement(i, j) + a.getElement(i, j));
            }
        }
        return result;
    }

    public UsualMatrix product(UsualMatrix a) {

        if(a.getRows() != this.getColumns()) {

            throw new RuntimeException("Invalid dimensions for multiplication\n");
        }
        UsualMatrix result = new UsualMatrix(a.getRows(), a.getColumns());
        for(int i = 0;i < this.getRows(); ++i) {
            for(int j = 0; j < a.getColumns(); ++j) {
                int tmp = 0;
                for(int k = 0; k < this.columns; ++k) {
                    tmp += this.getElement(i, k) * a.getElement(k, j);
                }
                result.setElement(i, j, tmp);
            }
        }
        return result;
    }
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                result.append(getElement(i, j));
                result.append(' ');
            }
            result.append('\n');
        }
        return result.toString();
    }
}
