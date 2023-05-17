<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>New Student</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>

<style>
    <%@include file="students.css" %>
</style>

<h1>Edit student</h1>


<p style="color:red">${error}</p>

<form
        <c:if test="${id==0}">action="add_student"</c:if>
        <c:if test="${id>0}">action="edit_student"</c:if> method="post">

    <input type="hidden" name="id" value="${student.id}" required>
    <input type="hidden" name="id_group" value="${student.academicGroup.id}" required>
    <div class="form-field">
        <label for="sname" class="form-field-label">Прізвище</label>
        <input name="surname" value="${student.lastName}" class="form-field-input-input" id="sname" required readonly>
    </div>

    <div class="form-field">
        <label for="fname" class="form-field-label">Ім`я</label>
        <input name="name" value="${student.firstName}" class="form-field-input-input" id="fname" readonly>
    </div>
    <div class="form-field">
        <label for="pname" class="form-field-label">По батькові</label>
        <input name="fathersName" value="${student.middleName}" class="form-field-input-input" id="pname" readonly>
    </div>
    <!-- Поле для ввода даты рождения -->
    <div class="form-field">
        <label for="dateOfBirth" class="form-field-label">День народження</label>
        <input type="date" name="birthday" value="${student.dateOfBirth}" class="form-field-input-input" id="dateOfBirth">
    </div>
    <!-- Поле для ввода бюджет-контракт -->
    <div class="form-field">
        <label for="contract" class="form-field-label">Форма навчання</label>
        <select
            class="form-field-input-input" id="contract" name="contract">
            <option <c:if test="${student.contract}">selected</c:if>>Контракт</option>
            <option <c:if test="${not student.contract}">selected</c:if>>Бюджет</option>
        </select>
    </div>
    <!-- Поле для ввода стипендия-нет -->
    <div class="form-field">
        <label for="takeScholarship" class="form-field-label">Отримання стипендії</label>
        <select class="form-field-input-input" id="takeScholarship"
                name="scholarship">
            <option <c:if test="${not student.takeScholarship}">selected</c:if>>Ні</option>
            <option <c:if test="${student.takeScholarship}">selected</c:if>>Так</option>
        </select>
    </div>
    <div>
        <li><input class="bottomButtons" type="submit" value="Save"></li>
        <%--${student.academicGroup.id} --%>
        <li><a href="students?id_group=${student.academicGroup.id}">Повернутися</a></li>
    </div>
</form>
</body>

</html>
