<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: root
  Date: 13.11.17
  Time: 23:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notebook</title>
</head>
<body>
<div class="parent">
    <div class="block">
        <div class="head">
            --Notebook for <%=request.getParameter("username")%>--<br>
        </div>
        <div class="table">
            <table>
                <tr>
                    <th>Name</th>
                    <th>Mobile number</th>
                </tr>
                <% Map<String, String> hashMap = (Map<String, String>) request.getAttribute("table");
                    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                        out.println("<tr>");
                        out.println("<td><strong>" + entry.getKey() + "</strong></td>");
                        out.println("<td>" + entry.getValue() + "</td>");
                        out.println("</tr>");
                    }
                %>
            </table>
        </div>
        <br>
        <div class="textAdd">Add new record</div>
        <div class="form">
            <form action="/user" method="post">
                <input name="newName" placeholder="Name..." class="inputUsername">
                <input name="newNumber" placeholder="Number..." class="inputUsername">
                <input name="username" value="<%=request.getParameter("username")%>" class="invisible">
                <button class="button">Add</button>
            </form>
            <form action="/user" method="post">
                <button class="buttonSort">Sort table</button>
                <input name="sort" value="true" class="invisible">
                <input name="username" value="<%=request.getParameter("username")%>" class="invisible">
            </form>
        </div>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="table.css">
</html>
