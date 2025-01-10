<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ensembles des grilles disponibles</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/parties.css">
</head>
<body>
<h1>Ensembles des grilles disponibles</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Lignes</th>
        <th>Colonnes</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="grille" items="${grilles}">
        <tr>
            <td>${grille.id}</td>
            <td>${grille.lignes}</td>
            <td>${grille.colonnes}</td>
            <td><a href="gameSession?grilleId=${grille.id}">Rejoindre</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>