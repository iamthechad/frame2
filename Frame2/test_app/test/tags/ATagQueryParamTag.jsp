<%@ page language="java" import="java.util.HashMap" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("url", "http://finance.yahoo.com/q");
%>
 
<frame2:a href="${url}">quotes<frame2:queryparam name="s" value="msft sunw orcl"/><frame2:queryparam name="d" value="v2"/></frame2:a>
