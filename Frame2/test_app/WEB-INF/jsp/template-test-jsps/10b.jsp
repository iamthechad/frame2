<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Different Names, Same Paths, Page Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="headerPageScope.jsp" scope="page" />
	<template:put name="header2" path="headerPageScope.jsp" scope="page" />	
	<template:put name="nav" path="navPageScope.jsp" scope="page" />
	<template:put name="nav2" path="navPageScope.jsp" scope="page" />
	<template:put name="footer" path="footerPageScope.jsp" scope="page" />
	<template:put name="footer2" path="footerPageScope.jsp" scope="page" />	
</template:insert>
</body
</html>