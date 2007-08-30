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

	final static String LOGGER_NAME = "org.megatome.frame2.log.TestLogging"; //$NON-NLS-1$

	private org.apache.log4j.Logger l4jLogger;

	private ByteArrayOutputStream stdStream;

	private Logger logger;

	@Override
	protected void setUp() {
		this.stdStream = new ByteArrayOutputStream(1000);
		this.l4jLogger = org.apache.log4j.Logger.getLogger(LOGGER_NAME);

		WriterAppender appender = new WriterAppender(new SimpleLayout(),
				this.stdStream);
		this.l4jLogger.addAppender(appender);
		appender.setImmediateFlush(true);

		this.l4jLogger.setLevel(org.apache.log4j.Level.ALL);

		try {
			LoggerFactory.setType(Log4jLogger.class.getName(), this.getClass()
					.getClassLoader());
		} catch (LoggerException e) {
			fail();
		}
		this.logger = LoggerFactory.instance(LOGGER_NAME);
	}

	@Override
	protected void tearDown() {
		this.stdStream.reset();
	}

	public void testLoggerFindsLevel() {
		Logger otherLogger = LoggerFactory.instance("some.other.logger"); //$NON-NLS-1$

		try {
			// If the wrong log4j API is used these will bomb (need to use
			// getEffectiveLevel).

			otherLogger.isDebugEnabled();
			otherLogger.isInfoEnabled();
			otherLogger.isWarnEnabled();
			otherLogger.isSevereEnabled();
		} catch (Throwable t) {
			fail();
		}
	}

	public void testName() {
		assertNotNull(this.logger);
		assertTrue(this.logger instanceof Log4jLogger);
		assertEquals(LOGGER_NAME, this.logger.getName());
	}

	public void testLogDebug() {
		this.logger.debug("debug message"); //$NON-NLS-1$

		assertTrue(this.stdStream.toString().indexOf("debug message") > 0); //$NON-NLS-1$

		this.logger.debug(
				"exception debug", new TestException("test exception")); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(this.stdStream.toString().indexOf("exception debug") > 0); //$NON-NLS-1$
		assertTrue(this.stdStream.toString().indexOf("test exception") > 0); //$NON-NLS-1$
	}

	public void testStatusDebug() {
		this.l4jLogger.setLevel(org.apache.log4j.Level.INFO);
		assertFalse(this.logger.isDebugEnabled());
		this.l4jLogger.setLevel(org.apache.log4j.Level.DEBUG);
		assertTrue(this.logger.isDebugEnabled());
	}

	public void testStatusInfo() {
		this.l4jLogger.setLevel(org.apache.log4j.Level.WARN);
		assertFalse(this.logger.isInfoEnabled());
		this.l4jLogger.setLevel(org.apache.log4j.Level.INFO);
		assertTrue(this.logger.isInfoEnabled());
	}

	public void testStatusWarn() {
		this.l4jLogger.setLevel(org.apache.log4j.Level.ERROR);
		assertFalse(this.logger.isWarnEnabled());
		this.l4jLogger.setLevel(org.apache.log4j.Level.WARN);
		assertTrue(this.logger.isWarnEnabled());
	}

	public void testStatusSevere() {
		this.l4jLogger.setLevel(org.apache.log4j.Level.OFF);
		assertFalse(this.logger.isSevereEnabled());
		this.l4jLogger.setLevel(org.apache.log4j.Level.ERROR);
		assertTrue(this.logger.isSevereEnabled());
	}

	public void testLogInfo() {
		this.logger.info("info message"); //$NON-NLS-1$

		assertTrue(this.stdStream.toString().indexOf("info message") > 0); //$NON-NLS-1$

		this.logger.info("exception info", new TestException("test exception")); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(this.stdStream.toString().indexOf("exception info") > 0); //$NON-NLS-1$
		assertTrue(this.stdStream.toString().indexOf("test exception") > 0); //$NON-NLS-1$
	}

	public void testLogSevere() {
		this.logger.severe("severe message"); //$NON-NLS-1$

		assertTrue(this.stdStream.toString().indexOf("severe message") > 0); //$NON-NLS-1$

		this.logger.severe(
				"exception severe", new TestException("test exception")); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(this.stdStream.toString().indexOf("exception severe") > 0); //$NON-NLS-1$
		assertTrue(this.stdStream.toString().indexOf("test exception") > 0); //$NON-NLS-1$
	}

	public void testLogWarn() {
		this.logger.warn("warn message"); //$NON-NLS-1$

		assertTrue(this.stdStream.toString().indexOf("warn message") > 0); //$NON-NLS-1$

		this.logger.warn("exception warn", new TestException("test exception")); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(this.stdStream.toString().indexOf("exception warn") > 0); //$NON-NLS-1$
		assertTrue(this.stdStream.toString().indexOf("test exception") > 0); //$NON-NLS-1$
	}

	private static class TestException extends Throwable {
		private static final long serialVersionUID = -2237703023771112459L;

		TestException(String msg) {
			super(msg);
		}
	}

}
