<%@ taglib uri="/WEB-INF/template_taglib.tld" prefix="template" %>

<HTML>
  <HEAD>
    <TITLE>Template3</TITLE>
  </HEAD>
  <BODY>
 
    before header
    <br>
    <template:get name="header"/>
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

  </BODY>
</HTML>
