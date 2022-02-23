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
                        <button type="button" onclick="search('haveRoom')">Search</button>
                    </div>
                </div>
                <div class="row information">
                    <div class="Banner">
                        <ul>
                            <li>
                                <a  href="InformationOfCustomerWait">Orders Wait</a>
                            </li>
                            <li><a 
                                    <c:if test="${title eq \"hadRoom\"}">
                                        style="background-color: #e9d1d1; color: red; padding: 3%;"
                                    </c:if>
                                    href="InformationOfCustomerHadRoom">Orders Have Room</a>
                            </li>
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
                            <c:forEach items="${allBookingNotCancel}" var="b">
                                <tr>
                                    <td>${b.orderWait.customer.customerName}</td>
                                    <td>${b.orderWait.customer.phone}</td>
                                    <td>${b.orderWait.noOfRoom}</td>
                                    <td>${b.orderWait.department.deptName}</td>
                                    <td>${b.orderWait.checkIn}</td>
                                    <td>${b.orderWait.checkOut}</td>
                                    <td>${b.orderWait.isRented()?"Yes":"No"}</td>
                                    <td>
                                        <a href="ChangeInformationOfCustomer?orderWaitID=${b.orderWait.orderWaitID}&page=${pageIndex}">Details</a>
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
                            <form action="ChangeInformationOfCustomer" method="POST" id="insertBooking">
                                <c:if test="${flag eq \"false\"}">
                                    <p style="color: red;">Please check the room. The number of rooms is
                                        different from the number of rooms the customer wants!</p>
                                    </c:if>
                                <table>
                                    <tr>
                                        <td><input type="hidden" name="oID" value="${bookingDetail.orderWait.orderWaitID}"></td>
                                        <td><input type="hidden" name="customerID" value="${bookingDetail.orderWait.customer.customerID}"></td>
                                    </tr>
                                    <tr>
                                        <td>Name:</td>
                                        <td class="up"><input name="customerName" type="text" value="${bookingDetail.orderWait.customer.customerName}"></td>
                                    </tr>
                                    <tr>
                                        <td>Phone:</td>
                                        <td class="up"><input name="phone" type="text" value="${bookingDetail.orderWait.customer.phone}"></td>
                                    </tr>
                                    <tr>
                                        <td>Address</td>
                                        <td class="up"><input name="address" type="text" value="${bookingDetail.orderWait.customer.address}"></td>
                                    </tr>
                                    <tr>
                                        <td>Email:</td>
                                        <td class="up"><input name="email" type="text" value="${bookingDetail.orderWait.customer.email}"></td>
                                    </tr>
                                    <tr>
                                        <td>Check-in:</td>
                                        <td class="up"><input name="checkIn" type="date" value="${bookingDetail.orderWait.checkIn}"></td>
                                    </tr>
                                    <tr>
                                        <td>Check-out:</td>
                                        <td class="up"><input name="checkOut" type="date" value="${bookingDetail.orderWait.checkOut}"></td>
                                    </tr>
                                    <tr>
                                        <td>Number of rooms:</td>
                                        <td class="up">
                                            <select name="noOfRoom" id="noOfRoom">
                                                <option ${bookingDetail.orderWait.noOfRoom == 1 ? "selected=\"selected\"" : ""} value="1">1</option>
                                                <option ${bookingDetail.orderWait.noOfRoom == 2 ? "selected=\"selected\"" : ""} value="2">2</option>
                                                <option ${bookingDetail.orderWait.noOfRoom == 3 ? "selected=\"selected\"" : ""} value="3">3</option>
                                                <option ${bookingDetail.orderWait.noOfRoom == 4 ? "selected=\"selected\"" : ""} value="4">4</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Room type:</td>
                                        <td class="up">
                                            <select name="deptName" id="getDeptName">
                                                <c:forEach items="${roomModel}" var="r">
                                                    <option 
                                                        ${r.deptName eq bookingDetail.orderWait.department.deptName? "selected=\"selected\"" : ""}
                                                        value="${r.deptName}">${r.deptName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <button style="background-color: #1f1cd8; width: 4em; margin: 2% 0;"
                                                    type="button" onclick="searchDeptbyName(${bookingDetail.orderWait.orderWaitID}, ${pageIndex})">Search</button>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>Choose room: </td>
                                        <td>
                                            <c:forEach items="${roomByName}" var="r">
                                                <input name="deptID" type="checkbox" 
                                                       <c:forEach items="${bookingDetail.departments}" var="bd">
                                                           ${(bd.deptID eq r.deptID) ? "checked=\"checked\"" : ""}
                                                       </c:forEach>
                                                       value="${r.deptID}"> ${r.deptID}
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </table>
                                <c:if test="${notic}">
                                    <p style="color: red; font-weight: bold">Change information done!</p>
                                </c:if>
                                <div class="control">
                                    <button class="save" name="button" value="update" onclick="checkRoom()" type="submit">Update</button>
                                    <button class="del" name="button" value="delete" type="submit">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
