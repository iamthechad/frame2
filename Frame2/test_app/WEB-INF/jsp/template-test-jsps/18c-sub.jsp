<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Good Name, Good Path, Request Scope -->

<template:insert definition="template2">
	<template:put name="header" path="subDir/headerRequestScope.jsp" scope="request" />
	<template:put name="nav" path="subDir/navRequestScope.jsp" scope="request" />
	<template:put name="footer" path="subDir/footerRequestScope.jsp" scope="request" />	
</template:insert>
</body>
</html>