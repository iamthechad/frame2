package org.megatome.frame2.template;

import junit.framework.TestCase;

import org.megatome.frame2.plugin.PluginInterface;
import org.megatome.frame2.template.TemplatePlugin;

/**
 * @author cjohnston
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestTemplatePlugin extends TestCase {

   /**
    * 
    */
   public TestTemplatePlugin() {
      super();
   }

   /**
    * @param name
    */
   public TestTemplatePlugin(String name) {
      super(name);
   }

   public void testCreateTemplatePluginObject() {
      try {
         TemplatePlugin plugin = new TemplatePlugin();
         assertTrue(plugin instanceof PluginInterface);
      } catch (Exception e) {
         fail();
      }
   }
}
