
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("name", "yabba");
   pageContext.setAttribute ("x", "7");
   int x = 7; 
%>
 
<frame2:password name="${name}" value="${x}" onfocus="true"/>
