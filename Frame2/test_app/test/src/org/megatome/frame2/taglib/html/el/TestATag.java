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
package org.megatome.frame2.taglib.html.el;

import javax.servlet.jsp.JspException;

import org.apache.cactus.WebResponse;
import org.megatome.frame2.taglib.html.Constants;
public class TestATag extends BaseHtmlTagTest {
	

	public TestATag(String theName) {
		super(theName);
		_testJspName = "ATag.jsp";
		_expectedLiveJsp = "<a href=\"dude.html\" name=\"dude\">Visit Dude</a>";		
	}
	

	public BaseHtmlTag createTag() {
		return new ATag();
	}

	public void testQueryParamsNoHref() throws Exception {

		ATag tag = new ATag();

		// Only the the queryParam is set.
		pageContext.setAttribute(Constants.QUERY_PARAMS, "${testParams}");

		tag.setPageContext(pageContext);
		tag.setQueryparams("${testParams}");

		try {
			tag.doStartTag();
         tag.doEndTag();
		} catch (JspException e) {
			return;
		}

		fail();

	}

	// Test for creating a valid yahoo.quote with some params.		
	public void testQueryParamsAndHref() throws Exception {
		pageContext.forward(JSP_TEST_DIR + "ATagQueryParams.jsp");
	}

	public void endQueryParamsAndHref(WebResponse webResponse)
		throws Exception {
		String actual = webResponse.getText().trim();

		String expected =
			"<a href=\"http://finance.yahoo.com/q?d=v2&amp;s=msft+sunw+orcl\">quotes</a>";

		assertEquals(expected, actual);

	}
   
   // Test for creating a valid yahoo.quote with some params using param tag.     
   public void testQueryParamTagAndHref() throws Exception {
      pageContext.forward(JSP_TEST_DIR + "ATagQueryParamTag.jsp");
   }

   public void endQueryParamTagAndHref(WebResponse webResponse)
      throws Exception {
      String actual = webResponse.getText().trim();

      String expected =
         "<a href=\"http://finance.yahoo.com/q?d=v2&amp;s=msft+sunw+orcl\">quotes</a>";

      assertEquals(expected, actual);

   }
   
   public void testNegativeQueryParamTagAndParam() throws Exception {
      try {
         pageContext.forward(JSP_TEST_DIR + "ATagQueryParamTagAndQuery.jsp");
      } catch (Exception e) {
         return;
      }
      
      fail();
   }
   
   // Test for creating a valid yahoo.quote with some params.     
   public void testHasBody() throws Exception {
      pageContext.forward(JSP_TEST_DIR + "ATagHasBody.jsp");
   }

   public void endHasBody(WebResponse webResponse)
      throws Exception {
      String actual = webResponse.getText().trim();

      String expected =
           "<a href=\"dude.html\" name=\"dude\">Visit Dude</a>";      

      assertEquals(expected, actual);

   }		
		
}
