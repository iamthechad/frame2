<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Good Name, Good Path, Request Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="headerRequestScope.jsp" scope="request" />
</template:insert>
</body
</html>