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
package org.megatome.frame2.taglib.html.el;

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;

/**
 * 
 */
public class SelectTag extends BaseSelectTag {
   
   private String _bodyContent = null;
   protected String _selected  = "";   
   protected String _multiple;
   
   protected void setType() {
      _type = Constants.SELECT;
   }   
   
   public void setTagName() {
      _tagName = Constants.SELECT_TAG;
   }
   
   protected void specialAttrHandler() throws JspException {
      // "selected" contains expr to compare options
      // save expression for options to use for compare.
      pageContext.setAttribute(Constants.SELECT_KEY,_selected);
   }
        
   // save body content, otherthan option tags
   public int doAfterBody() throws JspException {
      if (bodyContent != null) {
          String content = bodyContent.getString();
          if (content != null) {
             _bodyContent = content.trim();
          }
      }
      return (SKIP_BODY);
   }

    
   /**
    * Returns the selected.
    * @return String
    */
   public String getSelected() {
      return _selected;
   }

   /**
    * Sets the selected.
    * @param selected The selected to set
    */
   public void setSelected(String selected) {
      _selected = selected;
   }
   /**
    * Sets the multiple.
    * @param multiple The multiple to set
    */
   public void setMultiple(String multiple) {
       setAttr(Constants.MULTIPLE, multiple);
   }

   /**
    * Returns true
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
      if (_bodyContent != null) {
         buffer.append(_bodyContent);
      }
   }
      
   /**
    * Appends end Element Close
    * @param StringBuffer
    */
   public void getEndElementClose(StringBuffer buffer) {      
      pageContext.removeAttribute(Constants.SELECT_KEY);
      buffer.append(Constants.SELECT_CLOSE);
   }   
   
   protected void clear() {
      super.clear();
      _bodyContent = null;
      _selected  = "";   
      _multiple = null;
   }

   public void release() {
      super.release();
      clear();
   }

}

