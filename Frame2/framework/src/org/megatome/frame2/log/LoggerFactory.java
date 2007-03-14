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
package org.megatome.frame2.log;

import java.lang.reflect.Constructor;

import org.megatome.frame2.log.impl.StandardLogger;
import org.megatome.frame2.log.impl.StdoutLogger;

/**
 * The LoggerFactory generates instances of the Logger instances as used by the
 * framework. The Logger implementation is normally set through the
 * Frame2ContextListener.
 */
public class LoggerFactory {
    private static Constructor<?> loggerConstructor;

    private static boolean STD_LOGGING_AVAILABLE = true;

    /**
     * Constructor for LoggerFactory.
     */
    private LoggerFactory() {
    	// not public
    }

    /**
     * Creates a logger for the given name corresponding to the type set in
     * setType, otherwise returns a StandardLogger (if available) or a cruddy
     * StdoutLogger.
     * @param name The log instance name to create
     * @return Logger instance
     */
    public static Logger instance(String name) {
        if (loggerConstructor != null) {
            try {
                Object o = loggerConstructor.newInstance(new Object[] { name });

                return (Logger)o;
            } catch (Exception e) {
                throw new RuntimeException("Instantiating object failed.", e); //$NON-NLS-1$
            }
        }

        return getDefault(name);
    }

    static private Logger getDefault(String name) {
        Logger result = null;

        if (STD_LOGGING_AVAILABLE) {
            try {
                result = new StandardLogger(name);
            } catch (Throwable t) {
                STD_LOGGING_AVAILABLE = false;
            }
        }

        if (result == null) {
            result = new StdoutLogger(name);
        }

        return result;
    }

    /**
     * Sets the Logger implementation for use by the framework. The factory will
     * use the provided class loader to generate the instances.
     * @param className The fully qualified name of the Logger implementation.
     *        The class must implement the Logger interface and provide a public
     *        constructor that takes a string argument, which is its name.
     * @param classLoader The class loader to use.
     */
    public static void setType(String className, ClassLoader classLoader)
            throws LoggerException {
        Class<?> loggerClass = null;

        try {
            loggerClass = classLoader.loadClass(className);

            Class<?>[] interfaces = loggerClass.getInterfaces();
            boolean loggerIntFound = false;

            for (int i = 0; i < interfaces.length; i++) {
                if (interfaces[i] == Logger.class) {
                    loggerIntFound = true;

                    break;
                }
            }

            if (!loggerIntFound) {
                loggerConstructor = null;
                throw new LoggerException(className + " does not implement " //$NON-NLS-1$
                        + Logger.class.getName());
            }
        } catch (ClassNotFoundException e) {
            loggerConstructor = null;
            throw new LoggerException("Unable to load class " + className, e); //$NON-NLS-1$
        }

        try {
            loggerConstructor = loggerClass
                    .getConstructor(new Class[] { String.class });
        } catch (NoSuchMethodException e) {
            loggerConstructor = null;
            throw new LoggerException("Invalid constructor for " + loggerClass, //$NON-NLS-1$
                    e);
        }
    }
}