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

   Integer[] selectedList = new Integer[2];
   selectedList[0] = new Integer(2);
   selectedList[1] = new Integer(3);
      
   pageContext.setAttribute ("values", vals);
   pageContext.setAttribute ("dispvalues", list);
   pageContext.setAttribute ("selValue", selectedList);

%>

<frame2:options value="${values}" displayvalue="${dispvalues}" selected="${selValue}"/>

