<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<%@ page import="org.megatome.frame2.taglib.html.Constants" %>
<% 
   pageContext.setAttribute ("value", "value");
   pageContext.setAttribute (Constants.SELECT_KEY, "value");
   pageContext.setAttribute ("x", "3");

%>
 
<frame2:option value="${value}" displayvalue="displayValue" />

