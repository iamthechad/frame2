<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Good Name, Good Path, Request Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="subDir/headerRequestScope.jsp" scope="request" />
	<template:put name="nav" path="subDir/navRequestScope.jsp" scope="request" />
	<template:put name="footer" path="subDir/footerRequestScope.jsp" scope="request" />	
</template:insert>
</body
</html>