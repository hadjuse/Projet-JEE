<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.jsp.PageContext"%>
<html>
<head>
    <title>Grille</title>
    <link rel="stylesheet" type="text/css" href="css/grille.css">
</head>
<body>

<h1>Grille de jeu</h1>

<!-- Section d'informations sur le joueur -->
<div class="player-info">
    <h3>Informations du joueur</h3>
    <p>Nom : <strong>${joueur.nom}</strong></p>
    <p>Points de production : <strong>${joueur.pointsProduction}</strong></p>
    <p>Nombre de soldat : <strong>${joueur.nbSoldats}</strong></p>
</div>

<!-- Affichage de la grille si elle a des dimensions valides -->
<c:if test="${grille.lignes > 0 && grille.colonnes > 0}">
<div class="table-container">
    <table>
        <c:forEach var="i" begin="0" end="${grille.lignes - 1}">
            <tr>
                <c:forEach var="j" begin="0" end="${grille.colonnes - 1}">
                    <td>
                        <c:set var="tuile" value="${grille.getTuile(i,j)}"/>
                        <c:choose>
                            <c:when test="${tuile.getType() == 'SOLDATOCCUPE'}">
                                <img src="icons/Large/soldier.png" alt="Soldat" width="80" height="80">
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
                                <img src="icons/Large/forest.png" alt="Forêt" width="80" height="80">
                                <c:if test="${adjacentToSoldat[i][j]}">
                                    <form action="${pageContext.request.contextPath}/FrontController" method="post">
                                        <input type="hidden" name="action" value="collecterRessources">
                                        <input type="hidden" name="grilleId" value="${grille.id}">
                                        <input type="hidden" name="xSource" value="${i}">
                                        <input type="hidden" name="ySource" value="${j}">
                                        <button type="submit">Collecter Ressources</button>
                                    </form>
                                </c:if>
                            </c:when>

                            <c:when test="${tuile.getType() == 'VILLE'}">
                                <img src="icons/Large/city.png" alt="Ville" width="80" height="80">
                            </c:when>

                            <c:when test="${tuile.getType() == 'MONTAGNE'}">
                                <img src="icons/Large/montain.png" alt="Montagne" width="80" height="80">
                            </c:when>

                            <c:otherwise>
                                <img src="icons/Large/empty.png" alt="Vide" width="80" height="80">
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</div>
</c:if>

<!-- Message si la grille a des dimensions invalides -->
<c:if test="${grille.lignes <= 0 || grille.colonnes <= 0}">
    <p>Invalid grid dimensions.</p>
</c:if>

<!-- Lien de déconnexion -->
<div class="logout-link">
    <a href="${pageContext.request.contextPath}/logout">Déconnexion</a> <br>
</div>

</body>
</html>
