package org.suai.handbook;


import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;


public class NewUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter pw = response.getWriter();
		
		RequestDispatcher headDispatcher = request.getRequestDispatcher("form_head.html");
		RequestDispatcher bodyDispatcher = request.getRequestDispatcher("form_body.html");

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");

		headDispatcher.include(request, response);
		bodyDispatcher.include(request, response);

		pw.println("</html>");
	}

}