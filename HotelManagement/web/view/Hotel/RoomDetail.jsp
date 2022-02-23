<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Hotel Del Luna</title>
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
                        <p>Hotel Del Luna is a motel established in 20xx with 60 rooms of different sizes to suit many needs of customers. System of modern equipment, air-conditioner,
                            television, wooden tables and chairs, wifi wave and shuttle services to tourist attractions. Designed and decorated in a luxurious and noble style. 
                            The equipment and rooms of the motel - hotel are very comfortable and modern to bring satisfaction and comfort to customers during their
                            stay at the hotel room.
                        </p>
                    </div>
                </div>
                <!--slider end-->

                <div class="col-md-3 col-sm-12 booking">
                    <form action="Booking" method="POST" id="booking">
                        <c:if test="${!flag}">
                            <p style="color: #f12626; font-weight: bold;">Please enter valid information and do not leave it blank!</p>
                        </c:if>
                        <c:if test="${!error}">
                            <p style="color: #f12626; font-weight: bold;">Name/Email/Phone/Check-in/Check-out not valid. Please review!</p>
                        </c:if>    
                        <table>
                            <tr>
                                <td>Booking now</td>
                            </tr>
                            <tr>
                                <td><input type="hidden" name="roomName" value="${room.deptName}"></td>
                            </tr>
                            <tr>
                                <td><input id="name" class="in" type="text" name="customerName" placeholder="Name"
                                           pattern="^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z ]*)*$" 
                                           title="Fullname cannot contain special characters!" required></td>
                            </tr>
                            <tr>
                                <td><input id="phone" class="in" type="text" name="phone" placeholder="Phone Number"
                                           pattern="^[0-9]{9,20}$" title="Phone number must be number." required></td>
                            </tr>
                            <tr>
                                <td><input id="email" class="in" type="email" name="email" placeholder="E-mail:"
                                           pattern="^[a-z0-9A-Z]+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}+$" 
                                           title="Email in the form 'user@domain.com'" required></td>
                            </tr>
                            <tr>
                                <td>
                                    <select id="noOfRoom" class="in" name="noOfRoom">
                                        <option value="0">Number of rooms</option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Check-in:</td>
                            </tr>
                            <tr>
                                <td><input id="checkin" class="in" type="date" name="checkIn" title="check-in must before check-out" required></td>
                            </tr>

                            <tr>
                                <td>Check-out:</td>
                            </tr>
                            <tr>
                                <td><input id="checkout" class="in" type="date" name="checkOut" title="check-out must after check-in" required></td>
                            </tr>
                            <tr>
                                <td><button type="submit" onclick="booking()">Booking</button></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <jsp:include page="Footer.jsp"></jsp:include>
            </div>
            <!-- contianer end-->

        </body>
        <!--javascript of Bootstrap-->
    <script src="${pageContext.request.contextPath}/Bootstrap/js/Jquery.js"></script>
    <script src="${pageContext.request.contextPath}/Bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/JavaScript/BookingCode.js"></script>



</html>