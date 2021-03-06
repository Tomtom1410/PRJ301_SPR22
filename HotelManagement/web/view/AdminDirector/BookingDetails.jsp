<%-- 
    Document   : Room
    Created on : Mar 2, 2022, 11:37:48 AM
    Author     : conmu
--%>

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
                    Booking Details
                </header>
                <div class="form-horizontal tasi-form add-form" style="display: flex;">
                    <div class="panel-body col-lg-8">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Name:</label>
                            <div class="col-sm-10">
                                <span class="form-control" style="font-weight: bolder;">${booking.orderWait.customer.customerName}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Email:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${booking.orderWait.customer.email}</span>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Check-in:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${booking.orderWait.checkIn}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Check-out:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${booking.orderWait.checkOut}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Room type:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${booking.orderWait.department.deptName}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Phone Number:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${booking.orderWait.customer.phone}</span>
                        </div>
                    </div>

                    <c:if test="${!booking.isCancel()}">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Number of rooms:</label>
                            <div class="col-sm-10">
                                <span class="form-control" style="font-weight: bolder;">${booking.orderWait.noOfRoom}</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">List rooms:</label>
                            <div class="col-sm-10">
                                <span class="form-control">
                                    <c:forEach items="${booking.departments}" var="bd">
                                        ${bd.deptID},     
                                    </c:forEach>
                                </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Amount to be paid:</label>
                            <div class="col-sm-10">
                                <span class="form-control" style="font-weight: bolder;">${invoice.totalPrice}$</span>
                            </div>
                        </div>
                        <c:if test="${!booking.payment}">
                            <div class="col-sm-12">
                                <div class="col-sm-4">
                                    <button type="button" onclick="location.href = 'bookinghistory'" class="btn btn-success center-block col-sm-10">Back</button>
                                </div>
                                <div class="col-sm-2">
                                    <form action="bookingdetails" method="GET">
                                        <input type="hidden" name="orderId" value="${booking.orderWait.orderWaitID}">
                                        <button type="submit" class="btn btn-danger center-block col-sm-10">Cancel</button>
                                    </form>
                                </div>
                                <div class="col-sm-2">
                                    <form action="bookingdetails" method="POST">
                                        <input type="hidden" name="orderId" value="${booking.orderWait.orderWaitID}">
                                        <button type="submit" class="btn btn-primary center-block col-sm-10">Payment</button>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${booking.payment}">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label"></label>
                                <div class="col-sm-10">
                                    <span style="color: red; font-weight: bolder;">Bill has been paid</span>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="col-sm-2"></div>
                                <div class="col-sm-10">
                                    <button type="button" onclick="location.href = 'bookinghistory'" class="btn btn-success center-block col-sm-10">Back</button>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${booking.isCancel()}">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label"></label>
                            <div class="col-sm-10">
                                <span style="color: red; font-weight: bolder;">Customer has canceled the room</span>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-10">
                                <button type="button" onclick="location.href = 'bookinghistory'" class="btn btn-success center-block col-sm-10">Back</button>
                            </div>
                        </div>
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

