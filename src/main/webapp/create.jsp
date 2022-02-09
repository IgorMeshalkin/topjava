<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Create meal</h2>
<br/>
<form method="POST" action='meals' name="formCreateMeal">
    <input name="id" type="hidden" value="0" />
    DateTime:      <input name="datetime" type="datetime-local"/><br/><br/>
    Description:   <input name="description" type="text"/><br/><br/>
    Calories:      <input name="calories" type="number"/><br/><br/>
    <input name="save" type="submit" value="Save" />
    <input name="cansel" type="reset" value="Cansel" />
</form>
</body>
</html>
