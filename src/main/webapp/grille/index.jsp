<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Grille</title>
    <style>
        table {
            border-collapse: collapse;
        }
        td {
            width: 40px;
            height: 40px;
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
                        <!-- Afficher le type de la tuile -->
                        <c:set var="tuile" value="${grille.getTuile(i, j)}"/>
                        <c:out value="${tuile.type}"/>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${grille.lignes <= 0 || grille.colonnes <= 0}">
    <p>Invalid grid dimensions.</p>
</c:if>
</body>
</html>