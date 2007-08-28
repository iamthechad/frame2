<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Good Name, No Path, No Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="" />
	<template:put name="nav" path="" />
	<template:put name="footer" path="" />	
</template:insert>
</body>
</html>