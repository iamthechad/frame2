<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Good Name, Invalid Path, No Scope -->

<template:insert definition="template2">
	<template:put name="header" path="invalidPath.jsp" />
	<template:put name="nav" path="invalidPath.jsp" />
	<template:put name="footer" path="invalidPath.jsp" />	
</template:insert>
</body>
</html>