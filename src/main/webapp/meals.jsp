<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.02.2022
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="index.html">Add Meal</a>
<br>
<br>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${mealsToList}" var="user">
        <c:if test="${user.isExcess() == false}">
            <tr>
                <td><font
                        color="#008000">${user.getLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</font>
                </td>
                <td><font color="#008000">${user.getDescription()}</font></td>
                <td><font color="#008000">${user.getCalories()}</font></td>
                <td><a href="index.html">Update</a></td>
                <td><a href="index.html">Delete</a></td>
            </tr>
        </c:if>
        <c:if test="${user.isExcess()}">
            <tr>
                <td><font
                        color="#FF0000">${user.getLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</font>
                </td>
                <td><font color="#FF0000">${user.getDescription()}</font></td>
                <td><font color="#FF0000">${user.getCalories()}</font></td>
                <td><a href="index.html">Update</a></td>
                <td><a href="index.html">Delete</a></td>
            </tr>
        </c:if>

    </c:forEach>
</table>
</body>
</html>
