
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("name", "yabba");
   pageContext.setAttribute ("value", "value");
   pageContext.setAttribute ("multiple", "true");
   pageContext.setAttribute ("x", "3");

%>
 
<frame2:select name="${name}" multiple="${multiple}" >
body
</frame2:select>
