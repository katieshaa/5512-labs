package lab5.main;
import lab5.matrix.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/*Напишите интерфейс IMatrix с несколькими реализациями --- UsualMatrix и расширяющий его SquareMatirx
из предыдущих заданий и SparseMatrix для разреженных матриц.
SparseMatrix должен быть реализован с помощью LinkedList (возможно, вам потребуется создать какие-то еще дополнительные классы).
Все общие методы должны быть представлены в интерфейсе IMatrix.

Напишите программу, создающую 2 случайные матрицы размером 1000x1000 с 1000 нулевых элементов в каждой двумя способами
--- с помощью обычных и разреженных матриц.
Проверьте, что сложение и умножение для разных видов матриц дает одинаковые результаты.
*/
public class Main {
    public static void main (String[] args) throws IOException {
        /*int size = 2;
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("out.txt")));


        SparseMatrix sm1 = new SparseMatrix(size ,size, false);
        SparseMatrix sm2 = new SparseMatrix(size ,size, false);
        UsualMatrix um1 = new UsualMatrix(size, size, false);
        UsualMatrix um2 = new UsualMatrix(size, size, false);

        Random rnd = new Random();


        for (int i = 0; i < size * size; i++) {
            int x1 = rnd.nextInt(size);
            int x2 = rnd.nextInt(size);

            int y1 = rnd.nextInt(size);
            int y2 = rnd.nextInt(size);

            int value1 = rnd.nextInt(10) + 1;
            int value2 = rnd.nextInt(10) + 1;

            sm1.setEl(x1, y1, value1);
            sm2.setEl(x2, y2, value2);

            um1.setEl(x1, y1, value1);
            um2.setEl(x2, y2, value2);
        }
        out.println("sm1: \n" + sm1 + "\nsm2 \n" + sm2 + "\num1: \n" + um1 + "\num2: \n" + um2);


        out.println("***Умножение sm1 * sm2***");
        out.println( sm1.mul(sm2));

        out.println("***Умножение um1 * um2***");
        out.println(um1.mul(um2));

        out.println("***Сложение sm1 + sm2***");
        out.println(sm1.sum(sm2));
        out.println("***Сложение um1 + um2***");
        out.println(um1.sum(um2));
        out.close();*/

        long t1 = System.currentTimeMillis();
        test1();
        long t2 = System.currentTimeMillis();
        System.out.println("test1(ArrayList): " + (t2 - t1) );
        t1 = System.currentTimeMillis();
        test2();
        t2 = System.currentTimeMillis();
        System.out.println("test1(SparseMatrix): " + (t2 - t1) );
    }

    public static  void test1() {
        int size = 50;
        SparseMatrixArrayList sp = new SparseMatrixArrayList(size, size);
        SparseMatrixArrayList sp2 = new SparseMatrixArrayList(size, size);

        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int val1 = rnd.nextInt(9);
                int val2 = rnd.nextInt(9);
                sp.setEl(i, j, val1);
                sp2.setEl(i, j,val2 );
            }
        }
        sp.mul(sp2);
        /*System.out.println("sp: \n" + sp);
        System.out.println("sp2: \n" + sp2);
        System.out.println(sp.mul(sp2));*/

    }
    public static  void test2() {
        int size = 50;
        SparseMatrix sp = new SparseMatrix(size, size, false);
        SparseMatrix sp2 = new SparseMatrix(size, size, false);

        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int val1 = rnd.nextInt(9);
                int val2 = rnd.nextInt(9);
                sp.setEl(i, j, val1);
                sp2.setEl(i, j,val2 );
            }
        }
        sp.mul(sp2);
        /*System.out.println("sp: \n" + sp);
        System.out.println("sp2: \n" + sp2);
        System.out.println(sp.mul(sp2));*/

    }
}
