<?php
	$develop = false;
	
	require('./User.php');
	require('./Room.php');

	
	if((!isset($_POST['user']) ) || (!isset($_POST['room'])))
    {
        header('Location: index.php');
        exit();
		
    }
	
		$users  = User::createUsersFromXml('/home/pi/Java/MasterRoomControllerFx/dist/users.xml');
		$rooms = Room::createRoomsFromXml('/home/pi/Java/MasterRoomControllerFx/dist/rooms.xml'); 
		$functions = null;
		$privilegesValues = null;
		
	    $user_id = (int)$_POST['user'];
        $room_id = (int)$_POST['room'];
		
	if($develop == true)	echo "odebrano : ". $user_id . "  " . $room_id."<br>";
			
			
			
			
		$user = User::getUserById($users,$user_id); 
		$room = Room::getRoomById($rooms,$room_id);
		$privilege = User::getPrivilege($user->getPrivileges(),$room_id);
		
		if($user == null)
			echo " Blad wczytywania usera";
		
		if($room == null)
			echo "Blad wczytywania pokoju ";
		
		if($develop == true) echo $user->getName()."   w pokoju: ".$room->getName(). " ma uprawnienia: ".$privilege;
		
		 
		 for($i=0;$i<count($room->getFunctions());$i++)
		 {
			$functions[$i] = Room::getFunctionById($room,$i);
			if($develop == true) echo "<br>".Room::getFunctionById($room,$i);
			$privilegesValues[$i] = User::getPrivilegeValueByFunctionId($privilege,$i);
			if($develop == true) echo " : ".User::getPrivilegeValueByFunctionId($privilege,$i);
		 }
	     
		 
		 
		 User::createXMLFile($users);
		 
   
			
		/*$usersXML = simplexml_load_file('XML/users.xml');
		
				if( $usersXML == null)
					echo "nie udało się wczytać pliku".'<br />';
				else
					echo "wczytano plik pomyślnie".'<br />';

		
		
		echo $usersXML->user[$user_id]->name;
		echo $usersXML->user[$user_id]->privileges->privilege[0];*/
				
		
					

		
?>




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
	
	<script type="text/javascript" src="js/skrypt.js"></script>
    <link href="css/mobile.css" rel="stylesheet">

	<link rel = "stylesheet" href = "fontello/fontello_set/css/fontello.css">
	
	
	
	
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
    
        <!-- Custom styles for this template -->
    <link href="css/carousel.css" rel="stylesheet">
    
  </head>

  <body onLoad="main()">

  
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
              <li class="active" role="presentation"><a href="admin_panel.php">Home</a></li>
              <li role="presentation"><a href="#">About</a></li>
              <li role="presentation"><a href="#">Contact</a></li>
              <li role="presentation" class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Action<span class="caret"></span></a>
                <ul class="dropdown-menu" >
                  <li role="presentation" ><a href="addUser.php">Add user</a></li>
                  <li role="presentation"><a href="privileges.php">Change user privileges</a></li>
                  <li role="presentation"><a href="addSensor.php">Add sensor</a></li>
				  <li role="presentation"><a href="editUserData.php">Edit user data</a></li>
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
 

      <div class="page-header col-xs-12 col-sm-12 col-md-12">
        <h1>Change Privileges!</h1>
      </div>
	  <!-- "col-xs-6 col-xs-offset-3 col-sm-6 col-sm-offset-5 col-md-6 col-md-offset-5" -->
		<form action="changePrivileges.php" method="post" class ="form" >
	  		<?php
			
				echo '<input type="hidden" name="User_Id" value="'.$user_id.'" />';
				echo '<input type="hidden" name="Room_Id" value="'.$room_id.'" />';
				echo '<input type="hidden" name="privilege" value="'.$privilege.'" id ="privilege" />';
				
				echo '<input type="hidden" name="endPrivilege" value="'.$privilege.'" id ="endPrivilege" />';
				/*
				 for($i=0;$i<count($functions);$i++)
				 {
					 
					echo '<p>'.$functions[$i].'</p>';
					// echo '<div class="checkbox">
					//	   <label><input type="checkbox" value="">'.$functions[$i].'</label>
					//	   </div>';
					echo '<select name ='.'"'.(String)$functions[$i].'"'.'>';
					if($privilegesValues[$i] == 1)
					{
						echo'<option value= true>'.'true'.'</option>';
						echo'<option value= false>'.'false'.'</option>';
					}
					else
					{
						echo'<option value= false>'.'false'.'</option>';
						echo'<option value= true>'.'true'.'</option>';
					}
					echo '</select>';
					
					echo '</br>';
				 }
				 */
				 
			

				 
				 echo '<table id=inputs>';
				 for($i=0;$i<count($functions);$i++)
				 {
					 echo '<tr>';
					 
						$newName = str_replace("_"," ",$functions[$i]);
						echo '<td>'.$newName.'</td>';
						if($privilegesValues[$i] == 1)
						{
							$tmp = $functions[$i].'_e_'.$i.'_e_1';
							echo '<td class=fontello_icon_green >';
							echo '<i onclick="onclickHandler('.$tmp.')"; class="icon-ok-circled" id = '.$tmp.'></i></td>';
						}
						else
						{
							$tmp = $functions[$i].'_e_'.$i.'_e_0';
							echo '<td class=fontello_icon ><i onclick="onclickHandler('.$tmp.')"; class="icon-ok-circled" id = '.$tmp.'></i></td>';
						}
					 echo '</tr>';
				 }
				 echo '</table>';
	  
			?>
			
			<button type="submit" class="btn btn-primary" id = "buttonSubmit">Submit !</button>
			</form>
			
			
			
			
			
			
			
			
			<div class="fb col-sm-2 " id ><i class="icon-facebook"></i></div>
			<?php
				//echo'<p  id="a1">'.$privilegesValues[0].'</p>';
			?>

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





















