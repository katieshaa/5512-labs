import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;


public class Client {
	
	private final int SIZE = 1024;

	private DatagramSocket socket;
	private Sender sender = null;
	

	public Client(DatagramSocket socket) {
		this.socket = socket;
	}
	
	
	public void start(SocketAddress destination, String name) throws IOException {
		byte[] receivedMessage = null;
		DatagramPacket packet = null;
		String message = null;

		sender = new Sender(socket, destination, name);
		sender.start();
		
		while(true) {
			receivedMessage = new byte[SIZE];
			packet = new DatagramPacket(receivedMessage, SIZE);
			socket.receive(packet);

			message = new String(packet.getData());
			message = message.trim();
			
			if(message.equals("@join")) {
				sender.setDestination(packet.getSocketAddress());
			}
			else {
				System.out.println(message);	
			}
		}
	}

	public static void main(String[] args) {
		if(args.length >= 2) {
			DatagramSocket socket = null;
			DatagramPacket packet = null;
			SocketAddress destination = null;
			byte[] join = "@join".getBytes();

			try {
				destination = new InetSocketAddress(args[0], Integer.valueOf(args[1]));
				socket = new DatagramSocket();

				packet = new DatagramPacket(join, join.length, destination);
				socket.send(packet);

				Client client = new Client(socket);
				client.start(destination, "Client");
			}
			catch(IOException exception) {
				if(socket != null && !socket.isClosed()) {
					System.out.println(exception);
				}
			}
			catch(RuntimeException exception) {
				System.out.println(exception);
			}
		}
		else {
			System.out.println("ERROR: pls set address and port");
		}
	}
	
}