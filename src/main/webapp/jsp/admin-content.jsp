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

    <a class="btn btn-default" href="/jsp/registration-page.jsp" role="button" id="reg-button">
        <i class="fa fa-user-plus" aria-hidden="true"></i>
        Register new User
    </a>


    <table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>Name</th>
            <th>Position</th>
            <th>Office</th>
            <th>Age</th>
            <th>Options</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="user" items="${sessionScope.users}">
            <tr>
                <td>${user.getName()}</td>
                <td>${user.getPassport()}</td>
                <td>${user.getPhone()}</td>
                <td>${user.getBalance()}</td>
                <td>
                    <ul class="list-inline">
                        <li>
                            <a onclick="return confirmDelete()" href="controller?command=delete_user&passport=${user.getPassport()}">DELETE</a>
                        </li>
                        <li>
                            <!-- Trigger the modal with a button -->
                            <!--<input name="asasas" hidden>
                            <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">
                                Open Modal</button>-->
                            <a class="btn btn-default" id="${user.getPassport()}" href="#" role="button"
                               onclick="return confirmBan(id)">
                                BAN
                            </a>

                        </li>
                    </ul>
                </td>
            </tr>
        </c:forEach>

        <c:forEach var="banned" items="${sessionScope.bans}">
            <tr bgcolor="#ffe4c4">
                <td>${banned.getName()}</td>
                <td>${banned.getPassport()}</td>
                <td>${banned.getPhone()}</td>
                <td>${banned.getBalance()}</td>
                <td>
                    <ul class="list-inline">
                        <li>
                            <a href="controller?command=delete_user&passport=${banned.getPassport()}">DELETE</a>
                        </li>
                        <li>
                            <a href="#">UNBAN</a>
                        </li>
                    </ul>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

    <script>

        function confirmDelete() {
            var result = confirm("Want to delete?");
            if (result) {
                //Logic to delete the item
            }
            return result;
        }

        function confirmBan(passport) {
            do {
                var description = prompt('Reason for deleting', 'Write reason');
            }while(description=="");

            var id = document.getElementById(passport);

            var request = "controller?command=ban_user&passport="+passport+"&description="+description;

            var link = document.getElementById(passport).setAttribute("href", request);

            return true;
        }

        $(document).ready(function () {
            $('#example').DataTable();
        });

        $('#example').DataTable({
            "ordering": false
        });
    </script>
</div>
</body>
