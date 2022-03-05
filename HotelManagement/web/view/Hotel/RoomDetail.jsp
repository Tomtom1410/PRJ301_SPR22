<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Hotel Your House</title>
        <!-- Bootstrap && CSS-->
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/RoomDetailStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/FooterStyle.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/HeaderStyle.css" rel="stylesheet" type="text/css"/>
        <!--java Script-->
    <body>

        <div class="container">
            <jsp:include page="Header.jsp"></jsp:include>
                <div class="Banner">
                    <p>Room Detail</p>
                </div>

                <!-- Slider Content-->
                <div class="row">
                    <div class="col-md-7 col-sm-12 slide">
                        <!-- slider Start-->
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <!-- Wrapper for slides -->
                            <div class="carousel-inner">
                                <div class="item active img-responsive">
                                    <img src="${url_image}" alt="#">
                            </div>
                            <c:forEach items="${room.url}" var="u">
                                <div class="item img-responsive">
                                    <img src="${u}" alt="#">
                                </div>
                            </c:forEach>
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right"></span>
                        </a>
                    </div>
                    <div class="content">
                        <h1>${room.deptName}</h1>
                        <p class="Price">Price: ${room.price}$ / Day</p>
                        <p>Your House Hotel is a motel established in 20xx with 60 rooms of different sizes to suit many needs of customers. System of modern equipment, air-conditioner,
                            television, wooden tables and chairs, wifi wave and shuttle services to tourist attractions. Designed and decorated in a luxurious and noble style. 
                            The equipment and rooms of the motel - hotel are very comfortable and modern to bring satisfaction and comfort to customers during their
                            stay at the hotel room.
                        </p>
                    </div>
                </div>
                <!--slider end-->

                <div class="col-md-3 col-sm-12 booking">
                    <form action="booking" method="POST" id="booking">
                        <table>
                            <tr>
                                <td>Booking now</td>
                            </tr>
                            <tr>
                                <td><input type="hidden" name="roomName" value="${room.deptName}"></td>
                            </tr>
                            <tr>
                                <td><input id="name" class="in" type="text" name="customerName" placeholder="Name"
                                           pattern="^[a-zA-ZaAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴ\s]+$"
                                           title="Fullname cannot contain special characters!" 
                                           value="${requestScope.order.customer.customerName}" required></td>
                            </tr>
                            <tr>
                                <td><input id="phone" class="in" type="text" name="phone" placeholder="Phone Number"
                                           pattern="^[0-9]{9,20}$" title="Phone number must be number."
                                           value="${requestScope.order.customer.phone}" required></td>
                            </tr>
                            <tr>
                                <td><input id="email" class="in" type="email" name="email" placeholder="E-mail:"
                                           pattern="^[a-z0-9A-Z]+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}+$" 
                                           title="Email in the form 'user@domain.com'"
                                           value="${requestScope.order.customer.email}"
                                           required></td>
                            </tr>
                            <tr>
                                <td>
                                    <select id="noOfRoom" class="in" name="noOfRoom">
                                        <option value="0">Number of rooms</option>
                                        <option ${order.noOfRoom == 1 ? "selected=\"selected\"" : ""} value="1">1</option>
                                        <option ${order.noOfRoom == 2 ? "selected=\"selected\"" : ""} value="2">2</option>
                                        <option ${order.noOfRoom == 3 ? "selected=\"selected\"" : ""} value="3">3</option>
                                        <option ${order.noOfRoom == 4 ? "selected=\"selected\"" : ""} value="4">4</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Check-in:</td>
                            </tr>
                            <tr>
                                <td><input id="checkin" class="in" type="date" value="${requestScope.order.checkIn}" name="checkIn" title="check-in must before check-out" required></td>
                            </tr>

                            <tr>
                                <td>Check-out:</td>
                            </tr>
                            <tr>
                                <td><input id="checkout" class="in" type="date" value="${requestScope.order.checkOut}" name="checkOut" title="check-out must after check-in" required></td>
                            </tr>
                            <tr>
                                <td>
                                    <c:if test="${requestScope.note != null &&!requestScope.note}">
                                        <div id="note" style="color: red; font-weight: bolder;">
                                            Please review date check-in, check-out. Check-in is not after check-out!
                                        </div>
                                    </c:if>
                                    <div id="notic" style="color: red; font-weight: bolder;"></div>
                                    <button type="submit" onclick="booking()">Booking</button>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <script>
                        //RoomDetail
                        function booking() {
                            var noOfRoom = document.getElementById("noOfRoom");
                            if (document.getElementById('note') !== null) {
                                document.getElementById('note').style.display = 'none'; 
                            }
                            if (noOfRoom.options[noOfRoom.selectedIndex].value === "0") {
                                document.getElementById('notic').innerHTML = '<p>Please choose number of rooms!</p>';
                                document.getElementById('booking').onsubmit = function () {
                                    return false;
                                }
                            } else {
                                alert("Thank you for trusting and booking at our hotel!");
                                document.getElementById('booking').onsubmit = function () {
                                    return true;
                                }
                            }
                        }
                    </script>
                </div>
            </div>
            <jsp:include page="Footer.jsp"></jsp:include>
            </div>
            <!-- contianer end-->

        </body>
        <!--javascript of Bootstrap-->
        <script src="${pageContext.request.contextPath}/Bootstrap/js/Jquery.js"></script>
    <script src="${pageContext.request.contextPath}/Bootstrap/js/bootstrap.min.js"></script>



</html>