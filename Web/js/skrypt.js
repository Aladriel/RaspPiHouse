/**
 * 
 */
 var tmp;
 
 function testowa()
{
	//alert("Test");
}


function main()
{
	//testowa();
	var element = document.getElementById('button1');
	
	element.addEventListener('click', function(event)
	{
		var a = document.getElementById('a1');
		
		var val = a.innerHTML;
		
		
		if(a.value == null)
		{
			a.value = '0';	
		}
		
		var wynik = document.createTextNode('0');
		
		if(a.hasChildNodes())
		{
			tmp = a.innerHTML; 
			
			while(a.firstChild)
			{
				a.removeChild(a.firstChild);
			}
			
			if(tmp == '0')
				wynik = document.createTextNode('1');
			else
				wynik = document.createTextNode('0');	
		}
		
		a.appendChild(wynik);
	});
	
}


function onclickHandler(xyz)
    {
		  var separator ="_e_";
		  var privilege = document.getElementById('privilege').value;
		  var id = xyz.id;
		  
		  var a = id.split(separator);
		  var tabela = document.getElementById('inputs');
		
		  
		  tabela.deleteRow(a[1]);

		  var tmp ="";
		  var class_name="";
		  var answer = document.getElementById('endPrivilege');
		  
		  if(a[2]=="0")
		  {
			tmp= a[0]+separator+a[1]+separator+"1";
			class_name="fontello_icon_green";
			
			answer.value = parseInt(answer.value) + Math.pow(2, parseInt(a[1]));
		  }
		  
		  if(a[2]=="1")
		  {
			tmp = a[0]+separator+a[1]+separator+"0";
			class_name="fontello_icon";
			answer.value = parseInt(answer.value) - Math.pow(2, parseInt(a[1]));
		  }

		  	var row = tabela.insertRow(a[1]);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
		  	cell1.innerHTML = a[0].replace("_"," ");
			cell2.innerHTML = '<i onclick="onclickHandler('+tmp+')"; class="icon-ok-circled '+class_name+'" id = '+tmp+'></i></td>';
		  
			
			//alert(answer.value);
			
			
			
	       //  alert( a[0] + " " + a[1] + " " + a[2]  );
		  
      

    }
