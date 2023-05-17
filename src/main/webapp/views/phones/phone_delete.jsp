<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Телефон - вилучення</title>
    <meta charset="UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>
<style><%@include file="phone_delete.css"%></style>
<c:if test="${fn:length(error)>0}">
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <br>
        <h4 style="color:red"><b>${error}</b></h4>
        <br>
    </div>
</c:if>
<h1>Вилучення номеру телефону</h1>
<br>
<c:if test="${phone.id == 0}">
    <p style="color:blue">Record for deleting not found!</p>
    <br>
    <input type="hidden" name="id_owner" value="${owner.id}" required>
    <a href="phones?id_owner=${owner.id}">Повернутися</a>
</c:if>
<c:if test="${phone.id > 0}">
    <form style="alignment: center" action="delete_phone" method="post">
        <h3>Телефон:</h3>
        <p style="color:blue"><b><c:out value="${phone.toString()}"/></b></p>
        <br>
        <h3>Do you confirm delete record?</h3>
        <br>
        <input type="hidden" name="id_phone" value="${phone.id}" required>
        <input type="hidden" name="id_owner" value="${owner.id}" required>
        <div>
        <input class="bottomButtons" type="submit" value="Delete"><a href="phones?id_owner=${owner.id}">  Повернутися</a>
        </div>
    </form>
</c:if>


</body>
</html>
