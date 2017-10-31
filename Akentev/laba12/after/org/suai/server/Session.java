package org.suai.server;


import java.net.Socket;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Closeable;

import java.lang.Thread;

import org.suai.console.ConsoleHandler;

import org.suai.message.SystemMessage;
import org.suai.message.Message;


public class Session extends Thread implements Closeable {

	private String nameClient;
	private Socket client;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	private Server server;


	public Session(Socket client, Server server) throws IOException {
		this.nameClient = server.generateName();
		this.client = client;
		this.output = new ObjectOutputStream(client.getOutputStream());
		this.input = new ObjectInputStream(client.getInputStream());

		this.server = server;
	}


	public void close() throws IOException {
		this.client.close();
	}


	public String getNameClient() {
		return this.nameClient;
	}


	public void send(SystemMessage message) throws IOException {
		this.output.writeObject(message);
	}


	private SystemMessage nameHandler(String command, String value) {
		if(value == null) {
			value = this.nameClient;
		}
		else {
			boolean result = this.server.isNameAvailable(value);

			if(result) this.nameClient = value;
			else value = null;
		}

		return new SystemMessage(command, value);
	}


	private SystemMessage betHandler(String command, String value) {
		CasinoGame casino = this.server.getCasinoGame();
		int defaultBet = CasinoGame.DEFAULT_BET;

		if(casino != null) {
			int bet = ((value == null) ? defaultBet : Integer.valueOf(value));
			return casino.setBet(this, bet);
		}
		else {
			return new SystemMessage(command, "Casino closed.");
		}
	}


	private SystemMessage systemMessageHandler(SystemMessage message) {
		String command = message.getFrom();
		String value = message.getText();

		if(command.equals("@name")) {
			return nameHandler(command, value);
		}
		else if(command.equals("@casino")) {
			this.server.startCasino();
			return betHandler(command, value);
		}
		else if(command.equals("@bet")) {
			return betHandler("@casino", value);
		}

		return new SystemMessage("@", null);
	}


	private void finishSession() {
		String problem = this.nameClient + " disconnect.";

		this.server.removeSession(this);
		try {
			close();
		}
		catch(IOException closeException) {}

		ConsoleHandler.printSystemMessage(problem, null);
		try {
			this.server.sendMessage(this, new Message("SYSTEM", null, problem));
		}
		catch(IOException sendException) {}
	}


	@Override
	public void run() {
		try {
			while(true) {
				Object object = this.input.readObject();

				if(object instanceof Message) {
					Message message = (Message)object;

					this.server.sendMessage(this, message);
				}
				else {
					SystemMessage systemMessage = (SystemMessage)object;

					SystemMessage answer = systemMessageHandler(systemMessage);
					send(answer);
				}
			}
		}
		catch(IOException exception) {
			finishSession();
		}
		catch(ClassNotFoundException exception) {}
	}

}
