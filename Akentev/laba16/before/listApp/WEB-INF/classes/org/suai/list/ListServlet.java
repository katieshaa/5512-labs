package org.suai.list;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.ArrayList;

import org.suai.io.ListIO;


public class ListServlet extends HttpServlet implements Pathways, HTMLConstants {


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();

		RequestDispatcher headDispatcher = request.getRequestDispatcher(HEAD_MAIN);

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");

		headDispatcher.include(request, response);

		pw.println("<body>");
		pw.println("<div class='" + WRAPPER_CLASS + "'>");
		pw.println(getListHTML());
		pw.println("</div>");
		pw.println("</body>");

		pw.println("</html>");
	}


	private String getListHTML() {
		ListIO io = new ListIO(PROJECT + LIST_FILE);
		ArrayList<ArrayList<String>> list = io.read();

		return arrayToHTML(list);
	}


	private String arrayToHTML(ArrayList<ArrayList<String>> list) {
		StringBuilder builder = new StringBuilder();

		ArrayList<String> sublist = null;

		builder.append("<ol>");

		for(int i = 0; i < list.size(); i++) {
			sublist = list.get(i);

			if(sublist.size() > 1) {
				builder.append(generateDropdownHTML(sublist));
			}
			else if(sublist.size() == 1) {
				builder.append("<li>").append(sublist.get(0)).append("</li>");
			}
		}

		builder.append("</ol>");

		return builder.toString();
	}


	private String generateDropdownHTML(ArrayList<String> list) {
		StringBuilder builder = new StringBuilder();

		builder.append("<li class='").append(DROPDOWN_CLASS).append("'>");

		builder.append("<div class='").append(DROPDOWN_HEADER_CLASS)
			   .append("'>");
		builder.append(list.get(0));
		builder.append("</div>");

		builder.append("<a class='").append(DROPDOWN_MANAGER_CLASS)
			   .append("' onclick='").append(ONCLICK_DROPDOWN_LIST)
			   .append("' href='#'>");
		builder.append("[+]");
		builder.append("</a>");

		builder.append("<ul class='").append(DROPDOWN_LIST_CLASS)
			   .append("'>");

		for(int i = 1; i < list.size(); i++) {
			builder.append("<li>").append(list.get(i)).append("</li>");
		}

		builder.append("</ul>");
		builder.append("</li>");

		return builder.toString();
	}

}