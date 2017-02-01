<%@page contentType="text/html; charset = utf-8" pageEncoding="UTF-8" session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bootstrap Template</title>


    <!-- Bootstrap -->
    <link href="../../resource/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../resource/css/style.css">
    <link rel="stylesheet" type="text/css" href="../../resource/bootstrap/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="../../resource/css/form.css">

    <c:import url="/jsp/modal/login.jsp"/>
    <c:import url="/jsp/modal/change-password.jsp"/>


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<h1>${sessionScope.lang==null? 'en_EN':sessionScope.lang}</h1>

<fmt:setLocale
        value="${sessionScope.lang==null
         ? 'en_EN' : sessionScope.lang}"
        scope="session"/>


<fmt:setBundle basename="text" var="value"/>

<div class="container">
    <h1>Мой сайт</h1>

    <div class="navbar navbar-inverse">
        <div class="navbar-header">
            <a class="navbar-brand" hreaf="#">Логотип</a>
        </div>
        <div class="collapse navbar-collapse" id="responsive-menu">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/controller?command=show_service"><fmt:message
                        key="nav.service" bundle="${ value }"/></a>
                </li>
                <li><a href="#"><fmt:message key="nav.price" bundle="${ value }"/></a></li>
                <li><a href="${pageContext.request.contextPath}/jsp/coverage.jsp"><fmt:message key="nav.coverage"
                                                                                               bundle="${ value }"/></a>
                </li>
                <li><a href="#"><fmt:message key="nav.downloads" bundle="${ value }"/></a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">

                <c:choose>
                    <c:when test="${sessionScope.role==null}">
                        <li>
                            <a href="#" data-toggle="modal" data-target="#login-modal"><fmt:message key="nav.log-in"
                                                                                                    bundle="${ value }"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown">

                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Hello, ${SessionScope.role==0?"Admin":"User"}!<b
                                    class="caret"></b></a>

                            <ul class="dropdown-menu">

                                <c:choose>
                                    <c:when test="${sessionScope.role==0}">
                                        <li>
                                            <a href="controller?command=show_users">Пользователи</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <a href="/controller?command=show_user_profile">Профиль</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                                <li>
                                    <a href="#" data-toggle="modal" data-target="#changePassword-modal">Сменить
                                        пароль</a>
                                </li>
                                <li role="separator" class="divider"></li>
                                <li>
                                    <a href="/controller?command=logout">Выйти</a>
                                </li>
                            </ul>
                        </li>

                    </c:otherwise>
                </c:choose>

                <li>
                    <a href="controller?command=locale&lang=ru"><img class="icon-lang"
                                                                     src="../../resource/img/ru.svg"/></a>
                </li>
                <li>
                    <a href="controller?command=locale&lang=by"><img class="icon-lang"
                                                                     src="../../resource/img/by.svg"/></a>
                </li>
                <li>
                    <a href="controller?command=locale&lang=en"><img class="icon-lang"
                                                                     src="../../resource/img/us.svg"/></a>
                </li>

            </ul>

        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../../resource/bootstrap/js/bootstrap.js"></script>

</body>

</html>

