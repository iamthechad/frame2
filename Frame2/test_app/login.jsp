<%@ taglib uri="WEB-INF/taglib.tld" prefix="frame2" %>

<html>
   <head>
      <title>Login Page for Examples</title>
   </head>
   <body bgcolor="white">
	   <form method="POST" action='<%= response.encodeURL("j_security_check") %>' >
		   <table border="0" cellspacing="5">
			    <tr>
			      <th align="right">Username:</th>
			      <td align="left">
                  <frame2:text name="j_username"/>
			      </td>
			    </tr>
			    <tr>
			      <th align="right">Password:</th>
			      <td align="left">
			         <frame2:password name="j_password"/>
			      </td>
			    </tr>
			    <tr>
			      <td align="right">
			         <input type="submit" value="Log In">
			      </td>
			      <td align="left">
			         <frame2:reset/>
			      </td>
			    </tr>
		  </table>
	  </form>
   </body>
</html>
