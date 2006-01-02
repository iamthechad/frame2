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
package org.megatome.frame2.log;

import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerException;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.log.impl.Log4jLogger;

/**
 * 
 */
public class TestLog4jLogging extends TestCase {

   final static String LOGGER_NAME = "org.megatome.frame2.log.TestLogging";

   private org.apache.log4j.Logger l4jLogger;
   private ByteArrayOutputStream stdStream;

   private Logger logger;

   protected void setUp() {
      stdStream = new ByteArrayOutputStream(1000);
      l4jLogger = org.apache.log4j.Logger.getLogger(LOGGER_NAME);

      WriterAppender appender = new WriterAppender(new SimpleLayout(), stdStream);
      l4jLogger.addAppender(appender);
      appender.setImmediateFlush(true);

      l4jLogger.setLevel(org.apache.log4j.Level.ALL);

      try {
         LoggerFactory.setType(Log4jLogger.class.getName(), this.getClass().getClassLoader());
      } catch (LoggerException e) {
         fail();
      }
      logger = LoggerFactory.instance(LOGGER_NAME);
   }

   protected void tearDown() {
      stdStream.reset();
   }

   public void testLoggerFindsLevel() {
      Logger otherLogger = LoggerFactory.instance("some.other.logger");
      
      try {
         // If the wrong log4j API is used these will bomb (need to use getEffectiveLevel).
         
         otherLogger.isDebugEnabled();
         otherLogger.isInfoEnabled();
         otherLogger.isWarnEnabled();
         otherLogger.isSevereEnabled();
      } catch ( Throwable t ) {
         fail();
      }
   }

   public void testName() {
      assertNotNull(logger);
      assertTrue(logger instanceof Log4jLogger);
      assertEquals(LOGGER_NAME, logger.getName());
   }

   public void testLogDebug() {
      logger.debug("debug message");

      assertTrue(stdStream.toString().indexOf("debug message") > 0);

      logger.debug("exception debug", new TestException("test exception"));
      assertTrue(stdStream.toString().indexOf("exception debug") > 0);
      assertTrue(stdStream.toString().indexOf("test exception") > 0);
   }

   public void testStatusDebug() {
      l4jLogger.setLevel(org.apache.log4j.Level.INFO);
      assertFalse(logger.isDebugEnabled());
      l4jLogger.setLevel(org.apache.log4j.Level.DEBUG);
      assertTrue(logger.isDebugEnabled());
   }

   public void testStatusInfo() {
      l4jLogger.setLevel(org.apache.log4j.Level.WARN);
      assertFalse(logger.isInfoEnabled());
      l4jLogger.setLevel(org.apache.log4j.Level.INFO);
      assertTrue(logger.isInfoEnabled());
   }

   public void testStatusWarn() {
      l4jLogger.setLevel(org.apache.log4j.Level.ERROR);
      assertFalse(logger.isWarnEnabled());
      l4jLogger.setLevel(org.apache.log4j.Level.WARN);
      assertTrue(logger.isWarnEnabled());
   }

   public void testStatusSevere() {
      l4jLogger.setLevel(org.apache.log4j.Level.OFF);
      assertFalse(logger.isSevereEnabled());
      l4jLogger.setLevel(org.apache.log4j.Level.ERROR);
      assertTrue(logger.isSevereEnabled());
   }

   public void testLogInfo() {
      logger.info("info message");

      assertTrue(stdStream.toString().indexOf("info message") > 0);

      logger.info("exception info", new TestException("test exception"));
      assertTrue(stdStream.toString().indexOf("exception info") > 0);
      assertTrue(stdStream.toString().indexOf("test exception") > 0);
   }

   public void testLogSevere() {
      logger.severe("severe message");

      assertTrue(stdStream.toString().indexOf("severe message") > 0);

      logger.severe("exception severe", new TestException("test exception"));
      assertTrue(stdStream.toString().indexOf("exception severe") > 0);
      assertTrue(stdStream.toString().indexOf("test exception") > 0);
   }

   public void testLogWarn() {
      logger.warn("warn message");

      assertTrue(stdStream.toString().indexOf("warn message") > 0);

      logger.warn("exception warn", new TestException("test exception"));
      assertTrue(stdStream.toString().indexOf("exception warn") > 0);
      assertTrue(stdStream.toString().indexOf("test exception") > 0);
   }

   private class TestException extends Throwable {
      TestException(String msg) {
         super(msg);
      }
   }

}
