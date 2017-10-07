import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import org.suai.client.Client;

import org.suai.console.ConsoleHandler;


public class MainClient {

	public static void main(String[] args) {
		if(args.length < 2) {
			ConsoleHandler.printSystemMessage("Incorrect arguments.", null);
		}
		else {
			int port = 0;
			Socket clientSocket = null;
			Throwable error = null;

			try {
				port = Integer.valueOf(args[1]);
				clientSocket = new Socket(args[0], port);
			}
			catch(IllegalArgumentException exception) {
				error = exception;
				ConsoleHandler.printSystemMessage("Port must be number, which is between [0; 65535].", exception);
			}
			catch(UnknownHostException exception) {
				error = exception;
				ConsoleHandler.printSystemMessage("IP address of the host could not be determined.", exception);
			}
			catch(SecurityException exception) {
				error = exception;
				ConsoleHandler.printSystemMessage("Security managers checkConnect method doesn't allow the operation.", exception);
			}
			catch(IOException exception) {
				error = exception;
				ConsoleHandler.printSystemMessage("Server not responding.", exception);
			}
			

			if(error == null) {
				try(
					ConsoleHandler console = new ConsoleHandler();
					Client client = new Client(clientSocket, console);
				) {
					client.startMessaging();
				}
				catch(IOException exception) {
					ConsoleHandler.printSystemMessage("Input/Output on client doesnt work correct.", exception);
				}
			}
		}
	}

}