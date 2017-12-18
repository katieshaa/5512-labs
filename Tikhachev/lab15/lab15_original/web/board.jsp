<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: root
  Date: 06.12.17
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Board</title>
</head>
<body>
<div class="head">
    <div class="username">
        <%if(request.getSession().getAttribute("username") != null) {
            out.println("Hello " + request.getSession().getAttribute("username"));
        }%>
    </div>
    <%if(request.getSession().getAttribute("username") == null) {
        out.println("<div class=\"link\"><a href=\"registration.jsp\">Registration</a></div>");
    } else {
        out.println("<div class=\"link\">\n" +
                "        <form method=\"post\" action=\"/board\">\n" +
                "            <button class=\"exitButton\" value=\"true\" name=\"exit\">Exit</button>\n" +
                "        </form>\n" +
                "    </div>");
    }%>
</div>
<%
if (request.getSession().getAttribute("username") == null) {
    BufferedReader in = new BufferedReader(new FileReader(new File("/root/IdeaProjects/lab15/lab15_original/web/loginForm.html")));
    StringBuilder loginForm = new StringBuilder();
    String tmp = in.readLine();
    while (tmp != null) {
        loginForm.append(tmp);
        tmp = in.readLine();
    }
    out.println(loginForm.toString());
}
%>

    <div class="advert">
        <textarea class="titleAdvert" readonly rows="1">User: Admin    Time:<%out.println(new Date().toString());%></textarea>
        <textarea class="textAdvert" readonly>This ad is written for an example</textarea>
    </div>
    <%if(request.getSession().getAttribute("adverts") != null) {
        ArrayList<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) request.getSession().getAttribute("adverts");
        Iterator<HashMap<String, String>> it = list.listIterator();
        while (it.hasNext()) {
            HashMap<String, String> map = it.next();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                out.println("<div class=\"advert\">");
                out.println("<textarea class=\"titleAdvert\" readonly>" + entry.getKey() + "</textarea>");
                out.println("<textarea class=\"textAdvert\" readonly>" + entry.getValue() + "</textarea>");
                out.println("</div>");
            }
        }
    } else {
        if(request.getAttribute("adverts") != null) {
            ArrayList<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) request.getAttribute("adverts");
            Iterator<HashMap<String, String>> it = list.listIterator();
            while (it.hasNext()) {
                HashMap<String, String> map = it.next();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    out.println("<div class=\"advert\">");
                    out.println("<textarea class=\"titleAdvert\" readonly>" + entry.getKey() + "</textarea>");
                    out.println("<textarea class=\"textAdvert\" readonly>" + entry.getValue() + "</textarea>");
                    out.println("</div>");
                }
            }
        }
    }%>

<%if(request.getSession().getAttribute("username") != null) {
    BufferedReader in = new BufferedReader(new FileReader(new File("/root/IdeaProjects/lab15/lab15_original/web/addAdvert.html")));
    String tmp = in.readLine();
    while(tmp != null) {
        out.println(tmp);
        tmp = in.readLine();
    }
}%>
</body>
<link rel="stylesheet" type="text/css" href="index.css">
<link rel="stylesheet" type="text/css" href="default2.css">
</html>

