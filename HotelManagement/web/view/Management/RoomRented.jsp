<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Del Luna Management</title>
        <script src="mycode.js"></script>
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/RentAndCheckOutStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/MenuStyle.css" rel="stylesheet" type="text/css" />

    </head>

    <body>

        <div class="row main">
            <jsp:include page="Menu.jsp"></jsp:include>

                <div class="col-md-10 right">
                    <div class="row title">
                        <div class="col-md-4">
                            <h3><span class="glyphicon glyphicon-align-justify"></span> Rent - Check out</h3>
                        </div>
                    </div>

                    <div class="content">
                        <ul>
                            <li><a href="RoomEmpty">Empty room</a></li>
                            <li><a 
                                <c:if test="${tagMenu eq \"check-rent\"}">
                                    style="background-color: #e9d1d1; color: red; padding: 3%;"
                                </c:if>
                                href="RoomRented">The room is rented</a></li>
                    </ul>

                    <div class="typeRoom">
                        <div class="first">
                            <p>Single Room</p>
                        </div>
                        <div class="row">
                            <c:forEach items="${roomNotEmpty}" var="rN">
                                <c:if test="${rN.deptNameContainsKey(\"SINGLE\")}">
                                    <div class="col-md-2 Room">
                                        <p>Room Number: ${rN.deptID}</p>
                                        <p>Check-out: ${rN.time}</p>
                                        <p>Status: Rented</p>
                                        <form action="RoomRented" method="POST">
                                            <input type="hidden" name="deptID" value="${rN.deptID}">
                                            <input type="submit" value="Check-out">
                                        </form>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="typeRoom">
                        <div class="first">
                            <p>Double Room</p>
                        </div>
                        <div class="row">
                            <c:forEach items="${roomNotEmpty}" var="rN">
                                <c:if test="${rN.deptNameContainsKey(\"DOUBLE\")}">
                                    <div class="col-md-2 Room">
                                        <p>Room Number: ${rN.deptID}</p>
                                        <p>Check-out: ${rN.time}</p>
                                        <p>Status: Rented</p>
                                       <form action="RoomRented" method="POST">
                                            <input type="hidden" name="deptID" value="${rN.deptID}">
                                            <input type="submit" value="Check-out">
                                        </form>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="typeRoom">
                        <div class="first">
                            <p>Family Room</p>
                        </div>
                        <div class="row">
                            <c:forEach items="${roomNotEmpty}" var="rN">
                                <c:if test="${rN.deptNameContainsKey(\"FAMILY\")}">
                                    <div class="col-md-2 Room">
                                        <p>Room Number: ${rN.deptID}</p>
                                        <p>Check-out: ${rN.time}</p>
                                        <p>Status: Rented</p>
                                        <form action="RoomRented" method="POST">
                                            <input type="hidden" name="deptID" value="${rN.deptID}">
                                            <input type="submit" value="Check-out">
                                        </form>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>

</html>
