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


function jsOnClick()
{
	
	
}