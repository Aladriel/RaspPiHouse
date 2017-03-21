<?php
     
    session_start();

    if((!isset($_POST['login']) ) || (!isset($_POST['password'])))
    {
        header('Location: index.php');
        exit();
    }

  //  require_once"login.php";
	
	$admin = simplexml_load_file('XML/admin.xml');
  
    if($admin == null )
    {
        echo 'File loading error';
    }
    else
    {  
        $login = $_POST['login'];
        $password = $_POST['password'];
        
        $login = htmlentities($login, ENT_QUOTES, "UTF-8");
      
		$adminLogin = (string)$admin->login;
		$adminPassword = (string)$admin->password;
		$adminName = (string)$admin->name;
		
		echo $adminLogin . ' ' . $adminLogin . ' ' . $adminName;
		
		
		
            if($adminLogin == $login)
            {
                if($adminPassword == $password)
                {
                    $_SESSION['logged_in'] = true;
                    $_SESSION['user'] = $adminName;

                    unset($_SESSION['error']);
                    header('Location: admin_panel.php');
                }
                else
                {
                    $_SESSION['error'] = '<span style = "color:red">Bad password!</span>';
                    header('Location: index.php');
                }
            }
            else
            {
                $_SESSION['error'] = '<span style = "color:red">Bad login!</span>';
                header('Location: index.php');
            }
            

    }
      
?>
    
    
