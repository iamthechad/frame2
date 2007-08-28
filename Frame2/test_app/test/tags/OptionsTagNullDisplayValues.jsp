<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>

<%@ page import="org.megatome.frame2.taglib.html.Constants" %>
<%@ page import="java.util.ArrayList" %>
<%
   ArrayList selList = new ArrayList();
   selList.add("xxx");
   selList.add("xws");
   selList.add("radioValue");
   
   ArrayList list = new ArrayList();
   list.add("one");
   list.add("two");
   list.add("radioValue");
   list.add("me");
   
   pageContext.setAttribute ("values", list);
//   pageContext.setAttribute(Constants.SELECT_KEY, selList);
 
%>

<frame2:options value="${values}"/>

