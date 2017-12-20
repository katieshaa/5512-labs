import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Куддус on 26.11.2017.
 */
public class Logout extends HttpServlet{
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getSession().invalidate();
        out.println("<html><body>");
        request.getRequestDispatcher("linkin.html").include(request,response);
        out.println("Successful Exit");
        out.println("</body></html>");
        out.close();

    }

}
