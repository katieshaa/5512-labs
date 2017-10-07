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


	public ConsoleHandler getConsoleHandler() {
		return this.console;
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


	private void commandHandler(String command) throws IOException {
		if(command.startsWith("@name")) {
			if((command.length() > 6) && (command.charAt(5) == ' ')) {
				String name = command.substring(6);

				if(!name.startsWith("SYSTEM")) {
					sendSystemMessage("@name", name);
				}
				else {
					ConsoleHandler.printSystemMessage("Name cant starts with \"SYSTEM\".", null);
				}
			}
			else {
				ConsoleHandler.printSystemMessage("Incorrect command. Must be \"@name your_name\". "
					+ "Length of the name must be > 0. Also name cant starts with \"SYSTEM\".", null);
			}
		}
		else if(command.startsWith("@quit")) {
			if(command.length() == 5) {
				this.listener.interrupt();
				this.messaging = false;
			}
			else {
				ConsoleHandler.printSystemMessage("Incorrect command. Must be \"@quit\"", null);
			}
		}
		else if(command.startsWith("@senduser")) {
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
		else {
			ConsoleHandler.printSystemMessage("Unknown command.", null);
		}
	}


	public void startMessaging() throws IOException {
		this.listener = new Listener(this);
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