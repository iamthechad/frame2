<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute("alt", "yabba");
   pageContext.setAttribute("align", "alignValue");
   pageContext.setAttribute("border", "borderValue");
   pageContext.setAttribute("height", "heightValue");
   pageContext.setAttribute("hspace", "hspaceValue");
   pageContext.setAttribute("ismap", "ismapValue");
   pageContext.setAttribute("longdesc", "longdescValue");
   pageContext.setAttribute("lowsrc", "lowsrcValue");
   pageContext.setAttribute("src", "srcValue");
   pageContext.setAttribute("suppress", "suppressValue");
   pageContext.setAttribute("usemap", "usemapValue");
   pageContext.setAttribute("vspace", "vspaceValue");
   pageContext.setAttribute("width", "widthValue");

   int x = 7; 
%>
 
<frame2:img alt="${alt}" align="${align}" border="${border}" height="${height}" hspace="${hspace}" ismap="${ismap}" longdesc="${longdesc}" lowsrc="${lowsrc}" src="${src}" suppress="${suppress}" usemap="${usemap}" vspace="${vspace}" width="${width}" onfocus="true"/>
