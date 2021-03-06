/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.megatome.frame2.template.TemplateException;

public class TemplateConfiguration implements TemplateConfigurationInterface {
   public static final String TEMPLATE_PATH_EXCEPTION_MSG =
      "validateTemplateFiles(), Unable to load template path= "; //$NON-NLS-1$
   public static final String TEMPLATE_PUT_PATH_EXCEPTION_MSG =
      "validateTemplateFiles(), Unable to load template put path= "; //$NON-NLS-1$
   private Map<String, TemplateDefI> definitions = new HashMap<String, TemplateDefI>();
   private String configDir;
   
   public void setConfigDir(final String configDir) {
	   this.configDir = configDir;
   }

   public TemplateDefI getDefinition(String name) {
      return this.definitions.get(name);
   }

   public Map<String, TemplateDefI> getDefinitions() {
      return this.definitions;
   }

   public void setDefinitions(Map<String, TemplateDefI> map) {
      this.definitions = new HashMap<String, TemplateDefI>(map);
   }

   public void loadTemplateFile(InputStream is) throws TemplateException {
      TemplateConfigReader reader = new TemplateConfigReader(this.definitions);
      try {
         reader.execute(is);
      } catch (Exception e) {
         e.printStackTrace();
         throw new TemplateException(
            "Unable to load template definition file", //$NON-NLS-1$
            e);
      }
      for (TemplateDefI def : this.definitions.values()) {
    	  def.setConfigDir(this.configDir);
      }
   }

   public void validateTemplateFiles(ServletContext context)
      throws TemplateException {
      Collection<TemplateDefI> defs = this.definitions.values();

      for (TemplateDefI def : defs) {
         validateDefinitionPath(context, def);
         validateDefinitionPutPaths(context, def);
      }

   }
   protected void validateDefinitionPath(ServletContext context,TemplateDefI def)
                                                         throws TemplateException {
      InputStream is = context.getResourceAsStream(this.configDir + def.getPath());
      if (is == null) {
         throw new TemplateException(TEMPLATE_PATH_EXCEPTION_MSG +
                                      this.configDir +
                                      def.getPath());
      }
   }

   protected void validateDefinitionPutPaths(ServletContext context,TemplateDefI def)
                                                            throws TemplateException {
      Map<String, String> puts = def.getPutParams();
      Collection<String> paths = puts.values();
      for (String path : paths) {
         InputStream is = context.getResourceAsStream(this.configDir + path);
         if (is == null) {
            throw new TemplateException(TEMPLATE_PUT_PATH_EXCEPTION_MSG + 
                                         this.configDir + path);
         }
      }
   }

}
