<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Good Name, Good Path, No Scope -->

<template:insert definition="template2">
	<template:put name="header" path="subDir/headerNoScope.jsp" />
	<template:put name="nav" path="subDir/navNoScope.jsp" />
	<template:put name="footer" path="subDir/footerNoScope.jsp" />	
</template:insert>
</body
</html>