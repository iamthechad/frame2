/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by
 *        Megatome Technologies."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Frame2 Project", and "Frame2", 
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact iamthechad@sourceforge.net.
 *
 * 5. Products derived from this software may not be called "Frame2"
 *    nor may "Frame2" appear in their names without prior written
 *    permission of Megatome Technologies.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL MEGATOME TECHNOLOGIES OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.megatome.frame2.taglib.html;


/**
 * Manifest constants for this package.
 */

public final class Constants {
   private Constants() {}
   /** Input type open tag */
   public static final String INPUT_TYPE      = "<input type=";
   /** Close input type tag */
   public static final String INPUT_CLOSE     = "</input>";   
   
   /** Textarea open tag */
   public static final String TEXTAREA_OPEN   = "<textarea";
   /** Close textare tag */
   public static final String TEXTAREA_CLOSE  = "</textarea>";

   // base html tags
   /** accesskey attribute */
   public static final String ACCESS_KEY     = "accesskey";
   /** alt attribute */
   public static final String ALT            = "alt";
   /** altKey attribute */
   public static final String ALT_KEY        = "altKey";
   /** dir attribute */
   public static final String DIR            = "dir";
   /** disabled attribute */
   public static final String DISABLED       = "disabled";
   /** indexed attribute */
   public static final String INDEXED        = "indexed";
   /** lang attribute */
   public static final String LANG           = "lang";
   /** maxlength attribute */
   public static final String MAX_LENGTH     = "maxlength";
   /** name attribute */
   public static final String NAME           = "name";
   /** onblur attribute */
   public static final String ON_BLUR        = "onblur";
   /** onchange attribute */
   public static final String ON_CHANGE      = "onchange";
   /** onclick attribute */
   public static final String ON_CLICK       = "onclick";
   /** ondblclick attribute */
   public static final String ON_DBL_CLICK   = "ondblclick";
   /** onfocus attribute */
   public static final String ON_FOCUS       = "onfocus";
   /** onkeydown attribute */
   public static final String ON_KEY_DOWN    = "onkeydown";
   /** onkeypress attribute */
   public static final String ON_KEY_PRESS   = "onkeypress";
   /** onkeyup attribute */
   public static final String ON_KEY_UP      = "onkeyup";
   /** onmousedown attribute */
   public static final String ON_MOUSE_DOWN  = "onmousedown";
   /** onmousemove attribute */
   public static final String ON_MOUSE_MOVE  = "onmousemove";
   /** onmouseout attribute */
   public static final String ON_MOUSE_OUT   = "onmouseout";
   /** onmouseover attribute */
   public static final String ON_MOUSE_OVER  = "onmouseover";
   /** onmouseup attribute */
   public static final String ON_MOUSE_UP    = "onmouseup";
   /** onselect attribute */
   public static final String ON_SELECT      = "onSelect";
   /** property attribute */
   public static final String PROPERTY       = "property";
   /** readonly attribute */
   public static final String READONLY       = "readonly";
   /** size attribute */
   public static final String SIZE           = "size";
   /** style attribute */
   public static final String STYLE          = "style";
   /** styleClass attribute */
   //public static final String STYLE_CLASS    = "styleClass";
   /** styleId attribute */
   public static final String STYLE_ID       = "styleId";
   /** tabindex attribute */
   public static final String TAB_INDEX      = "tabindex";
   /** title attribute */
   public static final String TITLE          = "title";
   /** titleKey attribute */
   public static final String TITLE_KEY      = "titleKey";
   /** value attribute */
   public static final String VALUE          = "value";
   /** Value to be used for a null */
   public static final String NULL_VALUE		= "frame2.null";
   
     // a tags
   public static final String LINK_OPEN = "<a";
   public static final String LINK_CLOSE = "</a>";
   public static final String CHARSET       = "charset";  
   public static final String CLASS         = "class";  
   public static final String HREF_LANG     = "hreflang";  
   public static final String ID            = "id";  
   public static final String QUERY_PARAMS  = "queryparams";      
   public static final String REL            = "rel";  
   public static final String REV            = "rev";  
   public static final String SHAPE          = "shape";  
   public static final String TYPE           = "type";  
   public static final String URN            = "urn";  
   
