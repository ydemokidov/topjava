<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %><%--
  Created by IntelliJ IDEA.
  User: Юрий
  Date: 07.06.2020
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% List<MealTo> mealsList = (List<MealTo>)session.getAttribute("mealsList");%>
<link href="css/calories.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
    <div class = "mealsList">
        <c:forEach items="${mealsList}" var = "mealTo">
            <c:choose>
                <c:when test="${mealTo.isExcess()}">
                    <div class = "mealExceededRow">
                </c:when>
                <c:otherwise>
                    <div class = "mealRow">
                </c:otherwise>
            </c:choose>
                        <div class="column">
                            <c:out value="${mealTo.getFormattedDate()}"/>
                        </div>
                        <div class="column">
                            <c:out value="${mealTo.getDescription()}"/>
                        </div>
                        <div class="column">
                        <c:out value="${mealTo.getCalories()}"/>
                        </div>
                    </div>
        </c:forEach>
    </div>
</body>
</html>
