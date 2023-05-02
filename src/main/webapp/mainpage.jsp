<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<c:if test="${not empty sessionScope.username}">
    <p>Active user: ${sessionScope.username}</p>
    <p>Session: ${sessionScope.session}</p>
    <form action="logout" method="post">
        <input type="submit" value="Logout">
    </form>
</c:if>
<c:if test="${empty sessionScope.username}">
    <p>No login</p>
</c:if>
</p>
<br/>
<a href="employees">Open Database with Employees</a>
</body>
</html>
