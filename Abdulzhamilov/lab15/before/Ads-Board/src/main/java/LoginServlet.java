import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Куддус on 26.11.2017.
 */
public class LoginServlet extends HttpServlet {
    private ArrayList<Person> base;
    public  void init()
    {
        base = new ArrayList<>();
        String s;
        String []str;
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:\\Ads-Board-maste\\src\\main\\java\\users.txt"));
            while ((s = in.readLine()) !=null) {
                s.trim();
                str = s.split(" ");
                base.add(new Person(str[0],str[1]));
            }
        }
        catch (Exception e){}

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        init();
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        boolean flag = false;
        for (int i = 0; i < base.size();i++)
        {
            if ((name.equals(base.get(i).getName())) && (password.equals(base.get(i).getPassword())))
                flag = true;

        }
        out.println("<html><body>");
        if (flag) {
            request.getRequestDispatcher("link.html").include(request, response);
            out.println("Welcom " + name);
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
        }
        else {
            out.println("Error!!!!");
            request.getRequestDispatcher("login.html").include(request, response);
        }
        out.println("</body></html>");
        out.close();

        }


}
