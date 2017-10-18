package org.suai.client;


import java.io.IOException;
import java.io.ObjectInputStream;

import java.lang.Thread;

import org.suai.console.ConsoleHandler;

import org.suai.message.SystemMessage;
import org.suai.message.Message;


public class Listener extends Thread {

	private Client client;


	public Listener(Client client) {
		this.client = client;
	}


	private void handlerSystemMessage(SystemMessage systemMessage) {
		String command = systemMessage.getFrom();
		String value = systemMessage.getText();

		if(command.equals("@name")) {
			if(value == null) {
				ConsoleHandler.printSystemMessage("Name already taken.", null);
			}
			else {
				this.client.setName(value);
				ConsoleHandler.printSystemMessage("Your new name is " + value + ".", null);
			}
		}
	}


	@Override
	public void run() {
		ObjectInputStream input = this.client.getInputStream();
		ConsoleHandler console = this.client.getConsoleHandler();

		try {
			while(true) {
				Object object = input.readObject();

				if(object instanceof Message) {
					Message message = (Message)object;
					console.write(message);
				}
				else {
					SystemMessage systemMessage = (SystemMessage)object;
					handlerSystemMessage(systemMessage);
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