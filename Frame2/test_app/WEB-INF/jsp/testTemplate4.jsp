<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>


<template:insert definition="template3" >
   <template:put name="header" path="header1.jsp" scope="request"/>
   <template:put name="nav" path="nav1.jsp" scope="request"/>
   <template:put name="footer" path="footer1.jsp" scope="page"/>
</template:insert>