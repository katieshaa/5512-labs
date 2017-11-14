package org.suai.board;


import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Ad {

	private String author;
	private String header;
	private String text;
	private LocalDateTime date;
	private ArrayList<Answer> answers;


	public Ad(String author, String header, String text) {
		this.author = author;
		this.header = header;
		this.text = text;
		this.date = LocalDateTime.now();
		this.answers = new ArrayList<>();
	}


	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}


	public String toHtmlString(int id) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-uuuu | HH:mm");

		StringBuilder builder = new StringBuilder();

		builder.append("<div class='board-ad'>");

		builder.append("<div class='board-ad-header'>");
		builder.append("<div class='board-ad-header-name'>");
		builder.append(this.header);
		builder.append("</div>");

		builder.append("<div class='board-ad-header-info'>");
		builder.append(this.author);
		builder.append(", ");
		builder.append(this.date.format(formatter));
		builder.append("</div>");
		builder.append("</div>");

		builder.append("<div class='board-ad-content'>");
		builder.append(this.text);
		builder.append("</div>");
		
		builder.append("<div class='board-ad-answer-button'>");
		builder.append("<a href='new_answer.jsp?id=" + id + "' class='answer-button'>Answer</a>");
		builder.append("</div>");

		builder.append("<div class='board-ad-answers'>");
		for(int i = this.answers.size() - 1; i >= 0; i--) {
			builder.append(this.answers.get(i).toHtmlString());
		}
		builder.append("</div>");

		builder.append("</div>");

		return builder.toString();
	}

}