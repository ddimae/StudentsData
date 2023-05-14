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
        <input name="surname" value="${student.lastName}" class="form-field-input-input" id="sname" required>
    </div>

    <div class="form-field">
        <label for="fname" class="form-field-label">Ім`я</label>
        <input name="name" value="${student.firstName}" class="form-field-input-input" id="fname" required>
    </div>

    <div class="form-field">
        <label for="pname" class="form-field-label">FathersName</label>
        <input name="fathersName" value="${student.middleName}" class="form-field-input-input" id="pname" required>
    </div>
    <!-- Поле для ввода даты рождения -->

    <!-- Поле для ввода бюджет-контракт -->

    <!-- Поле для ввода стипендия-нет -->

    <div>
        <li><input class="bottomButtons" type="submit" value="Save"></li>
        <%--${student.academicGroup.id} --%>
        <li><a href="students?id_group=${student.academicGroup.id}">back to Group Students List</a></li>
    </div>
</form>
</body>

<script>
    var zChar = new Array(' ', '(', ')', '-', '.');
    var maxphonelength = 17;
    var phonevalue1;
    var phonevalue2;
    var cursorposition;

    function ParseForNumber1(object) {
        phonevalue1 = ParseChar(object.value, zChar);
    }

    function ParseForNumber2(object) {
        phonevalue2 = ParseChar(object.value, zChar);
    }

    function backspacerUP(object, e) {
        if (e) {
            e = e
        } else {
            e = window.event
        }
        if (e.which) {
            var keycode = e.which
        } else {
            var keycode = e.keyCode
        }

        ParseForNumber1(object)

        if (keycode >= 48) {
            ValidatePhone(object)
        }
    }

    function backspacerDOWN(object, e) {
        if (e) {
            e = e
        } else {
            e = window.event
        }
        if (e.which) {
            var keycode = e.which
        } else {
            var keycode = e.keyCode
        }
        ParseForNumber2(object)
    }

    function GetCursorPosition() {

        var t1 = phonevalue1;
        var t2 = phonevalue2;
        var bool = false
        for (i = 0; i < t1.length; i++) {
            if (t1.substring(i, 1) != t2.substring(i, 1)) {
                if (!bool) {
                    cursorposition = i
                    bool = true
                }
            }
        }
    }

    function ValidatePhone(object) {

        var p = phonevalue1

        p = p.replace(/[^\d]*/gi, "")

        if (p.length < 4) {
            object.value = p
        } else if (p.length == 4) {
            pp = p;
            d4 = p.indexOf('+(')
            d5 = p.indexOf(')')
            if (d4 == -1) {
                pp = "+(" + pp;
            }
            if (d5 == -1) {
                pp = pp + ")";
            }
            object.value = pp;
        } else if (p.length > 4 && p.length < 8) {
            p = "+(" + p;
            l30 = p.length;
            p30 = p.substring(0, 5);
            p30 = p30 + ")"

            p31 = p.substring(5, l30);
            pp = p30 + p31;

            object.value = pp;

        } else if (p.length >= 8) {
            p = "+(" + p;

            l30 = p.length;
            p30 = p.substring(0, 5);
            p30 = p30 + ")"
            p31 = p.substring(5, l30);
            pp = p30 + p31;

            l40 = pp.length;
            p40 = pp.substring(0, 8);
            p40 = p40 + "-"
            p41 = pp.substring(8, l40);
            ppp = p40 + p41;

            l50 = ppp.length;
            p50 = ppp.substring(0, 12);
            p50 = p50 + "-"
            p51 = ppp.substring(12, l50);
            pppp = p50 + p51;
            object.value = pppp.substring(0, maxphonelength);
        }

        GetCursorPosition()

        if (cursorposition >= 0) {
            if (cursorposition == 0) {
                cursorposition = 2
            } else if (cursorposition <= 2) {
                cursorposition = cursorposition + 1
            } else if (cursorposition <= 5) {
                cursorposition = cursorposition + 2
            } else if (cursorposition == 6) {
                cursorposition = cursorposition + 2
            } else if (cursorposition == 7) {
                cursorposition = cursorposition + 4
                e1 = object.value.indexOf(')')
                e2 = object.value.indexOf('-')
                if (e1 > -1 && e2 > -1) {
                    if (e2 - e1 == 4) {
                        cursorposition = cursorposition - 1
                    }
                }
            } else if (cursorposition < 11) {
                cursorposition = cursorposition + 3
            } else if (cursorposition == 11) {
                cursorposition = cursorposition + 1
            } else if (cursorposition >= 12) {
                cursorposition = cursorposition
            }

            var txtRange = object.createTextRange();
            txtRange.moveStart("character", cursorposition);
            txtRange.moveEnd("character", cursorposition - object.value.length);
            txtRange.select();
        }

    }

    function ParseChar(sStr, sChar) {
        if (sChar.length == null) {
            zChar = new Array(sChar);
        } else zChar = sChar;

        for (i = 0; i < zChar.length; i++) {
            sNewStr = "";

            var iStart = 0;
            var iEnd = sStr.indexOf(sChar[i]);

            while (iEnd != -1) {
                sNewStr += sStr.substring(iStart, iEnd);
                iStart = iEnd + 1;
                iEnd = sStr.indexOf(sChar[i], iStart);
            }
            sNewStr += sStr.substring(sStr.lastIndexOf(sChar[i]) + 1, sStr.length);

            sStr = sNewStr;
        }

        return sNewStr;
    }

    var clipboard = new Clipboard('.btn');

    clipboard.on('success', function (e) {
        console.log(e);
    });

    clipboard.on('error', function (e) {
        console.log(e);
    });
</script>
</html>
