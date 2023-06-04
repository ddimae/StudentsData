<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ua">
<head>
    <meta CHARSET="UTF-8">
    <title>New Student</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>

<style>
    <%@include file="student.css" %>
</style>

    <div class="header">
       <li><h4>Edit student</h4></li>
        <li><p style="color:red">${error}</p></li>
    </div>

    <form <c:if test="${id==0}">action="add_student"</c:if>
         <c:if test="${id>0}">action="edit_student"</c:if>
         method="post"
         class="groupTable"
    >

        <input type="hidden" name="id" value="${student.id}" required>
        <input type="hidden" name="id_group" value="${student.academicGroup.id}" required>

        <table border="2" id="students_table">
            <tr>
                <th>Прізвище</th>
                <td><c:out value="${student.lastName}"/></td>
            </tr>
                <th>Ім`я</th>
                <td><c:out value="${student.firstName}"/></td>
            <tr>
                <th>По-батькові</th>
                <td><c:out value="${student.middleName}"/></td>
            </tr>
                <th for="dateOfBirth">День народження</th>
                <td><input class="form-field-input-input"
                           type="date"
                           name="birthday"
                           value="${student.dateOfBirth}"
                           id="dateOfBirth">
                </td>
            <tr>
                <th for="contract">Форма навчання</th>
                <td><select class="form-field-input-input" id="contract" name="contract">
                        <option <c:if test="${student.contract}">selected</c:if>>Контракт</option>
                        <option <c:if test="${not student.contract}">selected</c:if>>Бюджет</option>
                    </select>
                </td>
            </tr>

            <tr>
                <th for="takeScholarship">Отримання стипендії</th>
                <td><select class="form-field-input-input" id="takeScholarship" name="scholarship">
                        <option <c:if test="${not student.takeScholarship}">selected</c:if>>Ні</option>
                        <option <c:if test="${student.takeScholarship}">selected</c:if>>Так</option>
                    </select>
                </td>
            </tr>
        </table>

        <li><input class="form-btn" type="submit" value="Зберегти"></li>
        <li><a class = "form-btn" href="students?id_group=${student.academicGroup.id}">Назад</a></li>
    </form>

</body>

</html>
