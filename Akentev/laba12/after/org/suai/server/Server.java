package org.suai.server;


import java.net.ServerSocket;
import java.net.Socket;

import java.nio.channels.IllegalBlockingModeException;

import java.io.IOException;
import java.io.Closeable;

import java.util.ArrayList;
import java.util.Random;

import org.suai.console.ConsoleHandler;

import org.suai.message.SystemMessage;
import org.suai.message.Message;


public class Server implements Closeable {

	private ServerSocket server;
	private ArrayList<Session> sessions = new ArrayList<>();

	private long counter = 0L;

	private CasinoGame casino = null;


	public Server(ServerSocket server) {
		this.server = server;
	}


	public void close() throws IOException {
		this.server.close();
	}


	public CasinoGame getCasinoGame() {
		return this.casino;
	}


	public void startCasino() {
		if(this.casino == null) {
			this.casino = new CasinoGame(this);
			this.casino.start();
		}
	}


	public void removeCasino() {
		this.casino = null;
	}


	public synchronized boolean isNameAvailable(String name) {
		for(Session session : this.sessions) {
			String nameClient = session.getNameClient();

			if(nameClient.equals(name)) {
				return false;
			}
		}

		return true;
	}


	public synchronized String generateName() {
		return "Client" + (++this.counter);
	}


	private synchronized void addSession(Socket client) throws NewSessionException {
		try {
			Session session = new Session(client, this);
			this.sessions.add(session);

			session.start();
		}
		catch(IOException exception) {
			throw new NewSessionException(exception);
		}
	}


	public synchronized void removeSession(Session session) {
		this.sessions.remove(session);
	}


	private synchronized void sendTo(Session source, Message message) throws IOException {
		String to = message.getTo();

		for(Session session : this.sessions) {
			String name = session.getNameClient();

			if(name.equals(to)) {
				session.send(message);
				return;
			}
		}

		source.send(new Message("SYSTEM", null, "User not found."));
	}


	private synchronized void sendAll(Message message) throws IOException {
		for(Session session : this.sessions) {
			session.send(message);
		}
	}


	public synchronized void sendMessage(Session source, Message message) throws IOException {
		if(message.getTo() != null) {
			sendTo(source, message);
		}
		else {
			sendAll(message);
		}
	}


	public void clientHandler() throws IOException {
		while(true) {
			try {
				Socket client = this.server.accept();

				addSession(client);
			}
			catch(SecurityException exception) {
				ConsoleHandler.printSystemMessage("Security managers checkAccept method doesn't allow the operation.", exception);
			}
			catch(IllegalBlockingModeException exception) {
				ConsoleHandler.printSystemMessage("Socket has an associated channel, the channel is in non-blocking mode,"
				+ "and there is no connection ready to be accepted.", exception);
			}
			catch(NewSessionException exception) {
				ConsoleHandler.printSystemMessage("Cant creating new session.", exception);
			}
		}
	}

}
