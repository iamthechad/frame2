<%@ page language="java" import="java.util.HashMap" %>
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
	// create an href = http://finance.yahoo.com/q?s=msft+sunw+orcl&d=v2
   HashMap params = new HashMap();
   params.put("s", "msft sunw orcl");
   params.put("d", "v2");
   pageContext.setAttribute ("url", "http://finance.yahoo.com/q");
   pageContext.setAttribute ("params", params);
%>
 
<frame2:a href="${url}" queryparams="${params}">quotes<frame2:queryparam name="s" value="msft sunw orcl"/><frame2:queryparam name="d" value="v2"/></frame2:a>
