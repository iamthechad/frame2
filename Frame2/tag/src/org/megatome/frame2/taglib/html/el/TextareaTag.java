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

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;

public class TextareaTag extends BaseHtmlTag {

	String _value;

	public void setCols(String cols) {
		setAttr(Constants.COLS, cols);
	}

	public void setRows(String rows) {
		setAttr(Constants.ROWS, rows);
	}

	// override this method for specific  
	// output needs in start tag
   protected StringBuffer buildStartTag() throws JspException {
      StringBuffer results = new StringBuffer();
      results.append(Constants.TEXTAREA_OPEN);
      results.append(genTagAttrs());
      return results;
   }

	// override ths if you want to handle an attribute
	protected void specialAttrHandler() throws JspException {
		handleValueAttr();
	}

	private void handleValueAttr() throws JspException {
		_value = getAttr(Constants.VALUE);
		if (_value != null) {
			try {
				_value = evalStringAttr(Constants.VALUE, _value);
			} catch (Exception e) {
				throw new JspException(
					" Evaluation attribute failed " + e.getMessage(),
					e);
			}
			// now remove this attr from map
			// so not output in genHTML()
			removeAttr(Constants.VALUE);
		}

	}

	protected void setTagName() {
		_tagName = Constants.TEXTAREA;
	}
   
   protected void setType() {
      _type = Constants.TEXTAREA;
   }

   /**
    * Returns if tag has body or not
    * @return boolean
    */
   public boolean evalTagBody() {
      return true;
   }

   /**
    * Appends _value String
    * @param StringBuffer
    */
   public void getBody(StringBuffer buffer) {
      // actual body content overrides
      // any display value.
	  if (bodyContent != null && 
		  !bodyContent.getString().equals("")) {
         buffer.append(bodyContent.getString());
      } 
      else if (_value != null) {
         buffer.append(_value); 
      }    
   }
      
   /**
    * Appends end Element Close
    * @param StringBuffer
    */
   public void getEndElementClose(StringBuffer buffer) {      
      buffer.append(Constants.TEXTAREA_CLOSE);
   }   

   protected void clear() {
      super.clear();
      _value = null;
   }

   public void release() {
      super.release();
      clear();
   }

}
