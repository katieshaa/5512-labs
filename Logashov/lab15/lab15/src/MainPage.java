import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import static java.awt.SystemColor.text;

public class MainPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><LINK REL=\"StyleSheet\" HREF=\"" + StylesChanger.path + "\" TYPE=\"text/css\"></head><body>");
        HttpSession session = request.getSession(false);
        if(session != null){
            request.getRequestDispatcher("link.html").include(request, response);
            out.println("<h2>Login version!!</h2>");
            String name = (String)session.getAttribute("name");
            out.println("<p>Hello " + name + "!</p>");
            printAds(response);
        }
        else{
            request.getRequestDispatcher("loginlink.html").include(request, response);
            out.println("<h2>Guest version!!</h2>");
            printAds(response);
        }

        out.println("</html></body>");
        out.close();
    }

    public void printAds(HttpServletResponse response) throws IOException{

        PrintWriter out = response.getWriter();
        BufferedReader in = new BufferedReader(new FileReader("/home/mozg/apache-tomcat-7.0.82/webapps/lab15/ads.txt"));
        String s;
        String [] str;
        try{
            adsList ads = new adsList();
            while((s = in.readLine()) != null){

                str = s.split(";");
                ads.addAd(str[0], str[1], str[2], str[3]);
            }
            for(int i = 0; i < ads.size(); i++){
                out.println("<table border = \"2\" width = \"100%\">" +
                        "<caption><h2>" + ads.getAd(i).getHeader() + "</h2></caption>" +
                        "<tr>" +
                        "<td rowspan=\"2\">" + "<div id = \"text\">" + ads.getAd(i).getDescription() + "</div></td>" +
                        "<td width = \"25%\">author: " + ads.getAd(i).getUsername() + "</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td>date: " + ads.getAd(i).getTime() +"</td>" +
                        "</tr>" +
                        "</table>");
            }}
        catch(Exception e){out.println(e.toString());}
        in.close();
        out.close();
    }
}
