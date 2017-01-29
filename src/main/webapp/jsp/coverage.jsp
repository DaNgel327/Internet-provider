<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 20.01.2017
  Time: 2:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCINqVlCjanlmaF1XNGQZBTIyA_MYj_3mI&callback=initMap">
    </script>
</head>
<body>


<div class="container alert alert-success" id="coverage-added-alert" hidden>
    <button type="button" class="close" data-dismiss="alert">x</button>
    <strong>Success! </strong>
    Coverage added.
</div>
<div class="container alert alert-warning" id="coverage-exist-alert" hidden>
    <button type="button" class="close" data-dismiss="alert">x</button>
    <strong>Error! </strong>
    Coverage exist.
</div>


<c:import url="parts/header.jsp"/>

<style>

    #spinner{
        display: inline-block;
    }
    .floating-box {
        float: left;
        width: 640px;
        height: 480px;
        margin: 10px;
    }

    .floating-form {
        width: 400px;
        height: 400px;
        margin: 10px;
        display: inline-block;
    }

    .floating-left-button {
        float: left;
        width: 45%;
        margin: 2% 5% 10% 0;
    }

    .floating-right-button {
        float: left;
        width: 45%;
        margin: 2% 0 10% 5%;
    }

    .row {
        margin: 0;
    }

    @media only screen and (max-width: 1300px) {
        /* For mobile phones: */
        .floating-box {
            float: none;
            width: 640px;
            display: table;
            margin: 0 auto;
        }

        .floating-form {
            width: 640px;
            display: table;
            margin: 0 auto;
        }
    }

    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    #removed{
        display: none;
    }

</style>

<iframe id="my_iframe" style="display:none;"></iframe>

<div class="container">

    <div class="floating-box">
        <iframe src="https://www.google.com/maps/d/embed?mid=1z8cSqA6mNGS0ejSEeBPLtL6curc" width="640"
                height="480"></iframe>
    </div>

    <c:if test="${sessionScope.role==0}">

        <div class="floating-form">
            <div class="row">
                <div class="floating-left-button" onclick="viewForm()">
                    <a href="#" class="btn btn-default btn-block">ADD NEW LOCATION</a>
                </div>
                <div class="floating-right-button">
                    <a href="/controller?command=generate_csv" onclick="spin()" class="btn btn-default btn-block">
                        Generate csv
                        <i id="removed" class="fa fa-spinner fa-pulse fa fa-fw"></i>
                    </a>
                </div>
            </div>
            <form hidden id="locationForm" action="/controller" method="post">
                <input hidden name="command" value="add_coverage"/>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <input required type="text" name="city" class="form-control input-sm" placeholder="City"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <input required type="text" name="street" class="form-control input-sm"
                                   placeholder="Street"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <input required type="number" min="1" name="house" class="form-control input-sm"
                                   placeholder="House"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <input required type="number" min="1" name="building" class="form-control input-sm"
                                   placeholder="Building"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <input type="submit" class="form-control input-sm" value="Add coverage"/>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </c:if>
</div>

<script>

    function spin() {
        document.getElementById("removed").removeAttribute("id");
    }

    function viewForm() {
        var form = document.getElementById("locationForm");
        form.hidden = !form.hidden;
    }
</script>

<c:if test="${complete!=null}">
    <c:choose>
        <c:when test="${complete}">

            <script>
                document.getElementsByTagName("coverage-added-alert").hidden = false;
                $("#coverage-added-alert").fadeTo(5000, 500).slideUp(500, function () {
                    $("#coverage-added-alert").slideUp(500);
                });
            </script>
        </c:when>
        <c:otherwise>
            <script>
                document.getElementsByTagName("coverage-exist-alert").hidden = false;
                $("#coverage-exist-alert").fadeTo(5000, 500).slideUp(500, function () {
                    $("#coverage-exist-alert").slideUp(500);
                });
            </script>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${generated && generated!=null}">
    <script>
        document.getElementById('my_iframe').src = 'resource/csv/coverage.csv';
    </script>
</c:if>

</body>
</html>
