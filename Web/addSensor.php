<?php
    session_start();

if(!isset($_SESSION['logged_in']))
{
    unset($_SESSION['error']);
    header('Location: index.php');
    exit();
}
?>


<!DOCTYPE html>
<html lang="pl">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico"><!-- ! -->

    <title>Pi house settings</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/layout.css" rel="stylesheet">



    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="container">

      <!-- Static navbar -->
     <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand menu_mobilne" href="admin_panel.php">Pi house</a>
			   <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-2">
                <span class="sr-only">Rozwiń nawigację</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
          </div>
          <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
            <ul class="nav navbar-nav">
              <li role="presentation"><a href="admin_panel.php">Home</a></li>
              <li role="presentation"><a href="#">About</a></li>
              <li role="presentation"><a href="#">Contact</a></li>
              <li role="presentation" class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Action<span class="caret"></span></a>
                <ul class="dropdown-menu">
                  <li role="presentation" ><a href="addUser.php">Add user</a></li>
                  <li role="presentation"><a href="privileges.php">Change user privileges</a></li>
                  <li role="presentation" class="active"><a href="addSensor.php">Add sensor</a></li>
                </ul>
              </li>
            </ul>
			<div class = "nav navbar-nav" id = "logout">
				<?php   
					echo "<a href=logout.php class='btn btn-info btn-lg'>";
					echo "<span class='glyphicon glyphicon-log-out'></span> Log out";
					echo"</a>";
				?>
			</div>
              
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

      <!-- Main component for a primary marketing message or call to action -->


       <div class="page-header">
        <h1>Add new sensor</h1>
      </div>
      
       <div class="main">
           

          <form action="/action_page.jsp">
            Sensor name:<br>
            <input type="text" name="firstname" value="">
            
            <br><br>
            
            Location:<br>
            <select>
              <option value="diningRoom">Dining Room</option>
              <option value="livingRoom">Living Room</option>
              <option value="kitchen">Kitchen</option>
              <option value="basement">Basement</option>
              <option value="garage">Garage</option>
              <option value="entryDoors">Entry Doors</option>
            </select>
            
            <br><br>
 
             <p>Sensor type:</p>
            <input type="radio" name="option" value="temperature" checked> Temperature <br>
            <input type="radio" name="option" value="smoke"> Smoke <br>
            <input type="radio" name="option" value="air pollution"> Air pollution <br>
            <input type="radio" name="option" value="anti-burglary"> Anti-burglary <br>
            <br>
            <input type="submit" value="Add">
          </form> 
         
       </div>



    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/vendor/jquery.min.js"><\/script>')</script>
    <script src="js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
