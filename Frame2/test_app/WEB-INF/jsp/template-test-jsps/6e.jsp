<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Good Name, No Path, Application Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="" scope="application" />
	<template:put name="nav" path="" scope="application" />
	<template:put name="footer" path="" scope="application" />	
</template:insert>
</body
</html>