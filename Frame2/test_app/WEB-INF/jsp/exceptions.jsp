<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>

<html>
<body>
<h1>Exception Found</h1>
<table>
	<tr>
		<td valign="top"><b>Exception:</b></td>
		<td><frame2:textarea name="exception" value="${exception.message}" rows="10" cols="60" /></td>
	</tr>

</table>
</body>
</html>