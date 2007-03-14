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
package org.megatome.frame2.template;

import java.util.Map;

import javax.servlet.ServletContext;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.plugin.PluginInterface;

public class TemplatePlugin implements PluginInterface {

	private static String DEFAULT_TEMPLATE_DIR = "/WEB-INF/templates/"; //$NON-NLS-1$

	private static String configDir = DEFAULT_TEMPLATE_DIR;

	private static String DEFAULT_TEMPLATE_FILE = "templates.xml"; //$NON-NLS-1$

	public static String PLUGIN_INIT_ERROR = "Template Plugin Initialization Error: "; //$NON-NLS-1$

	private String templateFile = DEFAULT_TEMPLATE_FILE;

	private Logger getLogger() {
		return LoggerFactory.instance(TemplatePlugin.class.getName());
	}

	/**
	 * 
	 */
	public TemplatePlugin() {
		super();
	}

	public void setConfigDir(String templateDir) {
		configDir = templateDir;
	}

	public void init(ServletContext context, @SuppressWarnings("unused")
	Map<String, String> initParams) throws PluginException {
		getLogger().debug("TemplatePlugin init()"); //$NON-NLS-1$

		try {
			TemplateConfigFactory.loadTemplateFile(context, configDir
					+ this.templateFile);
		} catch (TemplateException e) {
			getLogger().severe(PLUGIN_INIT_ERROR + e.getMessage());
			throw new PluginException(PLUGIN_INIT_ERROR + e.getMessage(), e);
		}
	}

	public void destroy(@SuppressWarnings("unused")
	ServletContext context, @SuppressWarnings("unused")
	Map<String, String> initParams) {
		getLogger().debug("TemplatePlugin destroy()"); //$NON-NLS-1$
		TemplateConfigFactory.release();
	}

	/**
	 * Returns the configDir.
	 * 
	 * @return String
	 */
	public static String getConfigDir() {
		return configDir;
	}

}
