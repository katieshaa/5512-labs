<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 06.12.17
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div class="parent">
    <div class="loginForm">
        <form action="/user" method="post">
            <div>
                <input name="newUsername" placeholder="Username..." class="inputUsername" required>
            </div>
            <div>
                <input name="newPassword" type="password" placeholder="Password..." class="inputUsername" required>
            </div>
            <button class="button">Registration</button>
        </form>
    </div>
</div>
</body>
<link rel="stylesheet" type="text/css" href="registration.css">
<link rel="stylesheet" type="text/css" href="default2.css">
</html>
