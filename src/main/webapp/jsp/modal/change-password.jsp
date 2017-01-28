<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 07.01.2017
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="modal fade" id="changePassword-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog">

        <div class="modalWindow-container">

            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>

            <div class="alert alert-warning alert-dismissable" id="alert" hidden>
                <p>Something goes wrong</p>
            </div>

            <h1>Login to Your Account</h1><br>
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input name="command" type="hidden" value="change_password">
                <input type="password" name="currentPassword" placeholder="Current password">
                <input type="password" name="newPassword" placeholder="New password">
                <input type="password" name="confirm" placeholder="Confirm new password">
                <input type="submit" onclick="return checkPasswords(this.form)" class="modalWindow-submit" value="Change">
            </form>
        </div>
    </div>
</div>

<div id="backstage" class="modal-backdrop fade in" hidden></div>

<c:choose>
    <c:when test="${!sessionScope.done && sessionScope.done!=null}">
        <script type="text/javascript">

            $(window).on('load', function(){
                $('#login-modal').modal('show');
            });

            document.getElementById("alert").hidden=false;

            $(".close").click(function(){

                document.getElementById("alert").hidden=true;
            });

        </script>
    </c:when>
</c:choose>


<script>

    function showError(inputNumber, errorMessage) {
        var input = document.getElementsByTagName('input')[inputNumber];
        if (input.value != "") {
            input.value = "";
        }
        input.setAttribute('placeholder', errorMessage);
    }

    function checkPassword(form){

        var elems = form.elements;
        if(elems.currentPassword.value.length<8){
            showError(1, 'Too small');
            return false;
        }

        if(elems.newPassword.value.length>16){
            showError(2, 'Too big');
            return false;
        }

        if(elems.newPassword.value!=elems.confirm.value){
            showError(2, 'Dont mathes');
            return false;
        }

        var re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d_-]{8,16}$/;

        if(elems.newPassword.value.test(re)){

            if(!elems.newPassword.value.search(/[a-z]+/)){
                showError(2, 'At least 1 lowercase');
                return false;
            }
            if(!elems.newPassword.value.search(/[A-Z]+/)){
                showError(2, 'At least 1 upperrcase');
                return false;
            }
            if(!elems.newPassword.value.search(/[0-9]+/)){
                showError(2, 'At least 1 number');
                return false;
            }
            showError(2, 'Bad password');
            return false;
        }

        return true;
    }
</script>

</body>
</html>
