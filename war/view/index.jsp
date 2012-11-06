<html>
	<body>

<form name="submitForm" action="ServletClass">
<input type="text" id="brandName"/>
</form>
<p>The data from servlet:</p> <%=session.getAttribute("data") %>
  </body>
</html>	