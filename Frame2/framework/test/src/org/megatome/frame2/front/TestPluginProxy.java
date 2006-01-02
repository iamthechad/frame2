/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2006 Megatome Technologies.  All rights
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
package org.megatome.frame2.front;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.front.config.PluginDef;
import org.megatome.frame2.plugin.MockPluginInterface;
import org.megatome.frame2.plugin.PluginInterface;

/**
 * @author cjohnston
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestPluginProxy extends TestCase {

   PluginProxy proxy = null;
   /**
    * Constructor for PluginProxyTest.
    * @param name
    */
   public TestPluginProxy(String name) {
      super(name);
   }

   /*
    * @see TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      PluginDef pluginDef = new PluginDef();
      pluginDef.setName("PluginName");
      pluginDef.setType("PluginType");
      HashMap testParams = new HashMap();
      testParams.put("param1", "value1");
      pluginDef.setInitParams(testParams);
      MockPluginInterface mockPlugin = new MockPluginInterface(100);
      
      proxy = new PluginProxy(pluginDef, mockPlugin);
   }

   /*
    * @see TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }

   public void testGetName() {
      assertTrue(proxy.getName().equals("PluginName"));
   }

   public void testGetType() {
      assertTrue(proxy.getType().equals("PluginType"));
   }

   public void testGetInitParams() {
      Map params = proxy.getInitParams();
      assertTrue(params.size() == 1);
      assertTrue(((String)params.get("param1")).equals("value1"));
   }

   public void testGetPlugin() {
      PluginInterface plugin = proxy.getPlugin();
      assertNotNull(plugin);
      assertTrue(plugin instanceof MockPluginInterface);
   }
   
   public void testInitThrewException() {
      assertFalse(proxy.initThrewException());
      proxy.setInitThrewException(true);
      assertTrue(proxy.initThrewException());
   }

}
