<!--Header-->
<header>
    <nav class="navbar navbar-inverse bg-primary" role="navigation">
        <div class="container-fluid">
            <div class="collapse navbar-collapse tag_nav" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">

                    <li class="${tag eq "home"?"active":""}">
                        <a href="home"> <span class="glyphicon glyphicon-home"></span>Home</a>
                    </li>

                    <li class="${tag eq "about"?"active":""}"><a href="about">About</a></li>
                    <li class="${tag eq "room"?"active":""}"><a href="room">Room Type</a></li>
                    <li class="${tag eq "contact"?"active":""}"><a href="contact"><span class="glyphicon glyphicon-envelope"></span>Contact</a> </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
            <div>
                <a href="booking"><button class="button-left">Booking</button></a>
            </div>

        </div>
        <!-- /.container-fluid -->
    </nav>
</header>
<!--Header and-->