<?php

class User
{
	private $_id;
	private $_name;
	private $_login;
	private $_password;
	private $_privileges;
	
	
	
   public function __construct($id,$name, $login, $password, $privileges)
   {
	  $this->_id = $id;
	  $this->_name = $name;
	  $this->_login = $login;
	  $this->_password = $password;
	  $this->_privileges = $privileges;    
   }
   
      public function getId()
   {

      return $this->_id;            

   } 
   public function getName()
   {

      return $this->_name;            

   } 
   public function getLogin()
   {

      return $this->_login;            

   } 
   
    public function getPassword()
   {

      return $this->_password;            

   } 
   public function getPrivileges()
   {

      return $this->_privileges;            

   } 
   

   
   public static function getPrivilege($privileges, $roomId)
   {
	   foreach($privileges as $privilege)
	   {
		  // echo "dostae".$privilege;
		   if(($privilege&57344) >>13 == $roomId)
		   {
			  // echo "zwrocilem".$privilege;
				return $privilege;
		   }
	   }
	   return null;
   }
   
   public static function getPrivilegeValueByFunctionId($_privilege,$functionId)
   {
	   $a[0] = 1;
	   $a[1] = 2;
	   $a[2] = 4;
	   $a[3] = 8;
	   $a[4] = 16;
	   $a[5] = 32;
	   $a[6] = 64;
	   $a[7] = 128;
	   $a[8] = 256;
	   $a[9] = 512;
	   $a[10] = 1024;
	   $a[11] = 2048;
	   $a[12] = 4096;   
	   
	   $privilege = $_privilege&8191;
	  
	   switch ($functionId) 
	   {
		case 0:
			return $privilege&$a[0];
		case 1:
			return ($privilege&$a[1])>>1;	
		case 2:		
			return ($privilege&$a[2])>>2;
		case 3:
			return ($privilege&$a[3])>>3;		
		case 4:
			return ($privilege&$a[4])>>4;
			
		case 5:
			return ($privilege&$a[5])>>5;
			
			
		}
   }
   
   public static function getUserById($usersTable,$id)
   {
	   foreach($usersTable as $user)
	   {
		   	if($user->getId() == $id)
			   return $user;
	   }   
	   return null;   
   }
   
   public static function createUsersFromXml($xmlFile)
   {
	   $usersXML = simplexml_load_file($xmlFile);
	   $usersTable = null;
	   
	   if($usersXML == null)
			return -1;
	   else
	   {
		   	$i=0;
			foreach($usersXML as $user)
			{		
				$_privileges=null;
				$_id = $user->id;
				$_name = $user->name;
				$_login = $user->login;
				$_password = $user->password;
						
				$j=0;
				foreach($user->privileges[0] as $privilege)
				{
					$_privileges[$j] = $privilege;
					$j++;
				}
						
				$usersTable[$i] = new User($_id,$_name, $_login,$_password,$_privileges);
						
				//echo "User:".$usersTable[$i]->getName().'<br />';
				$pr = $usersTable[$i]->getPrivileges();
			/*	for($s=0;$s<count($pr);$s++)
				{
					echo "PR(".$s."): ".$pr[$s].'<br />';
				}*/
				$i++;	
			}
		   return $usersTable;
	   }
   
   }
   
   
   public static function createXMLFile($usersTable)
   {
		$usersXML = file('XML/owoce.xml');

		$wynik = '<?xml version="1.0" encoding="utf-8"?>'."\r\n";
		$wynik .= '<users>'."\r\n";

		foreach ($usersTable as $user) {
			$wynik .= "\t".'<user>'."\r\n";
			$wynik .= "\t\t".'<id>'.$user->getId().'</id>'."\r\n";
			$wynik .= "\t\t".'<name>'.$user->getName().'</name>'."\r\n";
			$wynik .= "\t\t".'<login>'.$user->getLogin().'</login>'."\r\n";
			$wynik .= "\t\t".'<password>'.$user->getPassword().'</password>'."\r\n";
				$wynik .= "\t\t".'<privileges>'."\r\n";
				foreach ($user->getPrivileges() as $privilege) 
				{
					$wynik .= "\t\t\t".'<privilege>'.$privilege.'</privilege>'."\r\n";
				}
				$wynik .= "\t\t".'</privileges>'."\r\n";
			$wynik .= "\t".'</user>'."\r\n";
		}

		$wynik .= '</users>'."\r\n";

		file_put_contents('XML/owoce.xml', $wynik);

   }
}



?>