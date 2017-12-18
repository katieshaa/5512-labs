import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by root on 24.10.17 with love.
 */
public class Main {

    public static void main(String[] args) {
        int N;  //computer
        int M;  //tourist

        System.out.println("Enter the number of tourist");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        M = in.nextInt();
        System.out.println("Enter the number of computer");
        N = in.nextInt();


        Computer computer = new Computer(N, M);
        try {
            computer.startCafe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All computer are empty");
    }
}
