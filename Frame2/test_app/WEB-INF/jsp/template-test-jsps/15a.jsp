<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags No Name, Good Path, No Scope -->

<template:insert definition="template2">
	<template:put name="" path="headerNoScope.jsp" />
	<template:put name="" path="navNoScope.jsp" />
	<template:put name="" path="footerNoScope.jsp" />	
</template:insert>
</body>
</html>