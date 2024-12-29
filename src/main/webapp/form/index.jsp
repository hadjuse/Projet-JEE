<%--
  Created by IntelliJ IDEA.
  User: cytech
  Date: 14/12/2024
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.jsp.PageContext"%>
<html>
<head>
    <title>Register player</title>
</head>
<body>
<h1>Register player</h1>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label for="nom">Nom :</label>
    <input type="text" name="nom" id="nom" required>
    <label for="mdp">Password :</label>
    <input type="password" name="mdp" id="mdp" required>
    <br>
    <button type="submit">Register</button>
</form>
</body>