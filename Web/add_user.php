<?php

	require('./User.php');
	$userName = $_POST['name'];
	$users  = User::createUsersFromXml('/home/pi/Java/MasterRoomControllerFx/dist/users.xml');
	
	
	
	
session_start();
if(isset($_POST['email']))    
{
       //Udana walidacja?Za³ózmy, ¿e tak!
    $everything_OK = true;
    

    
    //Sprawdzenie d³ugoœci loginu 
    if((strlen($userName)<3) || (strlen($userName)>20))
    {
        $everything_OK=false;
        $_SESSION['e_name']="Name has to contain 3 to 20 charackters!";
    }
 
    // Sprawdzenie poprawnoœci adresu email
    $email = $_POST['email'];
    $emailB = filter_var($email, FILTER_SANITIZE_EMAIL);
    
    if((filter_var($emailB, FILTER_VALIDATE_EMAIL) == false) || ($emailB!=$email))
    {
        $everything_OK=false;
        $_SESSION['e_email'] = "E-mail is incorrect!";
    }
	
	if(User::isExistingEmail($users,$emailB))
	{
		$everything_OK=false;
         $_SESSION['e_email'] = "This e-mail is assigned to other account, try with another e-mail!";
		
	}
    
    
    // Sprawdzenie poprawnoœci has³a

    $password_1 = $_POST['password_1'];
    $password_2 = $_POST['password_2'];
    

    
    // Ograniczenie d³ugoœci has³a
    if((strlen($password_1)<8) || (strlen($password_1)>20))
    {
        $everything_OK=false;
        $_SESSION['e_password'] = "Password has to contain 8 to 20 characters!"; 
        
    }
    
    // Sprawdzenie przypadku gdy podane has³a s¹ ró¿ne
    if($password_1 != $password_2)
    {
        $everything_OK=false;
        $_SESSION['e_password'] = "Both passwords have to be the same!"; 
    }

    // Konwersja has³a do formy hash
    $password_hash = password_hash($password_1, PASSWORD_DEFAULT);
	
	
	//****************************************************************************
	
	$input_file = basename($_FILES["fileToUpload"]["name"]);
	$imageFileType = pathinfo($input_file,PATHINFO_EXTENSION);
	$target_dir = "/home/pi/Java/MasterRoomControllerFx/dist/img/";
	$target_file = $target_dir . $_POST['email'] . "." . $imageFileType;
	$uploadOk = 1;
	
	// Check if image file is a actual image
	if(isset($_POST["submit"])) {
		$check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
		if($check !== false) {
			
			
			echo "File is an image - " . $check["mime"] . ".";
			$uploadOk = 1;
		} else {
			 $_SESSION['e_image'] ="File is not an image.";
			 $everything_OK = false;
			 $uploadOk = 0;
		}
	}
	// Check if file already exists
	if (file_exists($target_file)) {
			 $_SESSION['e_image'] ="Sorry, image associated with that user name already exists.";
			 $everything_OK = false;		 
		$uploadOk = 0;
	}

	// Allow certain file formats
	if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
	&& $imageFileType != "gif" ) {
			$_SESSION['e_image'] ="Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
			$everything_OK = false;
		$uploadOk = 0;
	}

	
	
	
	
	
	
	
	//*****************************************************************************
	
	
	
	
    

            if($everything_OK == true)
            {
				
				$id = count($users);
				
				$privileges = User::generatePrivileges(8);
				
				
				
				$user = new User($id,$userName, $email, $password_hash, $privileges,$target_file);
				
				
				
				$users[count($users)] = $user;
				
				echo count($users)."<br/>";
				
                //Wszystko OK 
                	/*
					$userId = $_POST['User_Id'];
					$roomId = $_POST['Room_Id'];
					$newPrivilege = $_POST['endPrivilege'];
					
					$user = User::getUserById($users,$userId);
					$rooms = Room::createRoomsFromXml('/home/pi/Java/MasterRoomControllerFx/dist/rooms.xml');
					$room = Room::getRoomById($rooms,$roomId);
					
					
					
					*/
					
						
							   
						// echo "Udana walidacja";
						
						
					
				if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
					
					$_SESSION['i_userCreated'] = "User created!"; 	
					echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";
					
						println($userName);
						println($email);
						println($password_hash);
						
					   // echo "Udana walidacja";
						
						
				} else {
					echo "Sorry, there was an error uploading your file.";
					$bezZdjecia=1;
				}
					
					User::createXMLFile($users);
					if($bezZdjecia==1)
						$_SESSION['i_userCreated'] = "User was created, but there was an error uploading your image file";
					else
						$_SESSION['i_userCreated'] = "User created!"; 

					header('Location: index.php');
					
					

                
            }
			else
			{
				header('Location: addUser.php');
			}
           
        
        
        
    

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
function println($var)
{
	echo $var."<br/>";
}
?>


