<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Good Name, No Path, Request Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="" scope="request" />
	<template:put name="nav" path="" scope="request" />
	<template:put name="footer" path="" scope="request" />	
</template:insert>
</body>
</html>