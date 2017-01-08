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

<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog">
        <div class="loginmodal-container">

            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>

            <h1>Login to Your Account</h1><br>
            <form>
                <input type="text" name="user" placeholder="Username">
                <input type="password" name="pass" placeholder="Password">
                <input type="submit" name="login" class="login loginmodal-submit" value="Login">
            </form>

            <div class="login-help">
                <a href="#">How to get an account?</a> - <a href="#">Forgot Password</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
