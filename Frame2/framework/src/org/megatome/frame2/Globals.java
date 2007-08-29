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
package org.megatome.frame2;

/**
 * Defines global data needed by extenders of the framework. These values can be
 * changed by specifying the appropriate name and value as context parameters in
 * the web.xml file for the application.
 */
public final class Globals {

    private Globals() { // Non-public ctor
    }

    /**
     * The path for the Frame2 configuration file. Override the value
     * <em>org.megatome.frame2.CONFIG_FILE</em> to specify a different name
     * for the configuration file.
     */
    public static final String CONFIG_FILE = "org.megatome.frame2.CONFIG_FILE"; //$NON-NLS-1$

    /**
     * The key used to look up the Errors object from the request attributes
     * map.
     */
    public static final String ERRORS = "org.megatome.frame2.ERRORS"; //$NON-NLS-1$

    /**
     * Used to specify what type of logger to use for the Frame2 framework.
     * Override the value <em>org.megatome.frame2.LOGGER_TYPE</em> in web.xml
     * to specify logger settings.
     */
    public static final String LOGGER_TYPE = "org.megatome.frame2.LOGGER_TYPE"; //$NON-NLS-1$

    /**
     * The default configuration file: /WEB-INF/frame2-config.xml
     */
    public static final String DEFAULT_CONFIG_FILE = "/WEB-INF/frame2-config.xml"; //$NON-NLS-1$

    /**
     * The directory that uploaded files are temporarily placed in. Override the
     * value <em>org.megatome.frame2.FILE_UPLOAD_DIR</em> in web.xml to
     * specify the location uploaded files should be temporarily placed.
     * Defaults to the system temp directory.
     */
    public static final String FILE_UPLOAD_DIR = "org.megatome.frame2.FILE_UPLOAD_DIR"; //$NON-NLS-1$

    /**
     * The maximum file size for uploaded files. Override the value
     * <em>org.megatome.frame2.MAX_FILE_SIZE</em> in web.xml to specify a
     * maximum file size for uploaded files.
     */
    public static final String MAX_FILE_SIZE = "org.megatome.frame2.MAX_FILE_SIZE"; //$NON-NLS-1$

    /**
     * The size of the buffer to be used when uploading files. Override the
     * value <em>org.megatome.frame2.FILE_BUFFER_SIZE</em> in web.xml to
     * specify a buffer size for dealing with uploaded files.
     */
    public static final String FILE_BUFFER_SIZE = "org.megatome.frame2.FILE_BUFFER_SIZE"; //$NON-NLS-1$

    /**
     * The name of the resource bundle to be used by the framework. Override the
     * value <em>org.megatome.frame2.RESOURCE_BUNDLE</em> in web.xml to
     * specify the properties file to be used by the application.
     */
    public static final String RESOURCE_BUNDLE = "org.megatome.frame2.RESOURCE_BUNDLE"; //$NON-NLS-1$

    /**
     * Value used for indicating a request should be cancelled. Cannot be
     * overridden.
     */
    public static final String CANCEL = "org.megatome.frame2.CANCEL"; //$NON-NLS-1$

    /**
     * Value used when creating mapping errors in the introspector. Cannot be
     * overridden.
     */
    public static final String MAPPING_KEY_PREFIX = "frame2.mapping."; //$NON-NLS-1$

    /**
     * The forward slash character. Not sure we need this.
     */
    public static final String FORWARD_SLASH = "/"; //$NON-NLS-1$

    /** File name for Commons validator rules */
    //public static final String RULES_FILE = "commons-validator-rules.xml"; //$NON-NLS-1$

    /** File name for Commons Validator mappings */
    public static final String MAPPINGS_FILE = "commons-validation.xml"; //$NON-NLS-1$

    /** Error key for Commons Validator */
    public static final String ERRORS_KEY = "org.megatome.frame2.errors.Errors"; //$NON-NLS-1$

    /**
     * The public ID to be used with the Frame2 Configuration DTD.
     */
    public static final String FRAME2_DTD_PUBLIC_ID = "-//Megatome Technologies//DTD Frame2 Configuration 1.1//EN"; //$NON-NLS-1$

    /**
     * The system ID to be used with the Frame2 1.0 Configuration DTD
     */
    public static final String FRAME2_DTD_SYSTEM_ID_1_0 = "http://frame2.sourceforge.net/dtds/frame2-config_1_0.dtd"; //$NON-NLS-1$
    
    /**
     * The public ID to be used with the Frame2 1.0 Configuration DTD.
     */
    public static final String FRAME2_DTD_PUBLIC_ID_1_0 = "-//Megatome Technologies//DTD Frame2 Configuration 1.0//EN"; //$NON-NLS-1$

    /**
     * The system ID to be used with the Frame2 Configuration DTD
     */
    public static final String FRAME2_DTD_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/frame2-config_1_1.dtd"; //$NON-NLS-1$

    /**
     * The public ID to be used with the Frame2 Template DTD
     */
    public static final String FRAME2_TEMPLATE_DTD_PUBLIC_ID = "-//Megatome Technologies//DTD Frame2 Template Plugin 1.0//EN"; //$NON-NLS-1$

    /**
     * The system ID to be used with the Frame2 Template DTD
     */
    public static final String FRAME2_TEMPLATE_DTD_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/frame2-template_1_0.dtd"; //$NON-NLS-1$

    /**
     * The file name of the Frame2 Configuration DTD
     */
    public static final String FRAME2_DTD_FILE = "frame2-config_1_1.dtd"; //$NON-NLS-1$
    
    /**
     * The file name of the Frame2 1.0 Configuration DTD
     */
    public static final String FRAME2_DTD_FILE_1_0 = "frame2-config_1_0.dtd"; //$NON-NLS-1$

    /**
     * The file name of the Frame2 Template DTD
     */
    public static final String FRAME2_TEMPLATE_DTD_FILE = "frame2-template_1_0.dtd"; //$NON-NLS-1$
}