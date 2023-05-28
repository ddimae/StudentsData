<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Групи</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>

<style>
    <%@include file="groups.css" %>
</style>
<div class="header1">
    <div class="header-right">
        <li style="float:left"><h1>Перелік груп</h1></li>
        <!--
        <li style="float:left"><a href="login" > На головну </a></li>
        -->
        <li style="float:right"><input class="header-input" type="text"
                                       placeholder="Введіть номер групи" id="search-text"
                                       onkeyup="tableSearch()">
        </li>
 <!--
        <li style="float:right">
            <button class="header-button" onclick="sortTable()"> Сортувати за номером групи </button>
        </li>
-->
    </div>
</div>
<%--
<c:if test="${not empty sessionScope.username}">
<p>Active user: ${sessionScope.username}</p>
<p>Session: ${sessionScope.session}</p>
</c:if>
<c:if test="${empty sessionScope.username}">
<p>No login</p>
</c:if>
--%>
<div>
    <form enctype="multipart/form-data" action="${pageContext.request.contextPath}/load_students" method="post">
        <input type="file" accept=".xlsx"><button> Завантажити дані про групу </button>
    </form>
</div>
<div>
    <table border="2" id="groups_table">
        <tr>
            <th>Назва групи</th>
            <th>Мова навчання</th>
            <th>Перелік студентів</th>
            <th colspan="4">Зберегти у файл</th>
        </tr>
        <c:forEach items="${groups}" var="gr">
            <tr>
                <td>${gr.groupName}</td>
                <td>${gr.language}</td>
                <td>
                    <form action="students">
                        <input type="hidden" name="id_group" value="${gr.id}">
                        <input class="buttonfortable" type="submit" value="Перелік студентів">
                    </form>
                </td>
                <td>
                    <form action="groups/save_students1">
                        <input type="hidden" name="id" value="${gr.id}">
                        <input class="buttonfortable" type="submit" value="Форма 1">
                    </form>
                </td>
                <td>
                    <form action="groups/save_students2">
                        <input type="hidden" name="id" value="${gr.id}">
                        <input class="buttonfortable" type="submit" value="Форма 2">
                    </form>
                </td>
                <td>
                    <form action="save_students3">
                        <input type="hidden" name="id" value="${gr.id}">
                        <input class="buttonfortable" type="submit" value="Форма 3">
                    </form>
                </td>
                <td>
                    <form action="save_students4">
                        <input type="hidden" name="id" value="${gr.id}">
                        <input class="buttonfortable" type="submit" value="Форма 4">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<script type="text/javascript">
    // For confirm deleting
    function confirmation() {
        return confirm('Are you sure you want to do this?');
    }

    // For filtering
    function tableSearch() {
        var phrase = document.getElementById('search-text');
        //phrase = 'КН-'+phrase;
        var table = document.getElementById('groups_table');
        var regPhrase = new RegExp(phrase.value, 'i');
        var flag = false;
        for (var i = 1; i < table.rows.length; i++) {
            //!!!
            //flag = false;
            //for (var j = table.rows[i].cells.length - 1; j >= 0; j--) {
            flag = regPhrase.test(table.rows[i].cells[0].innerHTML); //cells[j]
            //if (flag) break;
            //}
            if (flag) {
                table.rows[i].style.display = "";
            } else {
                table.rows[i].style.display = "none";
            }

        }
    }

    // For sorting
    function sortTable() {
        var table, rows, switching, i, x, y, shouldSwitch;
        table = document.getElementById("groups_table");
        switching = true;
        //Сделайте цикл, которая будет продолжаться до тех пор, пока
        //никакого переключения не было сделано:
        while (switching) {
            //начните с того, что скажите: никакого переключения не происходит:
            switching = false;
            rows = table.rows;
            //Цикл через все строки таблицы (за исключением
            //во-первых, который содержит заголовки таблиц):
            for (i = 1; i < (rows.length - 1); i++) {
                //начните с того, что не должно быть никакого переключения:
                shouldSwitch = false;
                //Получите два элемента, которые вы хотите сравнить,
                //один из текущей строки и один из следующей:
                //Сортируем по полю нуль = имя
                x = rows[i].getElementsByTagName("TD")[0];
                y = rows[i + 1].getElementsByTagName("TD")[0];
                //проверьте, должны ли две строки поменяться местами:
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    //если это так, отметьте как переключатель и разорвите петлю:
                    shouldSwitch = true;
                    break;
                }
            }
            if (shouldSwitch) {
                /*Если переключатель был отмечен, сделайте переключатель
                и отметьте, что переключение было сделано:*/
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }
    }
</script>
</body>
</html>
