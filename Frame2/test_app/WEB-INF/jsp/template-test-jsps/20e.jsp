<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Different Names, Same Paths, Application Scope -->

<template:insert definition="template2">
	<template:put name="header" path="headerApplicationScope.jsp" scope="application" />
	<template:put name="header2" path="headerApplicationScope.jsp" scope="application" />	
	<template:put name="nav" path="navApplicationScope.jsp" scope="application" />
	<template:put name="nav2" path="navApplicationScope.jsp" scope="application" />
	<template:put name="footer" path="footerApplicationScope.jsp" scope="application" />
	<template:put name="footer2" path="footerApplicationScope.jsp" scope="application" />	
</template:insert>
</body>
</html>