/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2006 Megatome Technologies.  All rights
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

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.TagConstants;

/**
 * 
 */
public class HtmlTag extends BaseHtmlTag {
   
   protected void setType() {
      _type = Constants.HTML;
   }   
   
   public void setTagName() {
      tagName = Constants.HTML_TAG;
   }
   
   protected String genXHtmlTagAttr() {
      return ((pageContext.getAttribute(Constants.XHTML_KEY) != null) ? TagConstants.XMLNS : "");
   }
   
   protected StringBuffer buildStartTag() throws JspException {
      StringBuffer buffer = new StringBuffer();
      buffer.append(getTagName());
      buffer.append(genXHtmlTagAttr());
      buffer.append(genTagAttrs());
      return buffer;   
   }
 
   /**
    * Returns if tag has body or not
    * @return boolean
    */
   public boolean evalTagBody() {
      return true;
   }
   
   /**
    * Appends bodyContent String
    * @param StringBuffer
    */
   public void getBody(StringBuffer buffer) {      
      if (bodyContent != null) {
         buffer.append(bodyContent.getString());
      }
   }
      
   /**
    * Appends end Element Close
    * @param StringBuffer
    */
   public void getEndElementClose(StringBuffer buffer) {      
      pageContext.removeAttribute(Constants.XHTML_KEY);
      buffer.append(Constants.HTML_CLOSE);
   }   

   /**
    * Sets the selected.
    * @param selected The selected to set
    */
   public void setXhtml(String xhtml) throws JspException{

      try {
         if (Constants.TRUE.equals(evalStringAttr(Constants.XHTML, xhtml))) {
            pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
         }
      } catch (Exception e) {
         throw new JspException(
            " Evaluation of xhtml attribute failed " + e.getMessage(),
            e);
      }
   }

}

