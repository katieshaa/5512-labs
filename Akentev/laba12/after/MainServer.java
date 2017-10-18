import java.net.ServerSocket;

import java.io.IOException;

import org.suai.server.Server;

import org.suai.console.ConsoleHandler;


public class MainServer {

	public static void main(String[] args) {
		if(args.length < 1) {
			ConsoleHandler.printSystemMessage("You must specify the server port.", null);
		}
		else {
			int port = 0;
			ServerSocket serverSocket = null;
			Throwable error = null;

			try {
				port = Integer.valueOf(args[0]);
				serverSocket = new ServerSocket(port);
			}
			catch(IllegalArgumentException exception) {
				error = exception;
				ConsoleHandler.printSystemMessage("Port must be number, which is between [0; 65535].", exception);
			}
			catch(SecurityException exception) {
				error = exception;
				ConsoleHandler.printSystemMessage("Security managers checkListen method doesn't allow the operation.", exception);
			}
			catch(IOException exception) {
				error = exception;
				ConsoleHandler.printSystemMessage("No access to the port.", exception);
			}

			if(error == null) {
				try(
					Server server = new Server(serverSocket);
				) {
					server.clientHandler();
				}
				catch(IOException exception) {
					ConsoleHandler.printSystemMessage("I/O error occurs when waiting for a connection.", exception);
				}
			}
		}
	}

}