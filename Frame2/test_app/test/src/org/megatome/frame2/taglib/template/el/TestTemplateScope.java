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
package org.megatome.frame2.taglib.template.el;

import javax.servlet.jsp.JspException;

import org.apache.cactus.JspTestCase;
import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.taglib.template.TemplateHelper;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.TemplatePlugin;
import org.megatome.frame2.template.config.TemplateDef;

public class TestTemplateScope extends JspTestCase {

   private InsertTag _insertTag;
   private TemplateDef _def = null;
   private static final String TEMPLATE1 = "template1";
   /**
    * @param arg0
    */
   public TestTemplateScope(String arg0) {
      super(arg0);
   }

   /* (non-Javadoc)
    * @see junit.framework.TestCase#setUp()
    */
   protected void setUp() throws Exception {
      super.setUp();
      TemplateHelper.clearPageContextDefinition(pageContext,TEMPLATE1);
      _insertTag = new InsertTag();
      _insertTag.setDefinition(TEMPLATE1);
   }


   
   private void configureTemplateDef() {
      try {
         _def = TemplateConfigFactory.instance().getDefinition(TEMPLATE1);
         assertNotNull(_def);
         pageContext.setAttribute(TemplateConstants.FRAME2_INSERT_KEY, _def);
      } catch (TemplateException e) {
         fail();
      }
   }

   public void testNoOverrides() {
      configureTemplateDef();
      String getValue = getParameterValue("header");

      assertNotNull(getValue);
      assertEquals(TemplatePlugin.getConfigDir() + "yyy.jsp", getValue);

      getValue = getParameterValue("nav");

      assertNotNull(getValue);
      assertEquals(TemplatePlugin.getConfigDir() + "yyy.jsp", getValue);

      getValue = getParameterValue("footer");

      assertNotNull(getValue);
      assertEquals(TemplatePlugin.getConfigDir() + "yyy.jsp", getValue);
   }

   public void testDefaultPutScope() {
      configureTemplateDef();
      PutTag putTag = new PutTag();
      putTag.setParent(_insertTag);
      putTag.setPageContext(pageContext);

      putTag.setName("header");
      putTag.setPath("pageScope");

      try {
         putTag.doStartTag();
      } catch (JspException e) {
         fail();
      }

      String getValue = getParameterValue("header");

      assertNotNull(getValue);
      assertEquals(TemplatePlugin.getConfigDir() + "pageScope", getValue);
   }

   public void testBadScope() {
      configureTemplateDef();
      PutTag putTag = new PutTag();
      putTag.setParent(_insertTag);
      putTag.setPageContext(pageContext);

      putTag.setName("header");
      putTag.setPath("pageScope");
      putTag.setScope("badScope");

      try {
         putTag.doStartTag();
         fail();
      } catch (JspException e) {
      }
   }

   public void testNegativeOverrideAtPageScope() {
      configureTemplateDef();
      PutTag putTag = new PutTag();
      putTag.setParent(_insertTag);
      putTag.setPageContext(pageContext);

      putTag.setName("header");
      putTag.setPath("pageScope");
      putTag.setScope("page");

      try {
         putTag.doStartTag();
         fail();
      } catch (JspException e) {
      } 
   }

   public void testOverrideAtRequestScope() {
      configureTemplateDef();
      PutTag putTag = new PutTag();
      putTag.setParent(_insertTag);
      putTag.setPageContext(pageContext);

      putTag.setName("header");
      putTag.setPath("requestScope");
      putTag.setScope("request");

      try {
         putTag.doStartTag();
      } catch (JspException e) {
         fail();
      }

      String getValue = getParameterValue("header");

      assertNotNull(getValue);
      assertEquals(TemplatePlugin.getConfigDir() + "requestScope", getValue);
   }

   public void testOverrideAtSessionScope() {
      configureTemplateDef();
      PutTag putTag = new PutTag();
      putTag.setParent(_insertTag);
      putTag.setPageContext(pageContext);

      putTag.setName("header");
      putTag.setPath("sessionScope");
      putTag.setScope("session");

      try {
         putTag.doStartTag();
      } catch (JspException e) {
         fail();
      }

      String getValue = getParameterValue("header");

      assertNotNull(getValue);
      assertEquals(TemplatePlugin.getConfigDir() + "sessionScope", getValue);
   }


   public void testOverrideRequestSessionScope() {
      configureTemplateDef();
      PutTag putTag1 = new PutTag();
      putTag1.setParent(_insertTag);
      putTag1.setPageContext(pageContext);

      putTag1.setName("header");
      putTag1.setPath("pageScope");
      putTag1.setScope("request");

      PutTag putTag2 = new PutTag();
      putTag2.setParent(_insertTag);
      putTag2.setPageContext(pageContext);

      putTag2.setName("footer");
      putTag2.setPath("sessionScope");
      putTag2.setScope("session");

      try {
         putTag1.doStartTag();
         putTag2.doStartTag();
      } catch (JspException e) {
         fail();
      }

      String getHeaderValue = getParameterValue("header");
      String getFooterValue = getParameterValue("footer");

      assertNotNull(getHeaderValue);
      assertEquals(TemplatePlugin.getConfigDir() + "pageScope", getHeaderValue);

      assertNotNull(getFooterValue);
      assertEquals(
         TemplatePlugin.getConfigDir() + "sessionScope",
         getFooterValue);
   }

   public void testApplicationScope() {
      configureTemplateDef();
      PutTag putTag = new PutTag();
      putTag.setParent(_insertTag);
      putTag.setPageContext(pageContext);

      putTag.setName("header");
      putTag.setPath("applicationScope");
      putTag.setScope("application");

      try {
         putTag.doStartTag();
      } catch (JspException e) {
         fail();
      }

      String getValue = getParameterValue("header");

      assertNotNull(getValue);
      assertEquals(
         TemplatePlugin.getConfigDir() + "applicationScope",
         getValue);
   }

   public void testNegativeOverridePut() {
      configureTemplateDef();
      PutTag putTag = new PutTag();
      putTag.setParent(_insertTag);
      putTag.setPageContext(pageContext);

      putTag.setName("badName");
      putTag.setPath("sessionScope");
      putTag.setScope("session");

      try {
         putTag.doStartTag();
         fail();
      } catch (JspException e) {
      }
   }

   private String getParameterValue(String name) {
      String insertDef = _insertTag.getDefinition();
      TemplateDef def = null;
      try {
         def = TemplateConfigFactory.instance().getDefinition(insertDef);
      } catch (TemplateException e) {
         // We'll throw an error in just a minute, so an empty catch is OK
      }

      if (def == null) {
         fail();
      }

      return def.getPutParam(name, pageContext);
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
      TemplateHelper.clearPageContextDefinition(pageContext,TEMPLATE1);
   }

}
