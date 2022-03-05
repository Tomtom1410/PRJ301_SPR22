<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="header">
    <a href="#" class="logo">
        <!-- Add the class icon to your logo image or logo icon to add the margining -->
        Your House
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </a>
    </nav>
</header>
<!-- Left side column. contains the logo and sidebar --> 
<aside class="left-side sidebar-offcanvas">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <ul class="sidebar-menu">
            <li <c:if test="${tagmenu eq 'room'}">class="active"</c:if> >
                <a href="${pageContext.request.contextPath}/management/room">
                    <i class="fa fa-bed"></i> <span>Rooms</span>
                </a>
            </li>
            <li <c:if test="${tagmenu eq 'orders'}">class="active"</c:if> >
                <a href="${pageContext.request.contextPath}/management/orders">
                    <i class="fa fa-notes-medical"></i><span>Order Details</span>
                </a>
            </li>

            <li <c:if test="${tagmenu eq 'booking'}">class="active"</c:if> >
                <a href="${pageContext.request.contextPath}/management/bookinghistory">
                    <i class="fa fa-history"></i> <span>Booking History</span>
                </a>
            </li>

            <li <c:if test="${tagmenu eq 'feedback'}">class="active"</c:if> >
                
                <a href="${pageContext.request.contextPath}/management/feedback">
                    <i class="fa fa-comments"></i> <span>Feedback</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/logout">
                    <i class="glyphicon glyphicon-log-out"></i> <span>Logout</span>
                </a>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>