import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to Client side");
        byte[] sendData = new byte[1024];

        Scanner scanner = new Scanner(System.in);
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress ipAddress = InetAddress.getByName(args[0]);

        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, ipAddress, 4444);
        clientSocket.send(sendPacket);

        inThread in = new inThread(clientSocket, new File(".").getCanonicalPath());
        System.out.println(new File(".").getCanonicalPath());
        in.start();

        while(true)
        {
            String msg = scanner.nextLine();
            //отправка сообщения
	        sendData = new byte[1024];
            sendData = msg.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 4444);
            clientSocket.send(sendPacket);
	    if(msg.contains("@quit"))
	    {
		System.exit(-1);
	    }

            //получение сообщения
            /*byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String newMassege = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + newMassege);*/
        }

    }
}