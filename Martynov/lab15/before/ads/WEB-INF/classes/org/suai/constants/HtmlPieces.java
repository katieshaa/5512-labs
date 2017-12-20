package org.suai.constants;


public interface HtmlPieces {

	public default String getErrorPage(String title, String cause) {
		StringBuilder builder = new StringBuilder();

		builder.append("<!DOCTYPE html>");
		builder.append("<html>");

		builder.append("<head>");
		builder.append("<meta charset='utf-8'/>");
		builder.append("<title>");
		builder.append(title);
		builder.append("</title>");
		builder.append("<link rel='icon' href='" + Pathways.ICON + "'/>");
		builder.append("</head>");

		builder.append("<body>");
		builder.append("<p>ERROR</p>");
		builder.append("<p>");
		builder.append(cause);
		builder.append("</p>");
		builder.append("</body>");

		builder.append("</html>");

		return builder.toString();
	}

}