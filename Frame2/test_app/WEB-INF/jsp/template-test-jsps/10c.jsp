<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Different Names, Same Paths, Request Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="headerRequestScope.jsp" scope="request" />
	<template:put name="header2" path="headerRequestScope.jsp" scope="request" />	
	<template:put name="nav" path="navRequestScope.jsp" scope="request" />
	<template:put name="nav2" path="navRequestScope.jsp" scope="request" />
	<template:put name="footer" path="footerRequestScope.jsp" scope="request" />
	<template:put name="footer2" path="footerRequestScope.jsp" scope="request" />	
</template:insert>
</body>
</html>