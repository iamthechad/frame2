<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>
<% pageContext.setAttribute("header_name", "header"); %>

    before header
    <br>
    <template:get name="${header_name}"/>
    <br>
    after header
    <br>
    before nav
    <br>
    <template:get name="nav"/>
    <br>
    after nav
    <br>
    before footer
    <br>
    <template:get name="footer"/>
    <br>
    after footer
    <br>
<hr>
<% String[] paramValue = (String[])request.getAttribute("paramA"); if (paramValue == null) { %>
   Null Parameter for Param Tag
<% } else { %>
Param Tag Value(s):<br>
<% for (int i = 0; i < paramValue.length; i++) { %>
<%= paramValue[i] %><br>
<% } 
 } %>

