<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>
<template:insert definition="template_headeronly">
<template:put name="notintemplate" path="headerRequestScope.jsp" scope="request"/>
</template:insert>