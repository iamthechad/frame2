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
package org.megatome.frame2.log.impl;

import org.megatome.frame2.log.Logger;

/**
 * Implements the Logger interface for the Java 1.4 logging package.  In this implementation, the
 * levels are mapped as following:<br>
 * <br>
 * <code>org.megatome.frame2.log.debug(msg)</code> maps to <code>java.util.logging.Level.FINE</code><br>
 * <code>org.megatome.frame2.log.info(msg)</code> maps to <code>java.util.logging.Level.INFO</code><br>
 * <code>org.megatome.frame2.log.warn(msg)</code> maps to <code>java.util.logging.Level.WARNING</code><br>
 * <code>org.megatome.frame2.log.severe(msg)</code> maps to <code>java.util.logging.Level.SEVERE</code><br>
 */

public class StandardLogger implements Logger {
	private java.util.logging.Logger stdLogger;
	private java.util.logging.Level debugLevel = java.util.logging.Level.FINE;
	private java.util.logging.Level infoLevel = java.util.logging.Level.INFO;
	private java.util.logging.Level warnLevel = java.util.logging.Level.WARNING;
	private java.util.logging.Level severeLevel = java.util.logging.Level.SEVERE;

	/**
	 * Constructor for StandardLogger.
	 */
	public StandardLogger(String name) {
		this.stdLogger = java.util.logging.Logger.getLogger(name);
	}

	public String getName() {
		return this.stdLogger.getName();
	}

	public void debug(String message) {
      log(this.debugLevel,message);
	}

	public void debug(String message, Throwable t) {
      log(this.debugLevel,message,t);
	}

	public boolean isDebugEnabled() {
		return this.stdLogger.isLoggable(this.debugLevel);
	}

	public boolean isInfoEnabled() {
		return this.stdLogger.isLoggable(this.infoLevel);
	}

	public boolean isSevereEnabled() {
		return this.stdLogger.isLoggable(this.severeLevel);
	}

	public boolean isWarnEnabled() {
		return this.stdLogger.isLoggable(this.warnLevel);
	}

	public void info(String message) {
      log(this.infoLevel,message);
	}

	public void info(String message, Throwable t) {
      log(this.infoLevel,message,t);
	}

	public void warn(String message) {
      log(this.warnLevel,message);
	}

	public void warn(String message, Throwable t) {
      log(this.warnLevel,message,t);
	}

	public void severe(String message) {
      log(this.severeLevel,message);
	}

	public void severe(String message, Throwable t) {
      log(this.severeLevel,message,t);
	}

   private void log(java.util.logging.Level level,String message) {
      StackTraceElement frame = getOriginalCallingFrame();
      this.stdLogger.logp(level,frame.getClassName(),frame.getMethodName(), message);
   }

   private void log(java.util.logging.Level level,String message,Throwable t) {
      StackTraceElement frame = getOriginalCallingFrame();
      this.stdLogger.logp(level,frame.getClassName(),frame.getMethodName(), message, t);
   }

	private StackTraceElement getOriginalCallingFrame() {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();

      // We assume that the original caller is the fourth frame up (index 3)
      // where 0 is this method, 1 is the generic, private log method, 2 is the
      // debug/info/warn/severe method, and 3 is the original.

      return stack[3];
	}
}
