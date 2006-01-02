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
package org.megatome.frame2.template.config;

import java.util.HashMap;

import junit.framework.TestCase;

import org.megatome.frame2.template.config.TemplateConfiguration;
import org.megatome.frame2.template.config.TemplateConfigurationInterface;
import org.megatome.frame2.template.config.TemplateDef;


public class TestTemplateConfiguration extends TestCase {

   TemplateConfigurationInterface config;
   
   public TestTemplateConfiguration() {
      super();
   }


   public TestTemplateConfiguration(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      config = new TemplateConfiguration();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
   }
   
   public void testSingleDefNoPut(){
      TemplateDef def1 = new TemplateDef();
      def1.setName("def1");
      def1.setPath("path1");
      HashMap map = new HashMap();
      map.put("def1",def1);
      config.setDefinitions(map);
      
      assertNotNull(config.getDefinition("def1"));
      TemplateDef retDef = config.getDefinition("def1");
      assertTrue(retDef.getName().equals("def1"));
      assertTrue(retDef.getPath().equals("path1"));
      assertTrue(retDef.getPutParams().isEmpty());
   }
   
   public void testSingleDefWithPut(){
      TemplateDef def1 = new TemplateDef();
      def1.setName("def1");
      def1.setPath("path1");
      HashMap puts = new HashMap();
      puts.put("put1","val1");
      def1.setPutParams(puts);
      HashMap map = new HashMap();
      map.put("def1",def1);
      config.setDefinitions(map);
      
      assertNotNull(config.getDefinition("def1"));
      TemplateDef retDef = config.getDefinition("def1");
      assertTrue(retDef.getName().equals("def1"));
      assertTrue(retDef.getPath().equals("path1"));
      assertTrue(retDef.getPutParams().size() == 1);
   }
   
   public void testMultDef(){
      HashMap map = new HashMap();
      TemplateDef def1 = new TemplateDef();
      def1.setName("def1");
      def1.setPath("path1");
        
      TemplateDef def2 = new TemplateDef();
      def2.setName("def2");
      def2.setPath("path2");
         
      map.put("def1",def1);
      map.put("def2",def2);
      config.setDefinitions(map);      
      
      assertNotNull(config.getDefinition("def1"));
      TemplateDef retDef = config.getDefinition("def1");
      assertTrue(retDef.getName().equals("def1"));
      assertTrue(retDef.getPath().equals("path1"));
      assertTrue(retDef.getPutParams().isEmpty());
      
      assertNotNull(config.getDefinition("def2"));
      TemplateDef retDef2 = config.getDefinition("def2");
      assertTrue(retDef2.getName().equals("def2"));
      assertTrue(retDef2.getPath().equals("path2"));
      assertTrue(retDef2.getPutParams().isEmpty());
   }
   
   public void testNullOnUndefinedDef(){
      assertNull(config.getDefinition("def1"));
   }

}
