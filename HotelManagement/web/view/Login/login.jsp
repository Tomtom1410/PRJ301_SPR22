<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="keywords"
              content="unique login form,leamug login form,boostrap login form,responsive login form,free css html login form,download login form">
        <meta name="author" content="leamug">
        <title>Login</title>
        <link href="${pageContext.request.contextPath}/CSS/loginStyle.css" rel="stylesheet">
        <!-- Bootstrap core Library -->
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Dancing+Script" rel="stylesheet">
        <!-- Font Awesome-->
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
        <!-- Page Content -->
        <div class="container">
            <form action="login" method="POST">
                <h1 class='text-white'>Login to access the system</h1>
                <div class="form-login"></br>
                    <h4>Secure Login</h4>
                    </br>
                    <input type="text" name="username" id="userName" class="form-control input-sm chat-input" placeholder="username"/>
                    <br>
                    <input type="password" name="password" id="userPassword" class="form-control input-sm chat-input" placeholder="password"/>
                    <br>
                    <c:if test = "${!requestScope.isLogin}">
                        <span>Login failed! Please check username or password again!</span>
                    </c:if>
                    <br>
                    <div class="wrapper">
                        <input class="btn btn-danger btn-md login" type="submit" value="Login">
                    </div>
                </div>
            </form>
            </br></br></br>
            <!--footer-->
<!--            <div class="footer text-white text-center">
                <p>Have a nice day!</p>
            </div>-->
            <!--//footer-->
        </div>
    </body>
</html>
