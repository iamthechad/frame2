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
package org.megatome.frame2.log;


/**
 *
 */

/**
 * The logger interface defines a simple and neutral logging API for internal use by the Frame2
 * framework.  Frame2 developers make use the internal logging in two ways.  First, they may
 * specify one of the Logger implementations in the configuration (currently supported are the
 * standard Java logging and Log4j) or they may implement the interface for their own logging
 * utility.  In both cases they normally specify the implementation through the
 * Frame2ContextListener. <br>
 * Internally, the framework generally uses its class names for the logger names, so that Frame2
 * developers may set the logging features for the framework by specifying the
 * <code>org.megatome.frame2.*</code> name.
 */
public interface Logger {
   /**
    * Return the name in use for this logger.
    *
    * @return String
    */
   public String getName();

   /**
    * Log a debug message.
    *
    * @param message
    */
   public void debug(String message);

   /**
    * Log a debug message with an exception
    *
    * @param message
    * @param t
    */
   public void debug(String message, Throwable t);

   /**
    * Log an informational message.
    *
    * @param message
    */
   public void info(String message);

   /**
    * Log an informational message with an exception.
    *
    * @param message
    * @param t
    */
   public void info(String message, Throwable t);
   /**
    * Log a warning message.
    *
    * @param message
    */
   public void warn(String message);

   /**
    * Log a warning message with an exception.
    *
    * @param message
    * @param t
    */
   public void warn(String message, Throwable t);


   /**
    * Log an severe message.
    *
    * @param message
    */
   public void severe(String message);

   /**
    * Log an severe message with an exception.
    *
    * @param message
    * @param t
    */
   public void severe(String message, Throwable t);

   /**
    * Returns true is debug logging is enabled.
    */
   public boolean isDebugEnabled();

   /**
    * Returns true is informational logging is enabled.
    */
   public boolean isInfoEnabled();

   /**
    * Returns true is warning logging is enabled.
    */
   public boolean isWarnEnabled();

   /**
    * Returns true is severe logging is enabled.
    */
   public boolean isSevereEnabled();
}
