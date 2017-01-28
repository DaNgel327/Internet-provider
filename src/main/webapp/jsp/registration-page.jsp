<html>
<head>
    <meta charset="UTF-8">
    <title>title</title>

    <link rel="stylesheet" type="text/css" href="../resource/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="../resource/css/registration-form.css"/>
</head>
<body>

<style>
    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
</style>

<div class="row centered-form">
    <div id="reg-form" class="col-xs-12 col-sm-6 col-md-6 col-lg-4 col-sm-offset-3 col-md-offset-3 col-lg-offset-4">

        <h1>Register new user</h1><br>

        <form onsubmit="return validate(this)" role="form" action="${pageContext.request.contextPath}/controller">
            <input hidden name="command" value="register_user"/>
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
                        <input type="text" name="surname" class="form-control input-sm" placeholder="Surname" required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <input type="text" name="patronymic" class="form-control input-sm" placeholder="Patronymic" required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <input type="text" name="passport" class="form-control input-sm" placeholder="Passport"
                               required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <input type="text" name="phone" class="form-control input-sm" placeholder="Phone">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <input type="text" name="city" class="form-control input-sm" placeholder="City" required>
                    </div>
                </div>
                <div class="col-md-6">

                    <div class="form-group">
                        <input type="text" name="street" class="form-control input-sm" placeholder="Street" required>
                    </div>
                </div>
            </div>
            <div class="row">

                <div class="col-md-2">
                    <div class="form-group">
                        <input type="number" min="1" name="house" class="form-control input-sm" required placeholder="Hse">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <input type="number" min="1" name="apt" class="form-control input-sm" required
                               placeholder="Apt.">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <input type="number" min="1" name="building" class="form-control input-sm" required
                               placeholder="Building">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <select class="form-control input-sm">
                            <option value="" disabled selected>Select start service</option>
                            <option>Service1</option>
                            <option>Service2</option>
                            <option>Service2</option>
                        </select>

                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <input type="email" name="email" class="form-control input-sm" placeholder="Email" required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <input type="number" min="0" step="0.01" name="balance" class="form-control input-sm" placeholder="Balance">
                    </div>
                </div>
            </div>
            <input type="submit" value="Register" class="btn btn-info btn-block">
        </form>
    </div>
</div>

<script type="text/javascript">

    function showError(inputNumber, errorMessage) {
        var input = document.getElementsByTagName('input')[inputNumber];
        if (input.value != "") {
            input.value = "";
        }
        input.setAttribute('placeholder', errorMessage);
    }

    function check(input, regex, message, inputNumber) {

        if (!regex.test(input)) {
            showError(inputNumber, message);
            return false;
        }

        return true;
    }

    function validate(form) {
        var elems = form.elements;
        var errors = 0;

        var DOUBLE_OR_SINGLE_WORD_REG = /[a-zA-Z-]+/;
        var SINGLE_WORD_REG = /[a-zA-Z-]/;
        var PASSPORT_REG = /^([a-zA-Z]{2})([0-9]{7})$/;
        var PHONE_REG = /^([+])([0-9]{3})-([0-9]{2})-([0-9]{3})-([0-9]{2})-([0-9]{2})$/;

        var NAME_ERROR = 'Name error';
        var SURNAME_ERROR = 'Surname error';
        var PATRONYMIC_ERROR = 'Patronymic error';
        var PASSPORT_ERROR = 'Passport error';
        var PHONE_ERROR = 'Phone error';
        var CITY_ERROR = 'City error';
        var STREET_ERROR = 'Street error';
        var EMAIL_ERROR = 'Email error';

        if (!check(elems.name.value, DOUBLE_OR_SINGLE_WORD_REG, NAME_ERROR, 0)) {
            errors++;
        }

        if (!check(elems.surname.value, DOUBLE_OR_SINGLE_WORD_REG, SURNAME_ERROR, 1)) {
            errors++;
        }

        if (!check(elems.patronymic.value, DOUBLE_OR_SINGLE_WORD_REG, PATRONYMIC_ERROR, 2)) {
            errors++;
        }

        if (!check(elems.passport.value, PASSPORT_REG, PASSPORT_ERROR, 3)) {
            errors++;
        }

        if (!check(elems.phone.value, PHONE_REG, PHONE_ERROR, 4)) {
            errors++;
        }

        if (!check(elems.city.value, DOUBLE_OR_SINGLE_WORD_REG, CITY_ERROR, 5)) {
            errors++;
        }

        if (!check(elems.street.value, DOUBLE_OR_SINGLE_WORD_REG, STREET_ERROR, 6)) {
            errors++;
        }

        return errors == 0;
    }
</script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="../resource/bootstrap/js/bootstrap.js"></script>

</body>
</html>