package org.suai.user;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.io.IOException;


public class RegistrationServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher pageDispatcher = request.getRequestDispatcher("users");

		request.setAttribute("action", "registration");
		pageDispatcher.forward(request, response);
	}

}