/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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

import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.taglib.template.TemplateHelper;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.config.TemplateDef;

public class TestGetTag extends BaseTemplateTagTest {
   private static final String TEMPLATE1 = "template1";
   GetTag getTag = null;
   /**
    * @param arg0
    */
   public TestGetTag(String arg0) {
      super(arg0);
		_testJspName = "GetTag.jsp";
		_testNegativeJspName = "NegativeGetTag.jsp";
		_expectedLiveJsp = "Header2.jsp<br>";		
		_expectedNegativeLiveJsp = "Could not access entry 'dude' for template \"template_negativeget_name\"";
   }

   /* (non-Javadoc)
   	* @see org.megatome.frame2.taglib.template.el.BaseTemplateTagTest#createTag()
   	*/
   public BaseFrame2Tag createTag() {
      return new GetTag();
   }


   public void testGetTag() {
      getTag.setName("some.jsp");

      assertEquals("some.jsp", getTag.getName());
   }

   public void testGetTagWithDef() {
      getTag.setName("header");
      try {
         TemplateDef def =
            TemplateConfigFactory.instance().getDefinition("template1");
         assertNotNull(def);
         pageContext.setAttribute(TemplateConstants.FRAME2_INSERT_KEY, def);

         getTag.setPageContext(pageContext);

         getTag.doStartTag();
      } catch (TemplateException e) {
         fail();
      } catch (JspException e) {
         fail();
      }
   }

   public void testNegativeGetTagIsChild() {
      getTag.setName("some.jsp");

      assertEquals("some.jsp", getTag.getName());

      try {
         getTag.setPageContext(pageContext);
         getTag.doStartTag();
         fail();
      } catch (JspException e) {
      }
   }

   public void testNegativeGetTagNoTemplate() {
      getTag.setName("some.jsp");

      assertEquals("some.jsp", getTag.getName());

      InsertTag parent = new InsertTag();
      parent.setDefinition("definition");

      getTag.setParent(parent);
      getTag.setPageContext(pageContext);

      try {
         getTag.doStartTag();
         fail();
      } catch (JspException e) {
      }
   }

   public void testNegativeGetTag() {

      InsertTag insertTag = new InsertTag();
      insertTag.setDefinition("template1");

      getTag.setName("bar-none");

      getTag.setParent(insertTag);
      getTag.setPageContext(pageContext);
      try {
         getTag.doStartTag();
         fail();
      } catch (JspException e) {
      }
   }

   public void testNegativeGetTagNoDefinition() {

      InsertTag insertTag = new InsertTag();
      insertTag.setDefinition("nothing");

      getTag.setName("bar-put");

      getTag.setParent(insertTag);
      getTag.setPageContext(pageContext);
      try {
         getTag.doStartTag();
         fail();
      } catch (JspException e) {
      }
   }
   /* (non-Javadoc)
    * @see junit.framework.TestCase#setUp()
    */
   public void setUp() throws Exception {
      super.setUp();
      TemplateHelper.clearPageContextDefinition(pageContext,TEMPLATE1);
      getTag = new GetTag();
   }

   /**
    * @see junit.framework.TestCase#tearDown()
    */
   protected void tearDown() throws Exception {
      super.tearDown();
      TemplateHelper.clearPageContextDefinition(pageContext,TEMPLATE1);
   }

}
