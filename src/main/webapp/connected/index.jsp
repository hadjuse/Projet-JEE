<%@ page import="com.projet.model.Joueur" %><%--
  Created by IntelliJ IDEA.
  User: cytech
  Date: 15/12/2024
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.jsp.PageContext"%>
<%
    HttpSession session1 = request.getSession(false);
    Joueur joueur = (Joueur) session1.getAttribute("joueur");
    if (joueur == null){
        response.sendRedirect("../index.jsp");
        return;
    }
    String sessionName = joueur.getNom();
%>
<html>
<head>
    <title>Confirmation connexion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menuConnexion.css">
</head>
<body>
<h1>Confirmation de connexion</h1>
<p>Vous êtes connecté en tant que <%= sessionName %></p>
<div class="menu-container">
    <a href="${pageContext.request.contextPath}/createGrille">Créer une partie</a>
    <a href="${pageContext.request.contextPath}/retrieveGrilles">Rejoindre une partie</a>
    <a href="scores">Scores</a>
    <a href="rules">Règles</a>
    <a href="credits">Crédits</a>
    <a href="../form/index.jsp">Inscription</a>
    <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
</div>
</body>
</html>
