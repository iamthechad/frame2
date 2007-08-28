<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags No Name, Good Path, Request Scope -->

<template:insert definition="badtemplate">
	<template:put name="" path="headerRequestScope.jsp" scope="request" />
	<template:put name="" path="navRequestScope.jsp" scope="request" />
	<template:put name="" path="footerRequestScope.jsp" scope="request" />	
</template:insert>
</body>
</html>