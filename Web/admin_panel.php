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
<html lang = "pl">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
		
    <title>Smart House</title>
    <meta name = "description" content="">
    <meta name = "keywords" content="">
    <meta name = "author" content="">

    <link rel="stylesheet" href="css/bootstrap.css">
    <script src = "js/code.js"></script>
</head>
<body>
    
    <?php
        
    echo "<p>WITAJ ".$_SESSION['user']."!";
    echo "<a href=logout.php>Log out!</a>";
	
				
	?>
	

</body>

</html>