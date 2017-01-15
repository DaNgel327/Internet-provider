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
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="../resource/bootstrap/css/bootstrap.css"/>

    <link rel="stylesheet" type="text/css" href="../resource/bootstrap/css/font-awesome.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.13/js/dataTables.bootstrap.min.js"></script>
</head>
<body>

<style>
    #reg-button {
        margin-bottom: 20px !important;
    }
</style>

<div class="container">

    <button id="reg-button" class="btn btn-default">
        <i class="fa fa-user-plus" aria-hidden="true"></i>
        Register new User
    </button>

    <table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>Name</th>
            <th>Position</th>
            <th>Office</th>
            <th>Age</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="user" items="${sessionScope.users}">
            <tr>
                <th>${user.getName()}</th>
                <th>${user.getPassport()}</th>
                <th>${user.getPhone()}</th>
                <th>${user.getBalance()}</th>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <script>
        $(document).ready(function () {
            $('#example').DataTable();
        });

        $('#example').DataTable({
            "ordering": false
        });
    </script>
</div>
</body>
