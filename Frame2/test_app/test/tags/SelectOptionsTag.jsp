<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<%@ page import="java.util.ArrayList" %>
<%
   ArrayList list = new ArrayList();
   list.add("one");
   list.add("two");
   
   pageContext.setAttribute ("values", list);
 
%>
<frame2:select name="select">
<frame2:options value="${values}" displayvalue="${values}" />
</frame2:select>
