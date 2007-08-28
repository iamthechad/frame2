<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags No Name, Good Path, Page Scope -->

<template:insert definition="badtemplate">
	<template:put name="" path="headerPageScope.jsp" scope="page" />
	<template:put name="" path="navPageScope.jsp" scope="page" />
	<template:put name="" path="footerPageScope.jsp" scope="page" />	
</template:insert>
</body>
</html>