package org.megatome.frame2.plugin;

import java.util.HashMap;

import org.megatome.frame2.plugin.PluginException;

import servletunit.frame2.MockFrame2TestCase;

/**
 * @author cjohnston
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestMockPluginInterface extends MockFrame2TestCase {

   MockPluginInterface _pluginInterface;
	/**
	 * Constructor for MockPluginInterfaceTest.
	 * @param name
	 */
	public TestMockPluginInterface(String name) {
		super(name);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
      _pluginInterface = new MockPluginInterface(100);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInit() {
      try {
		   _pluginInterface.init(getContext(), new HashMap());
	   } catch (PluginException e) {
        fail("Unexpected PluginException");
	   }
      
      assertEquals(_pluginInterface.getStartIndex(), 10);
	}
    
   public void testInitWithException()
   {
       try {
           _pluginInterface.init(null, new HashMap());
        }
        catch (PluginException e) {
          return;
        }
        fail("Expected PluginException");
   }

	public void testDestroy() {
        try {
			_pluginInterface.destroy(getContext(), new HashMap());
		} catch (PluginException e) {
			fail("Unexpected PluginException");
		}
      
        assertEquals(_pluginInterface.getStartIndex(), 20);
	}
    
   public void testDestroyWithException()
   {
       try {
           _pluginInterface.destroy(null, new HashMap());
        }
        catch (PluginException e) {
          return;
        }
        fail("Expected PluginException");
   }
}
