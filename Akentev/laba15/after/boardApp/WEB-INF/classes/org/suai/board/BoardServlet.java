package org.suai.board;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.suai.constants.Pathways;
import org.suai.constants.HtmlPieces;


public class BoardServlet extends HttpServlet implements Pathways, HtmlPieces {

	private List<Ad> ads = Collections.synchronizedList(new ArrayList<Ad>());


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();

		RequestDispatcher headDispatcher = request.getRequestDispatcher(HEAD_BOARD);
		RequestDispatcher headerDispatcher = request.getRequestDispatcher(HEADER_BOARD);

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");

		headDispatcher.include(request, response);

		pw.println("<body>");
		headerDispatcher.include(request, response);

		pw.println("<div class='board'>");
		for(int i = 0; i < this.ads.size(); i++) {
			Ad ad = this.ads.get(i);

			pw.println(ad.toHtmlString(i));
		}
		pw.println("</div>");
		pw.println("</body>");

		pw.println("</html>");
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);

		String action = (String)request.getParameter("action");

		if(session != null) {
			if("add-ad".equals(action)) {
				handlerAd(request, response);
			}
			else if("add-answer".equals(action)) {
				handlerAdAnswer(request, response);
			}
			else {
				pw.println(getErrorPage("ERROR action", "Action doesnt exist."));
			}
		}
		else {
			pw.println(getErrorPage("ERROR login", "Please login."));
		}
	}


	private boolean checkInputData(HttpServletResponse response, String ... data) throws IOException {
		PrintWriter pw = response.getWriter();

		for(int i = 0; i < data.length; i++) {
			if(data[i] == null || data[i].length() < 1) {
				pw.println(getErrorPage("ERROR input data", "Data length must be > 0."));

				return false;
			}
		}

		return true;
	}


	private void handlerAd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);

		String author = (String)session.getAttribute("name");
		String header = (String)request.getParameter("header");
		String text = (String)request.getParameter("text");

		if(checkInputData(response, author, header, text)) {
			this.ads.add(new Ad(author, header, text));

			response.sendRedirect("board");
		}
	}


	private void handlerAdAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);

		String author = (String)session.getAttribute("name");
		String id = (String)request.getParameter("id");
		String text = (String)request.getParameter("text");

		int index = -1;
		try {
			index = Integer.valueOf(id);
		}
		catch(NumberFormatException exception) {}

		if(checkInputData(response, author, text)) {
			if(index >= 0 && index < this.ads.size()) {
				this.ads.get(index).addAnswer(new Answer(author, text));
			}

			response.sendRedirect("board");
		}
	}

}