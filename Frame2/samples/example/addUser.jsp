<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="taglib.tld" prefix="frame2" %>

<HTML><HEAD><TITLE>Add New User</TITLE></HEAD>
<BODY>
<frame2:errors/>
<frame2:form action="addUser.f2" method="POST">
<table border="0">
<tr>
<td align="left">User Name:</td>
<td><frame2:text name="userName" value="${addUser.userName}"/></td>
</tr><tr>
<td align="left">Email:</td>
<td><frame2:text name="email" value="${addUser.email}"/></td>
</tr>
</table>
<frame2:submit/><frame2:reset/><frame2:cancel/>
</frame2:form>
</BODY></HTML>