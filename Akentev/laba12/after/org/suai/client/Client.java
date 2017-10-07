package org.suai.client;


import java.net.Socket;

import java.io.Closeable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import org.suai.console.ConsoleHandler;

import org.suai.message.SystemMessage;
import org.suai.message.Message;


public class Client implements Closeable {

	private Socket client;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ConsoleHandler console;

	private volatile String name = null;
	private boolean messaging = true;
	private Listener listener = null;


	public Client(Socket client, ConsoleHandler console) throws IOException {
		this.client = client;
		this.output = new ObjectOutputStream(client.getOutputStream());
		this.input = new ObjectInputStream(client.getInputStream());
		this.console = console;
	}


	public void close() throws IOException {
		this.client.close();
	}


	public boolean isClosed() {
		return this.client.isClosed();
	}


	public ObjectInputStream getInputStream() {
		return this.input;
	}


	public void setName(String name) {
		this.name = name;
	}


	private void waitName() {
		ConsoleHandler.printSystemMessage("Waiting for the server to send the name.", null);
		while(this.name == null) {;}
	}


	private void sendSystemMessage(String command, String value) throws IOException {
		SystemMessage systemMessage = new SystemMessage(command, value);
		this.output.writeObject(systemMessage);
	}


	private void sendMessage(String to, String text) throws IOException {
		Message message = new Message(this.name, to, text);
		this.output.writeObject(message);
	}


	private void nameHandler(String command) throws IOException {
		if((command.length() > 6) && (command.charAt(5) == ' ')) {
			String name = command.substring(6);

			if(!name.startsWith("SYSTEM") && !name.startsWith("CASINO")) {
				sendSystemMessage("@name", name);
			}
			else {
				ConsoleHandler.printSystemMessage("Name cant starts with \"SYSTEM\" or \"CASINO\".", null);
			}
		}
		else {
			ConsoleHandler.printSystemMessage("Incorrect command. Must be \"@name your_name\". "
				+ "Length of the name must be > 0. Also name cant starts with \"SYSTEM\".", null);
		}
	}


	private void quitHandler(String command) {
		if(command.length() == 5) {
			this.listener.interrupt();
			this.messaging = false;
		}
		else {
			ConsoleHandler.printSystemMessage("Incorrect command. Must be \"@quit\"", null);
		}
	}


	private void senduserHandler(String command) throws IOException {
		if((command.length() > 10) && (command.charAt(9) == ' ')) {
			String to = command.substring(10);

			this.console.write(to + "< ");
			String text = this.console.read();

			sendMessage(to, text);
		}
		else {
			ConsoleHandler.printSystemMessage("Incorrect command. Must be \"@senduser name_user\".", null);
		}
	}


	private void sendBet(String command, String bet) throws IOException {
		try {
			int number = Integer.valueOf(bet);

			if(number >= 0) {
				sendSystemMessage(command, bet);
			}
			else {
				ConsoleHandler.printSystemMessage("Incorrect bet. Must be a positive number.", null);
			}
		}
		catch(IllegalArgumentException exception) {
			ConsoleHandler.printSystemMessage("Incorrect bet. Must be a positive number.", null);
		}
	}


	private void casinoHandler(String command) throws IOException {
		if(command.length() == 7) {
			sendSystemMessage("@casino", null);
		}
		else if((command.length() > 8) && (command.charAt(7) == ' ')) {
			sendBet("@casino", command.substring(8));
		}
		else {
			ConsoleHandler.printSystemMessage("Incorrect command. Must be \"@casino\" or \"@casino bet\".", null);
		}
	}


	private void betHandler(String command) throws IOException {
		if((command.length() > 5) && (command.charAt(4) == ' ')) {
			sendBet("@bet", command.substring(5));
		}
		else {
			ConsoleHandler.printSystemMessage("Incorrect command. Must be \"@bet number\".", null);
		}
	}


	private void commandHandler(String command) throws IOException {
		if(command.startsWith("@name")) {
			nameHandler(command);
		}
		else if(command.startsWith("@quit")) {
			quitHandler(command);
		}
		else if(command.startsWith("@senduser")) {
			senduserHandler(command);
		}
		else if(command.startsWith("@casino")) {
			casinoHandler(command);
		}
		else if(command.startsWith("@bet")) {
			betHandler(command);
		}
		else {
			ConsoleHandler.printSystemMessage("Unknown command.", null);
		}
	}


	public void startMessaging() throws IOException {
		this.listener = new Listener(this, console);
		this.listener.start();

		sendSystemMessage("@name", null);
		waitName();

		while(this.messaging) {
			String text = this.console.read();

			if(text.startsWith("@")) {
				commandHandler(text);
			}
			else {
				sendMessage(null, text);
			}
		}
	}

}