<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>
<% 
	pageContext.setAttribute ("template_def", "template2"); 
	pageContext.setAttribute ("put_path", "headerSessionScope.jsp"); 
	pageContext.setAttribute ("put_scope", "session"); 		
	pageContext.setAttribute ("param_value", "dude");
	pageContext.setAttribute ("param_value2", "another dude");
%>
<HTML>
  <HEAD>
    <TITLE>Template2</TITLE>
  </HEAD>
  <BODY>
<template:insert definition="${template_def}" >
<template:param name="paramA" value="${param_value}"/>
<template:param name="paramA" value="${param_value2}"/>
<template:put name="header" path="${put_path}" scope="${put_scope}" />
</template:insert> 
  </BODY>
</HTML>