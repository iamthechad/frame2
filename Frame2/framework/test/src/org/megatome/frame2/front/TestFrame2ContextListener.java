package org.megatome.frame2.front;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.megatome.frame2.Globals;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.log.impl.Log4jLogger;
import org.megatome.frame2.util.ResourceLocator;
import org.megatome.frame2.util.TestResourceLocator;

import servletunit.frame2.MockFrame2TestCase;

/**
 * TestFrame2ContextListener.java
 */
public class TestFrame2ContextListener extends MockFrame2TestCase {

	/**
	* Constructor for TestConfiguration.
	* @param name
	*/
	public TestFrame2ContextListener(String name) {
		super(name);
	}

	/**
	* @see junit.framework.TestCase#setUp()
	*/
	protected void setUp() throws Exception {
		super.setUp();
	}

   protected void tearDown() {
      ResourceLocator.setBasename(null);
   }

	public void testSetConfigFile() {
		String path = "/org/megatome/frame2/front/test-config.xml";

		assertEquals(Globals.DEFAULT_CONFIG_FILE, ConfigFactory.getConfigFilePath());

      sendContextInitializedEvent(Globals.CONFIG_FILE, path);

		assertEquals(path, ConfigFactory.getConfigFilePath());

		try {
			assertNotNull(ConfigFactory.instance());
		} catch (ConfigException e) {
			fail("Unexpected ConfigException: " + e.getMessage());
		}
	}

	public void testSetConfigFile_Invalid() {
      sendContextInitializedEvent(Globals.CONFIG_FILE, "foo");

		assertEquals("foo", ConfigFactory.getConfigFilePath());

		try {
			ConfigFactory.instance();
         fail();
		} catch (ConfigException e) {
		}
	}
   
   public void testSetConfigFile_Negative_Plugin() {
      sendContextInitializedEvent(Globals.CONFIG_FILE, 
                   "/org/megatome/frame2/front/config/pluginTag-Single-Param-1_1.xml");

      try {
         ConfigFactory.instance();
         fail();
      } catch (ConfigException e) {
      }
   }
   
   public void testSetLoggerType() {
     // assertTrue(LoggerFactory.instance("foo") instanceof StandardLogger);

      sendContextInitializedEvent(Globals.LOGGER_TYPE,"org.megatome.frame2.log.impl.Log4jLogger");

      assertTrue(LoggerFactory.instance("bar") instanceof Log4jLogger);
   }

   public void testSetLoggerType_Invalid() {

		try {
         sendContextInitializedEvent(Globals.LOGGER_TYPE,"org.megatome.frame2.log.impl.BogusLogger");
         fail();
		} catch (RuntimeException e) {
		}
   }

   public void testSetResourceBundle_NoFlag() {
      assertEmptyBundle(ResourceLocator.getBundle());

      sendContextInitializedEvent(null,null);

      assertEmptyBundle(ResourceLocator.getBundle());
   }

   public void testSetResourceBundle_Frame2Flag() {
      assertEmptyBundle(ResourceLocator.getBundle());

      sendContextInitializedEvent(Globals.RESOURCE_BUNDLE,"frame2-resource");

      ResourceBundle bundle = ResourceLocator.getBundle();

      TestResourceLocator.assertHasHasNot(bundle,"tag.question","alt.tag.question");
   }

   public void testSetResourceBundle_JstlFmtFlag() {
      assertEmptyBundle(ResourceLocator.getBundle());

      sendContextInitializedEvent(Frame2ContextListener.JSTL_CONTEXT_BUNDLE_PARAM,"alt-resource");

      ResourceBundle bundle = ResourceLocator.getBundle();

      TestResourceLocator.assertHasHasNot(bundle,"alt.tag.question","tag.question");
   }

   public void testSetResourceBundle_Frame2TakesPrecedence() {
      assertEmptyBundle(ResourceLocator.getBundle());

      sendContextInitializedEvent(Globals.RESOURCE_BUNDLE,"frame2-resource",Frame2ContextListener.JSTL_CONTEXT_BUNDLE_PARAM,"alt-resource");

      ResourceBundle bundle = ResourceLocator.getBundle();

      TestResourceLocator.assertHasHasNot(bundle,"tag.question","alt.tag.question");
   }

   private void assertEmptyBundle(ResourceBundle bundle) {
      assertNotNull(bundle);
      Enumeration keys = bundle.getKeys();
      assertFalse(keys.hasMoreElements());
   }
}
