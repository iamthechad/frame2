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

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.TagConstants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;

/**
 * OptionsTag.java
 */
public class OptionsTag extends BaseOptionTag {
   private Collection _selectedCollection;
   private Object[] _selectedArray;
   private String  _selectedString;
   private Collection _displayValueCollection;
   private Object[] _displayValueArray;
   private Collection _valueCollection;
   private Object[] _valueArray;

   protected void setType() {
      _type = Constants.OPTIONS;
   }
   
   protected StringBuffer buildStartTag() {     
      StringBuffer buffer = new StringBuffer();

      Object[] value = null;
      if (_valueCollection != null) {
         value = _valueCollection.toArray();
      }
      else if (_valueArray != null) {
         value = _valueArray;
      }
 
      Object[] displayValue = null;
      int displayLen = -1;
      if (_displayValueCollection != null) {
         displayValue = _displayValueCollection.toArray();
         displayLen = displayValue.length;
      }
      else if (_displayValueArray != null) {
         displayValue = _displayValueArray;
         displayLen = displayValue.length;
      }
           
      int len = value.length;     
      for (int i = 0; i<len; i++) {
         String dispValue = null;
         if (displayLen != -1 && i < displayLen) {
            //dispValue = (String)displayValue[i];
            dispValue = String.valueOf(displayValue[i]);
         }
         //addOptionTag(buffer,(String)value[i], dispValue);
         addOptionTag(buffer,String.valueOf(value[i]), dispValue);
      }     

      return buffer;   
   }
   
   // should be coolection or object[]
   protected void handleValueAttr() throws JspException {
      String valueExpr = getAttr(Constants.VALUE);
      // see if this is a collection
      try {
         _valueCollection = evalCollectionAttr(Constants.VALUE, valueExpr);
      } catch (Exception e) {
        // ignore, try array next;
      } 
        
      if (_valueCollection == null) {
         try {
            _valueArray = evalArrayAttr(Constants.VALUE, valueExpr);
         }  catch (Exception e) {
             throw new JspException(
               " Evaluation attribute failed, Collection or Object[] expected " + e.getMessage(), e);
         }   
      }
   }
   // override ths if you want to handle an attribute
   protected void specialAttrHandler() throws JspException {  
      super.specialAttrHandler();
      handleValueAttr();
   }  
   
   // for OPTIONS tag, expect displayvalue to be collection or [].
   // as iterate over selected(From SELECT_KEY), get
   // display value.
   protected void handleDisplayValueAttr() throws JspException{
       if (_displayExpr == null || _displayExpr == "") {
          // Evaluate the remainder of this page
          return;
       }
                 
      try {
         _displayValueCollection = evalCollectionAttr(Constants.DISPLAY_VALUE, _displayExpr);
      } catch (Exception e) {
        // ignore, try array next;
      } 
        
      if (_displayValueCollection == null) {
         try {
            _displayValueArray = evalArrayAttr(Constants.DISPLAY_VALUE, _displayExpr);
         }  catch (Exception e) {
             throw new JspException(
               " Evaluation attribute failed, Collection or Object[] expected " + e.getMessage(), e);
         }   
      }      
   } 
     
   protected void handleSelectedAttr() throws JspException{
     // get thr select attr 
      String selectExpr = 
             (String)pageContext.getAttribute(Constants.SELECT_KEY);
      //String selectExpr = _selected;
      
      if ((selectExpr == null) || (selectExpr == "")) {
         selectExpr = _selected;
      }
             
      // now get value   
      String valueExpr = getAttr(Constants.VALUE);
      if (valueExpr == null || valueExpr == "" ||
          selectExpr == null  || selectExpr == "") {
        return;
      }

      // see if this is a collection
      try {
         _selectedCollection = evalCollectionAttr(Constants.SELECTED, selectExpr);
      } catch (Exception e) {
        // ignore, try array next;
      } 
        
      if (_selectedCollection == null) {
         try {
            _selectedArray = evalArrayAttr(Constants.SELECTED, selectExpr);
         }  catch (Exception e) {
            // ignore, try string next;
         }   
      }
      
      // init "" for cmp with valueval later
      //String checkval = "";     
      if (_selectedCollection == null && _selectedArray == null) {
         try {
            //checkval = evalStringAttr(Constants.SELECTED, selectExpr);
            _selectedString = evalStringAttr(Constants.SELECTED, selectExpr);
         } catch (Exception e) {
            throw new JspException(
               " Evaluation attribute failed " + e.getMessage(), e);
         }   
      }
   }
   
   protected boolean isSelected(String valueval) {
      boolean selected = false;
      if (_selectedCollection != null) {
         Iterator iter = _selectedCollection.iterator();
         while (iter.hasNext()) {
            // NIT make work for all primatives
            //String val = (String)iter.next();
            String val = String.valueOf(iter.next());
            if (val.equals(valueval)) {
               selected = true;
               break;
            }
         }            
      } 
      else if (_selectedArray != null) {
         int len = _selectedArray.length;
         for (int i = 0; i< len; i++) {
            // NIT make work for all primatives
            //String val = (String)_selectedArray[i];
            String val = String.valueOf(_selectedArray[i]);
            if (val.equals(valueval)) {
               selected = true;
               break;
            }
         }            
      } else {           
         if (_selectedString != null) {
            selected = _selectedString.equals(valueval);
         } 
      }
      return selected;
   }
   
   protected void addOptionTag(StringBuffer buf, String value, String displayValue){
      buf.append(getTagName());        
      if (isSelected(value) ) {
//         buf.append(HTMLEncoder.encode(HTMLHelpers.buildHtmlAttr(Constants.SELECTED,Constants.TRUE)));
         //buf.append(HTMLHelpers.buildHtmlAttr(Constants.SELECTED,Constants.TRUE));
         buf.append(HTMLHelpers.buildHtmlAttr(Constants.SELECTED));
      } 
  //    buf.append(HTMLEncoder.encode(HTMLHelpers.buildHtmlAttr(Constants.VALUE,value))); 
      buf.append(HTMLHelpers.buildHtmlAttr(Constants.VALUE,value));      
      if (displayValue != null) {
         buf.append(TagConstants.RT_ANGLE);
         buf.append(displayValue + Constants.OPTION_CLOSE);
      }
      else {
         buf.append(getElementClose());
      }
      // is NL needed? NIT
   }
   public void getStartElementClose(StringBuffer buffer) {
   }
   
   public void release() {
      super.release();
      clear();
   }
   
   protected void clear() {
      super.clear();
      if (_selectedCollection != null) {
            _selectedCollection.clear();
         }
         if (_displayValueCollection != null) {
            _displayValueCollection.clear();
         }
         if (_valueCollection != null) {
            _valueCollection.clear();
         }
      
         _selectedArray = null;
         _selectedString = null;
         _displayValueCollection = null;
         _displayValueArray = null;
         _valueCollection = null;
         _valueArray = null;
   }

}
