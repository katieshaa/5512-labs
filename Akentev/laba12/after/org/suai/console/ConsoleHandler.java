package org.suai.console;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Closeable;
import java.io.IOException;

import org.suai.message.Message;


public class ConsoleHandler implements Closeable {

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));


	public void close() throws IOException {
		this.input.close();
	}


	public synchronized void write(String text) {
		System.out.print(text);
	}


	public synchronized void write(Message message) {
		System.out.println(message);
	}


	public String read() throws IOException {
		String text = this.input.readLine();
		return text.trim();
	}


	public static void printSystemMessage(String text, Throwable exception) {
		System.out.println("SYSTEM: " + text);
		if(exception != null) {
			System.out.println(exception);
		}
		System.out.println();
	}

}