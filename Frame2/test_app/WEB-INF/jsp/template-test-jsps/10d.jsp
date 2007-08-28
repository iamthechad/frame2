<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<html>
<head><Title></Title><head>
<body>

<!-- Bad Insert definition, Multiple Put tags Different Names, Same Paths, Session Scope -->

<template:insert definition="badtemplate">
	<template:put name="header" path="headerSessionScope.jsp" scope="session" />
	<template:put name="header2" path="headerSessionScope.jsp" scope="session" />	
	<template:put name="nav" path="navSessionScope.jsp" scope="session" />
	<template:put name="nav2" path="navSessionScope.jsp" scope="session" />
	<template:put name="footer" path="footerSessionScope.jsp" scope="session" />
	<template:put name="footer2" path="footerSessionScope.jsp" scope="session" />	
</template:insert>
</body>
</html>