   // base field tags
   public static final String ACCEPT          = "accept";
   public static final String REDISPLAY       = "redisplay";
     
 
   // radio/checkbox tags
   public static final String CHECKED        = "checked";
   public static final String DISPLAY_VALUE  = "displayvalue";
   
   // select tags
   public static final String SELECT_TAG     = "<select";
   public static final String SELECT_CLOSE   = "</select>"; 
   public static final String SELECT         = "checked";
   public static final String MULTIPLE       = "multiple";
   public static final String SELECT_KEY     = "selectKey";   

   // option tags
   public static final String SELECTED       = "selected";
   public static final String OPTION         = "option";
   public static final String OPTION_TAG     = "<option";
   public static final String OPTION_CLOSE   = "</option>"; 
   public static final String OPTIONS        = "options";
   public static final String LABEL          = "label";
     
   // textarea tag
   public static final String COLS           = "cols";
   public static final String ROWS       	  = "rows";
   
   // html input types
   public static final String CHECKBOX = "checkbox";   
   public static final String FILE     = "file";   
   public static final String HIDDEN   = "hidden";
   public static final String IMAGE    = "image"; 
   public static final String PASSWORD = "password";
   public static final String RADIO    = "radio";
   public static final String RESET    = "reset";
   public static final String SUBMIT   = "submit";
   public static final String CANCEL   = "cancel";
   public static final String TEXT     = "text";
   public static final String BUTTON   = "button";

   // img tag
   public static final String IMG_TAG     = "<img";
   public static final String IMG_CLOSE   = "</img>";
   public static final String ALIGN       = "align";
   public static final String BORDER      = "border";
   public static final String DATAFLD     = "datafld";
   public static final String DATASRC     = "datasrc";
   public static final String DYNSRC      = "dynsrc";
   public static final String GALLERYIMG  = "galleryimg";
   public static final String HEIGHT      = "height";
   public static final String HSPACE      = "hspace";
   public static final String ISMAP       = "ismap";
   public static final String LONGDESC    = "longdesc";
   public static final String LOOP        = "loop";
   public static final String LOWSRC      = "lowsrc";
   public static final String SRC         = "src";
   public static final String START       = "start";
   public static final String SUPPRESS    = "suppress";
   public static final String USEMAP      = "usemap";
   public static final String VSPACE      = "vspace";
   public static final String WIDTH       = "width";
 
   // html tag
   public static final String HTML        = "html";   
   public static final String HTML_TAG    = "<html";    
   public static final String HTML_CLOSE  = "</html>"; 
   public static final String XHTML_KEY   = "xhtmlKey"; 
   public static final String XHTML       = "xhtml"; 

   // head tag
   public static final String HEAD        = "head";   
   public static final String HEAD_TAG    = "<head";    
   public static final String HEAD_CLOSE  = "</head>"; 
   public static final String PROFILE     = "profile"; 
   
   // body tag
   public static final String BODY        = "body";   
   public static final String BODY_TAG    = "<body";    
   public static final String BODY_CLOSE  = "</body>"; 
   public static final String ON_LOAD        = "onload"; 
   public static final String ON_UNLOAD      = "onunload";         
   // base tag
   public static final String BASE        = "base";   
   public static final String BASE_TAG    = "<base";    
   public static final String TARGET      = "target"; 
   public static final String HREF        = "href";  

   // form tag
   public static final String FORM           = "form";   
   public static final String FORM_TAG       = "<form";    
   public static final String FORM_CLOSE     = "</form>"; 
   public static final String ACTION         = "action";
   public static final String ENCTYPE        = "enctype";
   public static final String ON_RESET       = "onreset"; 
   public static final String ON_SUBMIT      = "onsubmit";     
   public static final String METHOD         = "method"; 
   public static final String ACCEPT_CHARSET = "acceptcharset"; 
      
   // Tag names
   public static final String A 	 = "a";
   public static final String TEXTAREA = "textarea";
   public static final String IMG      = "img";
   public static final String QUERY_PARAM_TAG = "queryparam";
   
   public static final String TRUE     = "true";
   public static final String FALSE    = "false";
   
   public static final String RESET_BTN  = "Reset";
   public static final String CANCEL_BTN = "Cancel";
   public static final String SUBMIT_BTN = "Submit";
   public static final String BLANK_BTN  = "";
/*   
   public static final String XMLNS = " xmlns=\"http://www.w3.org/1999/xhtml\"";
*/
}
