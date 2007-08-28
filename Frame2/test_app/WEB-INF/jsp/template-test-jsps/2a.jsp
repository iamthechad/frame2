<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Good Name, No Path Attribute, No Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" />
</template:insert>
</body>
</html>