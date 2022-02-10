<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=add">Add Meal</a>
<br/><br/>
<table style="border-collapse: collapse" border="1" cellpadding="10">
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
                <td><font color="green">${formatter.format(meal.getLocalDateTime())}</font></td>
                <td><font color="green">${meal.getDescription()}</font></td>
                <td><font color="green">${meal.getCalories()}</font></td>
                <td><a href="meals?action=update&mealId=${meal.getId()}">Update</a></td>
                <td><a href="meals?action=delete&mealId=${meal.getId()}">Delete</a></td>
            </tr>
        </c:if>
        <c:if test="${meal.isExcess() != false}">
            <tr>
                <td><font color="red">${formatter.format(meal.getLocalDateTime())}</font></td>
                <td><font color="red">${meal.getDescription()}</font></td>
                <td><font color="red">${meal.getCalories()}</font></td>
                <td><a href="meals?action=update&mealId=${meal.getId()}">Update</a></td>
                <td><a href="meals?action=delete&mealId=${meal.getId()}">Delete</a></td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
