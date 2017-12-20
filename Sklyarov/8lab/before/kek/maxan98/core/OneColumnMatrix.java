package kek.maxan98.core;

/**
 * Created by MaksSklyarov on 21.04.17.
 */
public class OneColumnMatrix extends MyMatrix {
    public OneColumnMatrix(int row, int column){
        super(row, 1);
        actual_cols = column;
    }
    public void setData(int i, int j, int value){
        if(j > 0){
           j = 0;
        }
        this.data[i][j] = value;
    }
    public int getData(int i, int j){

        if(j > 0){
            j = 0;
        }
        return this.data[i][j];

    }

}
