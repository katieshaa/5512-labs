<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 06.11.17
  Time: 0:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<div class="parent">
    <div class="block">
        <br><div class="text"><strong>Enter exist username</strong></div><br>
        <div class="inputText">
            <form action="/user" method="post">
                <a class="plusWidth"></a>
                <input name="username" placeholder="Username..." class="inputUsername" required />
                <div><a href="addUser.jsp" class="registr">Add new user</a></div><br>
                <button class="button">Submit</button>
            </form>
        </div>
        <div>
        </div>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="index.css">
</html>
