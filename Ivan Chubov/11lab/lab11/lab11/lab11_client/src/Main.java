import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Enter port:");
        Scanner scanner = new Scanner(System.in);
        int port = scanner.nextInt();
        try {
            Client client = new Client(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
