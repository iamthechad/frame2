package org.megatome.frame2.util;

import junit.framework.TestCase;

import org.megatome.frame2.util.HTMLEncoder;

/**
 * TestHTMLEncoder.java
 */
public class TestHTMLEncoder extends TestCase {
   public static final String CONTAINS_HTML_CHARS =
    "May the force <replaces angles > be 'tick ' and \" quote \" and & this!.";

   public static final String CONTAINS_HTML_CHARS_OUTPUT =
      "May the force " + HTMLEncoder.LTSTR + 
      "replaces angles "+ HTMLEncoder.GTSTR + 
      " be " + HTMLEncoder.TICKSTR +
      "tick " + HTMLEncoder.TICKSTR + 
      " and " + HTMLEncoder.QUOTESTR +
      " quote " + HTMLEncoder.QUOTESTR + 
      " and " + HTMLEncoder.AMPSTR +
      " this!.";

   public static final String NO_HTML_CHARS = 
      "There should be no substitutions here!.";
      
   public void testHTMLEncoding(){
      String result = HTMLEncoder.encode(CONTAINS_HTML_CHARS);
      assertEquals(result,CONTAINS_HTML_CHARS_OUTPUT);
   }
   
   public void testNoHTMLEncoding(){
      String result = HTMLEncoder.encode(NO_HTML_CHARS);
      assertEquals(result,NO_HTML_CHARS);
   }
}
