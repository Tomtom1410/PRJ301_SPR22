<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Del Luna Management</title>
        <!-- Bootstrap -->
        <!--java Script-->
        <script src="${pageContext.request.contextPath}/JavaScript/ManagementCode.js"></script>
        <script src="${pageContext.request.contextPath}/JavaScript/BookingCode.js"></script>
        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/MenuStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/CSS/ManagementStyle/FeedbackStyle.css" rel="stylesheet" type="text/css" />
    </head>

    <body>

        <div class="row main">
            <jsp:include page="Menu.jsp"></jsp:include>

                <div class="col-md-10 right">
                    <div class="row title">
                        <div class="col-md-4">
                            <h3><span class="glyphicon glyphicon-align-justify"></span> Feedback</h3>
                        </div>
                        <div class="col-md-4 Search">
                            <span class=" glyphicon glyphicon-search"></span>
                            <input id="content"
                            <c:if test="${value != null}">
                                value =${value}
                            </c:if>
                            type="text" placeholder="Enter customer's name">
                        <button type="button" onclick="search('feedback')">Search</button>
                    </div>
                </div>
                <div class="row information">
                    <table>
                        <tr>
                            <td>Customer name</td>
                            <td>Date send</td>
                            <td>Feedback</td>
                        </tr>
                        <c:forEach items="${requestScope.feedbacks}" var="f">
                            <tr>
                                <td>${f.customer.customerName}</td>
                                <td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${f.date}" /></td>
                                <td>${f.feedbackContent}</td>
                            </tr>
                        </c:forEach>

                    </table>
                    <div id="paggingBottom" class="pageLine">
                    </div>
                    <script>
                        generatePagger('paggingBottom',${requestScope.pageIndex},${requestScope.totalPage}, '${requestScope.url}', 2);
                    </script>
                </div>
            </div>
        </div>
    </div>
</body>

</html>