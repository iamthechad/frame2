
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("x", "Stop clicking me");
%>
 
<frame2:button name="foo" value="${x}" onfocus="true"/>
