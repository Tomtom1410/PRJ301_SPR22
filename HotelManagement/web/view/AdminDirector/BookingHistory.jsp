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
        <aside class="right-side">
            <!-- Main content -->
            <section class="content">

                <div class="row">
                    <div class="col-xs-12">
                        <div class="panel">
                            <header class="panel-heading">
                                Booking History
                            </header>
                            <div class="panel-body table-responsive">
                                <div class="box-tools m-b-15 row">
                                    <div class="col-md-8 row">
                                        <form id="filter" action="bookinghistory" method="GET" onchange="submitForm()">
                                            <div class="col-md-6">
                                                <span>Status:</span>
                                                <select name="status">
                                                    <option ${requestScope.status eq 'all' ? "selected=\"selected\"":""} value="all">All</option>
                                                <option ${requestScope.status eq '0' ? "selected=\"selected\"":""} value="0">Not cancel</option>
                                                <option ${requestScope.status eq '1' ? "selected=\"selected\"":""} value="1">Cancel</option>
                                            </select>
                                        </div>
                                    </form>
                                    <script>
                                        function submitForm() {
                                            document.getElementById('filter').submit();
                                        }
                                    </script>
                                </div>
                                <div class="col-md-4">
                                    <form action="bookinghistory" method="GET">
                                        <div class="input-group">
                                            <input type="text" name="key" value="${requestScope.key}" class="form-control input-sm pull-right" style="width: 150px;" placeholder="Search"/>
                                            <div class="input-group-btn">
                                                <button type="submit" class="btn btn-sm btn-default"><i class="fa fa-search"></i></button>
                                            </div>
                                        </div>
                                    </form>
                                </div>

                            </div>
                            <c:if test="${requestScope.bookings.isEmpty()}">
                                <h2>There are no booking details you want</h2>
                            </c:if>
                            <c:if test="${!requestScope.bookings.isEmpty()}">
                                <table 
                                    <c:if test="${requestScope.bookings.isEmpty()}">
                                        style="display: none;"
                                    </c:if>
                                    class="table table-hover">
                                    <tr>
                                        <th>Customer Name</th>
                                        <th>Number phone</th>
                                        <th>Check-in</th>
                                        <th>Check-out</th>
                                        <th>Cancel Order</th>
                                        <th>View</th>
                                    </tr>
                                    <c:forEach items="${requestScope.bookings}" var="b">
                                        <tr>
                                            <td>${b.orderWait.customer.customerName}</td>
                                            <td>${b.orderWait.customer.phone}</td>
                                            <td>${b.orderWait.checkIn}</td>
                                            <td>${b.orderWait.checkOut}</td>
                                            <td>${b.isCancel()?"Yes":"No"}</td>
                                            <td>
                                                <form action="bookinghistory" method="POST">
                                                    <input type="hidden" name="orderId" value="${b.orderWait.orderWaitID}">
                                                    <button type="submit" class="label btn-primary" style="border: none; color: white;">Details</button>
                                                </form>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </table>
                                <div style="float: right;">
                                    <nav aria-label="Page navigation example">
                                        <ul id="paggingBottom" class="pagination">
                                        </ul>
                                    </nav>
                                </div>
                                <script>
                                    generatePagger('paggingBottom',${requestScope.pageindex},${requestScope.totalPage}, '${requestScope.url}', 1);
                                    function generatePagger(div, pageIndex, totalpage, url, gap) {
                                        var container = document.getElementById(div);
                                        if (pageIndex - gap > 0)
                                            container.innerHTML += '<li class="page-item"><a class="page-link" href="' + url + 1 + '">First</a></li>';
                                        for (var i = (pageIndex) - gap; i < pageIndex; i++) {
                                            if (i > 0)
                                                container.innerHTML += '<li class="page-item"><a class="page-link" href="' + url + i + '">' + i + '</a></li>';
                                        }
                                        container.innerHTML += '<li class="page-item active"><span class="page-link">' + pageIndex + '</span></li>';
                                        for (var i = (pageIndex) + 1; i <= pageIndex + gap; i++) {
                                            if (i <= totalpage)
                                                container.innerHTML += '<li class="page-item"><a class="page-link" href="' + url + i + '">' + i + '</a></li>';
                                        }
                                        if (pageIndex + gap < totalpage)
                                            container.innerHTML += '<li class="page-item"><a class="page-link" href="' + url + totalpage + '">Last</a></li>';
                                    }
                                </script>
                            </c:if>
                        </div><!-- /.box-body -->
                    </div><!-- /.box -->
                </div>
            </div>
        </section><!-- /.content -->
    </aside><!-- /.right-side -->
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

