<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All</title>
</head>
<body>
<h1>PhoneBook</h1>
<h2>All</h2>
<%--@elvariable id="all" type="java.util.List"--%>
<c:forEach items="${all}" var="i">
    <p> ${i.key}  ${i.value}</p>
</c:forEach>
</body>
</html>
