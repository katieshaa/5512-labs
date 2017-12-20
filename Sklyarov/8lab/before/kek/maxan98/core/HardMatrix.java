package kek.maxan98.core;

import kek.maxan98.core.exc.MatrixMulException;
import kek.maxan98.core.exc.MatrixSumException;
import kek.maxan98.core.exc.MyException;

/**
 * Created by MaksSklyarov on 22.03.17.
 */
public class HardMatrix extends MyMatrix  {


public HardMatrix(int row, int column){
    super(row, column/2 );
    actual_cols = column;


}

public void setData(int i, int j, int value){
    if(j >= columns){
        j = j - columns;
    }
    this.data[i][j] = value;
}
public int getData(int i, int j){

        if(j >= columns){
            j = j - columns;
        }
        return this.data[i][j];

}
    public String toString() {
        StringBuilder a = new StringBuilder();
        for(int i = 0;i!=this.getrows();i++){
            for(int j = 0;j!=this.actual_cols;j++){
                if(j == 0)
                    a.append("[");
                a.append(this.getData(i,j) + " ");
            }
            a.append("]\n");

        }
        return a.toString();
    }

}

