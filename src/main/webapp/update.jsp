<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<br/>
<form method="POST" action='meals' name="formEditMeal">
    <input name="id" type="hidden" value="${updateMeal.getId()}" />
    DateTime:      <input name="datetime" type="datetime-local"/><br/><br/>
    Description:   <input name="description" type="text" value="${updateMeal.getDescription()}"/><br/><br/>
    Calories:      <input name="calories" type="number" value="${updateMeal.getCalories()}"/><br/><br/>
    <input name="save" type="submit" value="Save" />
    <input name="cansel" type="reset" value="Cansel" />
</form>
</body>
</html>
