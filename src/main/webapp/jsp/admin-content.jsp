<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 13.01.2017
  Time: 12:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="../resource/js/admin-content.js"></script>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-3">
            <form action="#" method="get">
                <div class="input-group">
                    <!-- USE TWITTER TYPEAHEAD JSON WITH API TO SEARCH -->
                    <input class="form-control" id="system-search" name="q" placeholder="Search for" required>
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-default"><i
                                class="glyphicon glyphicon-search"></i></button>
                    </span>
                </div>
            </form>
        </div>
        <div class="col-md-9">
            <table class="table table-list-search">
                <thead>
                <tr>
                    <th>Entry</th>
                    <th>Entry</th>
                    <th>Entry</th>
                    <th>Entry</th>
                    <th>Entry</th>
                    <th>Entry</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${sessionScope.users}">
                    <tr>

                        <td>${user.getName()}</td>
                        <td>${user.getPassport()}</td>
                        <td>${user.getPhone()}</td>
                        <td>${user.getBalance()}</td>

                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
</body>


