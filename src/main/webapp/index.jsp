<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.jsp.PageContext"%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <title>Menu du jeu 4X</title>
</head>
<body>
<h1>Menu du jeu 4X</h1>
<div class="menu-container">
    <a href="connexion">Jouer</a>
    <a href="scores">Scores</a>
    <a href="rules">Règles</a>
    <a href="credits">Crédits</a>
    <a href="form/index.jsp">Inscription</a>
    <a href="connexion/index.jsp">Connexion</a>
</div>
</body>
</html>