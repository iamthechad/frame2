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
package org.megatome.frame2.taglib.template.el;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;

import org.megatome.frame2.taglib.template.ServletResponseIncludeWrapper;
import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.config.TemplateDef;

public class InsertTag extends BaseFrame2Tag {

   private Map _parameters = new HashMap();
   private TemplateDef _def = null;
   /**
    * 
    */
   public InsertTag() {
      super();
   }

   /**
    * @return
    */
   public String getDefinition() {
      return getAttr(TemplateConstants.DEFINITION);
   }

   /**
    * @param string
    */
   public void setDefinition(String def) {
      setAttr(TemplateConstants.DEFINITION, def);
   }

   public Map getParameterMap() {
      return _parameters;
   }

   public Iterator getParameterNames() {
      return _parameters.keySet().iterator();
   }

   public void addParameter(String name, String value) {
      String[] newValues = null;
      if (_parameters.containsKey(name)) {
         String[] origValues = (String[]) _parameters.get(name);
         newValues = new String[origValues.length + 1];
         System.arraycopy(origValues, 0, newValues, 0, origValues.length);
      } else {
         newValues = new String[1];
      }
      newValues[newValues.length - 1] = value;
      _parameters.put(name, newValues);
   }

   public String getParameter(String name) {
      String retValue = null;
      if (_parameters.containsKey(name)) {
         String[] paramValues = (String[]) _parameters.get(name);
         retValue = paramValues[0];
      }

      return retValue;
   }

   public String[] getParameterValues(String name) {
      return (String[]) _parameters.get(name);
   }

   public void addParamsToRequest() {
      Iterator names = getParameterNames();
      while (names.hasNext()) {
         String name = (String) names.next();
         pageContext.setAttribute(
            name,
            getParameterValues(name),
            PageContext.REQUEST_SCOPE);
      }
   }

   /* (non-Javadoc)
    * @see javax.servlet.jsp.tagext.TryCatchFinally#doFinally()
    */
   public void doFinally() {
      super.doFinally();
      pageContext.removeAttribute(TemplateConstants.FRAME2_INSERT_KEY);
      _parameters.clear();
      _def = null;
   }

   /**
    * @see javax.servlet.jsp.tagext.Tag#doEndTag()
    */
   public int doEndTag() throws JspException {
      super.doEndTag();
      try {
         addParamsToRequest();

         ServletResponseIncludeWrapper wrapper =
            new ServletResponseIncludeWrapper(
               pageContext.getResponse(),
               pageContext.getOut());

			RequestDispatcher rd =
			    pageContext.getRequest().getRequestDispatcher(
			       _def.getTemplateJspPath());
			rd.include(pageContext.getRequest(), wrapper);

      } catch (ServletException e) {
         throw new JspException(e);
      } catch (IOException e) {
         throw new JspException(e);
      }

      return Tag.EVAL_PAGE;
   }

   /**
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException {
      super.doStartTag();
      String definition = evaluateStringAttribute(TemplateConstants.DEFINITION);

      try {
         _def = TemplateConfigFactory.instance().getDefinition(definition);
      } catch (TemplateException e) {
         // We'll throw an error in just a minute, so an empty catch is OK
      }

      if (_def == null) {
         throw new JspException(
            "Could not access definition for template \"" + definition + "\"");
      }
      pageContext.setAttribute(
         TemplateConstants.FRAME2_INSERT_KEY,
         _def,
         PageContext.REQUEST_SCOPE);

      return BodyTag.EVAL_BODY_BUFFERED;
   }

   protected void setTagName() {
      _tagName = TemplateConstants.INSERT_TAG;
   }
}
