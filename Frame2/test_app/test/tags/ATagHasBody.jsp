
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("url", "dude.html");
   pageContext.setAttribute ("name", "dude");
%>
 
<frame2:a href="${url}" name="${name}" displayvalue="xxx">Visit Dude</frame2:a> 