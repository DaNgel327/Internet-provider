<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 30.01.2017
  Time: 2:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<div class="modal fade" id="changeLogin-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog">

        <div class="modalWindow-container">

            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>

            <div class="alert alert-warning alert-dismissable" id="badPassword-alert" hidden>
                <p>Old password isn't correct</p>
            </div>

            <h1>Login to Your Account</h1><br>
            <form onsubmit="return validate(this)" action="/controller" method="post">
                <input name="command" type="hidden" value="change_login">

                <input type="text" name="currentLogin" placeholder="Current login">
                <input type="text" name="newLogin" placeholder="New login">
                <input type="text" name="confirmLogin" placeholder="Confirm login">

                <input type="password" name="password" placeholder="Your password">
                <input type="submit" class="modalWindow-submit"
                       value="Change">
            </form>
        </div>
    </div>
</div>

<div id="backstage" class="modal-backdrop fade in" hidden></div>


<script>

    function showError(inputName, errorMessage) {

        var input = document.getElementsByName(inputName)[0];

        if (input.value != "") {
            input.value = "";
        }
        input.setAttribute('placeholder', errorMessage);
    }

    function validate(form) {

        var currentLogin = form.elements.currentLogin.value;
        var newLogin = form.elements.newLogin.value;
        var password = form.elements.password.value;
        var confirmLogin = form.elements.confirmLogin.value;

        if (currentLogin.length < 8) {
            showError("currentLogin", 'Too small');
            return false;
        }
        if (currentLogin.length > 20) {
            showError("currentLogin", 'Too big');
            return false;
        }

        var LOGIN_REGEXP = /[A-Za-z0-9-_]+/;

        if(LOGIN_REGEXP.test(currentLogin)){
            showError("currentLogin", 'Only letters, numbers, \'-\' and \'_\'');
            return false;
        }

        if (newLogin.length < 8) {
            showError("newLogin", 'Too small');
            return false;
        }
        if (newLogin.length > 20) {
            showError("newLogin", 'Too big');
            return false;
        }
        if(LOGIN_REGEXP.test(currentLogin)){
            showError("newLogin", 'Only letters, numbers, \'-\' and \'_\'');
            return false;
        }

        if (newLogin != confirmLogin) {
            showError("newLogin", 'Dont mathes');
            showError("confirmLogin", 'Dont mathes');
            return false;
        }
        if (password.length < 8) {
            showError("password", 'Too small');
            return false;
        }
        if (password.length > 16) {
            showError("password", 'Too big');
            return false;
        }

        var PASSWORD_REGEXP = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z\d_-]{8,16}$/;

        if (!PASSWORD_REGEXP.test(newLogin)) {

            if (newPassword.search(/[a-z]/) == -1) {
                showError("newPassword", 'At least 1 lowercase');
                showError("confirm", 'At least 1 lowercase');
                return false;
            }
            if (newPassword.search(/[A-Z]/) == -1) {
                showError("newPassword", 'At least 1 upperrcase');
                showError("confirm", 'At least 1 upperrcase');
                return false;
            }
            if (newPassword.search(/[0-9]/) == -1) {
                showError("newPassword", 'At least 1 number');
                showError("confirm", 'At least 1 number');
                return false;
            }
            showError("currentPassword", 'Only letters, numbers, \'-\' and \'_\'');
            return false;
        }

        return true;
    }
</script>


</body>
</html>
