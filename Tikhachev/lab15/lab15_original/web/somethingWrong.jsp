<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 07.12.17
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Something wrong</title>
</head>
<body>
<div class="parent">
    <div class="block">
        <div class="text"><strong><%
            if(request.getAttribute("whatDo").toString().toLowerCase().contains("nullpointerexception")) {
                out.println("Something Went Wrong");
            } else {
                out.println(request.getAttribute("whatDo"));
            }%></strong></div>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="somethingWrong.css">
<link rel="stylesheet" type="text/css" href="default2.css">
</html>
