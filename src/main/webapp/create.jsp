<%--
  Created by IntelliJ IDEA.
  User: Юрий
  Date: 09.06.2020
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create meal</title>
</head>
<body>
    <form method="post">
        <label>Date Time: </label><input type="datetime-local" id="dttm" name="dttm"><br/>
        <label>Description: </label><input type="text" id="description" name="description"><br/>
        <label>calories: </label><input type="number" id = "calories" name = "calories"><br/>
        <input type="submit">
    </form>
</body>
</html>
