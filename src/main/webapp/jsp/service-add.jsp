<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>title</title>


    <link rel="stylesheet" type="text/css" href="../resource/bootstrap/css/bootstrap.css"/>
</head>
<body>
<c:import url="parts/header.jsp"/>
<style>

    #add-service{
        margin-top: 10px;
        margin-bottom: 10px;
    }

    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
</style>

<div id="add-service" class="row centered-form">
    <div id="reg-form" class="col-xs-12 col-sm-6 col-md-6 col-lg-4 col-sm-offset-3 col-md-offset-3 col-lg-offset-4">

        <h1>Add new tariff</h1><br>

        <form role="form" action="${pageContext.request.contextPath}/controller">
            <input hidden name="command" value="add_service"/>

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <input type="text" name="name" class="form-control input-sm" placeholder="Name" required>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <textarea placeholder="Description" name="description" class="form-control" rows="3" required></textarea>
                    </div>
                </div>
            </div>

            <style>
                textarea {
                    max-width: 100%;
                }
            </style>

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <select class="form-control input-sm" name="validity">
                            <option>1 Day</option>
                            <option>1 Weekends</option>
                            <option>1 Week</option>
                            <option>2 Weeks</option>
                            <option>1 Month</option>
                            <option>3 Month</option>
                            <option>6 Month</option>
                            <option>Year</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <input type="number" min="0" name="cost" step="0.01" class="form-control input-sm" placeholder="Cost" required>
                    </div>
                </div>
            </div>


            <input type="submit" value="Add tariff" class="btn btn-info btn-block">
        </form>
    </div>
</div>

<c:import url="parts/footer.jsp"/>
</body>
</html>