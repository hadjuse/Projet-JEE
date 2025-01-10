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
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
<h1>Inscription</h1>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label for="nom">Nom :</label>
    <input type="text" name="nom" id="nom" required>
    <label for="mdp">Mot de passe :</label>
    <input type="password" name="mdp" id="mdp" required>
    <button type="submit">S'inscrire</button>
</form>
</body>