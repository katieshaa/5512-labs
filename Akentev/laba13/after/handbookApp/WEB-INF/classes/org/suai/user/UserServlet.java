package org.suai.user;


import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Iterator;

import org.suai.handbook.Pathways;

import org.suai.io.UserIO;


public class UserServlet extends HttpServlet implements Pathways {

	private ConcurrentHashMap<String, UserData> users = null;
	private UserIO io = new UserIO(PROJECT + USERS_FILE);


	public void init() {
		this.users = io.read();
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String)request.getAttribute("action");

		if(action.equals("createPhoneForm")) {
			createForm(request, response);
		}
		else if(action.equals("createHandbook")) {
			createHandbook(request, response);
		}
	}


	private void createForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();

		pw.println("<form action='users' method='post'>");

		pw.println("<table>");
		pw.println("<tr>");
		pw.println("<td>Choose user name:</td>");

		pw.println("<td>");
		pw.println(createNameSelector());
		pw.println("</td>");
		pw.println("</tr>");

		pw.println("<tr>");
		pw.println("<td>Phones:</td>");

		pw.println("<td>");
		pw.println("<input type='text' name='phones'/>");
		pw.println("</td>");
		pw.println("</tr>");

		pw.println("<tr>");
		pw.println("<td colspan='2'><input type='submit' value='Add'/></td>");
		pw.println("</tr>");
		pw.println("</table>");

		pw.println("</form>");
	}


	private String createNameSelector() {
		StringBuilder builder = new StringBuilder();

		Iterator<String> iterator = this.users.keySet().iterator();
		String name = null;

		builder.append("<select name='name'>");

		while(iterator.hasNext()) {
			name = iterator.next();
			builder.append("<option value='" + name + "'>" + name + "</option>");
		}

		builder.append("</select'>");

		return builder.toString();
	}


	private void createHandbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();

		Iterator<Map.Entry<String, UserData>> iterator = this.users.entrySet().iterator();
		Map.Entry<String, UserData> entry = null;

		pw.println("<table class='handbook'>");
		pw.println("<tr>");
		pw.println("<th></th>");
		pw.println("<th>NAME</th>");
		pw.println("<th>PHONE NUMBERS</th>");
		pw.println("</tr>");

		while(iterator.hasNext()) {
			entry = iterator.next();

			pw.println("<tr>");
			pw.println("<td class='handbook-avatar'>");
			pw.println(entry.getValue().avatarToHtml(50));
			pw.println("</td>");

			pw.println("<td class='handbook-name'>");
			pw.println(entry.getKey());
			pw.println("</td>");

			pw.println("<td class='handbook-phone'>");
			pw.println(entry.getValue().phonesToHtml());
			pw.println("</td>");
			pw.println("</tr>");
		}

		pw.println("</table>");
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher mainDispatcher = request.getRequestDispatcher("handbook");

		String nameRequest = request.getParameter("name");
		String phonesRequest = request.getParameter("phones");
		String avatarRequest = request.getParameter("avatar");

		if(nameRequest.matches("\\w+") && phonesRequest.matches("[0-9]+(,[0-9]+)*")) {
			String[] phonesTmp = phonesRequest.split(",");
				
			ArrayList<String> phones = new ArrayList<>();
			for(int i = 0; i < phonesTmp.length; i++) {
				phones.add(phonesTmp[i]);
			}

			addUser(nameRequest, phones, avatarRequest);

			response.sendRedirect("handbook");
		}
		else {
			createErrorHtml(request, response, nameRequest, phonesRequest);
		}
	}


	private void addUser(String name, ArrayList<String> phones, String avatar) {
		UserData data = this.users.get(name);

		if(data == null) {
			data = new UserData(phones, avatar);
			this.users.put(name, data);
		}
		else {
			data.merge(phones);

			if(avatar != null) {
				data.refreshAvatar(avatar);
			}
		}

		this.io.write(this.users);
	}


	private void createErrorHtml(HttpServletRequest request, HttpServletResponse response,
								String name, String phones) throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		
		RequestDispatcher headDispatcher = request.getRequestDispatcher("handbook_head.html");

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");

		headDispatcher.include(request, response);

		pw.println("<body>");
		pw.println("<p>ERROR</p>");
		pw.println("<p>Incorrect input data: username (" + name + "), phones (" + phones + ").</p>");
		pw.println("</body>");

		pw.println("</html>");
	}

}