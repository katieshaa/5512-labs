import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<LINK REL=\"StyleSheet\" HREF=\"" + StylesChanger.path + "\" TYPE=\"text/css\">");
        BufferedReader in = new BufferedReader(new FileReader("/home/mozg/apache-tomcat-7.0.82/webapps/lab15/users.txt"));

        String s;
        String [] str;
        Book base = new Book();
        while((s = in.readLine()) != null){
            str = s.split(";");
            base.addPerson(str[0], str[1]);
        }

        boolean flag = false;
        for(int i = 0; i < base.size(); i++){
            Person p;
            if((base.getPerson(i).getName().equals(name))&&(base.getPerson(i).getPassword().equals(password))){
                flag = true;
            }
        }

        if(flag){
            request.getRequestDispatcher("link.html").include(request, response);
            out.print("Welcome, " + name);
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
        }
        else{
            out.print("Sorry, username or password error!");
            request.getRequestDispatcher("login.html").include(request, response);
        }

        out.println("</html></body>");
        out.close();
    }
}

class Person{

    String name;
    String password;

    public Person(String n, String pass){
        this.name = n;
        this.password = pass;
    }

    public String getName(){
        return name;
    }

    public String getPassword() {
        return password;
    }
}



