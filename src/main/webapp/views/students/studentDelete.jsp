<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>LAB5 - Del Employee</title>
</head>
<body>
<h1>DELETE Employee</h1>
<p style="color:red">${error}<br></p>
<c:if test="${idDel == -1}">
    <p style="color:blue">Record for deleting not found!</p>
    <br>
    <a href="listEmployees">back to Employees list</a>
</c:if>
<c:if test="${idDel != -1}">
    <form action="deleteEmployee" method="post">
        <br>
        <h3>Employee:</h3>
        <br>
        <input type="hidden" name="idDel" value="${idDel}">
        <c:out value="CHANGE VIEW OF OUT OF THE YOUR TYPE OF OBJECT"/>
        <p style="color:red"><c:out value="${employee.toString()}"/></p>
        <br>
        <h4>Do you confirm delete record?</h4>
        <br>
        <input type="submit" value="Delete">
        <a href="employees">back to Employees list</a>
    </form>
</c:if>


</body>
</html>
