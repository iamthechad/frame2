
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("x", "7");
   int x = 7; 
%>
 
<frame2:submit value="${x}" onfocus="true"/>
