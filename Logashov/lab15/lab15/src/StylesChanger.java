import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 * Created by denis on 23.11.2017.
 */
public class StylesChanger {
    static public String path = "style.css";


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        path = name;

        PrintWriter out=response.getWriter();
        out.println("<html><body>");
        out.println("<LINK REL=\"StyleSheet\" HREF=\"" + StylesChanger.path + "\" TYPE=\"text/css\">");
        HttpSession session = request.getSession(false);
        if(session != null){
            request.getRequestDispatcher("link.html").include(request, response);
        }
        else{
            request.getRequestDispatcher("loginlink.html").include(request, response);
        }
        out.println("<h2>Styles Changed!!</h2>");
        out.println("</html></body>");

        out.close();
    }

}
