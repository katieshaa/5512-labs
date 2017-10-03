package org.suai.client;


import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import java.io.IOException;

import java.lang.Thread;

import org.suai.console.ConsoleManager;


public class Sender extends Thread {

	private Client client;

	private ConsoleManager console;


	public Sender(Client client, ConsoleManager console) {
		this.client = client;

		this.console = console;
	}


	public void send(String message) throws IOException {
		if(this.client.getDestinationSocket() != null) {
			DatagramSocket sourceSocket = this.client.getSourceSocket();
			SocketAddress destinationSocket = this.client.getDestinationSocket();

			byte[] data = message.getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length, destinationSocket);

			sourceSocket.send(packet);
		}
		else {
			this.console.printSystemMessage("No connections.", null);
		}
	}


	public void sendMessage(String message) throws IOException {
		message = this.client.getName() + ": " + message;
		send(message);

		this.console.write(message);
	}


	private void handlerCommands(String command) throws IOException {
		if(command.startsWith("@connect")) {
			if(command.length() == 8) {
				send(command);
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@connect\".", null);
			}
		}
		else if(command.startsWith("@name")) {
			if((command.length() > 6) && (command.charAt(5) == ' ')) {
				String name = command.substring(6);
				if(!name.startsWith("SYSTEM")) {
					this.client.setName(name);
				}
				else {
					this.console.printSystemMessage("Name cant be starts with \"SYSTEM\".", null);
				}
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@name Vasya\". "
					+ "Length of the name cant be zero.", null);
			}
		}
		else if(command.startsWith("@help")) {
			if(command.length() == 5) {
				this.console.writeListCommands();
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@help\".", null);
			}
		}
		else if(command.startsWith("@quit")) {
			if(command.length() == 5) {
				this.client.getListener().interrupt();
				interrupt();
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@quit\".", null);
			}
		}
		else {
			this.console.printSystemMessage("Unknown command.", null);
		}
	}


	@Override
	public void run() {
		try {
			handlerCommands("@help");
			
			while(true) {
				String message = this.console.read();
				message = message.trim();

				if(message.startsWith("@")) {
					handlerCommands(message);
				}
				else {
					if(this.client.getDestinationSocket() != null) {
						sendMessage(message);
					}
					else {
						this.console.printSystemMessage("No connections.", null);
					}
				}
			}
		}
		catch(IOException exception) {
			if(!this.client.isClosed()) {
				this.console.printSystemMessage("Error in sender.", exception);
			}
		}
	}

}
