<html>
<head>
    <meta charset="UTF-8">
    <title>title</title>

    <link rel="stylesheet" type="text/css" href="../resource/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="../resource/css/registration-form.css"/>
</head>
<body>

<style>
    textarea {
        background: none repeat scroll 0 0 #fff;
        border: medium none;
        -webkit-border-radius: 4px 4px 0 0;
        -moz-border-radius: 4px 4px 0 0;
        -ms-border-radius: 4px 4px 0 0;
        -o-border-radius: 4px 4px 0 0;
        border-radius: 4px 4px 0 0;
        color: #777777;
        font-family: Lato;
        font-size: 14px;
        height: 142px;
        letter-spacing: 0.3px;
        padding: 20px;
        width: 100%;
        resize: vertical;
        outline: none;
        border: 1px solid #F2F2F2;
    }
</style>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Why you ban this user?</h4>
            </div>
            <div class="modal-body">
                <textarea placeholder="Write a discription"></textarea>
            </div>
            <div class="modal-footer">
                <a class="btn btn-default"
                   href="controller?command=ban_user&passport=${sessionScope.get("passport")}" role="button">
                    Ban user
                </a>
            </div>
        </div>

    </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="../resource/bootstrap/js/bootstrap.js"></script>
</body>
</html>