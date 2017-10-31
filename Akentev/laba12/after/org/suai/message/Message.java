package org.suai.message;


public class Message extends SystemMessage {

	private String to;


	public Message(String from, String to, String text) {
		super(from, text);
		this.to = to;
	}


	public String getTo() {
		return this.to;
	}


	@Override
	public String toString() {
		return getFrom() + ((this.to != null) ? (" (private): ") : (": "))
				+ getText();
	}

}