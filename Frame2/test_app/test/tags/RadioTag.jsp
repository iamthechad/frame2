
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("name", "yabba");
   pageContext.setAttribute ("checked", "radioValue");
%>
 
<frame2:radio name="${name}" displayvalue="label" value="radioValue" onfocus="true" checked='${checked}' />