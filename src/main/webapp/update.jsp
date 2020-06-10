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
    <title>Update meal</title>
</head>
<body>
    <form action="<%= request.getContextPath()+"/meals/update"%>" method="post">
        <input type = "hidden" id = "id" name ="id" value="<%=request.getParameter("id")%>">
        <label>Date Time: </label><input type="datetime-local" id="dttm" name="dttm" value="<%=request.getParameter("dttm")%>"><br/>
        <label>Description: </label><input type="text" id="desc" name="desc" value="<%=request.getParameter("desc")%>"><br/>
        <label>calories: </label><input type="number" id = "calories" name = "calories" value="<%=Integer.parseInt(request.getParameter("calories"))%>"><br/>
        <input type="submit">
    </form>
</body>
</html>
