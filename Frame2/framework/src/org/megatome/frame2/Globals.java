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
package org.megatome.frame2;

/**
 * Defines global data needed by extenders of the framework.
 */
public final class Globals {
	/**
	 * The CONFIG_FILE key is used to look up the desired path of the WAM configuration XML.
	 */
	public static final String CONFIG_FILE = "org.megatome.frame2.CONFIG_FILE";

	/**
	 * The ERRORS key is used to look up the Errors object from the request attributes map.
	 */
	public static final String ERRORS = "org.megatome.frame2.ERRORS";

	/**
	 * The LOGGER_TYPE key is used to specify through the context parameters what type of logger to
	 * use for the WAM framework.
	 */
	public static final String LOGGER_TYPE = "org.megatome.frame2.LOGGER_TYPE";
	public static final String DEFAULT_CONFIG_FILE =
		"/WEB-INF/frame2-config.xml";

	/**
	 * Constants for overriding file upload values.
	 */
	public static final String FILE_UPLOAD_DIR =
		"org.megatome.frame2.FILE_UPLOAD_DIR";
	public static final String MAX_FILE_SIZE =
		"org.megatome.frame2.MAX_FILE_SIZE";
	public static final String FILE_BUFFER_SIZE =
		"org.megatome.frame2.FILE_BUFFER_SIZE";

	/**
	 * The RESOURCE_BUNDLE key is used to specify the name of the resource bundle to use for the
	 * application.  It should be set through the context parameters.
	 */
	public static final String RESOURCE_BUNDLE =
		"org.megatome.frame2.RESOURCE_BUNDLE";

	public static final String CANCEL = "org.megatome.frame2.CANCEL";

	public static final String MAPPING_KEY_PREFIX = "frame2.mapping.";

	public static final String FORWARD_SLASH = "/";

	/**
	 * Specify the Public and System IDs for the framework. These are used by an internal 
	 * entity resolver to avoid the setp of checking the web. 
	 */
	public static final String FRAME2_DTD_PUBLIC_ID =
		"-//Megatome Technologies//DTD Frame2 Configuration 1.0//EN";
	public static final String FRAME2_DTD_SYSTEM_ID =
		"http://frame2.sourceforge.net/dtds/frame2-config_1_0.dtd";
	public static final String FRAME2_TEMPLATE_DTD_PUBLIC_ID =
		"-//Megatome Technologies//DTD Frame2 Template Plugin 1.0//EN";
	public static final String FRAME2_TEMPLATE_DTD_SYSTEM_ID =
		"http://frame2.sourceforge.net/dtds/frame2-template_1_0.dtd";
	
	public static final String FRAME2_DTD_FILE = "frame2-config_1_0.dtd";
	public static final String FRAME2_TEMPLATE_DTD_FILE = "frame2-template_1_0.dtd";
}
