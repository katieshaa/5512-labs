<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 05.12.17
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New user</title>
</head>
<body>
<div class="parent">
    <div class="block">
        <div class="head">
            Registration for new user
        </div>
        <div class="input">
            <form action="/user" method="post">
            <input name="newUsername" placeholder="Username..." class="inputUsername" required>
            <button class="button">Register</button>
            </form>
        </div>

    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="addUser.css">
</html>
