<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Grille</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/grille.css">
</head>
<body>

<h1>Grille de jeu</h1>

<!-- Player Information Section -->
<div class="player-info">
    <h3>Informations du joueur</h3>
    <p>Nom : <strong>${joueur.nom}</strong></p>
    <p>Score : <strong>${joueur.score}</strong></p>
    <p>Points de production : <strong>${joueur.pointsProduction}</strong></p>
    <p>Nombre de soldat : <strong>${joueur.nbSoldats}</strong></p>
    <p>Nombre de villes : <strong>${joueur.nbVilles}</strong></p>
</div>

<div class ="table-container">
<!-- Grid Display -->
<c:if test="${grille.lignes > 0 && grille.colonnes > 0}">
    <div class="side-sections">
        <!-- Soldiers Section -->
        <div class="soldiers-section">
            <h3>Mes Soldats</h3>
            <c:forEach var="soldat" items="${joueur.soldats}">
                <c:if test="${soldat.grille.id == grille.id}">
                    <div class="soldier-info">
                        <p>Soldat ID : <strong>${soldat.id}</strong></p>
                        <p>Position : <strong>(${soldat.x}, ${soldat.y})</strong></p>
                        <p>Points de défense : <strong>${soldat.pointsDefense}</strong></p>
                        <p>Blessé : <strong>${soldat.blesse ? 'Oui' : 'Non'}</strong></p>
                        <form action="${pageContext.request.contextPath}/FrontController" method="post">
                            <input type="hidden" name="grilleId" value="${grille.id}">
                            <input type="hidden" name="action" value="guerirSoldat">
                            <input type="hidden" name="soldatId" value="${soldat.id}">
                            <button type="submit">Guérir</button>
                        </form>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <!-- Cities Section -->
        <div class="cities-section">
            <h3>Mes Villes</h3>
            <c:forEach var="ville" items="${joueur.villes}">
                <c:if test="${ville.grille.id == grille.id}">
                    <div class="city-info">
                        <p>Ville ID : <strong>${ville.id}</strong></p>
                        <p>Position : <strong>(${ville.x}, ${ville.y})</strong></p>
                        <p>Production par tour : <strong>${ville.productionParTour}</strong></p>
                        <form action="${pageContext.request.contextPath}/FrontController" method="post">
                            <input type="hidden" name="grilleId" value="${grille.id}">
                            <input type="hidden" name="action" value="ameliorerVille">
                            <input type="hidden" name="villeId" value="${ville.id}">
                            <button type="submit">Améliorer</button>
                        </form>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <!-- Bouton Passer Tour -->
    <div class="passer-tour">
        <form action="${pageContext.request.contextPath}/FrontController" method="post">
            <input type="hidden" name="grilleId" value="${grille.id}">
            <input type="hidden" name="action" value="passerTour">
            <button type="submit">Passer Tour</button>
        </form>
        <form action="${pageContext.request.contextPath}/gameSession?grilleId=${grille.id}" method="get">
            <input type="hidden" name="action" value="afficherGrille">
            <input type="hidden" name="grilleId" value="${grille.id}">
            <button type="submit">Rafraîchir</button>
        </form>
    </div>
    <!-- Bouton Rafraîchir -->
    <!-- Grid Display -->
    <c:if test="${grille.lignes > 0 && grille.colonnes > 0}">
        <table>
            <c:forEach var="i" begin="0" end="${grille.lignes - 1}">
                <tr>
                    <c:forEach var="j" begin="0" end="${grille.colonnes - 1}">
                        <td>
                            <c:set var="tuile" value="${grille.getTuile(i,j)}"/>
                            <c:choose>
                                <c:when test="${tuile.getType() == 'SOLDATOCCUPE'}">
                                    <img src="icons/Large/soldier.png" alt="Soldier">
                                    <c:if test="${tuile.getSoldat().getProprietaire().id == joueur.id && joueur.isTurn() && !tuile.getSoldat().isAJouer()}">
                                        <form action="${pageContext.request.contextPath}/FrontController" method="post">
                                            <input type="hidden" name="action" value="deplacerSoldat">
                                            <input type="hidden" name="grilleId" value="${grille.id}">
                                            <input type="hidden" name="xSource" value="${i}">
                                            <input type="hidden" name="ySource" value="${j}">
                                            <div class ="controls">
                                                <button type="submit" name="direction" value="up" class = "controls">Up</button>
                                                <button type="submit" name="direction" value="down" class = "controls">Down</button>
                                                <button type="submit" name="direction" value="left" class="controls">Left</button>
                                                <button type="submit" name="direction" value="right" class="controls">Right</button>
                                            </div>
                                        </form>
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
                                    <c:if test="${adjacentToSoldat[i][j] && tuile.ville.proprietaire.id != joueur.id}">
                                        <form action="${pageContext.request.contextPath}/FrontController" method="post">
                                            <input type="hidden" name="action" value="occuperVille">
                                            <input type="hidden" name="grilleId" value="${grille.id}">
                                            <input type="hidden" name="xSource" value="${i}">
                                            <input type="hidden" name="ySource" value="${j}">
                                            <button type="submit">Occuper la ville</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${joueur.pointsProduction >= 10 && tuile.ville.proprietaire.id == joueur.id}">
                                        <form action="${pageContext.request.contextPath}/FrontController" method="post">
                                            <input type="hidden" name="action" value="creerSoldat">
                                            <input type="hidden" name="grilleId" value="${grille.id}">
                                            <input type="hidden" name="xSource" value="${i}">
                                            <input type="hidden" name="ySource" value="${j}">
                                            <button type="submit">Creer un soldat</button>
                                        </form>
                                    </c:if>
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
    </c:if>
</c:if>
</div>

<!-- Invalid Grid Message -->
<c:if test="${grille.lignes <= 0 || grille.colonnes <= 0}">
    <p>Dimensions de la grille invalides.</p>
</c:if>

<!-- Logout Link -->

<div class="logout-link">
    <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
    <a href="${pageContext.request.contextPath}/connected">Retour au menu</a>
</div>
<!--
<script>
    // On récupère l'ID du joueur depuis la JSP
    var playerId = "${joueur.id}";
    // On compose l'URL du WebSocket Endpoint
    // Note: si vous êtes en HTTPS, remplacez ws:// par wss://
    var wsUrl = "ws://" + window.location.host + "${pageContext.request.contextPath}/ws/game/" + playerId;
    console.log("Connexion WebSocket à : " + wsUrl);

    var socket = new WebSocket(wsUrl);

    // Quand la connexion s'ouvre
    socket.onopen = function(event) {
        console.log("WebSocket ouvert pour le joueur " + playerId);
        // Optionnel : Envoyer un message au serveur (si besoin)
        // socket.send("Hello from player " + playerId);
    };

    // Quand on reçoit un message du serveur
    socket.onmessage = function(event) {
        console.log("Message reçu : " + event.data);
        if (event.data === "turn:true") {
            // Action que vous voulez faire quand le serveur vous annonce "c'est ton tour"
            alert("C'est votre tour de jouer !");
            // Option : recharger la page pour voir les boutons actifs
            // location.reload();
        }
        // Vous pouvez traiter d'autres messages (ex: "chat:Hello", etc.)
    };

    // Quand la connexion est fermée
    socket.onclose = function(event) {
        console.log("WebSocket fermé");
    };

    // Gestion des erreurs
    socket.onerror = function(event) {
        console.error("WebSocket error: ", event);
    };
</script>
-->
</body>
</html>