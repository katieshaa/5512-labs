package org.suai.handbook;


import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;


public class NewUserServlet extends HttpServlet implements Pathways {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter pw = response.getWriter();

		RequestDispatcher headDispatcher = request.getRequestDispatcher("form_head.html");

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");

		headDispatcher.include(request, response);
		pw.println(createBody());

		pw.println("</html>");
	}


	private String createBody() {
		StringBuilder builder = new StringBuilder();

		builder.append("<form action='users' method='post'>");

		builder.append("<table>");
		builder.append("<tr>");
		builder.append("<td>User name:</td>");

		builder.append("<td>");
		builder.append("<input type='text' name='name'/>");
		builder.append("</td>");
		builder.append("</tr>");

		builder.append("<tr>");
		builder.append("<td>Phones:</td>");

		builder.append("<td>");
		builder.append("<input type='text' name='phones'/>");
		builder.append("</td>");
		builder.append("</tr>");

		builder.append(createAvatarChooser());

		builder.append("<tr>");
		builder.append("<td colspan='2'><input type='submit' value='Add'/></td>");
		builder.append("</tr>");
		builder.append("</table>");

		builder.append("</form>");

		return builder.toString();
	}


	private String createAvatarChooser() {
		File directory = new File(PROJECT + CUSTOM_AVATARS);
		String[] avatars = directory.list((File directoryTest, String nameTest) -> {
			return nameTest.endsWith(".jpg");
		});

		StringBuilder builder = new StringBuilder();
		builder.append(createAvatarHtml(DEFAULT_AVATAR, true));

		for(int i = 0; i < avatars.length; i++) {
			builder.append(createAvatarHtml(CUSTOM_AVATARS + avatars[i], false));
		}

		return builder.toString();
	}


	private String createAvatarHtml(String imageLink, boolean checked) {
		StringBuilder builder = new StringBuilder();

		builder.append("<tr>");
		builder.append("<td>");
		builder.append("<input name='avatar' type='radio' value='" + imageLink + "' " + (checked == true ? "checked" : "") + "/>");
		builder.append("</td>");
		
		builder.append("<td>");
		builder.append("<img width='100px' height='100px' src='" + imageLink + "'/>");
		builder.append("</td>");
		builder.append("</tr>");

		return builder.toString();
	}

}