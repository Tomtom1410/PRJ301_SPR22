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
        <script src="${pageContext.request.contextPath}/JavaScript/BookingCode.js"></script>
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/BookingHistoryStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/MenuStyle.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div class="row main">
            <jsp:include page="Menu.jsp"></jsp:include>

                <div class="col-md-10 right">
                    <div class="row title">
                        <div class="col-md-4">
                            <h3><span class="glyphicon glyphicon-align-justify"></span> Booking History</h3>
                        </div>
                        <div class="col-md-4 Search">
                            <span class=" glyphicon glyphicon-search"></span> 
                            <input id="content"
                            <c:if test="${value != null}">
                                value =${value}
                            </c:if>
                            type="text" placeholder="Enter customer's name">
                        <button type="button" onclick="search('booking')">Search</button>
                    </div>
                </div>
                <div class="row information">
                    <div class="Banner">
                        <form action="BookingHistory" method="POST">
                            <span>Display: </span>
                            <select name="sort">
                                <option value="all">All</option>
                                <option
                                    <c:if test="${tag eq \"notCancel\"}">
                                        selected="selected"
                                    </c:if>
                                    value="notCancel">Not Cancel Room
                                </option>
                                <option 
                                    <c:if test="${tag eq \"cancel\"}">
                                        selected="selected"
                                    </c:if>
                                    value="cancel">Cancel Room
                                </option>
                            </select>
                            <button type="submit">Submit</button>
                        </form>
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
                                <td>Cancel room</td>
                                <td>Details</td>
                            </tr>
                            <c:forEach items="${bookingHistory}" var="b">
                                <tr>
                                    <td>${b.orderWait.customer.customerName}</td>
                                    <td>${b.orderWait.customer.phone}</td>
                                    <td>${b.orderWait.noOfRoom}</td>
                                    <td>${b.orderWait.department.deptName}</td>
                                    <td>${b.orderWait.checkIn}</td>
                                    <td>${b.orderWait.checkOut}</td>
                                    <td>${b.isCancel()?"Yes":"No"}</td>
                                    <td>
                                        <a href="BookingHistoryDetail?orderWaitID=${b.orderWait.orderWaitID}">Details</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                        <div id="paggingBottom" class="pageLine" style=" margin: 1%; float: right;">
                        </div>
                        <script>
                            generatePagger('paggingBottom',${requestScope.pageIndex},${requestScope.totalPage}, '${requestScope.url}', 2);
                        </script>
                    </div>
                    <c:if test="${bookingDetail != null}">
                        <div class="detail col-md-4">
                            <table>
                                <tr>
                                    <td>Name: ${bookingDetail.orderWait.customer.customerName}</td>
                                </tr>
                                <tr>
                                    <td>Phone: ${bookingDetail.orderWait.customer.phone}</td>
                                </tr>
                                <tr>
                                    <td>Address: ${bookingDetail.orderWait.customer.address}</td>
                                </tr>
                                <tr>
                                    <td>Email: ${bookingDetail.orderWait.customer.email}</td>
                                </tr>
                                <tr>
                                    <td>Check-in: ${bookingDetail.orderWait.checkIn}</td>
                                </tr>
                                <tr>
                                    <td>Check-out: ${bookingDetail.orderWait.checkOut}</td>
                                </tr>
                                <tr>
                                    <td>Room type: ${bookingDetail.orderWait.department.deptName}</td>
                                </tr>

                                <c:if test="${bookingDetail.isCancel()}">
                                    <tr>
                                        <td style="color: red;">Customer has canceled the room</td>
                                    </tr>
                                </c:if>
                                <c:if test="${!bookingDetail.isCancel()}">
                                    <tr>
                                        <td>Number of rooms: ${bookingDetail.orderWait.noOfRoom}</td>
                                    </tr>
                                    <tr>
                                        <td>List rooms: 
                                            <c:forEach items="${bookingDetail.departments}" var="bd">
                                                <span>${bd.deptID} </span>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Amount to be paid: ${invoice.totalPrice}$</td>
                                    </tr>
                                </c:if>

                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
