<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags No Name, Good Path, Page Scope -->

<template:insert definition="template2">
	<template:put name="" path="headerPageScope.jsp" scope="page" />
	<template:put name="" path="navPageScope.jsp" scope="page" />
	<template:put name="" path="footerPageScope.jsp" scope="page" />	
</template:insert>
</body>
</html>