<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 18.01.2017
  Time: 23:18
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
    #add-button {
        margin-bottom: 20px !important;
    }
</style>

<div class="container">

    <c:if test="${sessionScope.user=='admin'}">
        <a class="btn btn-default" href="/jsp/service-add.jsp" role="button" id="add-button">
            <i class="fa fa-plus" aria-hidden="true"></i>
            Add new tariff
        </a>
    </c:if>

    <table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Validity</th>
            <th onclick="sortCost(tbody, 3, 1)">Cost</th>
            <c:if test="${sessionScope.user=='admin'}">
                <th>Options</th>
            </c:if>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="service" items="${sessionScope.services}">
            <tr>
                <td>${service.getName()}</td>
                <td>${service.getDescription()}</td>
                <td>${service.getValidity()}</td>
                <td>${service.getCost()}</td>
                <c:if test="${sessionScope.user=='admin'}">
                    <td>
                        <ul class="list-inline">
                            <li>
                                <a class="btn btn-default" onclick="return confirmDelete()"
                                   href="controller?command=delete_service&name=${service.getName()}">DELETE</a>
                            </li>
                        </ul>
                    </td>
                </c:if>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>

    function confirmDelete() {
        var result = confirm("Want to delete?");
        if (result) {
            //Logic to delete the item
        }
        return result;
    }

    $(document).ready(function () {
        $('#example').DataTable();
    });

    $('#example').dataTable({
        "aoColumnDefs": [
            {'bSortable': false, 'aTargets': [0]},
            {'bSortable': false, 'aTargets': [1]},
            {'bSortable': false, 'aTargets': [2]},
            {'bSortable': false, 'aTargets': [3]}

        ]
    });

</script>
</body>
