package org.megatome.frame2.util;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import junit.framework.TestCase;

import org.megatome.frame2.Globals;
import org.megatome.frame2.util.ResourceLocator;

import servletunit.ServletContextSimulator;

public class TestResourceLocator extends TestCase {

   protected void tearDown() {
      ResourceLocator.setBasename(null);
   }

   public void testDefaultBundle() throws Exception {
      ResourceBundle bundle = ResourceLocator.getBundle();

      assertNotNull(bundle);
      Enumeration keys = bundle.getKeys();
      assertFalse(keys.hasMoreElements());
   }

   public void testDefaultBundle_FR() throws Exception {
      ResourceBundle bundle = ResourceLocator.getBundle(Locale.FRENCH);

      assertNotNull(bundle);
      Enumeration keys = bundle.getKeys();
      assertFalse(keys.hasMoreElements());
   }

   public void testResetBundleName() throws Exception {
      ResourceLocator.setBasename("frame2-resource");
      
      ResourceBundle bundle = ResourceLocator.getBundle();
      assertHasHasNot(bundle,"tag.question","alt.tag.question");
      bundle = ResourceLocator.getBundle(Locale.FRENCH);
      assertHasHasNot(bundle,"tag.question","alt.tag.question");
      
      ResourceLocator.setBasename("alt-resource");
      
      bundle = ResourceLocator.getBundle();
      
      assertHasHasNot(bundle,"alt.tag.question","tag.question");      
      bundle = ResourceLocator.getBundle(Locale.FRENCH);
      assertHasHasNot(bundle,"alt.tag.question","tag.question");
   }

   static public void assertHasHasNot(ResourceBundle bundle,String hasKey,String hasNotKey) {
      assertNotNull(bundle);
      assertNotNull(bundle.getString(hasKey));
      try {
         bundle.getString(hasNotKey);
         fail();
      } catch ( MissingResourceException e ) {
      }
   }

   public void testSetResourceBundle() throws Exception {
      ServletContextSimulator context = new ServletContextSimulator();
      context.setInitParameter(Globals.RESOURCE_BUNDLE,"frame2-resource");

      ResourceLocator.setBasename("frame2-resource");

      ResourceBundle bundle = ResourceLocator.getBundle();
      
      assertNotNull(bundle);

      try {
			assertEquals("{0}Is this Frame2?",bundle.getString("tag.question"));
         assertEquals("{0}Is this {1} Frame2?",bundle.getString("tag.question.with.parm"));
		} catch (MissingResourceException e) {
         fail();
		}
   }

   public void testSetResourceBundle_FR() throws Exception {
      ResourceLocator.setBasename("frame2-resource");

      ResourceBundle bundle = ResourceLocator.getBundle(Locale.FRENCH);

      assertNotNull(bundle);

      try {
         assertEquals("{0}Êtes-vous Bob?",bundle.getString("tag.question"));
         assertEquals("{0}Êtes-vous {1} Bob?",bundle.getString("tag.question.with.parm"));
      } catch (MissingResourceException e) {
         fail();
      }
      
      assertSame(bundle,ResourceLocator.getBundle(Locale.FRENCH));
   }


   public void testSetResourceBundle_DE() throws Exception {
      ResourceLocator.setBasename("frame2-resource");

      ResourceBundle bundle = ResourceLocator.getBundle(Locale.GERMANY);

      assertNotNull(bundle);

      try {
         assertEquals("{0}Sind Sie Bob?",bundle.getString("tag.question"));
         assertEquals("{0}Sind Sie {1} Bob?",bundle.getString("tag.question.with.parm"));
      } catch (MissingResourceException e) {
         fail();
      }
      
      assertSame(bundle,ResourceLocator.getBundle(Locale.GERMAN));
   }
}
