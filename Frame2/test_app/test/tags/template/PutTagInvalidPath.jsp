<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>
<template:insert definition="template_headeronly">
<template:put name="header" path="abc.jsp" scope="request"/>
</template:insert>
This should be the only text