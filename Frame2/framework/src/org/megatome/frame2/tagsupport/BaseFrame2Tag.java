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
package org.megatome.frame2.tagsupport;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.megatome.frame2.tagsupport.util.EvalHelper;

public abstract class BaseFrame2Tag
   extends BodyTagSupport
   implements TryCatchFinally {
   protected TreeMap _attrs = new TreeMap();

   protected String _tagName;
   protected boolean _tagHasBody;

   public BaseFrame2Tag() {
      setTagName();
   }

   /**
    * Returns the tagName.
    * @return String
    */
   public String getTagName() {
      return _tagName;
   }

   /**
    * Sets the tagName.
    * @param tagName The tagName to set
    */
   protected abstract void setTagName();

   /**
   * Handle any Attribute which needs to be processed other than
   * by the default behavior.
   */
   protected void setAttr(String key, String value) {
      _attrs.put(key, value);
   }
   protected void removeAttr(String key) {
      _attrs.remove(key);
   }

   protected String getAttr(String key) {
      return (String) _attrs.get(key);
   }

   /**
    * Resets attribute values for tag reuse.
    */
   public void release() {
      super.release();
      _attrs.clear();
   }
   /**
    * @see javax.servlet.jsp.tagext.TryCatchFinally#doFinally()
    * release not called until garbage collect.
    */
   public void doFinally() {
      // free resources,
      _attrs.clear();
      initializeAttributes();
   }
   
   protected void initializeAttributes() {
      // override to reset any attrs.   
   }
   /**
    * @see javax.servlet.jsp.tagext.TryCatchFinally#doCatch(Throwable)
    */
   public void doCatch(Throwable arg0) throws Throwable {
      throw arg0;
   }
   
   protected void clear() {
   }

   /**
    * Evaluates and returns a String given the input attribute name and attribute
    * value. 
    *
    * @param attrName attribute name being evaluated
    * @param attrValue String _value of attribute to be evaluated using EL     * 
    * @exception Exception if either the <code>attrValue</code>
    * was null, or the resulting evaluated _value was null.
    * @return Resulting attribute _value
    */
   protected String evalStringAttr(String attrName, String attrValue)
      throws Exception {
      return (String)
         (EvalHelper
            .eval(
               getTagName(),
               attrName,
               attrValue,
               String.class,
               this,
               pageContext));
   }

   protected HashMap evalHashMapAttr(String attrName, String attrValue)
      throws Exception {
      return (HashMap)
         (EvalHelper
            .eval(
               getTagName(),
               attrName,
               attrValue,
               HashMap.class,
               this,
               pageContext));
   }

   protected Collection evalCollectionAttr(String attrName, String attrValue)
      throws Exception {
      return (Collection)
         (EvalHelper
            .eval(
               getTagName(),
               attrName,
               attrValue,
               Collection.class,
               this,
               pageContext));
   }

   protected Object[] evalArrayAttr(String attrName, String attrValue)
      throws Exception {
      return (Object[])
         (EvalHelper
            .eval(
               getTagName(),
               attrName,
               attrValue,
               Object[].class,
               this,
               pageContext));
   }

   protected String evaluateStringAttribute(String attrName) throws JspException {
      String attrValue = null;
      try {
         attrValue = (String) evalStringAttr(attrName, getAttr(attrName));
      } catch (Exception e) {
         throw new JspException(
            " Evaluation attribute failed " + e.getMessage(),
            e);
      }

      return attrValue;
   }
}
