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

	private java.util.logging.Logger _stdLogger;
	private java.util.logging.Handler _stdHandler;
	private java.util.logging.Formatter _stdFormatter;
	private ByteArrayOutputStream _stdStream;

	private Logger _logger;

	protected void setUp() {
		_stdFormatter = new java.util.logging.SimpleFormatter();
		_stdStream = new ByteArrayOutputStream(1000);
		_stdLogger = java.util.logging.Logger.getLogger(LOGGER_NAME);
		_stdHandler = new java.util.logging.StreamHandler(_stdStream, _stdFormatter);

      // Strip other handlers
      
      java.util.logging.Handler[] defaultHandlers = _stdLogger.getHandlers();
      
      for ( int i = 0 ; i < defaultHandlers.length ; i++ ) {
         _stdLogger.removeHandler(defaultHandlers[i]);
      }

		_stdLogger.addHandler(_stdHandler);

		_stdLogger.setLevel(java.util.logging.Level.ALL);
		_stdHandler.setLevel(java.util.logging.Level.ALL);

		_logger = LoggerFactory.instance(LOGGER_NAME);
	}

	protected void tearDown() {
		_stdStream.reset();
	}

	public void testName() {
		assertNotNull(_logger);
		assertTrue(_logger instanceof StandardLogger);
		assertEquals(LOGGER_NAME, _logger.getName());
	}

	public void testStatusDebug() {
		_stdLogger.setLevel(java.util.logging.Level.INFO);
		assertFalse(_logger.isDebugEnabled());
		_stdLogger.setLevel(java.util.logging.Level.FINE);
		assertTrue(_logger.isDebugEnabled());
	}

	public void testStatusInfo() {
		_stdLogger.setLevel(java.util.logging.Level.WARNING);
		assertFalse(_logger.isInfoEnabled());
		_stdLogger.setLevel(java.util.logging.Level.INFO);
		assertTrue(_logger.isInfoEnabled());
	}

	public void testStatusWarn() {
		_stdLogger.setLevel(java.util.logging.Level.SEVERE);
		assertFalse(_logger.isWarnEnabled());
		_stdLogger.setLevel(java.util.logging.Level.WARNING);
		assertTrue(_logger.isWarnEnabled());
	}

	public void testStatusSevere() {
		_stdLogger.setLevel(java.util.logging.Level.OFF);
		assertFalse(_logger.isSevereEnabled());
		_stdLogger.setLevel(java.util.logging.Level.SEVERE);
		assertTrue(_logger.isSevereEnabled());
	}

   public void testLogDebug() {
      _logger.debug("debug message");

      _stdHandler.flush();
      assertTrue(_stdStream.toString().indexOf("debug message") > 0);

      _logger.debug("exception debug", new TestException("test exception"));

      assertLog("debug");
   }

	public void testLogInfo() {
		_logger.info("info message");

		_stdHandler.flush();
		assertTrue(_stdStream.toString().indexOf("info message") > 0);

		_logger.info("exception info", new TestException("test exception"));

      assertLog("info");
	}

	public void testLogSevere() {
		_logger.severe("severe message");

		_stdHandler.flush();
		assertTrue(_stdStream.toString().indexOf("severe message") > 0);

		_logger.severe("exception severe", new TestException("test exception"));

      assertLog("severe");
	}

	public void testLogWarn() {
		_logger.warn("warn message");

		_stdHandler.flush();
		assertTrue(_stdStream.toString().indexOf("warn message") > 0);

		_logger.warn("exception warn", new TestException("test exception"));

      assertLog("warn");
	}

   private void assertLog(String level) {
      _stdHandler.flush();
      String log = _stdStream.toString();

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
