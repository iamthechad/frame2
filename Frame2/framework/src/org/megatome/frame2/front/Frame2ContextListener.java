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
package org.megatome.frame2.front;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.megatome.frame2.Globals;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerException;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.plugin.PluginInterface;
import org.megatome.frame2.util.ResourceLocator;

/**
 * The Frame2ContextListener performs initialization for Frame2 based on the
 * context parameters provided in the web application deployment descriptor (web.xml).
 * The initialization is triggered by the context initialized event, and consists of the following
 * three steps:
 * 
 * 1) If the LOGGER_TYPE parameter is set, the value will be used as the type to be created
 * by the LoggerFactory. The type name must be the fully qualified name of the Logger type and
 * must implement the Logger interface.  If the parameter is not set, the default Logger will
 * be used.
 * 
 * 2) If the CONFIG_FILE parameter is set, the value will be used to configure the framework.
 * The value will be treated as a resource that will be streamed into the configuration parser.
 * If the parameter is not set, the framework will attempt to load the configuration using the
 * default path (DEFAULT_CONFIG_FILE).  Failure to configure using either the parameter or the
 * default is a runtime error.
 * 
 * 3) If the RESOURCE_BUNDLE parameter is set, the value will be used as the basename for all
 * resource calls used by the framework.  There is no default provided, though if the parameter is not set
 * the framework will attempt to the default bundle specified as part of the JSTL installation
 * (see the JSTL specification for setting the basename).
 * 
 * 4) Invoke the Plugin classes init()/destroy() methods at initialize/destroy events.
 * 
 * @see org.megatome.frame2.log.LoggerFactory
 * @see org.megatome.frame2.log.Logger
 * @see org.megatome.frame2.Globals
 * 
 */

public class Frame2ContextListener implements ServletContextListener {

   private Logger getLogger() {
      return LoggerFactory.instance(Frame2ContextListener.class.getName());
   }

   /**
    * This is the flag jstl uses to detect a bundle configuration for the web application.
    * The context listener will attempt to use this if the parameter is not specified
    * for Frame2 by use of the RESOURCE_BUNDLE parameter.
    */

   static String JSTL_CONTEXT_BUNDLE_PARAM = "javax.servlet.jsp.jstl.fmt.basename";

   /**
    * @see javax.servlet.ServletContextListener#contextInitialized(ServletContextEvent)
    */
   public void contextInitialized(ServletContextEvent event) {
      ServletContext context = event.getServletContext();

      setLoggerFirst(context);
      setConfigFile(context);
      setResourceBundle(context);
      setFileUploadOptions(context);
      initPlugins(context);
   }

   private void setResourceBundle(ServletContext context) {
      String basename = context.getInitParameter(Globals.RESOURCE_BUNDLE);

      if (basename == null) {
         basename = context.getInitParameter(JSTL_CONTEXT_BUNDLE_PARAM);
      }

      if (basename != null) {
         ResourceLocator.setBasename(basename);
      }
   }

   private void setConfigFile(ServletContext context) {
      String configFileName = context.getInitParameter(Globals.CONFIG_FILE);

      if (configFileName == null) {
         configFileName = Globals.DEFAULT_CONFIG_FILE;

         getLogger().info(
            "Configuration file not set through context params, using default path of "
               + configFileName);
      } else {
         getLogger().info("Configuration file set to " + configFileName);
      }

      InputStream is = context.getResourceAsStream(configFileName);

      if (is == null) {
         getLogger().severe("Unable to locate resource " + configFileName);
      } else {
         getLogger().info("Located resource " + configFileName);
      }

      ConfigFactory.setConfigFile(is, configFileName);
   }

   private void setLoggerFirst(ServletContext context) {
      String loggerType = context.getInitParameter(Globals.LOGGER_TYPE);

      try {
         if (loggerType != null) {
            LoggerFactory.setType(loggerType, getClass().getClassLoader());
            getLogger().info("Configuring Frame2 logging to use " + loggerType);
         }
      } catch (LoggerException e) {
         throw new RuntimeException("Unable to set logger type to " + loggerType, e);
      }
   }
   
   private void setFileUploadOptions(ServletContext context) {
   	String fileUploadDir = context.getInitParameter(Globals.FILE_UPLOAD_DIR);
   	if (fileUploadDir != null) {
   		FileUploadConfig.setFileTempDir(fileUploadDir);
   	}
   	
		String fileBufferSize = context.getInitParameter(Globals.FILE_BUFFER_SIZE);
		if (fileBufferSize != null) {
			try {
				int bufferSize = Integer.parseInt(fileBufferSize);
				FileUploadConfig.setBufferSize(bufferSize);
			} catch (NumberFormatException nfe) {
				getLogger().warn("Invalid file buffer size specified in init-param");
			}
		}
		
		String maxFileSize = context.getInitParameter(Globals.MAX_FILE_SIZE);
		if (maxFileSize != null) {
			try {
				Long maxFile = new Long(maxFileSize);
				FileUploadConfig.setMaxFileSize(maxFile.longValue());
			} catch (NumberFormatException nfe) {
				getLogger().warn("Invalid maximum file size specified in init-param");
			}
		}
   }
   
   private void initPlugins(ServletContext context) {
      getLogger().info("Frame2ContextListener, initPlugins()");
      
      List proxys;
      try {
         proxys = ConfigFactory.instance().getPluginProxies();
      } catch (ConfigException e) {
         getLogger().severe("Error: initPlugins(), Unable to load configFile,Not Loading Plugins.");
         return;
      }
      
      for (Iterator iter=proxys.iterator(); iter.hasNext();){
         PluginProxy proxy = (PluginProxy)iter.next(); 
         try {            
            PluginInterface plugin = proxy.getPlugin();
            plugin.init(context,proxy.getInitParams());
         } catch (PluginException e) {
            getLogger().warn("Warning: initPlugins(), Unable to initialize plugin: " + proxy.getName() + " (" + e.getMessage() + ")");
            proxy.setInitThrewException(true);
         }
      }    
   }

   private void destroyPlugins(ServletContext context) {
       getLogger().info("Frame2ContextListener, destroyPlugins()");
           
      List proxys;
      try {
         proxys = ConfigFactory.instance().getPluginProxies();
      } catch (ConfigException e) {
         getLogger().severe("Error: destroyPlugins(), Unable to load configFile,Not Loading Plugins.");
         return;
      }
      
      for (Iterator iter=proxys.iterator(); iter.hasNext();){
         PluginProxy proxy = (PluginProxy)iter.next(); 
         if (proxy.initThrewException()) {
            continue;
         }
         
         try {            
            PluginInterface plugin = proxy.getPlugin();
            plugin.destroy(context,proxy.getInitParams());
         } catch (PluginException e) {
            getLogger().warn("Warning: destroyPlugins(), Unable to destroy plugin: " + proxy.getName());        
         }
      }    
   }
   /**
    * @see javax.servlet.ServletContextListener#contextDestroyed(ServletContextEvent)
    */
   public void contextDestroyed(ServletContextEvent event) {
      ServletContext context = event.getServletContext();
      destroyPlugins(context);
      ConfigFactory.release();
   }
}
