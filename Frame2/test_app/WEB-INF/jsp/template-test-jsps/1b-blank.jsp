<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Blank Name, Good Path, Page Scope -->

<template:insert definition="badtemplate">
	<template:put name="" path="headerPageScope.jsp" scope="page" />
</template:insert>
</body>
</html>