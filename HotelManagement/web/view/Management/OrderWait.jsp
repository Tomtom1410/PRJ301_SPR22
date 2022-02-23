<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Del Luna Management</title>
        <!--java Script-->
        <script src="${pageContext.request.contextPath}/JavaScript/ManagementCode.js"></script>
        <script src="${pageContext.request.contextPath}/JavaScript/OrderCode.js"></script>
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/OrderWaitStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/MenuStyle.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div class="row main">
            <jsp:include page="Menu.jsp"></jsp:include>

                <div class="col-md-10 right">
                    <div class="row title">
                        <div class="col-md-4">
                            <h3><span class="glyphicon glyphicon-align-justify"></span> Information Of Customer</h3>
                        </div>
                        <div class="col-md-4 Search">
                            <span class=" glyphicon glyphicon-search"></span> 
                            <input id="content"
                            <c:if test="${value != null}">
                                value =${value}
                            </c:if>
                            type="text" placeholder="Enter customer's name">
                        <button type="button" onclick="search('orderWait')">Search</button>
                    </div>
                </div>
                <div class="row information">
                    <div class="Banner">
                        <ul>
                            <li>
                                <a 
                                    <c:if test="${title eq \"wait\"}">
                                        style="background-color: #e9d1d1; color: red; padding: 3%;"
                                    </c:if> href="InformationOfCustomerWait">Orders Wait
                                </a>
                            </li>
                            <li><a href="InformationOfCustomerHadRoom">Orders Have Room</a></li>
                        </ul>
                    </div>
                    <div class="content col-md-8">
                        <table>
                            <tr>
                                <td>Customer Name</td>
                                <td>Phone Number</td>
                                <td>No Of Rooms</td>
                                <td>Room Type</td>
                                <td>Check-in</td>
                                <td>Check-out</td>
                                <td>Rented</td>
                                <td>Details</td>
                            </tr>
                            <c:forEach items="${orders}" var="o">
                                <tr>
                                    <td>${o.customer.customerName}</td>
                                    <td>${o.customer.phone}</td>
                                    <td>${o.noOfRoom}</td>
                                    <td>${o.department.deptName}</td>
                                    <td>${o.checkIn}</td>
                                    <td>${o.checkOut}</td>
                                    <td>${o.isRented()?"Yes":"No"}</td>
                                    <td>
                                        <a href="BookingDetail?orderWaitID=${o.orderWaitID}&page=${pageIndex}">Details</a>
                                    </td>

                                </tr>
                            </c:forEach>
                        </table>

                        <div id="paggingBottom" class="pageLine" style=" margin: 1%; float: right;"></div>
                        <script>
                            generatePagger('paggingBottom',${requestScope.pageIndex},${requestScope.totalPage}, '${requestScope.url}', 2);
                        </script>
                    </div>
                    <c:if test="${o != null}">
                        <div class="detail col-md-4">
                            <form action="BookingDetail" method="POST" id="insertBooking">
                                <c:if test="${flag eq \"false\"}">
                                    <p style="color: red;">Please check the room. The number of rooms is
                                        different from the number of rooms the customer wants!</p>
                                    </c:if>
                                <table>
                                    <tr>
                                        <td><input type="hidden" name="oID" value="${o.orderWaitID}"></td>
                                        <td><input type="hidden" name="customerID" value="${o.customer.customerID}"></td>
                                    </tr>
                                    <tr>
                                        <td>Name: ${o.customer.customerName}</td>
                                    </tr>
                                    <tr>
                                        <td>Phone: ${o.customer.phone}</td>
                                    </tr>
                                    <tr>
                                        <td>Email: ${o.customer.email}</td>
                                    </tr>
                                    <tr>
                                        <td>Check-in: ${o.checkIn}</td>
                                    </tr>
                                    <tr>
                                        <td>Check-out: ${o.checkOut}</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="hidden" name="roomByName" value="${o.department.deptName}">
                                            Room type: ${o.department.deptName}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="hidden" name="noOfRoom" id="noOfRoom" value="${o.noOfRoom}">
                                            Number of rooms: ${o.noOfRoom}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Choose room: 
                                            <c:forEach items="${roomByName}" var="r">
                                                <input name="deptID" type="checkbox" 
                                                       value="${r.deptID}"> ${r.deptID}
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </table>
                                <c:if test="${notic}">
                                    <p style="color: red; font-weight: bold">Insert done!</p>
                                </c:if>
                                <button class="save" name="button" onclick="checkRoom()" value="update" type="submit">Update</button>
                                <button class="del" name="button" value="delete" type="submit">Cancel</button>
                            </form>

                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
