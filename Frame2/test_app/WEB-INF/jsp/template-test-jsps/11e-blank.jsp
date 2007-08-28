<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Blank Name, Good Path, Application Scope -->

<template:insert definition="template2">
	<template:put name="" path="headerApplicationScope.jsp" scope="application"/>
</template:insert>
</body>
</html>