<%@ taglib uri="/WEB-INF/taglib.tld" prefix="frame2" %>
<% 
   pageContext.setAttribute("alt", "yabba");
   pageContext.setAttribute("align", "alignValue");
   pageContext.setAttribute("border", "borderValue");
   pageContext.setAttribute("height", "heightValue");
   pageContext.setAttribute("hspace", "hspaceValue");
   pageContext.setAttribute("ismap", "ismapValue");
   pageContext.setAttribute("lowsrc", "lowsrcValue");
   pageContext.setAttribute("src", "srcValue");
   pageContext.setAttribute("usemap", "usemapValue");
   pageContext.setAttribute("vspace", "vspaceValue");
   pageContext.setAttribute("width", "widthValue");

   int x = 7; 
%>
 
<frame2:image alt="${alt}" align="${align}" border="${border}" height="${height}" hspace="${hspace}" ismap="${ismap}" lowsrc="${lowsrc}" src="${src}" usemap="${usemap}" vspace="${vspace}" width="${width}" onfocus="true"/>
