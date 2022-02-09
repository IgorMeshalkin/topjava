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
<a href="meals?action=add">Add Meal</a>
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
    <c:forEach items="${mealsToList}" var="meal">
        <c:if test="${meal.isExcess() == false}">
            <tr>
                <td><font
                        color="#008000">${meal.getLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</font>
                </td>
                <td><font color="#008000">${meal.getDescription()}</font></td>
                <td><font color="#008000">${meal.getCalories()}</font></td>
                <td><a href="meals?action=update&mealId=${meal.getId()}">Update</a></td>
                <td><a href="meals?action=delete&mealId=${meal.getId()}">Delete</a></td>
            </tr>
        </c:if>
        <c:if test="${meal.isExcess()}">
            <tr>
                <td><font
                        color="#FF0000">${meal.getLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</font>
                </td>
                <td><font color="#FF0000">${meal.getDescription()}</font></td>
                <td><font color="#FF0000">${meal.getCalories()}</font></td>
                <td><a href="meals?action=update&mealId=${meal.getId()}">Update</a></td>
                <td><a href="meals?action=delete&mealId=${meal.getId()}">Delete</a></td>
            </tr>
        </c:if>

    </c:forEach>
</table>
<form method="post" action="/delete"></form>
</body>
</html>
