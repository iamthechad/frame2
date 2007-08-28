<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags No Name, Good Path, Application Scope -->

<template:insert definition="badtemplate">
	<template:put name="" path="headerApplicationScope.jsp" scope="application" />
	<template:put name="" path="navApplicationScope.jsp" scope="application" />
	<template:put name="" path="footerApplicationScope.jsp" scope="application" />	
</template:insert>
</body>
</html>