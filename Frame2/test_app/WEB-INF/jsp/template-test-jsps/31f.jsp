<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, 1 Put tag Good Name, Good Path, Request Scope, Mutliple Param Same Name, Different Value -->

<template:insert definition="template2">
	<template:param name="paramname1" value="paramvalue1" />
	<template:param name="paramname2" value="paramvalue2" />
	<template:param name="paramname1" value="paramvalue3" />
	<template:param name="paramname2" value="paramvalue4" />
	<template:put name="header" path="headerRequestScope.jsp" scope="request" />
</template:insert>
</body>
</html>