<%@ page language="java" contentType="text/html" %>
<%@ taglib uri="taglib.tld" prefix="frame2" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<HTML><HEAD><TITLE>All Users</TITLE></HEAD>
<BODY>
<c:choose>
  <c:when test="${!empty displayUsers.users}">
    <TABLE width=75%>
  	<TH align="left" width=15%>User Name</TH>
    <TH align="left" width=20%>Email Address</TH>
  	<c:forEach items="${displayUsers.users}" var="nextUser">
  	<tr>
  	<td><c:out value="${nextUser.userName}"/></td>
  	<td><c:out value="${nextUser.email}"/></td>
  	</tr>
  	</c:forEach>
    </TABLE>	
 </c:when>
  <c:otherwise>
  There are no users defined
  </c:otherwise>
</c:choose>
<br><a href="addUser.jsp">Add New User</a>
</BODY></HTML>