import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Куддус on 27.11.2017.
 */
public class newAd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><head><link rel=\"StyleSheet\"  type=\"text/css\"></head><body>");
        request.getRequestDispatcher("link.html").include(request,response);
        String name = (String) request.getSession(false).getAttribute("name");
        String headr = request.getParameter("header");
        String dis = request.getParameter("description");
        out.println("<p>" + name+"</p>");
        out.println("<p>" + headr+"</p>");
        out.println("<p>" + name+"</p>");
        StringBuilder str = new StringBuilder();
        str.append("\n" + headr+";");
        str.append( dis +";");
        str.append(name +";");
        str.append(new java.util.Date().toString());
        String text = str.toString();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Ads-Board-maste\\src\\main\\java\\ads.txt",true));
            writer.write(text);
            writer.close();

        }
        catch (Exception e){}
        out.println("</body></html>");
        out.close();


    }


}
