
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute ("resource.key.test.one", "foo");
   pageContext.setAttribute ("resource.key.test.two", "bar");
%>

<frame2:button name="%{resource.key.test.one}" value="%{resource.key.test.two}" onfocus="true"/>
