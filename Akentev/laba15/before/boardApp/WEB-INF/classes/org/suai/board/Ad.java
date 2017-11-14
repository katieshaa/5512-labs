package org.suai.board;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Ad {

	private String header;
	private String text;
	private String author;
	private LocalDateTime date;


	public Ad(String header, String text, String author) {
		this.header = header;
		this.text = text;
		this.author = author;
		this.date = LocalDateTime.now();
	}


	public String toHtmlString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MMM-dd | HH:mm");

		StringBuilder builder = new StringBuilder();

		builder.append("<div class='board-ad'>");
		builder.append("<div class='board-ad-header'>");
		builder.append("<div class='board-ad-header-name'>" + this.header + "</div>");
		builder.append("<div class='board-ad-header-info'>" + this.author + ", " 
						+ this.date.format(formatter) + "</div>");
		builder.append("</div>");
		builder.append("<div class='board-ad-content'>" + this.text + "</div>");
		builder.append("</div>");

		return builder.toString();
	}

}