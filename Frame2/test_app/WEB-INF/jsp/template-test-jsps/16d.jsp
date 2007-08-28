<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Good Name, No Path, Session Scope -->

<template:insert definition="template2">
	<template:put name="header" path="" scope="session" />
	<template:put name="nav" path="" scope="session" />
	<template:put name="footer" path="" scope="session" />	
</template:insert>
</body>
</html>