<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags No Name, Good Path, Session Scope -->

<template:insert definition="template2">
	<template:put name="" path="headerSessionScope.jsp" scope="session" />
	<template:put name="" path="navSessionScope.jsp" scope="session" />
	<template:put name="" path="footerSessionScope.jsp" scope="session" />	
</template:insert>
</body
</html>