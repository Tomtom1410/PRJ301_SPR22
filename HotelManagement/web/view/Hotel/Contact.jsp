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
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/ContactStyle.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/FooterStyle.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/CSS/HotelStyle/HeaderStyle.css" rel="stylesheet" type="text/css"/>
        <!--java Script-->
        <script src="${pageContext.request.contextPath}/JavaScript/ContactCode.js"></script>

    </head>

    <body>

        <div class="container">
            <!--Nội dung Menu-->
            <jsp:include page="Header.jsp"></jsp:include>
                <div class="Banner">
                    <p>Contact US</p>
                </div>
                <!-- Contact -->
                <div class="row Contact">
                    <div class="col-md-8">
                        <form action="Contact" method="POST" id="customerFeedBack">
                            <h2>Comment Or Complaints To Us</h2>
                            <table>
                                <tr>
                                    <td>Note: * is required</td>
                                </tr>
                                <tr>
                                    <td><input name ="name" id="name" type="text" placeholder="Fullname"
                                               pattern="^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$" 
                                               title="Fullname cannot contain special characters!" required> *</td>
                                </tr>
                                <tr>
                                    <td><input name="email" id ="email" type="email" placeholder="Email"
                                               pattern="^[a-z0-9A-Z]+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}+$" 
                                               title="Email in the form 'user@domain.com'" required> *</td>
                                </tr>
                                <tr>
                                    <td><input name="phone" 
                                               pattern="^[0-9]{9,20}$" title="Phone number must be number"
                                               id="phone" type="text" placeholder="Number phone" required> *</td>
                                </tr>
                                <tr>
                                    <td><input name="address" id="address" type="text" placeholder="Address" required> *</td>
                                </tr>
                                <tr>
                                    <td><textarea name="feedback" id="feedback" cols="55" rows="8" placeholder="Comment" required></textarea> *</td>
                                </tr>
                                <tr>
                                    <td>
                                        <input type="submit" onclick="submitForm()" value="Send">
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>

                    <div class="col-md-4">
                        <h1>Contact Information </h1>
                        <h3>DEL LUNA HOTEL</h3>
                        <p><span class="glyphicon glyphicon-earphone"></span> Phone : 0981235670</p>
                        <p> <span class="glyphicon glyphicon-equalizer"></span> Address : 50-82 Bui Vien Street, <br> Pham Ngu Lao Ward, District 1, Ho Chi Minh City Copyright. <br> All rights Reserved.
                        </p>
                        <p><span class="glyphicon glyphicon-envelope"></span> Email: Delluna@gamil.com</p>
                    </div>
                </div>
            <jsp:include page="Footer.jsp"></jsp:include>
        </div>

    </body>

</html>