
<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<%@ page import="java.util.ArrayList" %>
<% 
   pageContext.setAttribute ("name", "yabba");
 //  pageContext.setAttribute ("checked", "radioValue");
   ArrayList list = new ArrayList();
   list.add("one");
   list.add("two");
   list.add("radioValue");
   list.add("me");
   pageContext.setAttribute ("checked", list);
%>
 
<frame2:checkbox name="${name}" value="radioValue" onfocus="true" checked='${checked}'>label</frame2:checkbox>