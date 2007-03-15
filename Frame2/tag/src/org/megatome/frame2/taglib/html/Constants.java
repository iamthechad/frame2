/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
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
   private Constants() {
	   // not public
   }
   /** Input type open tag */
   public static final String INPUT_TYPE      = "<input type="; //$NON-NLS-1$
   /** Close input type tag */
   public static final String INPUT_CLOSE     = "</input>";    //$NON-NLS-1$
   
   /** Textarea open tag */
   public static final String TEXTAREA_OPEN   = "<textarea"; //$NON-NLS-1$
   /** Close textare tag */
   public static final String TEXTAREA_CLOSE  = "</textarea>"; //$NON-NLS-1$

   // base html tags
   /** accesskey attribute */
   public static final String ACCESS_KEY     = "accesskey"; //$NON-NLS-1$
   /** alt attribute */
   public static final String ALT            = "alt"; //$NON-NLS-1$
   /** altKey attribute */
   public static final String ALT_KEY        = "altKey"; //$NON-NLS-1$
   /** dir attribute */
   public static final String DIR            = "dir"; //$NON-NLS-1$
   /** disabled attribute */
   public static final String DISABLED       = "disabled"; //$NON-NLS-1$
   /** indexed attribute */
   public static final String INDEXED        = "indexed"; //$NON-NLS-1$
   /** lang attribute */
   public static final String LANG           = "lang"; //$NON-NLS-1$
   /** maxlength attribute */
   public static final String MAX_LENGTH     = "maxlength"; //$NON-NLS-1$
   /** name attribute */
   public static final String NAME           = "name"; //$NON-NLS-1$
   /** onblur attribute */
   public static final String ON_BLUR        = "onblur"; //$NON-NLS-1$
   /** onchange attribute */
   public static final String ON_CHANGE      = "onchange"; //$NON-NLS-1$
   /** onclick attribute */
   public static final String ON_CLICK       = "onclick"; //$NON-NLS-1$
   /** ondblclick attribute */
   public static final String ON_DBL_CLICK   = "ondblclick"; //$NON-NLS-1$
   /** onfocus attribute */
   public static final String ON_FOCUS       = "onfocus"; //$NON-NLS-1$
   /** onkeydown attribute */
   public static final String ON_KEY_DOWN    = "onkeydown"; //$NON-NLS-1$
   /** onkeypress attribute */
   public static final String ON_KEY_PRESS   = "onkeypress"; //$NON-NLS-1$
   /** onkeyup attribute */
   public static final String ON_KEY_UP      = "onkeyup"; //$NON-NLS-1$
   /** onmousedown attribute */
   public static final String ON_MOUSE_DOWN  = "onmousedown"; //$NON-NLS-1$
   /** onmousemove attribute */
   public static final String ON_MOUSE_MOVE  = "onmousemove"; //$NON-NLS-1$
   /** onmouseout attribute */
   public static final String ON_MOUSE_OUT   = "onmouseout"; //$NON-NLS-1$
   /** onmouseover attribute */
   public static final String ON_MOUSE_OVER  = "onmouseover"; //$NON-NLS-1$
   /** onmouseup attribute */
   public static final String ON_MOUSE_UP    = "onmouseup"; //$NON-NLS-1$
   /** onselect attribute */
   public static final String ON_SELECT      = "onSelect"; //$NON-NLS-1$
   /** property attribute */
   public static final String PROPERTY       = "property"; //$NON-NLS-1$
   /** readonly attribute */
   public static final String READONLY       = "readonly"; //$NON-NLS-1$
   /** size attribute */
   public static final String SIZE           = "size"; //$NON-NLS-1$
   /** style attribute */
   public static final String STYLE          = "style"; //$NON-NLS-1$
   /** styleClass attribute */
   //public static final String STYLE_CLASS    = "styleClass";
   /** styleId attribute */
   public static final String STYLE_ID       = "styleId"; //$NON-NLS-1$
   /** tabindex attribute */
   public static final String TAB_INDEX      = "tabindex"; //$NON-NLS-1$
   /** title attribute */
   public static final String TITLE          = "title"; //$NON-NLS-1$
   /** titleKey attribute */
   public static final String TITLE_KEY      = "titleKey"; //$NON-NLS-1$
   /** value attribute */
   public static final String VALUE          = "value"; //$NON-NLS-1$
   /** Value to be used for a null */
   public static final String NULL_VALUE		= "frame2.null"; //$NON-NLS-1$
   
     // a tags
   public static final String LINK_OPEN = "<a"; //$NON-NLS-1$
   public static final String LINK_CLOSE = "</a>"; //$NON-NLS-1$
   public static final String CHARSET       = "charset";   //$NON-NLS-1$
   public static final String CLASS         = "class";   //$NON-NLS-1$
   public static final String HREF_LANG     = "hreflang";   //$NON-NLS-1$
   public static final String ID            = "id";   //$NON-NLS-1$
   public static final String QUERY_PARAMS  = "queryparams";       //$NON-NLS-1$
   public static final String REL            = "rel";   //$NON-NLS-1$
   public static final String REV            = "rev";   //$NON-NLS-1$
   public static final String SHAPE          = "shape";   //$NON-NLS-1$
   public static final String TYPE           = "type";   //$NON-NLS-1$
   public static final String URN            = "urn";   //$NON-NLS-1$
   
   // base field tags
   public static final String ACCEPT          = "accept"; //$NON-NLS-1$
   public static final String REDISPLAY       = "redisplay"; //$NON-NLS-1$
     
 
   // radio/checkbox tags
   public static final String CHECKED        = "checked"; //$NON-NLS-1$
   public static final String DISPLAY_VALUE  = "displayvalue"; //$NON-NLS-1$
   
   // select tags
   public static final String SELECT_TAG     = "<select"; //$NON-NLS-1$
   public static final String SELECT_CLOSE   = "</select>";  //$NON-NLS-1$
   public static final String SELECT         = "checked"; //$NON-NLS-1$
   public static final String MULTIPLE       = "multiple"; //$NON-NLS-1$
   public static final String SELECT_KEY     = "selectKey";    //$NON-NLS-1$

   // option tags
   public static final String SELECTED       = "selected"; //$NON-NLS-1$
   public static final String OPTION         = "option"; //$NON-NLS-1$
   public static final String OPTION_TAG     = "<option"; //$NON-NLS-1$
   public static final String OPTION_CLOSE   = "</option>";  //$NON-NLS-1$
   public static final String OPTIONS        = "options"; //$NON-NLS-1$
   public static final String LABEL          = "label"; //$NON-NLS-1$
     
   // textarea tag
   public static final String COLS           = "cols"; //$NON-NLS-1$
   public static final String ROWS       	  = "rows"; //$NON-NLS-1$
   
   // html input types
   public static final String CHECKBOX = "checkbox";    //$NON-NLS-1$
   public static final String FILE     = "file";    //$NON-NLS-1$
   public static final String HIDDEN   = "hidden"; //$NON-NLS-1$
   public static final String IMAGE    = "image";  //$NON-NLS-1$
   public static final String PASSWORD = "password"; //$NON-NLS-1$
   public static final String RADIO    = "radio"; //$NON-NLS-1$
   public static final String RESET    = "reset"; //$NON-NLS-1$
   public static final String SUBMIT   = "submit"; //$NON-NLS-1$
   public static final String CANCEL   = "cancel"; //$NON-NLS-1$
   public static final String TEXT     = "text"; //$NON-NLS-1$
   public static final String BUTTON   = "button"; //$NON-NLS-1$

   // img tag
   public static final String IMG_TAG     = "<img"; //$NON-NLS-1$
   public static final String IMG_CLOSE   = "</img>"; //$NON-NLS-1$
   public static final String ALIGN       = "align"; //$NON-NLS-1$
   public static final String BORDER      = "border"; //$NON-NLS-1$
   public static final String DATAFLD     = "datafld"; //$NON-NLS-1$
   public static final String DATASRC     = "datasrc"; //$NON-NLS-1$
   public static final String DYNSRC      = "dynsrc"; //$NON-NLS-1$
   public static final String GALLERYIMG  = "galleryimg"; //$NON-NLS-1$
   public static final String HEIGHT      = "height"; //$NON-NLS-1$
   public static final String HSPACE      = "hspace"; //$NON-NLS-1$
   public static final String ISMAP       = "ismap"; //$NON-NLS-1$
   public static final String LONGDESC    = "longdesc"; //$NON-NLS-1$
   public static final String LOOP        = "loop"; //$NON-NLS-1$
   public static final String LOWSRC      = "lowsrc"; //$NON-NLS-1$
   public static final String SRC         = "src"; //$NON-NLS-1$
   public static final String START       = "start"; //$NON-NLS-1$
   public static final String SUPPRESS    = "suppress"; //$NON-NLS-1$
   public static final String USEMAP      = "usemap"; //$NON-NLS-1$
   public static final String VSPACE      = "vspace"; //$NON-NLS-1$
   public static final String WIDTH       = "width"; //$NON-NLS-1$
 
   // html tag
   public static final String HTML        = "html";    //$NON-NLS-1$
   public static final String HTML_TAG    = "<html";     //$NON-NLS-1$
   public static final String HTML_CLOSE  = "</html>";  //$NON-NLS-1$
   public static final String XHTML_KEY   = "xhtmlKey";  //$NON-NLS-1$
   public static final String XHTML       = "xhtml";  //$NON-NLS-1$

   // head tag
   public static final String HEAD        = "head";    //$NON-NLS-1$
   public static final String HEAD_TAG    = "<head";     //$NON-NLS-1$
   public static final String HEAD_CLOSE  = "</head>";  //$NON-NLS-1$
   public static final String PROFILE     = "profile";  //$NON-NLS-1$
   
   // body tag
   public static final String BODY        = "body";    //$NON-NLS-1$
   public static final String BODY_TAG    = "<body";     //$NON-NLS-1$
   public static final String BODY_CLOSE  = "</body>";  //$NON-NLS-1$
   public static final String ON_LOAD        = "onload";  //$NON-NLS-1$
   public static final String ON_UNLOAD      = "onunload";          //$NON-NLS-1$
   // base tag
   public static final String BASE        = "base";    //$NON-NLS-1$
   public static final String BASE_TAG    = "<base";     //$NON-NLS-1$
   public static final String TARGET      = "target";  //$NON-NLS-1$
   public static final String HREF        = "href";   //$NON-NLS-1$

   // form tag
   public static final String FORM           = "form";    //$NON-NLS-1$
   public static final String FORM_TAG       = "<form";     //$NON-NLS-1$
   public static final String FORM_CLOSE     = "</form>";  //$NON-NLS-1$
   public static final String ACTION         = "action"; //$NON-NLS-1$
   public static final String ENCTYPE        = "enctype"; //$NON-NLS-1$
   public static final String ON_RESET       = "onreset";  //$NON-NLS-1$
   public static final String ON_SUBMIT      = "onsubmit";      //$NON-NLS-1$
   public static final String METHOD         = "method";  //$NON-NLS-1$
   public static final String ACCEPT_CHARSET = "acceptcharset";  //$NON-NLS-1$
      
   // Tag names
   public static final String A 	 = "a"; //$NON-NLS-1$
   public static final String TEXTAREA = "textarea"; //$NON-NLS-1$
   public static final String IMG      = "img"; //$NON-NLS-1$
   public static final String QUERY_PARAM_TAG = "queryparam"; //$NON-NLS-1$
   
   public static final String TRUE     = "true"; //$NON-NLS-1$
   public static final String FALSE    = "false"; //$NON-NLS-1$
   
   public static final String RESET_BTN  = "Reset"; //$NON-NLS-1$
   public static final String CANCEL_BTN = "Cancel"; //$NON-NLS-1$
   public static final String SUBMIT_BTN = "Submit"; //$NON-NLS-1$
   public static final String BLANK_BTN  = ""; //$NON-NLS-1$
/*   
   public static final String XMLNS = " xmlns=\"http://www.w3.org/1999/xhtml\"";
*/
}
