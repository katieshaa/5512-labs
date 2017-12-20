import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Куддус on 27.11.2017.
 */
public class Page extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><head><link rel=\"StyleSheet\"  type=\"text/css\"></head><body>");
        HttpSession session = request.getSession(false);
        if(session != null)
        {
            request.getRequestDispatcher("link.html").include(request,response);
            String name = (String)session.getAttribute("name");
            out.print("welcom " + name);
            printAll(out);
        }
        else
        {
            request.getRequestDispatcher("linkin.html").include(request,response);
            out.print("welcom");
            printAll(out);
        }
        out.println("</body></html>");
        out.close();


    }
    private void printAll(PrintWriter out)throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("C:\\Ads-Board-maste\\src\\main\\java\\ads.txt"));

        String s;
        String [] str;
        AdList ads = new AdList();
        while((s = in.readLine()) != null){
            str = s.split(";");
            ads.add(str[0], str[1], str[2], str[3]);
        }

        for(int i = 0; i < ads.size(); i++){
            out.println("<table border = \"2\" width = \"100%\">" +
                    "<caption><h2>" + ads.getAd(i).getHeader() + "</h2></caption>" +
                    "<tr>" +
                    "<td rowspan=\"2\">" + "<div id = \"text\">" + ads.getAd(i).getDescription() + "</div></td>" +
                    "<td width = \"25%\">author: " + ads.getAd(i).getName() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>date: " + ads.getAd(i).getTime() +"</td>" +
                    "</tr>" +
                    "</table>");
        }
        in.close();
        out.close();

    }
}
