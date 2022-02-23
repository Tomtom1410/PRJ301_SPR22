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
                            <li><a 
                                <c:if test="${ept eq \"eR\"}">
                                    style="background-color: #e9d1d1; color: red; padding: 3%;"
                                </c:if> href="RoomEmpty">Empty room</a></li>
                        <li><a href="RoomRented">The rooms are rented</a></li>
                    </ul>

                    <div class="typeRoom">
                        <div class="first">
                            <p>Single Room</p>
                        </div>
                        <div class="row">
                            <c:forEach items="${roomEmpty}" var="rE">
                                <c:if test="${rE.deptNameContainsKey(\"SINGLE\")}">
                                    <div class="col-md-2 Room">
                                        <p>Room Number: ${rE.deptID}</p>
                                        <c:if test="${rE.time == null}">
                                            <p>Check-in: None</p>
                                        </c:if>
                                        <c:if test="${rE.time != null}">
                                            <p>Check-in: ${rE.time}</p>
                                        </c:if>
                                        <c:if test="${!rE.status}">
                                            <p>Status: Empty</p>
                                        </c:if>
                                        <!--<p><input type="button" value="Check-out"></p>-->
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
                            <c:forEach items="${roomEmpty}" var="rE">
                                <c:if test="${rE.deptNameContainsKey(\"DOUBLE\")}">
                                    <div class="col-md-2 Room">
                                        <p>Room Number: ${rE.deptID}</p>
                                        <c:if test="${rE.time == null}">
                                            <p>Check-in: None</p>
                                        </c:if>
                                        <c:if test="${rE.time != null}">
                                            <p>Check-in: ${rE.time}</p>
                                        </c:if>
                                        <c:if test="${!rE.status}">
                                            <p>Status: Empty</p>
                                        </c:if>
                                        <!--<p><input type="button" value="Check-out"></p>-->
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
                            <c:forEach items="${roomEmpty}" var="rE">
                                <c:if test="${rE.deptNameContainsKey(\"FAMILY\")}">
                                    <div class="col-md-2 Room">
                                        <p>Room Number: ${rE.deptID}</p>
                                        <c:if test="${rE.time == null}">
                                            <p>Check-in: None</p>
                                        </c:if>
                                        <c:if test="${rE.time != null}">
                                            <p>Check-in: ${rE.time}</p>
                                        </c:if>
                                        <c:if test="${!rE.status}">
                                            <p>Status: Empty</p>
                                        </c:if>
                                        <!--<p><input type="button" value="Check-out"></p>-->
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
