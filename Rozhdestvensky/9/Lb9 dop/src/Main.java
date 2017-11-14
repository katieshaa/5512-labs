import cafe.Computer;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Vengr on 05.11.2017.
 */

/*

2. [бассеин ресурсов] В маленьком интернет кафе в деревне есть N компьютеров. Кафе работает по принципу FIFO.
В кафе приходит М туристов. Турист проводит за компьютером от 15 минут до 2 часов (время выбирается случайно).
Напишите программу, которая имитирует порядок доступа к компьютерам в кафе и выводит журнал доступа на экран во время работы.
Необходимо так выбрать масштаб времени в имитации что бы время выполненя программы было меньше 3 минут.

*/
public class Main {
    public static void main(String[] args) {

        int n; //Computs
        int m; //Tourists

        System.out.print("Number of Computers = ");
        Scanner in = new Scanner(new InputStreamReader(System.in));
        n = in.nextInt();
        System.out.print("Number of Tourists = ");
        m = in.nextInt();

        Computer computer = new Computer(m, n);
        computer.cafe();
        System.out.println("The place is empty. See you space cowboy..");

    }
}
