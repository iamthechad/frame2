<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Good Insert definition, Multiple Put tags Same Names, Different Paths, Request Scope -->

<template:insert definition="template2">
	<template:put name="header" path="headerRequestScope.jsp" scope="request" />
	<template:put name="header" path="header1.jsp" scope="request" />	
	<template:put name="nav" path="navRequestScope.jsp" scope="request" />
	<template:put name="nav" path="nav1.jsp" scope="request" />
	<template:put name="footer" path="footerRequestScope.jsp" scope="request" />
	<template:put name="footer" path="footer1.jsp" scope="request" />	
</template:insert>
</body>
</html>