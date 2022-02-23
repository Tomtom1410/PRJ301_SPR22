<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Hotel Del Luna</title>
        <script src="mycode.js"></script>
        <!-- Bootstrap -->
        <link href="${pageContext.request.contextPath}/Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/AboutStyle.css" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Dancing+Script" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/FooterStyle.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/HeaderStyle.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>

        <div class="container">
            <!--Nội dung Menu-->
            <jsp:include page="Header.jsp"></jsp:include>

                <div class="Banner">
                    <p>About Hotel Del Luna</p>
                </div>
                <!-- content -->
                <div class="row content">
                    <h3>HISTORY ABOUT DEL LUNA</h3>
                    <span>Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                        sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                        Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                        sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                        Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
                        sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                    </span>
                </div>

                <div class="row pic">
                    <img src="https://2trip.vn/wp-content/uploads/2020/08/swiss-belresort-hotel.jpg" alt="">
                </div>

                <div class="Convinient">
                    <div class="header_1">
                        <h3>WELCOME TO DEL LUNA</h3>
                        <h1>Highlights About Del Luna</h1>
                    </div>
                    <div class="row ">
                        <div class="col-md-4">
                            <ul>
                                <li><span class="glyphicon glyphicon-check"></span> Fully equipped rooms</li>
                                <li><span class="glyphicon glyphicon-check"></span> Wide coverage wifi</li>
                                <li><span class="glyphicon glyphicon-check"></span> Focus on the spot</li>
                            </ul>
                        </div>
                        <div class="col-md-4">
                            <ul>
                                <li><span class="glyphicon glyphicon-check"></span> Private space</li>
                                <li><span class="glyphicon glyphicon-check"></span> Large parking lot</li>
                                <li><span class="glyphicon glyphicon-check"></span></span> Dinner Buffet</li>
                            </ul>
                        </div>
                        <div class="col-md-4">
                            <ul>
                                <li><span class="glyphicon glyphicon-check"></span>Sauna & massage</li>
                                <li><span class="glyphicon glyphicon-check"></span> Bar & garden cafe</li>
                                <li><span class="glyphicon glyphicon-check"></span> Swimming pool & Gym</li>
                            </ul>
                        </div>
                    </div>
                </div>
            <jsp:include page="Footer.jsp"></jsp:include>
        </div>
        <!--contianer end-->
    </body>
</html>
