<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Good Name, Blank Path, Session Scope -->

<template:insert definition="template2">
	<template:put name="header" path="" scope="session" />
</template:insert>
</body
</html>