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

   private org.apache.log4j.Logger _l4jLogger;
   private ByteArrayOutputStream _stdStream;

   private Logger _logger;

   protected void setUp() {
      _stdStream = new ByteArrayOutputStream(1000);
      _l4jLogger = org.apache.log4j.Logger.getLogger(LOGGER_NAME);

      WriterAppender appender = new WriterAppender(new SimpleLayout(), _stdStream);
      _l4jLogger.addAppender(appender);
      appender.setImmediateFlush(true);

      _l4jLogger.setLevel(org.apache.log4j.Level.ALL);

      try {
         LoggerFactory.setType(Log4jLogger.class.getName(), this.getClass().getClassLoader());
      } catch (LoggerException e) {
         fail();
      }
      _logger = LoggerFactory.instance(LOGGER_NAME);
   }

   protected void tearDown() {
      _stdStream.reset();
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
      assertNotNull(_logger);
      assertTrue(_logger instanceof Log4jLogger);
      assertEquals(LOGGER_NAME, _logger.getName());
   }

   public void testLogDebug() {
      _logger.debug("debug message");

      assertTrue(_stdStream.toString().indexOf("debug message") > 0);

      _logger.debug("exception debug", new TestException("test exception"));
      assertTrue(_stdStream.toString().indexOf("exception debug") > 0);
      assertTrue(_stdStream.toString().indexOf("test exception") > 0);
   }

   public void testStatusDebug() {
      _l4jLogger.setLevel(org.apache.log4j.Level.INFO);
      assertFalse(_logger.isDebugEnabled());
      _l4jLogger.setLevel(org.apache.log4j.Level.DEBUG);
      assertTrue(_logger.isDebugEnabled());
   }

   public void testStatusInfo() {
      _l4jLogger.setLevel(org.apache.log4j.Level.WARN);
      assertFalse(_logger.isInfoEnabled());
      _l4jLogger.setLevel(org.apache.log4j.Level.INFO);
      assertTrue(_logger.isInfoEnabled());
   }

   public void testStatusWarn() {
      _l4jLogger.setLevel(org.apache.log4j.Level.ERROR);
      assertFalse(_logger.isWarnEnabled());
      _l4jLogger.setLevel(org.apache.log4j.Level.WARN);
      assertTrue(_logger.isWarnEnabled());
   }

   public void testStatusSevere() {
      _l4jLogger.setLevel(org.apache.log4j.Level.OFF);
      assertFalse(_logger.isSevereEnabled());
      _l4jLogger.setLevel(org.apache.log4j.Level.ERROR);
      assertTrue(_logger.isSevereEnabled());
   }

   public void testLogInfo() {
      _logger.info("info message");

      assertTrue(_stdStream.toString().indexOf("info message") > 0);

      _logger.info("exception info", new TestException("test exception"));
      assertTrue(_stdStream.toString().indexOf("exception info") > 0);
      assertTrue(_stdStream.toString().indexOf("test exception") > 0);
   }

   public void testLogSevere() {
      _logger.severe("severe message");

      assertTrue(_stdStream.toString().indexOf("severe message") > 0);

      _logger.severe("exception severe", new TestException("test exception"));
      assertTrue(_stdStream.toString().indexOf("exception severe") > 0);
      assertTrue(_stdStream.toString().indexOf("test exception") > 0);
   }

   public void testLogWarn() {
      _logger.warn("warn message");

      assertTrue(_stdStream.toString().indexOf("warn message") > 0);

      _logger.warn("exception warn", new TestException("test exception"));
      assertTrue(_stdStream.toString().indexOf("exception warn") > 0);
      assertTrue(_stdStream.toString().indexOf("test exception") > 0);
   }

   private class TestException extends Throwable {
      TestException(String msg) {
         super(msg);
      }
   }

}
