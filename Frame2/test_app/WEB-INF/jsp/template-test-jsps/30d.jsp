<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, 1 Put tag Good Name, Good Path, Request Scope, Single Param Good Name, Blank Value -->

<template:insert definition="template2">
	<template:param name="paramname1" value="" />
	<template:put name="header" path="headerRequestScope.jsp" scope="request" />
</template:insert>
</body>
</html>