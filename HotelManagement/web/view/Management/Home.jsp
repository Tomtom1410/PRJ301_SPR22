
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Del Luna Management</title>
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/MenuStyle.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />
    </head>
    <body>
        <div class="row main">
            <jsp:include page="Menu.jsp"></jsp:include>

            <div class="col-md-10 right">
                <div class="row title">
                    <div class="col-md-4">
                        <h3><span class="glyphicon glyphicon-align-justify"></span> Home</h3>
                    </div>
                </div>

                <div class="content">
                    <h1>Have A Nice Day!!<br>
                        <span class="glyphicon glyphicon-fire"></span>
                    </h1>
                </div>
            </div>
        </div>

        <style>
            .right {
                height: auto;
            }

            .right .title {
                margin: 0 0 0 -2%;
                padding: 0 0 4% 3%;
                height: 8rem;
                border-bottom: 1px solid grey;
            }

            .content{
                text-align: center;
                color: red;
            }

            .content h1{
                font-size: 12rem;
                margin: 16% 0;
            }

            .content span{
                color: #0072ff73;
            }
        </style>
    </body>
</html>
