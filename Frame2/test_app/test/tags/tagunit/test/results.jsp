<%@ include file="header.jspf" %>

  <jsp:useBean id="testContext" type="org.tagunit.TestContextContainer" scope="request"/>

  <table width="100%" border="0">
    <tr>
      <td>
        <h2>Test results</h2>
      </td>
      <td align="right">
        <%@ include file="links.jspf" %>
      </td>
    </tr>

  </table>

  <p>
  <center>
  <%@ include file="stats.jspf" %>
  </center>
  </p>

  <table width="100%" border="0">
    <tagunit:testResults id="result">

      <tagunit:showLevel level="1">
        <tr><td colspan="2">&nbsp;</td></tr>
      </tagunit:showLevel>

      <tr>
        <td>
          <img src="images/spacer.gif" width="<%= level.intValue()*16 %>" height="1">
          <tagunit:hasChildren><b></tagunit:hasChildren>
          <%
            if (result instanceof org.tagunit.TagTestContext) {
              org.tagunit.TagTestContext ttc = (org.tagunit.TagTestContext)result;
              out.print("<a name=\"" + ttc.getTagInfo().getName() + "\">");
              out.print("<a href=\"servlet/ViewResults?&tag=" + ttc.getTagInfo().getName() + "#" + ttc.getTagInfo().getName() + "\">");
            }
          %>
          <jsp:getProperty name="result" property="name"/>
          <%
            if (result instanceof org.tagunit.TagTestContext) {
              org.tagunit.TagTestContext ttc = (org.tagunit.TagTestContext)result;
              out.print("</a>");
            }
          %>
          <tagunit:hasChildren></b></tagunit:hasChildren>
        </td>
        <td>
          <tagunit:hasChildren><b></tagunit:hasChildren>
          <tagunit:pass><span class="pass">Pass</span></tagunit:pass>
          <tagunit:warning><span class="warning">Warning</span></tagunit:warning>
          <tagunit:failure><span class="fail">Fail</span></tagunit:failure>
          <tagunit:error><span class="error">Error</span></tagunit:error>
          <tagunit:hasChildren></b></tagunit:hasChildren>
      </tr>

      <tagunit:hasMessage>
          <tagunit:warning>
            <tr><td><span class="warning"><img src="images/spacer.gif" width="<%= level.intValue()*24 %>" height="1">Warning : <tagunit:filter><jsp:getProperty name="result" property="message"/></tagunit:filter></span></td><td>&nbsp;</td></tr>
          </tagunit:warning>
          <tagunit:failure>
            <tr><td><span class="fail"><code><img src="images/spacer.gif" width="<%= level.intValue()*24 %>" height="1">Failure : <tagunit:filter><jsp:getProperty name="result" property="message"/></tagunit:filter></code></span></td><td>&nbsp;</td></tr>
          </tagunit:failure>
          <tagunit:error>
            <tr><td><span class="error"><code><img src="images/spacer.gif" width="<%= level.intValue()*24 %>" height="1">Error : <tagunit:filter><jsp:getProperty name="result" property="message"/></tagunit:filter></code></span></td><td>&nbsp;</td></tr>
          </tagunit:error>
      </tagunit:hasMessage>

    </tagunit:testResults>

  </table>

<%@ include file ="footer.jspf" %>
