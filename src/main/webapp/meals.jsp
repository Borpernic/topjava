<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="ru.javawebinar.topjava.util.TimeUtil" %>


<%--
  Created by IntelliJ IDEA.
  User: bn
  Date: 22.07.2017
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link href='styles.css' rel='stylesheet' type='text/css'>
    <style>
        TABLE {
            width: 300px; /* Ширина таблицы */
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }

        TD, TH {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }
    </style>


    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>


<table>
    <tr style="color: black">
        <td> #</td>
        <td>Дата</td>
        <td>Описание</td>
        <td>Калории</td>
        <td>Статус</td>
    </tr>
    <c:forEach var="meal" items="${meals}" varStatus="сounter">
        <c:if test="${meal.isExceed() == true}">
            <tr style="color: red" >
        </c:if>
        <c:if test="${meal.isExceed() != true}">
            <tr style="color: green">
        </c:if>
        <td> ${сounter.count}</td>

        <td>${fn:formatLocalDateTime(meal.getDateTime())}</td>
        <td>${meal.getDescription()}</td>
        <td>${meal.getCalories()}</td>
        <td>${meal.isExceed()}</td>
        </tr>

        <tr> </tr>
    </c:forEach>
</table>
<%--//meals.stream().forEach(mealWithExceed -> System.out.printf("Meals: %1", mealWithExceed.toString()));--%>

</body>
</html>
