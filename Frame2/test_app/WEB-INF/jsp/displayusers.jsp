<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ page import="test.org.megatome.app.user.User" %>
<%@ page import="org.megatome.frame2.Globals" %>
<%@ page import="org.megatome.frame2.errors.Errors" %>
<%@ page import="test.org.megatome.app.user.DisplayUsers" %>

<HTML>
  <HEAD>
    <TITLE>Welcome to the Frame2 Users BullPen </TITLE>
  </HEAD>
  <BODY>
  <TABLE width=75%>
  	<TH align="left" width=15%>First Name</TH>
  	<TH align="left" width=15%>Last Name</TH>
  	<TH align="left" width=20%>Email Address</TH>
  	<TH align="left" width=50%>Date Created</TH>
  <% 
  	DisplayUsers displayUsers = (DisplayUsers) request.getAttribute("displayUsers");
    List usersList = displayUsers.getUsers();

    Iterator iter = usersList.iterator();
	while(iter.hasNext()) {
		User user = (User) iter.next();
		
        // message number
        out.println("<tr><td>" + user.getFirstName() + "</td>");

        // name
        out.println("<td>" + user.getLastName() + "</td>");

        // email address
        out.print("<td>" + "<a href=\"mailto:");
        out.println(user.getEmail() + "\">" + user.getEmail() + "</a>" + "</td>");

        // creation date
        out.println("<td>" + "<i>");
        out.println(user.getCreationDate() + "</i><br>" + "</td></tr>");
	}
	%>
  	<TR>
  		<TD>&nbsp;</TD>
  	</TR>
  	
	<%	

  %>
  
  	<TR>
  		<TD>&nbsp;</TD>
  	</TR>
  	<TR>
  		<TD><b><A HREF="addUser.f2">Add User</A></b></TD>
  	</TR>
   <TR>
      <TD>&nbsp;</TD>
   </TR>  
   <TR>
      <TD><b><A HREF="testTags.f2">Test Tags</A></b></TD>
   </TR>
  </TABLE>	
  </BODY>
</HTML>
