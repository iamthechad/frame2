<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Good Name, No Path, Page Scope -->

<template:insert definition="template2">
	<template:put name="header" path="" scope="page" />
	<template:put name="nav" path="" scope="page" />
	<template:put name="footer" path="" scope="page" />	
</template:insert>
</body
</html>