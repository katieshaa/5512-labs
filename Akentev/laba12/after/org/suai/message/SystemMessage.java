package org.suai.message;


import java.io.Serializable;


public class SystemMessage implements Serializable  {

	private String from;
	private String text;


	public SystemMessage(String from, String text) {
		this.from = from;
		this.text = text;
	}


	public String getFrom() {
		return this.from;
	}


	public String getText() {
		return this.text;
	}

}