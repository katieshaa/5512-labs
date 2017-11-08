package org.suai.board;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Answer {

	private String author;
	private String text;
	private LocalDateTime date;


	public Answer(String author, String text) {
		this.author = author;
		this.text = text;
		this.date = LocalDateTime.now();
	}


	public String toHtmlString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-uuuu | HH:mm");

		StringBuilder builder = new StringBuilder();

		builder.append("<div class='answer'>");

		builder.append("<div class='answer-info'>");
		builder.append(this.author);
		builder.append(", ");
		builder.append(this.date.format(formatter));
		builder.append("</div>");

		builder.append("<div class='answer-text'>");
		builder.append(this.text);
		builder.append("</div>");

		builder.append("</div>");

		return builder.toString();	
	}

}