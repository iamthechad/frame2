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

   ArrayList selectedList = new ArrayList();
   selectedList.add("2");
   selectedList.add("3");
      
   pageContext.setAttribute ("values", vals);
   pageContext.setAttribute ("dispvalues", list);
   pageContext.setAttribute ("selValue", selectedList);

%>
<frame2:select name="select" selected="${selValue}">
<frame2:options value="${values}" displayvalue="${dispvalues}"/>
</frame2:select>
