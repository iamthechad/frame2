
package org.megatome.frame2.validator;

import junit.framework.TestCase;

import org.megatome.frame2.plugin.PluginInterface;
import org.megatome.frame2.validator.CommonsValidatorPlugin;

/**
 * @author cjohnston
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestCommonsValidatorPlugin extends TestCase {

   /**
    * 
    */
   public TestCommonsValidatorPlugin() {
      super();
   }

   /**
    * @param name
    */
   public TestCommonsValidatorPlugin(String name) {
      super(name);
   }
   
   public void testCommonsValidatorPlugin() {
      CommonsValidatorPlugin plugin = new CommonsValidatorPlugin();
      
      assertTrue(plugin instanceof PluginInterface);            
   }

}
