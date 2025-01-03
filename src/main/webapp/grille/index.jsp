<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.jsp.PageContext"%>
<html>
<head>
    <title>Grille</title>
    <style>
        table {
            border-collapse: collapse;
        }
        td {
            width: 100px;
            height: 100px;
            border: 1px solid black;
            text-align: center;
        }
    </style>
</head>
<body>
<h1>Grille de jeu</h1>
<c:if test="${grille.lignes > 0 && grille.colonnes > 0}">
    <table>
        <c:forEach var="i" begin="0" end="${grille.lignes - 1}">
            <tr>
                <c:forEach var="j" begin="0" end="${grille.colonnes - 1}">
                    <td>
                        <c:set var="tuile" value="${grille.getTuile(i,j)}"/>
                        <c:choose>
                            <c:when test="${tuile.getType() == 'SOLDATOCCUPE'}">
                                <img src="imagesTuiles/chevalier.jpg" alt="Soldat" width="80" height="80">
                                <c:if test="${tuile.soldat.proprietaire.id == joueur.id}">
                                    <form action="${pageContext.request.contextPath}/FrontController" method="post">
                                        <input type="hidden" name="action" value="deplacerSoldat">
                                        <input type="hidden" name="grilleId" value="${grille.id}">
                                        <input type="hidden" name="xSource" value="${i}">
                                        <input type="hidden" name="ySource" value="${j}">
                                        <button type="submit" name="direction" value="up">Up</button>
                                        <button type="submit" name="direction" value="down">Down</button>
                                        <button type="submit" name="direction" value="left">Left</button>
                                        <button type="submit" name="direction" value="right">Right</button>
                                    </form>
                                </c:if>
                            </c:when>

                            <c:when test="${tuile.getType() == 'FORET'}">
                                <img src="imagesTuiles/foret.jpg" alt="Forêt" width="80" height="80">
                            </c:when>
                            <c:when test="${tuile.getType() == 'VILLE'}">
                                <img src="imagesTuiles/chateau.jpg" alt="Ville" width="80" height="80">
                            </c:when>
                            <c:when test="${tuile.getType() == 'MONTAGNE'}">
                                <img src="imagesTuiles/montagne.jpg" alt="Montagne" width="80" height="80">
                            </c:when>
                            <c:otherwise>
                                <img src="imagesTuiles/vide.jpg" alt="Vide" width="80" height="80">
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${grille.lignes <= 0 || grille.colonnes <= 0}">
    <p>Invalid grid dimensions.</p>
</c:if>
<a href="${pageContext.request.contextPath}/logout">Déconnexion</a> <br>
</body>
</html>