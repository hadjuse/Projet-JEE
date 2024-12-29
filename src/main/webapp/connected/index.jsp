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
    <title>Connection confirmation</title>
</head>
<body>
<h1>Connexion  confirmation</h1>
<p>Vous êtes connecté en tant que <%= sessionName %></p>
<a href="${pageContext.request.contextPath}/gameSession">Creer une partie</a> <br>
<a href="${pageContext.request.contextPath}/FrontController?action=creerGrille">Rejoindre une partie</a> <br>
<a href="scores">Scores</a> <br>
<a href="rules">Règles</a> <br>
<a href="credits">Crédits</a> <br>
<a href="../form/index.jsp">Inscription</a> <br>
<!-- add deconexion -->
<a href="${pageContext.request.contextPath}/logout">Deconnexion</a> <br>
</body>
</html>
