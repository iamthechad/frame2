<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.lang.String" %>

<%@ page import="test.org.megatome.app.user.User" %>

<HTML>
  <HEAD>
    <TITLE>Add a User</TITLE>
  </HEAD>
  <BODY>
    <FORM method="post" action="saveUser.f2">
      <TABLE>
        <TR>
          <% String val = User.today( ); %>
          <TD><b>Creation Date:</b></TD>
          <TD><%=val%></TD>
          <INPUT type="hidden" name="createdate" value="<%=val%>">
        </TR>
        <TR>
          <TD><b>First Name:</b></td>
          <TD><INPUT type="text" name="firstName" size="40"></TD>
        </TR>
        <TR>
          <TD><b>Last Name:</b></td>
          <TD><INPUT type="text" name="lastName" size="40"></TD>
        </TR>
        <TR>
          <TD><b>Email address:</b></TD>
          <TD><INPUT type="text" name="email" size="40"></TD>
        </TR>
        <TR>
          <TD>&nbsp;</TD>
        </TR>
        <TR>
          <TD align=center><INPUT type="submit" name="adduser" value="Add User"/></TD>
        </TR>
      </TABLE>
    </FORM>
  </BODY>
</HTML>
