<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Same Names, Different Paths, Session Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="headerSessionScope.jsp" scope="session" />
	<template:put name="header" path="header1.jsp" scope="session" />	
	<template:put name="nav" path="navSessionScope.jsp" scope="session" />
	<template:put name="nav" path="nav1.jsp" scope="session" />
	<template:put name="footer" path="footerSessionScope.jsp" scope="session" />
	<template:put name="footer" path="footer1.jsp" scope="session" />	
</template:insert>
</body
</html>