package org.megatome.frame2.log;

import junit.framework.TestCase;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerException;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.log.impl.Log4jLogger;
import org.megatome.frame2.log.impl.StandardLogger;

/**
 * 
 */
public class TestLoggerFactory extends TestCase {

    public void testStandardLogger () throws LoggerException {
        String className = "org.megatome.frame2.log.impl.StandardLogger";
        LoggerFactory.setType(className, this.getClass().getClassLoader()); 
        Logger logger = LoggerFactory.instance("real");
        assertNotNull(logger);
        assertTrue (logger instanceof StandardLogger);
        assertEquals("real", logger.getName());
      
    }
   
    public void testLog4jLogger () throws LoggerException {
        String className = "org.megatome.frame2.log.impl.Log4jLogger";
        LoggerFactory.setType(className, this.getClass().getClassLoader()); 
        Logger logger = LoggerFactory.instance("real");
        assertNotNull(logger);
        assertTrue (logger instanceof Log4jLogger);
        assertEquals("real", logger.getName());
      
    }
   
   
    public void testBogusLogger () throws LoggerException {
        String className = "org.megatome.frame2.log.impl.BogusLogger";
        try {
            LoggerFactory.setType(className, this.getClass().getClassLoader()); 
            fail();
        } catch (LoggerException e) {
        }
      
        Logger logger = LoggerFactory.instance("bogus");
        assertNotNull(logger);
        assertTrue (logger instanceof StandardLogger);
        assertEquals("bogus", logger.getName());     
      
    }

}
