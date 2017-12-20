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


	public String getAuthor() {
		return this.author;
	}


	public String toHtmlString(String author, int id) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MMM-dd | HH:mm");

		StringBuilder builder = new StringBuilder();

		builder.append("<div class='board-ad'>");
		builder.append("<div class='board-ad-header'>");
		builder.append("<div class='board-ad-header-name'>" + this.header + "</div>");
		builder.append("<div class='board-ad-header-info'>" + this.author + ", " 
						+ this.date.format(formatter) + "</div>");
		builder.append("</div>");
		builder.append("<div class='board-ad-content'>" + this.text + "</div>");

		if(this.author.equals(author)) {
			builder.append("<form action='board' method='post' class='board-ad-delete'>");
			builder.append("<input type='hidden' name='action' value='delete-ad'/>");
			builder.append("<input type='hidden' name='id' value='")
			       .append(id).append("' />");
			builder.append("<input type='submit' value='Delete' class='board-ad-delete-button'/>");
			builder.append("</form>");
		}

		builder.append("</div>");

		return builder.toString();
	}

}
