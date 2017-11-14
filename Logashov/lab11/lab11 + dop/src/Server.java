import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

/**
 * Created by denis on 21.09.2017.
 */
public class Server
{
    public static void main(String[] args) throws IOException {

        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(args[0]));
        Scanner scanner = new Scanner(System.in);
        String msg;
        System.out.println("Welcome to Server side");
        byte[] receiveData = new byte[1024];
        DatagramPacket packet;

        while(true)
        {
            packet = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(packet);
            if(packet.getAddress() != null)
                break;
        }
        InetAddress ipAddress = packet.getAddress();
        int port = packet.getPort();
        System.out.println("client is connected");
        inThread in = new inThread(serverSocket, new File(".").getCanonicalPath());
        in.start();

        while(true)
        {
            msg = scanner.nextLine();
            //отправка сообщения
            byte[] sendData = new byte[1024];
            sendData = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            serverSocket.send(sendPacket);
	    if(msg.contains("@quit"))
	    {
		System.exit(-1);
	    }

            /*byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePackt = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePackt);

            //massege = "";
            massege = new String(receivePackt.getData());
            System.out.println("RECEIVED:" + massege);*/

            /*InetAddress ipAddress = receivePackt.getAddress();
            int port = receivePackt.getPort();
            String newMassege = massege.toUpperCase();
            sendData = newMassege.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ipAddress,port);
            serverSocket.send(sendPacket);
           */
        }
    }

}

