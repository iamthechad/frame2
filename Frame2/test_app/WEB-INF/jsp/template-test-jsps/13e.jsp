<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, 1 Put tag Good Name, Invalid Path, Application Scope -->

<template:insert definition="template2">
	<template:put name="header" path="invalidPath.jsp" scope="application" />
</template:insert>
</body>
</html>