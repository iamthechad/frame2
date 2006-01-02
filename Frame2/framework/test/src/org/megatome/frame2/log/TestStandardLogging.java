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

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.log.impl.StandardLogger;

/**
 * 
 */
public class TestStandardLogging extends TestCase {

	final static String LOGGER_NAME = "org.megatome.frame2.log.TestLogging";

	private java.util.logging.Logger stdLogger;
	private java.util.logging.Handler stdHandler;
	private java.util.logging.Formatter stdFormatter;
	private ByteArrayOutputStream stdStream;

	private Logger logger;

	protected void setUp() {
		stdFormatter = new java.util.logging.SimpleFormatter();
		stdStream = new ByteArrayOutputStream(1000);
		stdLogger = java.util.logging.Logger.getLogger(LOGGER_NAME);
		stdHandler = new java.util.logging.StreamHandler(stdStream, stdFormatter);

      // Strip other handlers
      
      java.util.logging.Handler[] defaultHandlers = stdLogger.getHandlers();
      
      for ( int i = 0 ; i < defaultHandlers.length ; i++ ) {
         stdLogger.removeHandler(defaultHandlers[i]);
      }

		stdLogger.addHandler(stdHandler);

		stdLogger.setLevel(java.util.logging.Level.ALL);
		stdHandler.setLevel(java.util.logging.Level.ALL);

		logger = LoggerFactory.instance(LOGGER_NAME);
	}

	protected void tearDown() {
		stdStream.reset();
	}

	public void testName() {
		assertNotNull(logger);
		assertTrue(logger instanceof StandardLogger);
		assertEquals(LOGGER_NAME, logger.getName());
	}

	public void testStatusDebug() {
		stdLogger.setLevel(java.util.logging.Level.INFO);
		assertFalse(logger.isDebugEnabled());
		stdLogger.setLevel(java.util.logging.Level.FINE);
		assertTrue(logger.isDebugEnabled());
	}

	public void testStatusInfo() {
		stdLogger.setLevel(java.util.logging.Level.WARNING);
		assertFalse(logger.isInfoEnabled());
		stdLogger.setLevel(java.util.logging.Level.INFO);
		assertTrue(logger.isInfoEnabled());
	}

	public void testStatusWarn() {
		stdLogger.setLevel(java.util.logging.Level.SEVERE);
		assertFalse(logger.isWarnEnabled());
		stdLogger.setLevel(java.util.logging.Level.WARNING);
		assertTrue(logger.isWarnEnabled());
	}

	public void testStatusSevere() {
		stdLogger.setLevel(java.util.logging.Level.OFF);
		assertFalse(logger.isSevereEnabled());
		stdLogger.setLevel(java.util.logging.Level.SEVERE);
		assertTrue(logger.isSevereEnabled());
	}

   public void testLogDebug() {
      logger.debug("debug message");

      stdHandler.flush();
      assertTrue(stdStream.toString().indexOf("debug message") > 0);

      logger.debug("exception debug", new TestException("test exception"));

      assertLog("debug");
   }

	public void testLogInfo() {
		logger.info("info message");

		stdHandler.flush();
		assertTrue(stdStream.toString().indexOf("info message") > 0);

		logger.info("exception info", new TestException("test exception"));

      assertLog("info");
	}

	public void testLogSevere() {
		logger.severe("severe message");

		stdHandler.flush();
		assertTrue(stdStream.toString().indexOf("severe message") > 0);

		logger.severe("exception severe", new TestException("test exception"));

      assertLog("severe");
	}

	public void testLogWarn() {
		logger.warn("warn message");

		stdHandler.flush();
		assertTrue(stdStream.toString().indexOf("warn message") > 0);

		logger.warn("exception warn", new TestException("test exception"));

      assertLog("warn");
	}

   private void assertLog(String level) {
      stdHandler.flush();
      String log = stdStream.toString();

      String titleLevel = Character.toUpperCase(level.charAt(0)) + level.substring(1);

      assertTrue(log.indexOf("StandardLogger") == -1);
      assertTrue(log.indexOf(this.getClass().getName() + " testLog" + titleLevel) > 0);
      assertTrue(log.indexOf("exception " + level) > 0);
      assertTrue(log.indexOf("test exception") > 0);
   }

	private class TestException extends Throwable {
		TestException(String msg) {
			super(msg);
		}
	}

}
