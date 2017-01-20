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
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
</head>
<body>
<c:import url="header.jsp"/>
<div class="container">
    <iframe src="https://www.google.com/maps/d/embed?mid=1z8cSqA6mNGS0ejSEeBPLtL6curc" width="640"
            height="480"></iframe>
</div>
<c:import url="footer.jsp"/>


</body>
</html>
