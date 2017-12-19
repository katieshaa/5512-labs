package server;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter port:");
        Scanner in = new Scanner(System.in);
        int port = in.nextInt();
        try {
            Server server = new Server(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}