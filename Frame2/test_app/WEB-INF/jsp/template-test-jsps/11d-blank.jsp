<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Blank Name, Good Path, Session Scope -->

<template:insert definition="template2">
	<template:put name="" path="headerSessionScope.jsp" scope="session" />
</template:insert>
</body
</html>