
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("name", "yabba");
   pageContext.setAttribute ("x", "3");
   pageContext.setAttribute ("text", "The avs are better than the ugly redwings");
   int x = 7; 
%>
 
<frame2:textarea name="${name}" rows="${x}" cols="7" value="${text}">red wings suck</frame2:textarea>
