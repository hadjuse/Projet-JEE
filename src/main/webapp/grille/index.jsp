<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Grille</title>
    <link rel="stylesheet" type="text/css" href="css/grille.css">
</head>
<body>

<h1>Grille de jeu</h1>

<!-- Player Information Section -->
<div class="player-info">
    <h3>Informations du joueur</h3>
    <p>Nom : <strong>${joueur.nom}</strong></p>
    <p>Points de production : <strong>${joueur.pointsProduction}</strong></p>
    <p>Nombre de soldat : <strong>${joueur.nbSoldats}</strong></p>
</div>

<!-- Grid Display -->
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
                                    <img src="icons/Large/soldier.png" alt="Soldier">
                                    <c:if test="${tuile.getSoldat().getProprietaire().id == joueur.id}">
                                        <div class="controls">
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
                                        </div>
                                    </c:if>
                                </c:when>
                                <c:when test="${tuile.getType() == 'FORET'}">
                                    <img src="icons/Large/forest.png" alt="Forêt">
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
                                    <img src="icons/Large/city.png" alt="Ville">
                                </c:when>
                                <c:when test="${tuile.getType() == 'MONTAGNE'}">
                                    <img src="icons/Large/montain.png" alt="Montagne">
                                </c:when>
                                <c:otherwise>
                                    <img src="icons/Large/empty.png" alt="Empty">
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

<!-- Invalid Grid Message -->
<c:if test="${grille.lignes <= 0 || grille.colonnes <= 0}">
    <p>Dimensions de la grille invalides.</p>
</c:if>

<!-- Logout Link -->
<div class="logout-link">
    <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
</div>

</body>
</html>
