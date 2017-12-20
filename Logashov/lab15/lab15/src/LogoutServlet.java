import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();
        session.invalidate();

        PrintWriter out=response.getWriter();
        out.println("<html><body>");
        out.println("<LINK REL=\"StyleSheet\" HREF=\"" + StylesChanger.path + "\" TYPE=\"text/css\">");
        request.getRequestDispatcher("loginlink.html").include(request, response);
        out.println("You are successfully logged out!");

        out.println("</html></body>");

        out.close();
    }
}
