<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Same Names, Different Paths, No Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="headerNoScope.jsp" />
	<template:put name="header" path="header1.jsp" />	
	<template:put name="nav" path="navNoScope.jsp" />
	<template:put name="nav" path="nav1.jsp" />
	<template:put name="footer" path="footerNoScope.jsp" />
	<template:put name="footer" path="footer1.jsp" />	
</template:insert>
</body
</html>