package org.suai.client;


import java.io.IOException;
import java.io.ObjectInputStream;

import java.lang.Thread;

import org.suai.console.ConsoleHandler;

import org.suai.message.SystemMessage;
import org.suai.message.Message;


public class Listener extends Thread {

	private Client client;
	private ConsoleHandler console;


	public Listener(Client client, ConsoleHandler console) {
		this.client = client;
		this.console = console;
	}


	private void nameHandler(String value) {
		if(value == null) {
			ConsoleHandler.printSystemMessage("Name already taken.", null);
		}
		else {
			this.client.setName(value);
			ConsoleHandler.printSystemMessage("Your new name is " + value + ".", null);
		}
	}


	private void casinoHandler(String value) {
		Message message = new Message("CASINO", null, value);
		this.console.write(message);
	}


	private void systemMessageHandler(SystemMessage systemMessage) {
		String command = systemMessage.getFrom();
		String value = systemMessage.getText();

		if(command.equals("@name")) {
			nameHandler(value);
		}
		else if(command.equals("@casino")) {
			casinoHandler(value);
		}
	}


	@Override
	public void run() {
		ObjectInputStream input = this.client.getInputStream();

		try {
			while(true) {
				Object object = input.readObject();

				if(object instanceof Message) {
					Message message = (Message)object;
					this.console.write(message);
				}
				else {
					SystemMessage systemMessage = (SystemMessage)object;
					systemMessageHandler(systemMessage);
				}
			}
		}
		catch(IOException exception) {
			if(!this.client.isClosed()) {
				ConsoleHandler.printSystemMessage("Server not respond.", exception);
			}
		}
		catch(ClassNotFoundException exception) {}
	}

}