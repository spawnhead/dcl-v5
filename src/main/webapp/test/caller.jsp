<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<html>
<head>
	<script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/prototype.js'/>'></script>
	<script language="JScript" type="text/javascript" >
		function test(){
			//alert('1');
			new Ajax.Request('/dcl/trusted/test.do',
			{
				method:'get',
				asynchronous :false,
				onSuccess: function(transport){
					debugger;
					alert('['+transport.responseText+']');
					//var response = transport.responseText || "no response text";
					//alert("Success! \n\n" + response);
				},
				onFailure: function(){ alert('Something went wrong...') }
			});
		}
		//initFunctions.push(function(){test()});
		document.onclick=test;
	</script>
</head>
<body >
</body>
</html>
