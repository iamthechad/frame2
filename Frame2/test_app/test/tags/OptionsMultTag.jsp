<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<%@ page import="java.util.ArrayList" %>
<%
   ArrayList list = new ArrayList();
   list.add("one");
   list.add("two");
   list.add("radioValue");
   list.add("me");
   
   pageContext.setAttribute ("values", list);
%>
<frame2:select name="sel1">
<frame2:options value="${values}" displayvalue="${values}" />
</frame2:select>
<frame2:select name="sel2">
<frame2:options value="${values2}" displayvalue="${values2}"/>
</frame2:select>

