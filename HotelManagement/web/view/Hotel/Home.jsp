
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Hotel Del Luna</title>
        <!--<script src="mycode.js"></script>-->
        <!-- Bootstrap -->
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <!--CSS-->
        <link href="https://fonts.googleapis.com/css?family=Dancing+Script" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/HomeStyle.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/FooterStyle.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/HeaderStyle.css" rel="stylesheet" type="text/css"/>

    </head>

    <body>

        <div class="container">
            <jsp:include page="Header.jsp"></jsp:include>
                <!--Slider Content-->
                <div class="row slide">
                    <div>
                        <!--slider start-->
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <!-- Indicators -->
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                            </ol>

                            <!-- Wrapper for slides -->
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img src="image/slider_1.jpg" alt="#">
                                    <p class="descript_slider">Modern, comfortable rooms</p>
                                </div>

                                <div class="item">
                                    <img src="image/slider_2.jpg" alt=""/>
                                    <p class="descript_slider">Luxury swimming pool</p>
                                </div>

                                <div class="item">
                                    <img style="width: 1519px; height: 600px;" src="image/slider_4.jpg" alt="#">
                                    <p class="descript_slider">Breakfast in the room </p>
                                </div>

                            </div>

                            <!-- Controls -->
                            <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                        </div>
                        <!--slider end-->
                    </div>
                </div>

                <!-- main-->
                <div class="row">
                    <form action="Home" method="POST">
                    <c:if test="${!flag}">
                        <p style="color: #f12626; margin: 0 8%;">Please choose date Check-in/Check-out valid!</p>
                    </c:if>
                    <table class="booking">
                        <tr>
                            <td>Check-in*:</td>
                            <td>Check-out*:</td>
                            <td>Choose Room:</td>
                            <td>Number of people:</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><input class="infor" type="date" name="checkIn"></td>
                            <td><input class="infor" type="date" name="checkOut"></td>
                            <td>
                                <select style="text-align: left;" class="infor" name="typeRoom">
                                    <option value="all">Choose room</option>
                                    <c:forEach items="${roomType}" var="rt">
                                        <option value="${rt}">${rt}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><input class="infor" type="text" name="noOfPeople" placeholder="No of people"></td>
                            <td><input class="infor bottom-left" type="submit" value="SEARCH"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="About">
                <h3>Del Luna Hotel</h3>
                <h1>WELCOME TO US</h1>
                <p class="descript">One of the top factors to make your business and travel trips light and comfortable is choosing a 
                    high-class hotel to stay during your business trip. Luxury hotels with many high-class services,
                    close to the center and the place of business help you relax and enjoy your rest time.</p>
            </div>

            <div class="row room">
                <div class="col-md-5 content">
                    <h1>BEAUTIFUL, COOL ROOMS</h1>
                    <p class="descript2">Come to Del Luna Hotel, you will experience translation
                        High-class spa services, modern fitness center and recreational activities on the sea.
                    </p>
                    <a style="color: #c9ec4d; font-weight: bold; font-size: 23px; text-decoration-line: revert;" href="Room">Details</a>
                </div>

                <div class="col-md-7 row">
                    <!--line 1-->
                    <div class="line1">
                        <img class="img-responsive img-thumbnail" src="image/PLATINUM DOUBLE ROOM.jpg" />
                    </div>
                    <!--line 2-->
                    <div class="row line2">
                        <div class="col-md-6">
                            <img class="img-responsive img-thumbnail" src="image/view.jpg" />
                        </div>
                        <div class="col-md-6">
                            <img  class="img-responsive img-thumbnail" src="image/view1.jpg" />
                        </div>
                    </div>
                </div>
            </div>

            <div class="service">
                <div>
                    <h3>Del Luna Hotel</h3>
                    <h1>HOT SERVICES</h1>
                </div>

                <div class="row">
                    <div class="col-md-3 box">
                        <!-- First dish-->
                        <div><img src="image/Banh cu cai.jpg" class="img-responsive" /></div>

                        <div>
                            <h3>Delicious Meal</h3>
                            <p>One of the top factors for you to become light and comfortable</p>
                        </div>
                        <!-- <div class="clearfix"> </div>-->
                        <!--Prevent the next box from running up-->
                    </div>

                    <div class="col-md-3 box">
                        <!-- Second dish-->
                        <div> <img src="image/ho boi.jpg" class="img-responsive" /></div>

                        <div>
                            <h3> Luxury Swimming Pool </h3>
                            <p>One of the top factors for you to become light and comfortable</p>
                        </div>
                    </div>



                    <div class="col-md-3 box">
                        <!-- Third dish-->
                        <div> <img src="image/LAXURY ROOM.jpg" class="img-responsive" /></div>

                        <div>
                            <h3>Modern Room</h3>
                            <p>One of the top factors for you to be light and comfortable</p>
                        </div>
                        <!--<div class="clearfix"> </div>-->
                        <!--Prevent the next box from running up-->
                    </div>

                    <div class="col-md-3 box">
                        <!-- Fourth dish-->
                        <div> <img src="image/khu_vui_choi_cho_be.jpg" class="img-responsive" /></div>
                        <div>
                            <h3> Children's Playground </h3>
                            <p>One of the top factors for you to be light and comfortable</p>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="Footer.jsp"></jsp:include>
            </div>
            <!--contianer end-->
            <!--Librabry javascript of Bootstrap-->
            <script src="${pageContext.request.contextPath}/Bootstrap/js/Jquery.js"></script>
        <script src="${pageContext.request.contextPath}/Bootstrap/js/bootstrap.min.js"></script>
    </body>

</html>
