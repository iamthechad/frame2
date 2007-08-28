<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, 1 Put tag Good Name, Good Path, Page Scope -->

<template:insert definition="template2">
	<template:put name="header" path="headerPageScope.jsp" scope="page" />
</template:insert>
</body>
</html>