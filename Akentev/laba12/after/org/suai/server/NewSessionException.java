package org.suai.server;


public class NewSessionException extends Exception {

	public NewSessionException(Throwable cause) {
		super(cause);
	}


	public NewSessionException(String text) {
		super(text);
	}

}