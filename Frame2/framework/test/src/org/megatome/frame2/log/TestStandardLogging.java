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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.megatome.frame2.log.impl.StandardLogger;

/**
 * 
 */
public class TestStandardLogging {

	final static String LOGGER_NAME = "org.megatome.frame2.log.TestLogging"; //$NON-NLS-1$

	private java.util.logging.Logger stdLogger;

	private java.util.logging.Handler stdHandler;

	private java.util.logging.Formatter stdFormatter;

	private ByteArrayOutputStream stdStream;

	private Logger logger;

	@Before
	public void setUp() {
		this.stdFormatter = new java.util.logging.SimpleFormatter();
		this.stdStream = new ByteArrayOutputStream(1000);
		this.stdLogger = java.util.logging.Logger.getLogger(LOGGER_NAME);
		this.stdHandler = new java.util.logging.StreamHandler(this.stdStream,
				this.stdFormatter);

		// Strip other handlers

		java.util.logging.Handler[] defaultHandlers = this.stdLogger
				.getHandlers();

		for (int i = 0; i < defaultHandlers.length; i++) {
			this.stdLogger.removeHandler(defaultHandlers[i]);
		}

		this.stdLogger.addHandler(this.stdHandler);

		this.stdLogger.setLevel(java.util.logging.Level.ALL);
		this.stdHandler.setLevel(java.util.logging.Level.ALL);

		this.logger = LoggerFactory.instance(LOGGER_NAME);
	}

	@After
	public void tearDown() {
		this.stdStream.reset();
	}

	@Test
	public void testName() {
		assertNotNull(this.logger);
		assertTrue(this.logger instanceof StandardLogger);
		assertEquals(LOGGER_NAME, this.logger.getName());
	}

	@Test
	public void testStatusDebug() {
		this.stdLogger.setLevel(java.util.logging.Level.INFO);
		assertFalse(this.logger.isDebugEnabled());
		this.stdLogger.setLevel(java.util.logging.Level.FINE);
		assertTrue(this.logger.isDebugEnabled());
	}

	@Test
	public void testStatusInfo() {
		this.stdLogger.setLevel(java.util.logging.Level.WARNING);
		assertFalse(this.logger.isInfoEnabled());
		this.stdLogger.setLevel(java.util.logging.Level.INFO);
		assertTrue(this.logger.isInfoEnabled());
	}

	@Test
	public void testStatusWarn() {
		this.stdLogger.setLevel(java.util.logging.Level.SEVERE);
		assertFalse(this.logger.isWarnEnabled());
		this.stdLogger.setLevel(java.util.logging.Level.WARNING);
		assertTrue(this.logger.isWarnEnabled());
	}

	@Test
	public void testStatusSevere() {
		this.stdLogger.setLevel(java.util.logging.Level.OFF);
		assertFalse(this.logger.isSevereEnabled());
		this.stdLogger.setLevel(java.util.logging.Level.SEVERE);
		assertTrue(this.logger.isSevereEnabled());
	}

	@Test
	public void testLogDebug() {
		this.logger.debug("debug message"); //$NON-NLS-1$

		this.stdHandler.flush();
		assertTrue(this.stdStream.toString().indexOf("debug message") > 0); //$NON-NLS-1$

		this.logger.debug(
				"exception debug", new TestException("test exception")); //$NON-NLS-1$ //$NON-NLS-2$

		assertLog("debug"); //$NON-NLS-1$
	}

	@Test
	public void testLogInfo() {
		this.logger.info("info message"); //$NON-NLS-1$

		this.stdHandler.flush();
		assertTrue(this.stdStream.toString().indexOf("info message") > 0); //$NON-NLS-1$

		this.logger.info("exception info", new TestException("test exception")); //$NON-NLS-1$ //$NON-NLS-2$

		assertLog("info"); //$NON-NLS-1$
	}

	@Test
	public void testLogSevere() {
		this.logger.severe("severe message"); //$NON-NLS-1$

		this.stdHandler.flush();
		assertTrue(this.stdStream.toString().indexOf("severe message") > 0); //$NON-NLS-1$

		this.logger.severe(
				"exception severe", new TestException("test exception")); //$NON-NLS-1$ //$NON-NLS-2$

		assertLog("severe"); //$NON-NLS-1$
	}

	@Test
	public void testLogWarn() {
		this.logger.warn("warn message"); //$NON-NLS-1$

		this.stdHandler.flush();
		assertTrue(this.stdStream.toString().indexOf("warn message") > 0); //$NON-NLS-1$

		this.logger.warn("exception warn", new TestException("test exception")); //$NON-NLS-1$ //$NON-NLS-2$

		assertLog("warn"); //$NON-NLS-1$
	}

	private void assertLog(String level) {
		this.stdHandler.flush();
		String log = this.stdStream.toString();

		String titleLevel = Character.toUpperCase(level.charAt(0))
				+ level.substring(1);

		assertTrue(log.indexOf("StandardLogger") == -1); //$NON-NLS-1$
		assertTrue(log.indexOf(this.getClass().getName()
				+ " testLog" + titleLevel) > 0); //$NON-NLS-1$
		assertTrue(log.indexOf("exception " + level) > 0); //$NON-NLS-1$
		assertTrue(log.indexOf("test exception") > 0); //$NON-NLS-1$
	}

	private static class TestException extends Throwable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4728252888233693925L;

		TestException(String msg) {
			super(msg);
		}
	}

}
