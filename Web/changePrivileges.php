

<?php 
session_start();
	require('./User.php');
	require('./Room.php');
	
	
$userId = $_POST['User_Id'];
$roomId = $_POST['Room_Id'];
$newPrivilege = $_POST['endPrivilege'];
$users  = User::createUsersFromXml('/home/pi/Java/MasterRoomControllerFx/dist/users.xml');
$user = User::getUserById($users,$userId);
$rooms = Room::createRoomsFromXml('/home/pi/Java/MasterRoomControllerFx/dist/rooms.xml');
$room = Room::getRoomById($rooms,$roomId);

$privilege = User::getPrivilege($user->getPrivileges(),$roomId);


$answer = 0 ;

for($i=0;$i<count($room->getFunctions());$i++)
{
	
	$function = Room::getFunctionById($room,$i);
	
	echo $function;
	$function =$function;
	$privilegesValue = User::getPrivilegeValueByFunctionId($privilege,$i);
	$xyz = $_POST[(String)$function];
	
	echo "   ".$xyz;
	echo '</br>';
	switch($xyz)
	{
		case 'true':
		$answer = $answer + pow(2,$i);
		echo pow(2,$i);
	
		echo '</br>';
		break;

		case 'false':
		echo '0';
		echo '</br>';
		break;

	}
}


echo '</br>';
echo $answer ;
echo '</br>';


echo "Room:".$roomId;
echo '</br>';

(int)$tmpRoomId = (int)$roomId;
$wykladnik = 13;

while($tmpRoomId!=0)
{
	$mod = $tmpRoomId%2;
	
	if($mod == 1)
	{
		$answer += pow(2,$wykladnik);
	}
	
	(int)$tmpRoomId = (int)($tmpRoomId/2);
	
	$wykladnik++;
}




echo $newPrivilege ;

$user->setPrivilegeByRoomId($roomId,$newPrivilege );
$t = $user->getPrivileges();

//echo $t[0];


User::createXMLFile($users);
header('Location: privileges.php');
$_SESSION['i_change'] = "Privileges change was successful!"; 
   

?>