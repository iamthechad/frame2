<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag No Name Attribute, Good Path, Page Scope -->

<template:insert definition="badtemplate">
	<template:put path="headerPageScope.jsp" scope="page" />
</template:insert>
</body>
</html>