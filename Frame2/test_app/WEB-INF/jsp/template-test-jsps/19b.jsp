<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Same Names, Different Paths, Page Scope -->

<template:insert definition="template2">
	<template:put name="header" path="headerPageScope.jsp" scope="page" />
	<template:put name="header" path="header1.jsp" scope="page" />	
	<template:put name="nav" path="navPageScope.jsp" scope="page" />
	<template:put name="nav" path="nav1.jsp" scope="page" />
	<template:put name="footer" path="footerPageScope.jsp" scope="page" />
	<template:put name="footer" path="footer1.jsp" scope="page" />	
</template:insert>
</body
</html>