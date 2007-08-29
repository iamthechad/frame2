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
package org.megatome.frame2.front;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;

/**
 * HttpFrontController is the entry point for HTML clients. It needs to be
 * mapped to the servlet paths of the event names.
 */
public class HttpFrontController extends HttpServlet {
	private static final long serialVersionUID = -1352822098236647088L;

	private static Logger LOGGER = LoggerFactory
            .instance(HttpFrontController.class.getName());

    // NIT keeping a reference to config here
    // will cause reload issues later. let
    // ConfigFactory keep reference.
    //private Configuration config;

    /**
     * Load the Frame2 configuration file. The servlet's init parameters are
     * searched for the <code>Globals.CONFIG_FILE</code> parameter. If found,
     * that path is used to locate the configuration file, otherwise the default
     * <code>frame2-config.xml</code> path is used. The configuration file is
     * loaded as a resource, that is, from the classpath of the application.
     * @see org.megatome.frame2.Globals
     * @see javax.servlet.GenericServlet#init()
     */

    // DOC: tell the user that the init controls the reading of the config, so
    // that the user can control lazy reading through the init-on-startup.
    @Override
	public void init() throws ServletException {
        super.init();

        try {
            ConfigFactory.instance();
        } catch (ConfigException e) {
            //this.config = null;
            throw new ServletException("Failed to initialize with config " //$NON-NLS-1$
                    + ConfigFactory.getConfigFilePath(), e);
        }
    }

    /**
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest,
     *      HttpServletResponse)
     */
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        doPost(request, response);
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest,
     *      HttpServletResponse)
     */
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        RequestProcessor processor = null;

        try {
            /*if (this.config == null) {
                throw new ServletException(
                        "POST called on uninitialized servlet"); //$NON-NLS-1$
            }*/

            processor = RequestProcessorFactory.instance(ConfigFactory.instance(),
                    getServletContext(), request, response);
            if (processor == null) {
                String error = "Unable to instantiate Request Processor"; //$NON-NLS-1$
                LOGGER.severe(error);
                throw new ServletException(error);
            }

            try {
                processor.preProcess();
            } catch (RuntimeException re) {
                LOGGER
                        .severe("Caught exception in RequestProcessor:preProcess() " //$NON-NLS-1$
                                + re);
                re.printStackTrace();
            }

            processor.processRequest();

        } catch (ServletException e) {
            throw e;
        } catch (Throwable e) {
            LOGGER.warn("Unable to process request: " + e); //$NON-NLS-1$
            throw new ServletException("Unable to process request", e); //$NON-NLS-1$
        } finally {
            if (processor != null) {
                try {
                    processor.postProcess();
                } catch (RuntimeException re) {
                    LOGGER
                            .severe("Caught exception in RequestProcessor:postProcess() " //$NON-NLS-1$
                                    + re);
                    re.printStackTrace();
                }
                processor.release();
            }
        }
    }

    /**
     * @see javax.servlet.Servlet#destroy()
     */
    @Override
	public void destroy() {
        super.destroy();
    }

    /**
     * @see javax.servlet.Servlet#getServletInfo()
     */
    @Override
	public String getServletInfo() {
        return getClass().getName();
    }

    Configuration getConfiguration() {
        try {
			return ConfigFactory.instance();
		} catch (ConfigException e) {
			return null;
		}
    }
}