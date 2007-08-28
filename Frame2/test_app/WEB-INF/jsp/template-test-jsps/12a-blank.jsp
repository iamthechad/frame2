<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Good Name, Blank Path, No Scope -->

<template:insert definition="template2">
	<template:put name="header" path="" />
</template:insert>
</body>
</html>