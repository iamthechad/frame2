<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Good Name, No Path, Page Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="" scope="page" />
	<template:put name="nav" path="" scope="page" />
	<template:put name="footer" path="" scope="page" />	
</template:insert>
</body>
</html>