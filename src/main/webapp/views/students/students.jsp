<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employees</title>
</head>
<body>
<h3>My Employees</h3>
<!--<c:if test="${not empty sessionScope.username}">
    <p>Active user: ${sessionScope.username}</p>
    <p>Session: ${sessionScope.session}</p>
</c:if>
<c:if test="${empty sessionScope.username}">
    <p>No login</p>
</c:if>
<p><a href="mainpage.jsp">На головну</a></p>-->
<br>
<p><button onclick="sortTable()">Сортувати</button>
    <a href="addEmployee">Додати співробітника</a></p>
<input class="form-control" type="text" placeholder="Start to enter name" id="search-text" onkeyup="tableSearch()">

<table border="2" id="myTable">
    <tr>
        <th>Name</th>
        <th>Age</th>
        <th>Pol</th>
        <th>Salary</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${employees}" var="empl">
        <tr>
            <td><c:out value="${empl.name}"/></td>
            <td><c:out value="${empl.age}"/></td>
            <td>
                <c:if test="${empl.pol==true}">Male</c:if>
                <c:if test="${empl.pol==false}">Female</c:if>
            </td>
            <td><c:out value="${empl.salary}"/></td>
            <td>
                <form action="editEmployee">
                    <input type="hidden" name="id" value="${empl.id}">
                    <input type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form action="deleteEmployee">
                    <input type="hidden" name="id" value="${empl.id}">
                    <input type="submit" value="Delete">
                    <!--onclick="return confirmation()"-->
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<script type="text/javascript">
    // For confirm deleting
    function confirmation() {
        return confirm('Are you sure you want to do this?');
    }

    // For filtering
    function tableSearch() {
        var phrase = document.getElementById('search-text');
        var table = document.getElementById('myTable');
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
        table = document.getElementById("myTable");
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
