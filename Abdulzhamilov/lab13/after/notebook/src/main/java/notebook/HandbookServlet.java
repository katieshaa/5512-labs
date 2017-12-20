package notebook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@WebServlet("/HandbookServlet")
public class HandbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	    private HandbookIO io ;
	    public void init() {
	         io  = new HandbookIO("C:\\Users\\Куддус\\Desktop\\notebook\\users.txt");
	         io.read();
	    }
	    public void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        PrintWriter out = response.getWriter();

	        out.println("<!DOCTYPE html>");
	        out.println("<html>\n<head>\n<meta charset='utf-8'/>\n<title>Handbook</title>\n</head>\n");
	        out.println("<body>");
			out.println("<p><a href=\"http://localhost:8080/AddPhoneNumber.html\">Add Phone Number</a></p>");
            out.println("<p><a href=\"http://localhost:8080/AddUser.html\">Add User</a></p>");
            String uri = request.getRequestURI();
            if( uri.equals("/notebook/Handbook/addPhone") ) {
                io.addPhone(request.getParameter("name"),request.getParameter("phone"));
            }
            if( uri.equals("/notebook/Handbook/addUser") ) {
                io.addUser(request.getParameter("name"),request.getParameter("phones"));
            }

	        out.println("<h1>Contact list</h1>");
	        out.println(toStringHTML());
	        out.println("</body>");
	        out.println("</html>");

	}
	  private String toStringHTML()
	  {
	      StringBuilder str = new StringBuilder();
	      ArrayList<String> phones;
	      ConcurrentHashMap<String,ArrayList<String>> base = io.getBase();

	      for(Map.Entry<String,ArrayList<String>>entry: base.entrySet())
	      {
	            str.append("<h2>Name:"+ entry.getKey() + "<br>" + "Phones:");
	            phones = base.get(entry.getKey());
	            for (int i = 0; i < phones.size() - 1; i++)
	            {
	                str.append( phones.get(i) + ",");
	            }
	            str.append(phones.get(phones.size() - 1) + "</h1><br>");
	      }
	      return str.toString();
	  }
	}

 

