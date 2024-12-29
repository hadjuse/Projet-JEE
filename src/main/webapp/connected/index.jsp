<%@ page import="com.projet.model.Joueur" %><%--
  Created by IntelliJ IDEA.
  User: cytech
  Date: 15/12/2024
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Joueur joueur = (Joueur) request.getSession().getAttribute("joueur");
    String sessionName = joueur.getNom();
%>
<html>
<head>
    <title>Connection confirmation</title>
</head>
<body>
<h1>Connexion  confirmation</h1>
<p>Vous êtes connecté en tant que <%= sessionName %></p>
<a href="game">Jouer</a> <br>
<a href="scores">Scores</a> <br>
<a href="rules">Règles</a> <br>
<a href="credits">Crédits</a> <br>
<a href="../form/index.jsp">Inscription</a> <br>
<!-- add deconexion -->
</body>
</html>
