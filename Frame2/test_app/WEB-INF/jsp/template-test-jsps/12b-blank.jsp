<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Good Name, Blank Path, Page Scope -->

<template:insert definition="template2">
	<template:put name="header" path="" scope="page" />
</template:insert>
</body
</html>