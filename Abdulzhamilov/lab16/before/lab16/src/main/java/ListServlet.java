import sun.plugin.dom.html.HTMLConstants;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.ArrayList;


public class ListServlet extends HttpServlet {
private int count = 1;
    ArrayList<ArrayList<String>> arrayList;
    public void init()
    {
        ListReader list = new ListReader("C:\\Users\\Куддус\\Desktop\\Ads-Board-master\\list.txt");
        arrayList = list.read();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        count = 1;
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'/>");
        out.println("<title>list</title>");
        out.println("<script src='list.js' defer></script>");
        out.println("<script src='list.js' defer></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='wrapper'>");
        out.println(toHtml());
        out.println("</div>");
               out.println("</body>");
        out.println("</html>");

    }
    public String toHtml() {
        ArrayList<String> tmp;
        StringBuilder str = new StringBuilder();
        str.append("<ol>");
        for (int i = 0; i < arrayList.size(); i++) {
            tmp = arrayList.get(i);
            if (tmp.size() > 1)
                str.append(toHtmlArray(tmp));
            else if (tmp.size() == 1) {
                str.append("<div id=\"" + count + "\"><li>" + tmp.get(0) + "</li></div>");
                count++;
            }
        }
        str.append("</ol>");
        return str.toString();
    }
    public  String toHtmlArray(ArrayList<String> arrayList) {
        StringBuilder str = new StringBuilder();
        str.append("<li class='dropdown' id = \""+count +"\""+"+>");
        count++;
        str.append("<div class='title' >");
        str.append(arrayList.get(0));
        str.append("</div>");
        str.append("<a class='show-manager'").append(" onclick='showList(this)' href = '#'>");
        str.append("[+]");
        str.append("</a>");
        str.append("<ul class='show-list'>");
        for (int i = 1; i < arrayList.size(); i++) {
            str.append("<li>" + arrayList.get(i) + "</li>");
        }
        str.append("</ul>");
        str.append("</li>");
        return str.toString();
    }

 }

