/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
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
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.template.config.TemplateDef;


public class TestTemplateDef extends TestCase {
   
   private TemplateDef templateDef;
   
   public TestTemplateDef() {
      super();
   }

   public TestTemplateDef(String name) {
      super(name);
   }

   /**
    * @see junit.framework.TestCase#setUp()
    */
   @Override
protected void setUp() throws Exception {
      super.setUp();
      this.templateDef = new TemplateDef();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   @Override
protected void tearDown() throws Exception {
      super.tearDown();
   }
   
   public void testTemplateDef()
   {
      this.templateDef.setName("name"); //$NON-NLS-1$
      this.templateDef.setPath("/WEB-INF/jsp/my.jsp"); //$NON-NLS-1$
      
      assertEquals("name", this.templateDef.getName()); //$NON-NLS-1$
      assertEquals("/WEB-INF/jsp/my.jsp", this.templateDef.getPath()); //$NON-NLS-1$
   }
   
   public void testTemplateDefPuts()
   {
      Map<String, String> fakePuts = new HashMap<String, String>();
      fakePuts.put("param1", "/WEB-INF/jsp/your.jsp"); //$NON-NLS-1$ //$NON-NLS-2$
      
      this.templateDef.setPutParams(fakePuts);
      
      testTemplateDef();
      Map<String, String> returnedParams = this.templateDef.getPutParams();
      assertTrue(returnedParams.containsKey("param1")); //$NON-NLS-1$
   }
   
   public void testTemplateDefGetParamByName()
   {
   	testTemplateDefPuts();
   	
   	String paramValue = this.templateDef.getPutParam("param1"); //$NON-NLS-1$
   	assertNotNull(paramValue);
   	assertEquals(paramValue, "/WEB-INF/jsp/your.jsp"); //$NON-NLS-1$
   }

}
