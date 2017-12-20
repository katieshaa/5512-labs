import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class main extends HttpServlet {
    Names names = new Names();
    String group = "All";
    public void init(ServletConfig config) {
        try
        {
            File list = new File("/home/mozg/apache-tomcat-7.0.82/webapps/lab13/list.txt");
            String in;
            Scanner sc = new Scanner(list);
            while(sc.hasNextLine())
            {
                in = sc.nextLine();
                String[] tmp = in.split(" ");
                StringBuilder name = new StringBuilder(tmp[0]);
                for(int i = 1; i < tmp.length - 1; i++)
                {
                    name.append(" ");
                    name.append(tmp[i]);
                }
                for(int i = 0; i < Integer.parseInt(tmp[tmp.length - 1]); i++)
                {
                    names.add(name.toString(), sc.nextLine());
                }
            }
        }
        catch(IOException e)
        {
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        PrintWriter out = response.getWriter();
        out.println("<html>\n<body>\n");
        out.println("<a href=/lab13/servlet/lab13>All</a>|<a href=/lab13/servlet/lab13/colleague>Colleague</a>|<a href=/lab13/servlet/lab13/family>Family</a>|<a href=/lab13/servlet/lab13/friend>Friend</a>\n");



        if( uri.equals("/lab13/servlet/lab13/addName") )
        {
            out.println(getAddNamePage());
        }


        else if( uri.equals("/lab13/servlet/lab13/addPhone") ) {
            out.println(getAddPhonePage());
        }


        else if( uri.equals("/lab13/servlet/lab13/addName/add") )
        {
            if(!names.contain(request.getParameter("name")))
            {
                names.add(request.getParameter("name"),request.getParameter("phone"));
		names.addGroup(request.getParameter("name"), group);
            }
            else
            {
                out.println("This user was already add\n");
            }
            out.println(getMainPage());
        }


        else if( uri.equals("/lab13/servlet/lab13/addPhone/add") )
        {
            if(names.contain(request.getParameter("name")))
            {
                names.add(request.getParameter("name"),request.getParameter("phone"));
            }
            else
            {
                out.println("This user is not in the list\n");
            }
            out.println(getMainPage());
        }


        else if(uri.equals("/lab13/servlet/lab13/colleague"))
        {
            out.println(button());
            group = "col";
            out.println(getMainPage());
        }


        else if(uri.equals("/lab13/servlet/lab13/family"))
        {
            out.println(button());
            group = "fam";
		out.println(group);
            out.println(getMainPage());
        }


        else if(uri.equals("/lab13/servlet/lab13/friend"))
        {
            out.println(button());
            group = "fri";
            out.println(getMainPage());
        }


        else if(uri.equals("/lab13/servlet/lab13/addToGroup"))
        {
		out.println(button());
           names.addGroup(request.getParameter("name"), group);
           out.println(getMainPage());
        }
        else
        {
            group = "All";
            out.println(getMainPage());

        }
        out.println("</body>\n</html>");
    }

    public String getMainPage() {
        StringBuilder sb = new StringBuilder();

        sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addName\">\n");

        sb.append("<input type=\"submit\" value=\"add user\">\n");

        sb.append("</form>");
        sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addPhone\">\n");
        sb.append("<input type=\"submit\" value=\"add phone\">\n");
        sb.append("</form>");
	
        HashMap<String, ArrayList<String>> tmp = names.getNamesStrings(group);
        for(String n: tmp.keySet())
        {
            sb.append("<p>" + n + ":" + "</p>\n");
            for(String p: tmp.get(n))
            {
                sb.append("<p>" + p + "</p>\n");
            }
        }
/*S


sb.append("<a href=\"/servlet-2/servlet/Testing/reset\">reset</a>");*/
        return sb.toString();
    }

    public String getAddNamePage()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addName/add\">\n");
        sb.append("Name: <input type=\"text\" name=\"name\">\n");
        sb.append("Phone: <input type=\"text\" name=\"phone\">\n");
        sb.append("<input type=\"submit\" value=\"add\">\n");
        sb.append("</form>");
        return sb.toString();
    }
  
    public String getAddPhonePage()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addPhone/add\">\n");
        sb.append("Name: <input type=\"text\" name=\"name\">\n");
        sb.append("Phone: <input type=\"text\" name=\"phone\">\n");
        sb.append("<input type=\"submit\" value=\"add\">\n");
        sb.append("</form>");
        return sb.toString();
    }
    public String button()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addToGroup\">\n");
        sb.append("Name: <input type=\"text\" name=\"name\">\n");
        sb.append("<input type=\"submit\" value=\"add\">\n");
	sb.append("</form>");
        return sb.toString();
    }
}

