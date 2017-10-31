package org.suai.handbook;


import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;


public class NewPhoneServlet extends HttpServlet implements Pathways {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();

		RequestDispatcher headDispatcher = request.getRequestDispatcher("form_head.html");
		RequestDispatcher formDispatcher = request.getRequestDispatcher("users");

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");

		headDispatcher.include(request, response);

		pw.println("<body>");
		request.setAttribute("action", "createPhoneForm");
		formDispatcher.include(request, response);
		pw.println("</body>");
		
		pw.println("</html>");
	}

}