<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Hotel Del Luna</title>
        <script src="mycode.js"></script>
        <!-- Bootstrap && CSS-->
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/RoomStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/FooterStyle.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/HeaderStyle.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>

        <div class="container">
            <jsp:include page="Header.jsp"></jsp:include>
                <div class="Banner">
                    <p>Room</p>
                </div>

                <div class="row Room">
                <c:forEach items="${depts}" var="d">
                    <div class="col-md-4 detail">
                        <c:forEach items="${d.url}" var="du">
                            <div><img src="${du}" alt=""></div>
                            </c:forEach>
                        <div>
                            <h3>${d.deptName}</h3>
                            <div>
                                <p>Price: ${d.price}$/day</p>
                                <a href="Room_detail?deptName=${d.deptName}">Detail</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <jsp:include page="Footer.jsp"></jsp:include>
            </div>
            <!--contianer end-->

            <!--javascript of Bootstrap-->
            <script src="${pageContext.request.contextPath}/Bootstrap/js/Jquery.js"></script>
        <script src="${pageContext.request.contextPath}/Bootstrap/js/bootstrap.min.js"></script>


    </body>

</html>