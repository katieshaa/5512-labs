/**
 * Created by root on 14.07.17 with love.
 */
public class UsualMatrix{
    protected int[][] matr;
    protected int rows;
    protected int columns;

    public UsualMatrix(int r, int c){
        rows = r;
        columns = c;
        if((r < 0) && (c < 0)) {
            throw new IndexOutOfBoundsException("Matrix::Matrix() size < 0");
        }
        matr = new int[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                matr[i][j] = 0;
            }
        }
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    public UsualMatrix sum(UsualMatrix M) throws Exception {
        if((rows != M.rows()) || (columns != M.columns())) {
            throw new Exception("Matrix::sum() Bad size!");
        }
        UsualMatrix res = new UsualMatrix(rows, columns);
        for(int i = 0; i < res.rows(); i++) {
            for(int j = 0; j < res.columns(); j++) {
                res.setElement(i, j, (this.getElement(i, j) + M.getElement(i, j)));
            }
        }
        return res;
    }

    public UsualMatrix product(UsualMatrix M) throws Exception {
        if(columns != M.rows()) {
            throw new Exception("Matrix::product() column != rows");
        }
        UsualMatrix res = new UsualMatrix(rows, M.columns());
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                for(int k = 0; k < columns; k++) {
                    res.setElement(i, j, (res.getElement(i,j) + this.getElement(i, k) * M.getElement(k, j)));
                }
            }
        }
        return res;
    }

    public void setElement(int row, int column, int value) {
        if((row >= rows) || (column >= columns) || (row < 0) || (column < 0)) {
            throw new IndexOutOfBoundsException("Matrix::setElement() out of mas [" + row + "][" + column +"]");
        }
        matr[row][column] = value;
    }


    public int getElement(int row, int column) {
        if((row >= rows) || (column >= columns) || (row < 0) || (column < 0)) {
            throw new IndexOutOfBoundsException("Matrix::getElement() out of mas [" + row + "][" + column +"]");
        }
        return matr[row][column];
    }

    @Override
    public boolean equals(Object m){
        try {
            if (!(m instanceof UsualMatrix)) {
                return false;
            }
            UsualMatrix tmp;
            tmp = (UsualMatrix) m;
            if (rows != tmp.rows() || columns != tmp.columns()) {
                return false;
            }
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (this.getElement(i, j) != tmp.getElement(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e){
            System.out.println("UsualMatrix::equals() Error");
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                res.append(this.getElement(i, j));
                res.append(" ");
            }
            res.append("\n");
        }
        res.append("\n");
        return res.toString();
    }
}
