<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, 1 Put tag Blank Name, Good Path, No Scope -->

<template:insert definition="badtemplate">
	<template:put name="" path="headerNoScope.jsp" />
</template:insert>
</body
</html>