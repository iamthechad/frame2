<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>

<%@ page import="java.util.ArrayList" %>
<%
   ArrayList vals = new ArrayList();
   vals.add(new Integer(1));
   vals.add(new Integer(2));
   vals.add(new Integer(3));
   
   ArrayList list = new ArrayList();
   list.add("one");
   list.add("two");
   list.add("three");
   
   pageContext.setAttribute ("values", vals);
   pageContext.setAttribute ("dispvalues", list);
 
%>

<frame2:options value="${values}" displayvalue="${dispvalues}" />

