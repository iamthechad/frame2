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

import org.apache.cactus.WebResponse;
import org.megatome.frame2.taglib.template.TemplateConstants;
import org.megatome.frame2.taglib.template.TemplateHelper;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.config.TemplateDef;

public class TestPutTag extends BaseTemplateTagTest {

   private static final String TEMPLATE1 = "template1";
   private PutTag putTag = null;
   /**
    * @param arg0
    */
   public TestPutTag(String arg0) {
      super(arg0);
      _testJspName = "PutTag.jsp";
      _testNegativeJspName = "NegativePutTag.jsp";
      _expectedLiveJsp = "HeaderRequestScope.jsp<br>";
      _expectedNegativeLiveJsp =
         "Cannot override param header: cannot have an empty path";
   }

   /* (non-Javadoc)
    * @see junit.framework.TestCase#setUp()
    */
   public void setUp() throws Exception {
      super.setUp();
      TemplateHelper.clearPageContextDefinition(pageContext,TEMPLATE1);
      putTag = new PutTag();
   }

   /* (non-Javadoc)
    * @see junit.framework.TestCase#tearDown()
    */
  protected void tearDown() throws Exception {
      super.tearDown();
      TemplateHelper.clearPageContextDefinition(pageContext,TEMPLATE1);
   }
   
   public void testPutTag() {
      putTag.setName("putName");
      putTag.setPath("some.jsp");
      putTag.setScope("page");

      assertEquals("putName", putTag.getName());
      assertEquals("some.jsp", putTag.getPath());
      assertEquals("page", putTag.getScope());
   }

   public void testputTagIsChild() {
      putTag.setName("header");
      putTag.setPath("some.jsp");
      putTag.setScope("request");

      assertEquals("header", putTag.getName());
      assertEquals("some.jsp", putTag.getPath());
      assertEquals("request", putTag.getScope());

      InsertTag parent = new InsertTag();

      putTag.setParent(parent);

      try {
         TemplateDef def =
            TemplateConfigFactory.instance().getDefinition("template1");
         assertNotNull(def);
         pageContext.setAttribute(TemplateConstants.FRAME2_INSERT_KEY, def);

         putTag.setPageContext(pageContext);

         putTag.doStartTag();
      } catch (TemplateException e) {
         fail();
      } catch (JspException e) {
         fail();
      }
   }

   public void testNegativePutTagIsChild() {
      putTag.setName("putName");
      putTag.setPath("some.jsp");
      putTag.setScope("request");

      assertEquals("putName", putTag.getName());
      assertEquals("some.jsp", putTag.getPath());
      assertEquals("request", putTag.getScope());
      try {
         putTag.doStartTag();
         fail();
      } catch (JspException e) {
      }
   }

   public void testNegativePutScope() {
      try {
         pageContext.forward(JSP_TEST_DIR + "NegativePutTagScope.jsp");
         fail();
      } catch (Exception e) {
			assertTrue(e.getMessage().indexOf("Scope 'dude' is invalid") != -1);
      } 
   }
   
	public void testNegativePutName() {
		try {
			pageContext.forward(JSP_TEST_DIR + "NegativePutTagName.jsp");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().indexOf("Cannot override param notintemplate: does not exist in template definition template_headeronly") != -1);
		} 
	}
	
	public void testPutInvalidPath() throws Exception {
		pageContext.forward(JSP_TEST_DIR + "PutTagInvalidPath.jsp");
	}

	public void endPutInvalidPath(WebResponse webResponse)
		throws Exception {
		String actual = webResponse.getText().trim();

		String expected =	"This should be the only text";

		assertEquals(expected, actual);

	}

   /* (non-Javadoc)
    * @see org.megatome.frame2.taglib.template.el.BaseTemplateTagTest#createTag()
    */
   public BaseFrame2Tag createTag() {
      return null;
   }

}
