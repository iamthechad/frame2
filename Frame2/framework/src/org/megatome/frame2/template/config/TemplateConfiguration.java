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
package org.megatome.frame2.template.config;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.TemplatePlugin;

public class TemplateConfiguration implements TemplateConfigurationInterface {
   public static final String TEMPLATE_PATH_EXCEPTION_MSG =
      "validateTemplateFiles(), Unable to load template path= ";
   public static final String TEMPLATE_PUT_PATH_EXCEPTION_MSG =
      "validateTemplateFiles(), Unable to load template put path= ";
   HashMap _definitions = new HashMap();

   public TemplateDef getDefinition(String name) {
      return (TemplateDef) _definitions.get(name);
   }

   public Map getDefinitions() {
      return Collections.unmodifiableMap(_definitions);
   }

   public void setDefinitions(HashMap map) {
      _definitions = new HashMap(map);
   }

   public void loadTemplateFile(InputStream is) throws TemplateException {
      TemplateConfigReader reader = new TemplateConfigReader(_definitions);
      try {
         reader.execute(is);
      } catch (Exception e) {
         e.printStackTrace();
         throw new TemplateException(
            "Unable to load template definition file",
            e);
      }
   }

   public void validateTemplateFiles(ServletContext context)
      throws TemplateException {
      Collection defs = _definitions.values();

      for (Iterator iterator = defs.iterator(); iterator.hasNext();) {
         TemplateDef def = (TemplateDef) iterator.next();
         validateDefinitionPath(context, def);
         validateDefinitionPutPaths(context, def);
      }

   }
   protected void validateDefinitionPath(ServletContext context,TemplateDef def)
                                                         throws TemplateException {
      InputStream is = context.getResourceAsStream(TemplatePlugin.getConfigDir() + def.getPath());
      if (is == null) {
         throw new TemplateException(TEMPLATE_PATH_EXCEPTION_MSG +
                                      TemplatePlugin.getConfigDir() +
                                      def.getPath());
      }
   }

   protected void validateDefinitionPutPaths(ServletContext context,TemplateDef def)
                                                            throws TemplateException {
      Map puts = def.getPutParams();
      Collection paths = puts.values();
      for (Iterator iter = paths.iterator(); iter.hasNext();) {
         String path = (String) iter.next();
         InputStream is = context.getResourceAsStream(TemplatePlugin.getConfigDir() + path);
         if (is == null) {
            throw new TemplateException(TEMPLATE_PUT_PATH_EXCEPTION_MSG + 
                                         TemplatePlugin.getConfigDir() + path);
         }
      }
   }

}
