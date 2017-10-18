package org.suai.server;


import java.net.ServerSocket;
import java.net.Socket;

import java.nio.channels.IllegalBlockingModeException;

import java.io.IOException;
import java.io.Closeable;

import java.util.ArrayList;

import org.suai.console.ConsoleHandler;

import org.suai.message.SystemMessage;
import org.suai.message.Message;


public class Server implements Closeable {

	private ServerSocket server;
	private ArrayList<Session> sessions = new ArrayList<>();

	private long counter = 0L;


	public Server(ServerSocket server) {
		this.server = server;
	}


	public void close() throws IOException {
		this.server.close();
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


	public synchronized void sendMessage(Message message) throws IOException {
		String to = message.getTo();

		for(Session session : this.sessions) {
			if(to == null) {
				session.send(message);
			}
			else {
				String name = session.getNameClient();

				if(name.equals(to)) {
					session.send(message);
					return;
				}
			}
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