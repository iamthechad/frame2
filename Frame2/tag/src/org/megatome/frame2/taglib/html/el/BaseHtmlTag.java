/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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
package org.megatome.frame2.taglib.html.el;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;
import org.megatome.frame2.tagsupport.TagConstants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;
import org.megatome.frame2.util.HTMLEncoder;

public abstract class BaseHtmlTag extends BaseFrame2Tag {
   protected String _type;

   public BaseHtmlTag() {
      super();
      setType();
   }

   // override to set specific _type
   protected abstract void setType();

   public String getType() {
      return _type;
   }

   /**
   * Handle any Attribute which needs to be processed other than
   * by the default behavior.
   */
   public void setAccesskey(String accesskey) {
      setAttr(Constants.ACCESS_KEY, accesskey);
   }
   public void setAlt(String alt) {
      setAttr(Constants.ALT, alt);
   }
   public void setAltKey(String altKey) {
      setAttr(Constants.ALT_KEY, altKey);
   }
   public void setDir(String dir) {
      setAttr(Constants.DIR, dir);
   }
   public void setDisabled(String disabled) {
      setAttr(Constants.DISABLED, disabled);
   }
   public void setLang(String lang) {
      setAttr(Constants.LANG, lang);
   }
   public void setIndexed(String indexed) {
      setAttr(Constants.INDEXED, indexed);
   }
   public void setMaxlength(String maxlength) {
      setAttr(Constants.MAX_LENGTH, maxlength);
   }
   public void setName(String name) {
      setAttr(Constants.NAME, name);
   }
   public void setOnblur(String onblur) {
      setAttr(Constants.ON_BLUR, onblur);
   }
   public void setOnchange(String onchange) {
      setAttr(Constants.ON_CHANGE, onchange);
   }
   public void setOnclick(String onclick) {
      setAttr(Constants.ON_CLICK, onclick);
   }
   public void setOndblclick(String ondblclick) {
      setAttr(Constants.ON_DBL_CLICK, ondblclick);
   }
   public void setOnfocus(String onfocus) {
      setAttr(Constants.ON_FOCUS, onfocus);
   }
   public void setOnkeydown(String onkeydown) {
      setAttr(Constants.ON_KEY_DOWN, onkeydown);
   }
   public void setOnkeypress(String onkeypress) {
      setAttr(Constants.ON_KEY_PRESS, onkeypress);
   }
   public void setOnkeyup(String onkeyup) {
      setAttr(Constants.ON_KEY_UP, onkeyup);
   }
   public void setOnmousedown(String onmousedown) {
      setAttr(Constants.ON_MOUSE_DOWN, onmousedown);
   }
   public void setOnmousemove(String onmousemove) {
      setAttr(Constants.ON_MOUSE_MOVE, onmousemove);
   }
   public void setOnmouseout(String onmouseout) {
      setAttr(Constants.ON_MOUSE_OUT, onmouseout);
   }
   public void setOnmouseover(String onmouseover) {
      setAttr(Constants.ON_MOUSE_OVER, onmouseover);
   }
   public void setOnmouseup(String onmouseup) {
      setAttr(Constants.ON_MOUSE_UP, onmouseup);
   }
   public void setReadonly(String readonly) {
      setAttr(Constants.READONLY, readonly);
   }
   public void setStyle(String style) {
      setAttr(Constants.STYLE, style);
   }
   public void setSize(String size) {
      setAttr(Constants.SIZE, size);
   }
   public void setStyleClass(String styleClass) {
      setAttr(Constants.CLASS, styleClass);
   }
   /*
   public void setClass(String styleClass) {
      setAttr(Constants.STYLE_CLASS, styleClass);
   }
   */
   public void setStyleId(String styleId) {
      setAttr(Constants.STYLE_ID, styleId);
   }
   public void setTabindex(String tabindex) {
      setAttr(Constants.TAB_INDEX, tabindex);
   }
   public void setTitle(String title) {
      setAttr(Constants.TITLE, title);
   }
   public void setTitleKey(String titleKey) {
      setAttr(Constants.TITLE_KEY, titleKey);
   }
   public void setValue(String value) {
      setAttr(Constants.VALUE, value);
   }
   public void setOnselect(String onSelect) {
      setAttr(Constants.ON_SELECT, onSelect);
   }
   public String getOnselect() {
      return getAttr(Constants.ON_SELECT);
   }

