<%-- 
    Document   : Room
    Created on : Mar 2, 2022, 11:37:48 AM
    Author     : conmu
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="Linkcss.jsp"></jsp:include>
    <!-- header logo: style can be found in header.less -->

<jsp:include page="LeftMenu.jsp"></jsp:include>
    <div class="wrapper row-offcanvas row-offcanvas-left">
        <!-- Right side column. Contains the navbar and content of the page -->
        <!-- Main content -->

        <aside class="right-side">
            <section class="panel">
                <header class="panel-heading">
                    Order Details
                </header>
                <div style="display: flex;">
                    <div class="panel-body col-lg-8">
                    <c:if test="${requestScope.rented eq '0'}">
                        <form class="form-horizontal tasi-form add-form" id="insertBooking" action="orderdetails" method="POST">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Name:</label>
                                <div class="col-sm-10">
                                    <input type="hidden" name="orderId" value="${requestScope.order.orderWaitID}">
                                    <input type="hidden" id="noOfRoom" name="noOfRoom" value="${requestScope.order.noOfRoom}">
                                    <input type="hidden" name="roomName" value="${requestScope.order.department.deptName}">
                                    <input type="hidden" name="customerId" value="${requestScope.order.customer.customerID}">
                                    <input type="hidden" name="type" value="wait">
                                    <span class="form-control" style="font-weight: bolder;">${requestScope.order.customer.customerName}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Email:</label>
                                <div class="col-sm-10">
                                    <span class="form-control" style="font-weight: bolder;">${requestScope.order.customer.email}</span>
                                </div>

                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Check-in:</label>
                                <div class="col-sm-10">
                                    <span class="form-control" style="font-weight: bolder;">${requestScope.order.checkIn}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Check-out:</label>
                                <div class="col-sm-10">
                                    <span class="form-control" style="font-weight: bolder;">${requestScope.order.checkOut}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Room type:</label>
                                <div class="col-sm-10">
                                    <span class="form-control" style="font-weight: bolder;">${requestScope.order.department.deptName}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Phone Number:</label>
                                <div class="col-sm-10">
                                    <span class="form-control" style="font-weight: bolder;">${requestScope.order.customer.phone}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Number of rooms:</label>
                                <div class="col-sm-10">
                                    <span class="form-control" style="font-weight: bolder;">${requestScope.order.noOfRoom}</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Choose rooms:</label>
                                <div class="col-sm-10" style="margin-top: 1%;">
                                    <div style="display: flex;">
                                        <c:forEach items="${requestScope.rooms}" var="r">
                                            <div style="margin-right: 3%;">
                                                <input name="deptId" type="checkbox" value="${r.deptID}"> ${r.deptID}
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <div id="notic" style="color: red;"></div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="col-sm-4">
                                    <button type="button" class="btn btn-success center-block col-sm-10" onclick="location.href = 'orders'">Back</button>
                                </div>
                                <div class="col-sm-2">
                                    <button type="submit" name="button" value="cancel" class="btn btn-danger center-block col-sm-10">Cancel</button>
                                </div>
                                <div class="col-sm-2">
                                    <button type="submit" onclick="checkRoom()" name="button" value="save" class="btn btn-info center-block col-sm-10">Save</button>
                                </div>
                            </div>
                        </form>
                        <script>
                            function checkRoom() {
                                var noOfRoom = document.getElementById('noOfRoom').value;
                                var rooms = document.getElementsByName('deptId');
                                let count = 0;
                                for (var i = 0; i < rooms.length; i++) {
                                    if (rooms[i].checked === true) {
                                        count++;
                                    }
                                }
                                if (Number.parseInt(count) !== Number.parseInt(noOfRoom)) {
                                    document.getElementById('notic').innerHTML = '<span>Please check the room. The number of rooms is different from the number of rooms the customer wants!</span>';
                                    document.getElementById('insertBooking').onsubmit = function () {
                                        return false;
                                    };
                                } else if (Number.parseInt(count) === Number.parseInt(noOfRoom)) {
                                    document.getElementById('insertBooking').onsubmit = function () {
                                        return true;
                                    };
                                }
                            }
                        </script>
                    </c:if>
                    <c:if test="${requestScope.rented eq '1'}">
                        <form class="form-horizontal tasi-form add-form" id="updateBooking" action="orderdetails" method="POST">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Full Name *:</label>
                                <div class="col-sm-10">
                                    <input type="hidden" name="orderId" value="${booking.orderWait.orderWaitID}">
                                    <input type="hidden" name="type" value="update">
                                    <input type="hidden" name="customerID" value="${booking.orderWait.customer.customerID}"> 
                                    <input type="text" name="customerName" class="form-control" 
                                           pattern="^[a-zA-ZaAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴ\s]+$"
                                           title="Fullname cannot contain special characters!" 
                                           value="${booking.orderWait.customer.customerName}"
                                           required placeholder="Full Name">
                                </div>

                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Email *:</label>
                                <div class="col-sm-10">
                                    <input type="email" name="email" class="form-control" 
                                           value="${booking.orderWait.customer.email}"
                                           required placeholder="Email">
                                </div>

                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Phone *:</label>
                                <div class="col-sm-10">
                                    <input type="text" name="phone" class="form-control"
                                           pattern="^[0-9]{9,20}$" title="Phone number must be number and have length from 9 to 20 characters!" 
                                           value="${booking.orderWait.customer.phone}"
                                           required placeholder="Phone Number">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Address: </label>
                                <div class="col-sm-10">
                                    <input type="text" name="address" class="form-control"
                                           value="${booking.orderWait.customer.address}"
                                           placeholder="Address">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Check-in*:</label>
                                <div class="col-sm-10">
                                    <input class="form-control" id="checkin" name="checkin" required type="date" style="font-weight: bolder;" value="${booking.orderWait.checkIn}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Check-out*:</label>
                                <div class="col-sm-10">
                                    <input class="form-control" id="checkout" name="checkout" required type="date" style="font-weight: bolder;" value="${booking.orderWait.checkOut}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Number of rooms*:</label>
                                <div class="col-sm-10">
                                    <select id="noOfRoom" class="form-control" name="noOfRoom" id="noOfRoom">
                                        <option ${booking.orderWait.noOfRoom == 1 ? "selected=\"selected\"" : ""} value="1">1</option>
                                        <option ${booking.orderWait.noOfRoom == 2 ? "selected=\"selected\"" : ""} value="2">2</option>
                                        <option ${booking.orderWait.noOfRoom == 3 ? "selected=\"selected\"" : ""} value="3">3</option>
                                        <option ${booking.orderWait.noOfRoom == 4 ? "selected=\"selected\"" : ""} value="4">4</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Room type*:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" name="deptName" id="getDeptName"
                                            onchange="searchRoomByName(${booking.orderWait.orderWaitID}, ${requestScope.rented})">
                                        <c:forEach items="${roomType}" var="r">
                                            <option 
                                                ${deptName eq r.deptName? "selected=\"selected\"" : ""}
                                                value="${r.deptName}">${r.deptName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label">Choose rooms*:</label>
                                <div class="col-sm-10" style="margin-top: 1%; ">
                                    <div style="display: flex;">
                                        <c:forEach items="${requestScope.rooms}" var="r">
                                            <div style="margin-right: 3%;">
                                                <input type="checkbox" name="deptId"
                                                       <c:forEach items="${booking.departments}" var="bd">
                                                           ${(bd.deptID eq r.deptID) ? "checked=\"checked\"" : ""}
                                                       </c:forEach>
                                                       value="${r.deptID}"> ${r.deptID}
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <div style="color: orangered;">
                                        Note: (*) is required.
                                    </div>
                                    <div id="notic" style="color: red; font-weight: bolder;">
                                    </div>
                                    <c:if test="${checkDate != null && checkDate eq false}">
                                        <div style="color: red; font-weight: bolder;">
                                            Please review the check-in and checkout dates. Check in is not after check out!
                                        </div>
                                    </c:if>
                                    <c:if test="${tag eq 'done'}">
                                        <div id="note" style="color: green; margin: 1% 38% 2% 38%;">
                                            Update successful.
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <div class="col-sm-12">
                                <div class="col-sm-4">
                                    <button type="button" class="btn btn-success center-block col-sm-10" onclick="location.href = 'orders?rented=1'">Back</button>
                                </div>
                                <div class="col-sm-2">
                                    <button type="submit" name="button" value="cancel" class="btn btn-danger center-block col-sm-10">Cancel</button>
                                </div>
                                <div class="col-sm-2">
                                    <button type="submit" name="button" value="save" onclick="checkInfor()" class="btn btn-info center-block col-sm-10">Save</button>
                                </div>
                            </div>
                            <script>
                                function searchRoomByName(orderId, rented) {
                                    var deptName = document.getElementById('getDeptName').value;
                                    window.location.href = "orderdetails?orderId=" + orderId + "&rented="
                                            + rented + "&deptName=" + deptName;
                                }

                                function checkInfor() {
                                    var noOfRoom = document.getElementById('noOfRoom').value;
                                    var rooms = document.getElementsByName('deptId');
                                    let count = 0;
                                    for (var i = 0; i < rooms.length; i++) {
                                        if (rooms[i].checked === true) {
                                            count++;
                                        }
                                    }
                                    if (document.getElementById('note') !== null) {
                                        document.getElementById('note').style.display = 'none';
                                    }
                                    console.log(count);
                                    console.log(noOfRoom);
                                    if (Number.parseInt(count) !== Number.parseInt(noOfRoom)) {
                                        document.getElementById('notic').innerHTML = '<span>Please check the room. The number of rooms is different from the number of rooms the customer wants!</span>';
                                        document.getElementById('updateBooking').onsubmit = function () {
                                            return false;
                                        };
                                    } else if (Number.parseInt(count) === Number.parseInt(noOfRoom)) {
                                        document.getElementById('updateBooking').onsubmit = function () {
                                            return true;
                                        };
                                    }
                                }
                            </script>  
                        </form>
                    </c:if>

                </div>
            </div>
        </section>
    </aside>
</div><!-- ./wrapper -->


<!-- jQuery 2.0.2 -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/view/AdminDirector/BootstrapForManage/js/jquery.min.js" type="text/javascript"></script>

<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/view/AdminDirector/BootstrapForManage/js/bootstrap.min.js" type="text/javascript"></script>
<!-- Director App -->
<script src="${pageContext.request.contextPath}/view/AdminDirector/BootstrapForManage/js/Director/app.js" type="text/javascript"></script>
</body>
</html>

