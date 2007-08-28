<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Good Name, Invalid Path, Application Scope -->

<template:insert definition="template2">
	<template:put name="header" path="invalidPath.jsp" scope="application" />
	<template:put name="nav" path="invalidPath.jsp" scope="application" />
	<template:put name="footer" path="invalidPath.jsp" scope="application" />	
</template:insert>
</body>
</html>