<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>webHBN - Employee</title>
</head>
</head>
<body>
<h1>Employee</h1>
<p style="color:red">${error}<br></p>
<form
        <c:if test="${id==-1}">
            action="addEmployee"
        </c:if>
        <c:if test="${id>0}">
            action="editEmployee"
        </c:if>
        method="post">
    <p><label for="empl_name">Name:</label></p>
    <p>
        <input required type="text" id="empl_name" name="name"
               placeholder="Input surname an English"
               value="${employee.name}"
               pattern="[A-Z][a-z]{1,14}" title="Required, Surname an English,
           first letter capital, other - small, no more 15">
    </p>
    <p><label for="empl_age">Age:</label></p>
    <p>
        <input required type="number" id="empl_age" name="age"
               placeholder="Input age from 18 to 75"
               value="${employee.age}"
               min="18" max="75"
               title="Age is integer value from 18 to 75">
    </p>
    <p><label>Pol:</label>
        <input type="radio" name="pol" value="male"
        <c:if test="${employee.pol}">
               checked
        </c:if>
        >male
        <input type="radio" name="pol" value="female"
        <c:if test="${!employee.pol}">
               checked
        </c:if>
        >female
    </p>
    <p><label for="empl_salary">Salary:</label></p>
    <p>
        <input required type="number" id="empl_salary" name="salary"
               min="6500.00" step="100"
               value="${employee.salary}"
               title="Input salary by template - 6500.00">
    </p>
    <input type="hidden" name="id" value="${id}">
    <br><br>
    <input type="submit" value="Save" value="${employee.id}">
    <a href="employees">back to Employees list</a>
</form>

</body>
</html>
