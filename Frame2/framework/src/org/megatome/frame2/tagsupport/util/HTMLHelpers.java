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

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.megatome.frame2.tagsupport.TagConstants;

/**
 * The HTMLHelpers class provides static services for constructing the output from
 * the Frame2 custom tags.
 */

public final class HTMLHelpers
{
   private HTMLHelpers() {}

   /**
    * Construct an HTML attribute/value pair from the given name and value. For example,
    * a tag with attribute "href" and value "http://frame2.sourceforge.net" will return
    * a string of the form ' href="http://frame2.sourceforge.net"'. (Note leading space).
    * @param attrName The attribute name
    * @param attrValue The attribute value
    * @return The resulting pair, or an empty string if the value is empty or null.
    */
	public static String buildHtmlAttr(String attrName, String attrValue) {
		if (attrValue != null && ! attrValue.trim().equals("")) {
			StringBuffer exprAttr = new StringBuffer();
			exprAttr.append(TagConstants.SPACE);
			exprAttr.append(attrName);
			exprAttr.append(TagConstants.EQUAL + TagConstants.QUOTE);
			exprAttr.append(attrValue);
			exprAttr.append(TagConstants.QUOTE);
			return exprAttr.toString();
		} else {
			return "";
		}
	}
	
	public static String buildHtmlAttr(String attrName) {
	   if ((attrName != null) && (!attrName.trim().equals(""))) {
	      StringBuffer exprAttr = new StringBuffer();
	      exprAttr.append(TagConstants.SPACE);
	      exprAttr.append(attrName);
	      return exprAttr.toString();
	   } else {
	      return "";
	   }
	}
   
   /**
    * Write the data to the output of the page.
    * @param context The page context to write out to
    * @param data The data to write out to the page
    * @throws JspException
    */
   public static void writeOutput(PageContext context,String data) throws JspException {
       JspWriter writer = context.getOut();
       try {
           writer.print(data);
       } catch (Exception e) {              
           throw new JspException ("Failure writing to pageContext.  Exception = " + e.getMessage(), e);               
       }            
    }
    
   /**
    * Construct the value of an expression attribute.  The name is placed into the expression
    * language notation.
    * @param attrName The name of an attribute.
    * @return The name in EL notation. e.g. "foo" -> "${foo}"
    */
   public static String buildExprAttr(String attrName) {
      StringBuffer exprAttr = new StringBuffer();
      exprAttr.append("${");
      exprAttr.append(attrName);
      exprAttr.append("}");
      return exprAttr.toString();
   }

   /**
    * Locate the locale to use for formatting custom tag content.  The rule for resolving
    * a locale is to first look for a Local under the preferred locale key in the session.  If
    * the key is not specified or the locale is not found (was null), the locale from the request
    * is then used.  If that is not found then the default locale is used.
    * @param context The page context to search in
    * @param preferredLocaleKey The preferred locale
    * @return The preferred locale, or the request's locale, or the default locale, depending
    * on which is found first.
    */
	static public Locale getLocale(PageContext context,String preferredLocaleKey) {
      Locale result = null;
      
      if ( preferredLocaleKey != null ) {
         result = (Locale) context.getAttribute(preferredLocaleKey,PageContext.SESSION_SCOPE);
      }

      if ( result == null ) {
         result = context.getRequest().getLocale();
      }

      if ( result == null ) {
         result = Locale.getDefault();
      }

      return result;
   }


}
