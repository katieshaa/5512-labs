package lab5.matrix;
import lab5.exception.MyException;

import java.util.Random;

public class UsualMatrix implements IMatrix {
    protected int[][] array;
    public  UsualMatrix (int _row, int _column, boolean _rand) {
        array = new int[_row][_column];
        for (int i = 0; i < _row; i++) {
            for (int j = 0; j < _column; j++) {
                if (_rand) {
                    array[i][j] = (int) (Math.random() * 9);
                } else {
                    array[i][j] = 0;
                }
            }
        }
    }
    public  UsualMatrix (int _row, int _column, boolean _rand, int countRandoms) {
        array = new int[_row][_column];
        if (_rand) {
            Random rnd = new Random();

            for (int i = 0; i < countRandoms; i++) {
                int x = rnd.nextInt(_row);
                int y = rnd.nextInt(_column);

                int value = rnd.nextInt(10) + 1;
                array[x][y] = value;


            }
        }
    }

    public String toString() {
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < getRow(); i++) {
            strB.append('[');
            for (int j = 0; j < getColumn(); j++) {
                strB.append(Integer.toString(getEl(i, j)));
                if (j != getColumn() - 1)
                    strB.append(' ');
            }
            strB.append("]\n");
        }
        return strB.toString();
    }

    public IMatrix mul(IMatrix two)  {

      /*  if (getColumn() != two.getRow())
            throw new MyException("Умножение матриц невозможно!\nКоличество столбцов первой матрицы не равно количеству строк второй матрицы.");

        int m = getRow();
        int n = two.getColumn();
        int n2 = getRow();
        UsualMatrix temp_m = new UsualMatrix(m, n,false);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = 0;
                for (int k = 0; k < n2; k++) {
                    temp += getEl(i, k) * two.getEl(i, k);
                }
                temp_m.setEl(i, j, temp);
            }
        }

        return temp_m;*/
        if (getColumn() != two.getRow())
            throw new MyException("Умножение матриц невозможно!\nКоличество столбцов первой матрицы не равно количеству строк второй матрицы.");

        int m = getRow();
        int n = two.getColumn();
        int n2 = two.getRow();
        UsualMatrix temp_m = new UsualMatrix(m, n,false);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = 0;
                for (int k = 0; k < n2; k++) {
                    temp += getEl(i, k) * two.getEl(k, j);
                }
                temp_m.setEl(i, j, temp);
            }
        }

        return temp_m;
    }

    public IMatrix sum(IMatrix two){
        if (getRow() != two.getRow() && this.getColumn() != two.getColumn())
            throw new MyException("Сложение матриц невозможно!\nМатрицы не квадратные, или разного размера.");
        UsualMatrix temp_m = new UsualMatrix(getRow(), getColumn(), false);
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getColumn(); j++) {
                //array[i][j] += two.g etEl(i, j);
                //temp_m.array[i][j] = this.array[i][j];
                temp_m.setEl(i, j, this.getEl(i, j) + two.getEl(i, j));
            }
        }
        return temp_m;
    }



    public int getRow() {
        return array.length;
    }

    public int getColumn() {
        return array[0].length;
    }

    public int getEl(int i, int j) {
        if (i >= this.getRow() || j >= this.getColumn() || i < 0 || j < 0)
            throw new MyException("Не верно заданы индексы в методе getEl(int i, int j)");
        return array[i][j];
    }



    public void setEl(int i, int j, int value) {
        if (i >= this.getRow() || j >= this.getColumn() || i < 0 || j < 0)
            throw new MyException("Не верно заданы индексы в методе setEl(int i, int j, int value)");
        array[i][j] = value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UsualMatrix)) {
            return false;
        }
        UsualMatrix m = (UsualMatrix)obj;
        if (getRow() != m.getRow()) {
            return false;
        }
        if (getColumn() != m.getColumn()) {
            return false;
        }

        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getColumn(); j++) {
                if (array[i][j] != m.array[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


}
