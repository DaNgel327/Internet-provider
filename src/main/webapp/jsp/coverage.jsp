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
<c:import url="parts/header.jsp"/>
<div class="container">
    <div id="map" style="float:left">
        <iframe src="https://www.google.com/maps/d/embed?mid=1z8cSqA6mNGS0ejSEeBPLtL6curc" width="640"
                height="480"></iframe>
    </div>

    <c:if test="${sessionScope.user=='admin'}">
        <div class="row">
            <div class="form-group">
                <div class="col-md-6">
                    <a href="#" class="btn btn-default">ADD NEW LOCATION</a>
                </div>
                <div class="col-md6">
                    <a href="/controller?command=generate_csv" class="btn btn-default">Generate csv</a>
                </div>
            </div>
        </div>
    </c:if>
</div>
<c:import url="parts/footer.jsp"/>

</body>
</html>
