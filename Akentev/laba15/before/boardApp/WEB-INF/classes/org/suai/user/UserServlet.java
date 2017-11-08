package org.suai.user;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.concurrent.ConcurrentHashMap;

import org.suai.io.UserIO;
import org.suai.constants.Pathways;
import org.suai.constants.HtmlPieces;


public class UserServlet extends HttpServlet implements Pathways, HtmlPieces {

	private ConcurrentHashMap<String, String> users = null;
	private UserIO io = new UserIO(PROJECT + USERS_FILE);


	public void init() {
		this.users = this.io.read();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();

		String action = (String)request.getAttribute("action");
		String name = (String)request.getParameter("name");
		String password = (String)request.getParameter("password");

		if(name == null || name.length() < 1
		|| password == null || password.length() < 1) {
			pw.println(getErrorPage("ERROR input data", "Incorrect name or password. Length must be > 0."));

			return;
		}

		if("registration".equals(action)) {
			if(!this.users.containsKey(name)) {
				this.users.put(name, password);
				this.io.write(users);

				createSession(request, response, name);
			}
			else {
				pw.println(getErrorPage("ERROR name", "Name already exists."));
			}
		}
		else if("login".equals(action)) {
			if(password.equals(this.users.get(name))) {
				createSession(request, response, name);
			}
			else {
				pw.println(getErrorPage("ERROR input data", "Incorrect name or password."));	
			}
		}
	}


	private void createSession(HttpServletRequest request, HttpServletResponse response, String name) throws IOException {
		HttpSession session = request.getSession();
		session.setAttribute("name", name);

		response.sendRedirect("board");
	}

}