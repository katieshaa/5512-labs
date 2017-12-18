import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Куддус on 04.12.2017.
 */
public class AdminServlet extends HttpServlet{
    private ArrayList<Person> base;
    private ArrayList<Person> user;
    public  void init()
    {
        base = new ArrayList<>();
        user = new ArrayList<>();
        String s;
        String []str;
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Ads-Board-maste\\src\\main\\java\\admin.txt"));
            while ((s = in.readLine()) !=null) {
                s.trim();
                str = s.split(" ");
                base.add(new Person(str[0],str[1]));
            }
            BufferedReader pw = new BufferedReader(new FileReader("C:\\Ads-Board-maste\\src\\main\\java\\users.txt"));
            while ((s = pw.readLine()) !=null) {
                s.trim();
                str = s.split(" ");
                user.add(new Person(str[0],str[1]));
            }
        }
        catch (Exception e){}

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        init();
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        if(session ==  null) {
            String name = request.getParameter("nameAdmin");
            String password = request.getParameter("password");
            boolean flag = false;
            for (int i = 0; i < base.size(); i++) {
                if ((name.equals(base.get(i).getName())) && (password.equals(base.get(i).getPassword())))
                    flag = true;

            }
            out.println("<html><body>");
            if (flag) {
                request.getRequestDispatcher("link.html").include(request, response);
                out.println("Welcom " + name);
                session = request.getSession();
                session.setAttribute("name", name);
                session.setAttribute("nameAdmin", name);
                out.println(printDelete());
            } else {
                out.println("Error!!!!");
                request.getRequestDispatcher("admin.html").include(request, response);
            }
        }
        else
        {
            request.getRequestDispatcher("link.html").include(request, response);
            out.println("Welcom " + session.getAttribute("name"));
            if( (request.getParameter("user") !=null) && (session.getAttribute("nameAdmin") != null) ) {
                delete(request.getParameter("user"));
                out.println("<p><strong>delete \n" + "successful</strong></p>");
            }
            out.println(printDelete());
        }
        out.println("</body></html>");
        out.close();

    }
    public  synchronized String printDelete()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<form method=\"POST\" >" +  "<p><strong>select user</strong></p>");
        stringBuilder.append("<p><select name=\"user\">");
        for (int i = 0; i < user.size();i++)
        {
            stringBuilder.append("<option value=\""+ user.get(i).getName()+"\">"+user.get(i).getName()+"</option>");

        }
        stringBuilder.append("</select>");
        stringBuilder.append( "<input type=\"submit\" value=\"submit\"></p>" + "</form>");
        return stringBuilder.toString();
    }
    public synchronized void  delete(String us)
    {
        for (int i = 0; i < user.size(); i++) {
            if (us.equals(user.get(i).getName())) {
                user.remove(i);
                break;
            }
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < user.size(); i++) {
            str.append(user.get(i).getName()+" "+user.get(i).getPassword()+"\n");
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Ads-Board-maste\\src\\main\\java\\users.txt"));
            writer.write(str.toString());
            writer.close();

        }
        catch (Exception e){}

    }


}
