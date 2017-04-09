<?php

class Room
{
	private $_name;
	private $_id;
	private $_functions;
		
   public function __construct($id,$name,$functions)
   {
      $this->_id = $id;
	  $this->_name = $name;
	  $this->_functions = $functions;
   }
   
   public function getName()
   {
      return $this->_name;            
   } 

   public function getId()
   {
      return $this->_id;            
   } 

   public function getFunctions()
   {
      return $this->_functions;            
   } 
   
      public static function getFunctionById($room,$functionId)
   {
			   $functions = $room->getFunctions();
			   return $functions[$functionId];
   }
   public static function getRoomById($roomsTable,$roomId)
   {
		foreach($roomsTable as $room)
		{
			if($room->getId() == $roomId)
			{
				return $room;
			}
		}
	return null;
   }
   
   public static function createRoomsFromXml($xmlFile)
   {
	   $roomsXML = simplexml_load_file($xmlFile);
	   $roomsTable = null;
	   
	   if($roomsXML == null)
			return -1;
	   else
	   {
		   	$i=0;
			foreach($roomsXML as $room)
			{		
				$_functions = null;
				$_id = $room->id;
				$_name = $room->name;
						
				$j=0;
				foreach($room->functions[0] as $function)
				{
					$_functions[$j] = $function;
					$j++;
				}
						
				$roomsTable[$i] = new Room($_id,$_name,$_functions);
						
				//echo "Room:".$roomsTable[$i]->getName().'<br />';
				//echo "ID:".$roomsTable[$i]->getId().'<br />';
				$pr = $roomsTable[$i]->getFunctions();
			/*	for($s=0;$s<count($pr);$s++)
				{
					echo "S(".$s."): ".$pr[$s].'<br />';
				}*/
				$i++;	
			}
		   return $roomsTable;
	   }
   }
   
}



?>