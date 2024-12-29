<%--
  Created by IntelliJ IDEA.
  User: cytech
  Date: 14/12/2024
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.jsp.PageContext"%>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
<h1>Connexion</h1>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="nom">Nom :</label>
    <input type="text" name="nom" id="nom" required>
    <label for="mdp">Mot de passe :</label>
    <input type="password" name="mdp" id="mdp" required>
    <br>
    <button type="submit">Se connecter</button>
</form>
</body>
</html>

