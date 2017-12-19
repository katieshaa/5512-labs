package notebook;

import java.io.*;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class MainServlet extends HttpServlet {
    private final Book book = new Book("C:\\Users\\Jekos_Mgdn\\IdeaProjects\\notebook\\Phone.txt");
    private static Logger logger = Logger.getLogger(MainServlet.class.getName());

    @Override
    public void init() throws ServletException {
        book.readBook();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        PrintWriter out = response.getWriter();
        String uri = request.getRequestURI();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        out.println("<html><head>");
        out.println("<title>PhoneBook</title>");
        out.println("</head><body>");

        out.println("<h1>PhoneBook</h1>");

        out.println("<h2>Functions_of_book</h2>");
        out.println("<a href=\"http://localhost:8080/addName.html\">Add new contact </a>");
        out.println("<br><a href=\"http://localhost:8080/remove.html\">Remove contact </a>");

        out.println("<h2>Contacts </h2>");
        if (uri.equals("/notebook/MainServlet/add")) {
            String name = request.getParameter("name");
            book.add(name, request.getParameter("phone"));

        } else if (uri.equals("/notebook/MainServlet/remove")) {
            book.remove(request.getParameter("name"));
        }

        out.println(book.getBook());
    }catch (Exception e){
        logger.info(e.getMessage());
    }
    }

}
