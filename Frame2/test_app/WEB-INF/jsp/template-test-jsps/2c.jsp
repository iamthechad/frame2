<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Good Name, No Path Attribute, Request Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" scope="request" />
</template:insert>
</body>
</html>