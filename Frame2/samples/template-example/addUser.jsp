<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="taglib.tld" prefix="frame2" %>
<%@ taglib uri="template_taglib.tld" prefix="template" %>
<template:insert definition="defaultTopLayout">
	<template:param name="page_title" value="Add User"/>
	<template:put name="preBody" path="addUserPreBody.jsp"/>
</template:insert>
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
<template:insert definition="defaultBottomLayout"/>