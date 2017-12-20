
/**
 * Created by MaksSklyarov on 17.02.17.
 */
package kek.maxan98.core;
import kek.maxan98.core.exc.MatrixMulException;
import kek.maxan98.core.exc.MyException;

import java.util.Random;

public class MyMatrix {
    protected int[][] data;
    protected int rows;
    protected int columns;
    protected int actual_cols;

    public MyMatrix(int row, int column) {
        data = new int[row][column];
        for (int i = 0; i != row;i++) {
            for (int j = 0; j != column; j++) {
                this.data[i][j] = 0;
            }
        }
        this.rows = row;
        this.columns = this.data[0].length;
        this.actual_cols = this.data[0].length;
    }

    @Override
    public String toString() {
        StringBuilder a = new StringBuilder();
        for(int i = 0;i!=this.getrows();i++){
            for(int j = 0;j!=this.actual_cols;j++){
                if(j == 0)
                    a.append("[");
                a.append(this.getData(i,j)+ " ");
            }
            a.append("]\n");

        }
        return a.toString();
    }

    public void init(int value){
        for(int i = 0;i < rows;i++){
            for(int j = 0;j< columns;j++){
                this.data[i][j] = value;
            }
        }
    }
    public void setData(int i,int j,int value) {
        this.data[i][j] = value;
    }

    public int getData(int i, int j){
        return this.data[i][j];
    }
    public int[][] getData(){
        return this.data;
    }
    public MyMatrix sum(MyMatrix m2){
        MyMatrix tmp = new MyMatrix(rows,actual_cols);
        tmp.zero();
        for(int i = 0;i!=rows;i++){
            for(int j = 0;j!=actual_cols;j++){
                //tmp.data[i][j] = this.data[i][j] + m2.data[i][j];
                tmp.setData(i,j,this.getData(i,j)+m2.getData(i,j));

            }
        }
        return tmp;
    }
    public void zero(){
        for(int i = 0; i != rows;i++){
            for(int j = 0; j != columns;j++){
                this.setData(i,j,0);
            }
        }
    }
    public MyMatrix mul(MyMatrix m2) throws MyException {
        if(this.actual_cols != m2.rows){
            throw new MyException(69, "Братан такие матрицы я не умножу.");

        }
        int m = getrows();
        int n = m2.actual_cols;
        int o = m2.getrows();
        MyMatrix res = new MyMatrix(m,n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    //res.data[i][j] += this.data[i][k] * m2.data[k][j];
                    res.setData(i,j,res.getData(i,j)+(this.getData(i,k)*m2.getData(k,j)));
                }
            }
        }
        return res;
    }



    public boolean equals(Object o){
        if(!(o instanceof MyMatrix)){return  false;}
        MyMatrix m2 = (MyMatrix)o;
        for (int i = 0; i < this.getrows();i++){
            for (int j = 0; j < this.getcols();j++){
                if(this.data[i][j] != m2.getData(i,j))
                    return false;

            }
        }
        return true;
    }
    public void random(){
        Random random = new Random();
        for(int i = 0 ;i<this.getrows();i++){
            for(int j = 0;j < this.actual_cols;j++){

                this.setData(i,j,random.nextInt(25));

            }
        }
    }
    public int getrows(){
        return rows;
    }
    public int getcols(){
        return columns;
    }
}
