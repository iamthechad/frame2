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
package org.megatome.frame2.taglib.error;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.taglib.Globals;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;
import org.megatome.frame2.util.MessageFormatter;
import org.megatome.frame2.util.ResourceLocator;


/**
 * The ErrorsTag provides a simple facility for generating output consisting of formatted
 * error information.  The error data derives from the errors object used by the framework
 * to record problems with processing a request.  The tag uses four resource properties
 * to format the output.  In general these will consist of HTML markup. The basic format
 * will be (for a request that generated three errors):<br>
 * <br><code>
 * [header]<br>
 * [prefix][formatted error 1][suffix]<br>
 * [prefix][formatted error 2][suffix]<br>
 * [prefix][formatted error 3][suffix]<br>
 * [footer]</code><br>
 * <br>
 * The errors are formatted using the java.text.MessageFormatter and locale for the request,
 * if available, else the default locale.  The locale selection can be overridden by specifying
 * the locale key, in which case that key will be used to locate the locale in the session.
 * 
 * @see org.megatome.frame2.Globals#ERRORS
 */
public class ErrorsTag extends TagSupport {
   private static Logger LOGGER = LoggerFactory.instance(ErrorsTag.class.getName());
   private String _localeKey;
   private String _name;
   private String _errorKey;

   /**
    * Sets a key for a locale placed in the session.
    *
    * @param locale The locale to set
    */
   public void setLocaleKey(String locale) {
      _localeKey = locale;
   }

   /**
    * Sets the name.
    *
    * @param name The name to set
    */
   public void setName(String name) {
      _name = name;
   }

   /**
    * If set, only errors corresponding to this key will be emmitted.
    *
    * @param key The key to set
    */
   public void setErrorKey(String key) {
      _errorKey = key;
   }

   /**
    * Generate the HTML for the error information placed in the request.
    *
    * @throws JspException
    */
   public int doStartTag() throws JspException {
      Errors errors = getErrors(pageContext);

      if ((errors == null) || errors.isEmpty()) {
         return EVAL_BODY_INCLUDE;
      }

      StringBuffer buffer = new StringBuffer();

      Error[] errs = errors.get(_errorKey);

      Locale locale = HTMLHelpers.getLocale(pageContext,_localeKey);
      ResourceBundle bundle = ResourceLocator.getBundle(locale);

      addElement(bundle, locale, buffer, Globals.ERRORS_HEADER, true);

      for (int i = 0; i < errs.length; i++) {        
         addElement(bundle, locale, buffer, Globals.ERRORS_PREFIX, false);                  
         buffer.append(errs[i].getMessage(locale));
         addElement(bundle, locale, buffer, Globals.ERRORS_SUFFIX, false);
         buffer.append("\n");
      }

      addElement(bundle, locale, buffer, Globals.ERRORS_FOOTER, true);

      try {
         pageContext.getOut().write(buffer.toString());
      } catch (IOException e) {
         throw new JspException("Unable to write errors to page", e);
      }

      return EVAL_BODY_INCLUDE;
   }

   private void addElement(ResourceBundle bundle, Locale locale, StringBuffer buffer, String key,
      boolean linefeed) {
      String element = getResourceString(bundle, key);

      if (element != null) {
         buffer.append(MessageFormatter.format(element, locale, null));

         if (linefeed) {
            buffer.append("\n");
         }
      }
   }

   private String getResourceString(ResourceBundle bundle, String key) {
      String result = null;

      if (key != null) {
         try {
            result = bundle.getString(key);
         } catch (MissingResourceException e) {
         }
      }

      return result;
   }

   private Errors getErrors(PageContext pageContext) {
      return (Errors) pageContext.getRequest().getAttribute(org.megatome.frame2.Globals.ERRORS);
   }

   /**
    * Release the instance.
    */
   public void release() {
      super.release();
      _localeKey = null;
      _name = null;
      _errorKey = null;
   }

}
