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
package org.megatome.frame2.tagsupport.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;
import org.megatome.frame2.util.ResourceLocator;

/**
 * The EvalHelper provides services for evaluating attributes set in the Frame2 tags.
 */

public final class EvalHelper {

   // DOC: document the conventions and rules for referencing a resource key (vice expression)

   private static String RESOURCE_PREFIX = "%{";
   private static char RESOURCE_SUFFIX = '}';

   private EvalHelper() {
   }

   /**
    * Evaluate the attribute value and return the result of the expression.  The expression may be
    * of three types:<br>
    * <br>
    * 1) String literal, in which case the expression will return the attribute value.<br>
    * 2) Expression language (EL) expression, in which case the expression will be evaluated
    * per the language.  Expressions are denoted through the dollar-sign and bracket convention
    * of <code>${foo.bar}</code><br>
    * 3) Resource key, in which case the expression will return the corresponding value from the
    * configured resource bundle.  Resource keys are denoted using a Frame2 convention of
    * percent-sign and brackets, e.g. <code>%{foo.bar}</code>.
    * @param tagName name of the tag
    * @param attrName name of the attribute
    * @param attrValue value of the attribute. This parameter must following the conventions indicated
    * above to be properly evaluated
    * @param attrType type of the attribute
    * @param tagObject source tag for the attribute
    * @param pageContext page context
    * @return Object result of the expression
    * @throws JspException
    * @throws NullAttributeException thrown if no result is located.
    */
   public static Object eval(
      String tagName,
      String attrName,
      String attrValue,
      Class attrType,
      Tag tagObject,
      PageContext pageContext)
      throws JspException, NullAttributeException {

      Object result = null;

      if (isResourceKey(attrValue)) {
         result = getResource(attrValue, pageContext);

      } else {
         result =
            ExpressionUtil.evalNotNull(
               tagName,
               attrName,
               attrValue,
               attrType,
               tagObject,
               pageContext);
      }

      if (result == null) {
         throw new NullAttributeException(attrName, tagName);
      }

      return result;
   }

   private static Object getResource(String attrValue, PageContext pageContext)
      throws JspException {
      Object result = null;

      ResourceBundle bundle =
         ResourceLocator.getBundle(HTMLHelpers.getLocale(pageContext, null));

      String key = attrValue.substring(2, attrValue.length() - 1);
      
      try {
         if (bundle != null) {
            result = bundle.getObject(key);
         }
      } catch (MissingResourceException e) {
         throw new JspException("Unable to load resource for key " + key, e);
      }
      return result;
   }


   private static boolean isResourceKey(String value) {
      boolean result = false;

      if (value != null && value.indexOf(RESOURCE_PREFIX) == 0) {
         result = value.charAt(value.length() - 1) == RESOURCE_SUFFIX;
      }

      return result;
   }
}