   /**
    * 
    * @return true if the tag is nested within an html tag with xhtml set to true, false
    * otherwise.
    */
   protected boolean isXhtml() {
      return (
         pageContext.getAttribute(Constants.XHTML_KEY) != null ? true : false);
   }

   /**
    * Returns the closing brace for an input element depending on xhtml status.  The tag
    * must be nested within an %lt;html:html&gt; tag that has xhtml set to true.
    * @return String - &gt; if xhtml is false, /&gt; if xhtml is true
    * @since Struts 1.1
    */
   protected String getElementClose() {
      return (isXhtml() ? TagConstants.RT_ANGLE_CLOSE : TagConstants.RT_ANGLE);
   }

   public void getStartElementClose(StringBuffer buffer) {
      if (isXhtml() && !_tagHasBody) {
         buffer.append(TagConstants.RT_ANGLE_CLOSE);
      } else {
         buffer.append(TagConstants.RT_ANGLE);
      }
   }

   //	basic lifecycle for start
   public int doStartTag() throws JspException {
      super.doStartTag();
      specialAttrHandler();

      // Create an appropriate "input" element based on our parameters
      StringBuffer results = buildStartTag();
      HTMLHelpers.writeOutput(pageContext, results.toString());

      // Continue processing this page
      return (EVAL_BODY_BUFFERED);
   }

   // basic lifecycle for end
   public int doEndTag() throws JspException {
      super.doEndTag();
      specialEndAttrHandler();
      StringBuffer results = buildEndTag();
      HTMLHelpers.writeOutput(pageContext, results.toString());
      clear();
      return (EVAL_PAGE);
   }

   //	basic lifecycle for end
   public StringBuffer buildEndTag() throws JspException {
      _tagHasBody = evalTagBody();
      StringBuffer results = new StringBuffer();
      getStartElementClose(results);
      if (_tagHasBody) {
         getBody(results);
         getEndElementClose(results);
      }
      return results;
   }

   //	implement this method for specific  
   // output needs in start tag
   protected abstract StringBuffer buildStartTag() throws JspException;

   //	override ths if you want to handle an attribute
   protected void specialAttrHandler() throws JspException {
   }
   
   // override this if you want to perform any handling in
   // doEndTag()
   protected void specialEndAttrHandler() throws JspException {
   }

   // Default implementation
   public boolean evalTagBody() {
      return false;
   }

   // Default implementation
   public void getBody(StringBuffer buffer) {
   }

   // Default implementation
   public void getEndElementClose(StringBuffer buffer) {
   }

   protected String handleAttr(String key, String value) throws JspException {
      String result = value;

      if (value != null && !value.trim().equals("")) {
         try {
            result = evalStringAttr(Constants.ALIGN, value);
            removeAttr(key);
         } catch (Exception e) {
            throw new JspException(
               "Unable to evaluate " + key + " attribute",
               e);
         }
      }

      return result;
   }

   /**
   	 * Processes all the attributes using the expression language.  
   	 * If any of the expression language evaluations fail, a JspException is thrown.
   	 *
   	 * @exception JspException 
   	 */
   //NIT decide if all output encoded, otherwise
   // set new attr for encode ="true" to encode or not.
   protected String genTagAttrs() throws JspException {
      StringBuffer tagAttrs = new StringBuffer();
      Iterator it = _attrs.keySet().iterator();
      while (it.hasNext()) {
         String attrName = (String) it.next();

         // Some of the attributes have unique behaviors.
         String attrValue = null;
         String attrExprValue = (String) _attrs.get(attrName);
         if (!attrExprValue.equals(Constants.NULL_VALUE)) {
	         try {
	            attrValue = evalStringAttr(attrName, attrExprValue);
	         } catch (Exception e) {
	            throw new JspException(
	               " Evaluation attribute failed " + e.getMessage(),
	               e);
	         }
	         String encodeValue = HTMLEncoder.encode(attrValue);
	
	         tagAttrs.append(HTMLHelpers.buildHtmlAttr(attrName, encodeValue));
         } else {
            tagAttrs.append(HTMLHelpers.buildHtmlAttr(attrName));
         }
      }
      return tagAttrs.toString();
   }

}
