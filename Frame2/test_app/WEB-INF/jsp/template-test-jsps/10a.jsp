<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Different Names, Same Paths, No Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="headerNoScope.jsp" />
	<template:put name="header2" path="headerNoScope.jsp" />	
	<template:put name="nav" path="navNoScope.jsp" />
	<template:put name="nav2" path="navNoScope.jsp" />
	<template:put name="footer" path="footerNoScope.jsp" />
	<template:put name="footer2" path="footerNoScope.jsp" />	
</template:insert>
</body>
</html>