<!--Header-->
<header>
    <nav class="navbar navbar-inverse bg-primary" role="navigation">
        <div class="container-fluid">
            <div class="collapse navbar-collapse tag_nav" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">

                    <li class="${tag eq "home"?"active":""}">
                        <a href="Home"> <span class="glyphicon glyphicon-home"></span>Home</a>
                    </li>

                    <li class="${tag eq "about"?"active":""}"><a href="About">About</a></li>
                    <li class="${tag eq "room"?"active":""}"><a href="Room">Room Type</a></li>
                    <li class="${tag eq "contact"?"active":""}"><a href="Contact"><span class="glyphicon glyphicon-envelope"></span>Contact</a> </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
            <div>
                <a href="Booking"><button class="button-left">Booking</button></a>
            </div>

        </div>
        <!-- /.container-fluid -->
    </nav>
</header>
<!--Header and-->