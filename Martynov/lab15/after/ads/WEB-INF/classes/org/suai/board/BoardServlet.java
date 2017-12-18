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
		HttpSession session = request.getSession(false);

		String author = null;

		PrintWriter pw = response.getWriter();

		RequestDispatcher headDispatcher = request.getRequestDispatcher(HEAD_BOARD);
		RequestDispatcher headerDispatcher = null;

		pw.println("<!DOCTYPE html>");
		pw.println("<html>");

		headDispatcher.include(request, response);

		if(session == null) {
			headerDispatcher = request.getRequestDispatcher(HEADER_GUEST);
		}
		else {
			author = (String) session.getAttribute("name");
			headerDispatcher = request.getRequestDispatcher(HEADER_USER);
		}

		headerDispatcher.include(request, response);

		pw.println("<body>");
		pw.println("<div class='board'>");

		for(int i = 0; i < this.ads.size(); i++) {
			Ad ad = this.ads.get(i);

			pw.println(ad.toHtmlString(author, i));
		}
		
		pw.println("</div>");
		pw.println("</body>");

		pw.println("</html>");
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);

		String action = (String) request.getParameter("action");

		if(session != null) {
			String author = (String) session.getAttribute("name");

			if("new-ad".equals(action)) {
				String header = (String) request.getParameter("header");
				String text = (String) request.getParameter("text");

				this.ads.add(new Ad(header, text, author));
				response.sendRedirect("board");
			}
			else if("delete-ad".equals(action)) {
				String id = (String) request.getParameter("id");
				int index = Integer.valueOf(id);

				Ad removeAd = this.ads.get(index);
				if(author.equals(removeAd.getAuthor())) {
					this.ads.remove(index);
				}				

				response.sendRedirect("board");
			}
		}
		else {
			pw.println(getErrorPage("ERROR login", "Please login."));
		}
	}

}
