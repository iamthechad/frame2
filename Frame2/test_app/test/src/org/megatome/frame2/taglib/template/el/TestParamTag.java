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

import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.cactus.WebResponse;
import org.megatome.frame2.tagsupport.BaseFrame2Tag;

public class TestParamTag extends BaseTemplateTagTest {

   ParamTag paramTag = null;
   /**
    * @param arg0
    */
   public TestParamTag(String arg0) {
      super(arg0);
      _testJspName = "ParamTag.jsp";
      _testNegativeJspName = "NegativeParamTag.jsp";
      _expectedLiveJsp = "value<br>";
      _expectedNegativeLiveJsp = "Parameter name in param cannot be empty";
   }

   /* (non-Javadoc)
   	 * @see junit.framework.TestCase#setUp()
   	 */
   public void setUp() throws Exception {
      super.setUp();
      paramTag = new ParamTag();
   }

   /* (non-Javadoc)
    * @see junit.framework.TestCase#tearDown()
    */
   public void tearDown() throws Exception {
      super.tearDown();
   }

   public void testParamTag() {
      paramTag.setName("paramName");
      paramTag.setValue("paramValue");

      assertEquals("paramName", paramTag.getName());
      assertEquals("paramValue", paramTag.getValue());

      InsertTag parent = new InsertTag();

      paramTag.setParent(parent);

      try {
         paramTag.doStartTag();
      } catch (JspException e) {
         fail();
      }

      Map inputParams = parent.getParameterMap();
      assertNotNull(inputParams);
      assertTrue(inputParams.containsKey("paramName"));
      assertEquals("paramValue", parent.getParameter("paramName"));

      String[] paramValues = parent.getParameterValues("paramName");
      assertNotNull(paramValues);
      assertTrue(paramValues.length == 1);
   }

   public void testMultiParamTag() {
      paramTag.setName("paramName");
      paramTag.setValue("paramValue");

      assertEquals("paramName", paramTag.getName());
      assertEquals("paramValue", paramTag.getValue());

      ParamTag paramTag2 = new ParamTag();

      paramTag2.setName("paramName2");
      paramTag2.setValue("paramValue2");

      assertEquals("paramName2", paramTag2.getName());
      assertEquals("paramValue2", paramTag2.getValue());

      InsertTag parent = new InsertTag();

      paramTag.setParent(parent);
      paramTag2.setParent(parent);

      try {
         paramTag.doStartTag();
         paramTag2.doStartTag();
      } catch (JspException e) {
         fail();
      }

      Map inputParams = parent.getParameterMap();
      assertNotNull(inputParams);
      assertTrue(inputParams.containsKey("paramName"));
      assertEquals("paramValue", parent.getParameter("paramName"));

      String[] paramValues = parent.getParameterValues("paramName");
      assertNotNull(paramValues);
      assertTrue(paramValues.length == 1);

      assertTrue(inputParams.containsKey("paramName2"));
      assertEquals("paramValue2", parent.getParameter("paramName2"));

      paramValues = parent.getParameterValues("paramName2");
      assertNotNull(paramValues);
      assertTrue(paramValues.length == 1);
   }

   public void testMultiValueParamTag() {
      paramTag.setName("paramName");
      paramTag.setValue("paramValue");

      assertEquals("paramName", paramTag.getName());
      assertEquals("paramValue", paramTag.getValue());

      ParamTag paramTag2 = new ParamTag();

      paramTag2.setName("paramName");
      paramTag2.setValue("paramValue2");

      assertEquals("paramName", paramTag2.getName());
      assertEquals("paramValue2", paramTag2.getValue());

      InsertTag parent = new InsertTag();

      paramTag.setParent(parent);
      paramTag2.setParent(parent);

      try {
         paramTag.doStartTag();
         paramTag2.doStartTag();
      } catch (JspException e) {
         fail();
      }

      Map inputParams = parent.getParameterMap();
      assertNotNull(inputParams);
      assertTrue(inputParams.containsKey("paramName"));
      assertEquals("paramValue", parent.getParameter("paramName"));

      String[] paramValues = parent.getParameterValues("paramName");
      assertNotNull(paramValues);
      assertTrue(paramValues.length == 2);
      assertEquals(paramValues[0], "paramValue");
      assertEquals(paramValues[1], "paramValue2");
   }

   public void testParamTagIsChild() {
      paramTag.setName("paramName");
      paramTag.setValue("paramValue");

      assertEquals("paramName", paramTag.getName());
      assertEquals("paramValue", paramTag.getValue());

      InsertTag parent = new InsertTag();

      paramTag.setParent(parent);

      try {
         paramTag.doStartTag();
      } catch (JspException e) {
         fail();
      }
   }

   public void testNegativeParamTagIsChild() {
      paramTag.setName("paramName");
      paramTag.setValue("paramValue");

      assertEquals("paramName", paramTag.getName());
      assertEquals("paramValue", paramTag.getValue());

      try {
         paramTag.doStartTag();
         fail();
      } catch (JspException e) {
      }
   }

   //	Test for creating a valid yahoo.quote with some params.		
   public void testLiveJspMultiParams() throws Exception {
      pageContext.forward(JSP_TEST_DIR + "MultiParamTag.jsp");
   }

   public void endLiveJspMultiParams(WebResponse webResponse)
      throws Exception {
      String actual = webResponse.getText().trim();

      String expected =
         "value<br>value2<br>value3<br>";

      assertEquals(expected, actual);

   }
   
//	Test for creating a valid yahoo.quote with some params.		
	public void testLiveJspMultiParamNames() throws Exception {
		pageContext.forward(JSP_TEST_DIR + "ParamTagMultiNames.jsp");
	}

	public void endLiveJspMultiParamNames(WebResponse webResponse)
		throws Exception {
		String actual = webResponse.getText().trim();

		String expected =
			"value<br>\r\n\r\ndude_value<br>";

		assertEquals(expected, actual);

	}

   /* (non-Javadoc)
    * @see org.megatome.frame2.taglib.template.el.BaseTemplateTagTest#createTag()
    */
   public BaseFrame2Tag createTag() {
      return null;
   }

}
