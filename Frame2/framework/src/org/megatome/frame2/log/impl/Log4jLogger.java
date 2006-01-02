/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2006 Megatome Technologies.  All rights
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
 * Implements the Logger interface for the log4j logging package.  In this implementation, the
 * levels are mapped as following:<br>
 * <br>
 * <code>org.megatome.frame2.log.debug(msg)</code> maps to <code>org.apache.log4j.Level.DEBUG</code><br>
 * <code>org.megatome.frame2.log.info(msg)</code> maps to <code>org.apache.log4j.Level.INFO</code><br>
 * <code>org.megatome.frame2.log.warn(msg)</code> maps to <code>org.apache.log4j.Level.WARN</code><br>
 * <code>org.megatome.frame2.log.severe(msg)</code> maps to <code>org.apache.log4j.Level.ERROR</code><br>
 */
public class Log4jLogger implements Logger {
   private org.apache.log4j.Logger stdLogger;
   private org.apache.log4j.Level debugLevel = org.apache.log4j.Level.DEBUG;
   private org.apache.log4j.Level infoLevel = org.apache.log4j.Level.INFO;
   private org.apache.log4j.Level warnLevel = org.apache.log4j.Level.WARN;
   private org.apache.log4j.Level severeLevel = org.apache.log4j.Level.ERROR;

   /**
    * Constructor for Log4jLogger.
    */
   public Log4jLogger(String name) {
      stdLogger = org.apache.log4j.Logger.getLogger(name);
   }

   public String getName() {
      return stdLogger.getName();
   }

   public void debug(String message) {
      stdLogger.log(debugLevel, message);
   }

   public void debug(String message, Throwable t) {
      stdLogger.log(debugLevel, message, t);
   }

   public boolean isDebugEnabled() {
      return debugLevel.isGreaterOrEqual(stdLogger.getEffectiveLevel());
   }

   public boolean isInfoEnabled() {
      return infoLevel.isGreaterOrEqual(stdLogger.getEffectiveLevel());
   }

   public boolean isSevereEnabled() {
      return severeLevel.isGreaterOrEqual(stdLogger.getEffectiveLevel());
   }

   public boolean isWarnEnabled() {
      return warnLevel.isGreaterOrEqual(stdLogger.getEffectiveLevel());
   }

   public void info(String message) {
      stdLogger.log(infoLevel, message);
   }

   public void info(String message, Throwable t) {
      stdLogger.log(infoLevel, message, t);
   }

   public void warn(String message) {
      stdLogger.log(warnLevel, message);
   }

   public void warn(String message, Throwable t) {
      stdLogger.log(warnLevel, message, t);
   }

   public void severe(String message) {
      stdLogger.log(severeLevel, message);
   }

   public void severe(String message, Throwable t) {
      stdLogger.log(severeLevel, message, t);
   }
}
