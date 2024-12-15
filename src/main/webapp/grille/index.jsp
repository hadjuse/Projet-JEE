<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="https://jakarta.ee/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 12/14/2024
  Time: 6:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Grille</title>
    <style>
        table{
            border-collapse: collapse;
        }
        td{
            width: 40px;
            height: 40px;
            border: 1px solid black;
            text-align: center;
        }
    </style>
</head>
<body>
<h1>Grille de jeu</h1>
<table>
    <c:forEach var="i" begin="0" end="${grille.lignes-1}">
        <tr>
            <c:forEach var="j" begin="0" end="${grille.colonnes-1}">
                <td>
                    <!--Afficher le type de la tuile-->
                    <c:set var="tuile" value="${grille.getTuile(i,j)}"/>
                    <c:out value="${tuile.type}"/>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>
