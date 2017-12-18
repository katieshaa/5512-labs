import java.io.IOException;

import java.net.DatagramSocket;


public class Server {
 	 
	public static void main(String[] args) {
		if(args.length >= 1) {
			DatagramSocket socket = null;

			try {
				socket = new DatagramSocket(Integer.valueOf(args[0]));

				Client client = new Client(socket);
				client.start(null, "Server");
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
			System.out.println("ERROR: pls set port");
		}
	}
	
}
