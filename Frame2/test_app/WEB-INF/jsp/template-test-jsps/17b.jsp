<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Good Name, Invalid Path, Page Scope -->

<template:insert definition="template2">
	<template:put name="header" path="invalidPath.jsp" scope="page" />
	<template:put name="nav" path="invalidPath.jsp" scope="page" />
	<template:put name="footer" path="invalidPath.jsp" scope="page" />	
</template:insert>
</body>
</html>