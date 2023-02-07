<%--
  Created by IntelliJ IDEA.
  User: rassa
  Date: 06.02.2023
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Meal</h2>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post" action="meals">
    <input type="hidden" name="mealId" value="${meal.id}"/>
    <input type="datetime-local" name="dateTime" value="${meal.dateTime}" required/>
    <hr>
    <input type="text" name="description" value="${meal.description}" required/>
    <hr>
    <input type="number" name="calories" value="${meal.calories}" required/>
    <hr>
    <input type="submit" value="Save"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
