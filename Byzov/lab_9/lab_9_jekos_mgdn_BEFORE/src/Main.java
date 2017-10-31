/* 
	| Synchronized |

	Написать программу, приводящую к ситуации взаимной блокировки (deadlock).
*/
import java.util.Random;

import org.suai.matrix.UsualMatrix;
import org.suai.matrix.CellsProduct;
import org.suai.deadlock.DeadLock;


public class Main {

    public static void main(String[] args) {
        try {
            UsualMatrix firstMatrix = getRandomMatrix(10, 10, 10);

            UsualMatrix secondMatrix = getRandomMatrix(10, 10, 10);

            DeadLock.deadLockProductMatrix(firstMatrix, secondMatrix);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static UsualMatrix getRandomMatrix(int rows, int columns, int module) {
        Random random = new Random();

        UsualMatrix result = new UsualMatrix(rows, columns);

        for(int i = 0; i < result.getRows(); i++) {
            for(int j = 0; j < result.getColumns(); j++) {
                result.setElement(i, j, random.nextInt() % module);
            }
        }

        return result;
    }

}