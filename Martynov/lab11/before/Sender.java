import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Scanner;


public class Sender extends Thread {
	
	private String name;
	private DatagramSocket socket;
	private SocketAddress destination;
	

	public Sender(DatagramSocket socket, SocketAddress destination, String name) {
		this.socket = socket;
		this.name = name;
		this.destination = destination;
	}


	public void setDestination(SocketAddress destination) {
		this.destination = destination;
	}
	

	public void run() {
		try {
			Scanner in = new Scanner(System.in);
			String message = null;
			byte[] data = null;
			DatagramPacket packet = null;

			while(true) {
				message = in.nextLine();
				
				if(message.equals("@quit")) {
					socket.close();

					return;
				}
				else if(message.startsWith("@name ") && message.length() > 6) {
					name = message.substring(6);
				}
				else if(destination != null) {
					if(!message.equals("@join")) {
						message = name + ": " + message;
						System.out.println(message);
					}

					data = message.getBytes();
					
					packet = new DatagramPacket(data, data.length, destination);
			
					socket.send(packet);
				}
				else {
					System.out.println("ERROR: need set destination");
				}
			}
		}
		catch(IOException exception) {
			if(!socket.isClosed()) {
				System.out.println(exception);
			}
		}
	}
	
}