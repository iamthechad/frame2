<% String[] paramValue = (String[])request.getAttribute("param"); if (paramValue != null) { %>
<% for (int i = 0; i < paramValue.length; i++) { %><%= paramValue[i] %><br><% } } %>
<% paramValue = (String[])request.getAttribute("dude"); if (paramValue != null) { %>
<% for (int i = 0; i < paramValue.length; i++) { %><%= paramValue[i] %><br><% } } %>