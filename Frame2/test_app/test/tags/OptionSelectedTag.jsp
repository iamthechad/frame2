<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<%@ page import="org.megatome.frame2.taglib.html.Constants" %>
<% 
   pageContext.setAttribute ("value", "value");
%>
<frame2:select name="select">
<frame2:option value="${value}" displayvalue="displayValue" selected="${value}"/>
</frame2:select>

