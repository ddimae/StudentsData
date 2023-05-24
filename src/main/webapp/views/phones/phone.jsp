<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Телефон</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>
<style>
    <%@include file="phone.css" %>
</style>
<c:if test="${fn:length(error)>0}">
    <div class="alert">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <br>
        <h4 style="color:red"><b>${error}</b></h4>
        <br>
    </div>
</c:if>
<h1>Редагування номеру телефону</h1>
<br>
<form <c:if test="${phone.id==0}">action="add_phone"</c:if>
        <c:if test="${phone.id>0}">action="edit_phone"</c:if> method="post">
    <input type="hidden" name="id_phone" value="${phone.id}" required>
    <input type="hidden" name="id_owner" value="${owner.id}" required>
    <div class="form-field">
        <label for="phone_number" class="form-field-label">Номер телефону</label>
        <input name="phone_number"
               value="${phone.phoneNumber}"
               class="form-field-input-input"
               id="phone_number"
               required
               type="tel"
               name="phone_number"
               placeholder="Введіть номер телефону"
               pattern="[0-9]{12}"
               size="12"
               title="12 цифр без '+'">
    </div>
    <div class="form-field">
        <label for="is_active" class="form-field-label">Чи доступний?</label>
        <select class="form-field-input-input" id="is_active" name="active" required>
            <option <c:if test="${phone.active}">selected</c:if>>Активний</option>
            <option <c:if test="${not phone.active}">selected</c:if>>Неактивний</option>
        </select>
    </div>
    <div class="form-field">
        <label for="is_prior" class="form-field-label">Є основним?</label>
        <select class="form-field-input-input" id="is_prior" name="prior" required>
            <option <c:if test="${phone.prior}">selected</c:if>>Основний</option>
            <option <c:if test="${not phone.prior}">selected</c:if>>Додатковий</option>
        </select>
    </div>
    <div>
        <input class="bottomButtons" type="submit" value="Save"><a href="phones?id_owner=${owner.id}">  Повернутися</a>
    </div>
</form>
</body>

</html>
