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

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;

/**
 * RadioTag.java
 */
public class RadioTag extends BaseInputTag {
   protected String _checked;
   protected String _displayValue;
   
   public void setType() {
      _type = Constants.RADIO;
   }  
   // check needs special hndler for attribute
   public void setChecked(String checked) {
      _checked = checked;
   }
   
   public void setDisplayvalue(String value) {
      setAttr(Constants.DISPLAY_VALUE, value);
   }

   // override ths if you want to handle an attribute
   protected void specialAttrHandler() throws JspException {
      handleCheckedAttr();
      handleDisplayValueAtrr();
   }  
   
   private void handleDisplayValueAtrr() throws JspException{
      String dispExpr = getAttr(Constants.DISPLAY_VALUE);
       if (dispExpr == null || dispExpr == "") {
          // Evaluate the remainder of this page
          return;
       }
                 
       try {
          _displayValue = (String) evalStringAttr(Constants.DISPLAY_VALUE, dispExpr);
       } catch (Exception e) {
          throw new JspException(
             " Evaluation attribute failed " + e.getMessage(), e);
       }     
       // now remove this attr from map
       // so not output in genHTML()
       removeAttr(Constants.DISPLAY_VALUE);      
   }
   
   // nit get this into helper.
   protected void handleCheckedAttr() throws JspException{
      // now get value   
      String valueExpr = getAttr(Constants.VALUE);
      if (valueExpr == null || valueExpr == "" ||
          _checked == null  || _checked == "") {
        return;
      }

      String valueval = null; // init diff of checkval
      try {
         valueval = (String) evalStringAttr(Constants.VALUE, valueExpr);
      } catch (Exception e) {
         throw new JspException(
            " Evaluation attribute failed " + e.getMessage(), e);
      }    
              
      // see if this is a collection
      Collection collection = null;
      try {
         collection = evalCollectionAttr(Constants.CHECKED, _checked);
      } catch (Exception e) {
        // ignore, try array next;
      } 
        
      Object[] array = null;
      if (collection == null) {
         try {
            array = evalArrayAttr(Constants.CHECKED, _checked);
         }  catch (Exception e) {
            // ignore, try string next;
         }   
      }
      
      // init "" for cmp with valueval later
      String checkval = "";     
      if (collection == null && array == null) {
         try {
            checkval = evalStringAttr(Constants.CHECKED, _checked);
         } catch (Exception e) {
            throw new JspException(
               " Evaluation attribute failed " + e.getMessage(), e);
         }   
      }
      
      if (collection != null) {
         Iterator iter = collection.iterator();
         while (iter.hasNext()) {
            // NIT make work for all primatives
            String val = (String)iter.next();
            if (val.equals(valueval)) {
               setAttr(Constants.CHECKED,Constants.TRUE);
               break;
            }
         }            
      } 
      else if (array != null) {
         int len = array.length;
         for (int i = 0; i< len; i++) {
            // NIT make work for all primatives
            String val = (String)array[i];
            if (val.equals(valueval)) {
               setAttr(Constants.CHECKED,Constants.TRUE);
               break;
            }
         }            
      } else {           
         if (checkval.equals(valueval)) {
            setAttr(Constants.CHECKED,Constants.TRUE);
         } 
      }
   }

	/**
	 * Returns if tag has body or not
	 * @return boolean
	 */
	public boolean evalTagBody() {
		if (_displayValue != null ||
			(bodyContent != null && 
			 !bodyContent.getString().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Appends _displayValue String
	 * @param StringBuffer
	 */
	public void getBody(StringBuffer buffer) {
		// actual body content overrides
		// any display value.
		if (bodyContent != null && 
		    !bodyContent.getString().equals("")) {
			buffer.append(bodyContent.getString());
		} else if (_displayValue != null) {
			buffer.append(_displayValue);
		}
	}
      
   /**
    * Appends end Element Close
    * @param StringBuffer
    */
   public void getEndElementClose(StringBuffer buffer) {      
      buffer.append(Constants.INPUT_CLOSE);     
   }   
   
   public void release() {
      super.release();
      clear();
   }

   protected void clear() {
      super.clear();
      _checked = null;
      _displayValue = null;
   }

}


