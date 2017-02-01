<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 30.01.2017
  Time: 0:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<c:import url="parts/header.jsp"/>
<div class="container">
    <!-- Навигация -->
    <ul class="nav nav-tabs" role="tablist">
        <li class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Пользователь</a></li>
        <li><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Счета</a></li>
    </ul>
    <!-- Содержимое вкладок -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">

        </div>
        <div role="tabpanel" class="tab-pane" id="profile">
            <c:choose>
                <c:when test="${operations!=null}">
                    <table class="table">
                        <thead>

                        <th>
                            Тип операции
                        </th>
                        <th>
                            Дата
                        </th>
                        <th>
                            Сумма
                        </th>
                        </thead>
                        <tbody>

                        <c:forEach var="operation" items="${operations}">
                            <tr>
                                <td>
                                    <c:if test="${operation.getType()== 0}">
                                        Пополнение счета
                                    </c:if>
                                    <c:if test="${operation.getType() == 1}">
                                        ь Списание средств
                                    </c:if>
                                    <c:if test="${operation.getType()== 2}">
                                        Обещанный платеж
                                    </c:if>
                                </td>
                                <td>${operation.getDate()}</td>
                                <td>${operation.getSum()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </c:when>
                <c:otherwise>
                    <h1>NOTHING</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<c:import url="parts/footer.jsp"/>

</body>
</html>
