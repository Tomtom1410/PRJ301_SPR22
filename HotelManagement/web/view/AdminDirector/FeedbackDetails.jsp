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
                    Feedback Details
                </header>
                <div class="form-horizontal tasi-form add-form" style="display: flex;">
                    <div class="panel-body col-lg-8">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">Customer Name:</label>
                            <div class="col-sm-10">
                                <span class="form-control" style="font-weight: bolder;">${feedback.customer.customerName}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Customer Email:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${feedback.customer.email}</span>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Phone Number:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${feedback.customer.phone}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Address:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${feedback.customer.address}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">Feedbacks:</label>
                        <div class="col-sm-10">
                            <span class="form-control" style="font-weight: bolder;">${feedback.feedbackContent}</span>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="col-sm-4">
                        </div>
                        <div class="col-sm-6">
                            <button type="button" onclick="location.href = 'feedback'" class="btn btn-danger center-block col-sm-10">Discard</button>
                        </div>
                    </div>
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

