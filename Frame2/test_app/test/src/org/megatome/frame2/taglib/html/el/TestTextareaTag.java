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

import org.apache.cactus.WebResponse;
import org.megatome.frame2.taglib.html.Constants;


public class TestTextareaTag extends BaseHtmlTagTest { 
	
	public TestTextareaTag(String theName) {
		super(theName);       
		_testJspName = "TextareaTag.jsp";
		_expectedLiveJsp = "<textarea cols=\"7\" name=\"yabba\" rows=\"3\">The avs are better than the ugly redwings</textarea>";	
	}
	
	public  BaseHtmlTag createTag() { 
		return new TextareaTag();
	}

   public void testXhtmlTerminator_withBody() throws Exception {
      TextareaTag tag = (TextareaTag) createTag();
      pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
      tag.setPageContext(pageContext);
      tag.setName("foo");
      tag.setRows("4");
      tag.setValue("MyTextArea");
      tag.doStartTag();
      tag.doEndTag();
   }

   public void endXhtmlTerminator_withBody(WebResponse webResponse) throws Exception {
      String expected = "<textarea name=\"foo\" rows=\"4\">MyTextArea</textarea>";
      String actual = webResponse.getText();
      assertEquals(expected, actual);
   }
   
   public void testXhtmlTerminator_withoutBody() throws Exception {
      TextareaTag tag = (TextareaTag) createTag();
      pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
      tag.setPageContext(pageContext);
      tag.setName("foo");
      tag.setRows("4");
      tag.doStartTag();
      tag.doEndTag();
   }

   public void endXhtmlTerminator_withoutBody(WebResponse webResponse) throws Exception {
      String expected = Constants.TEXTAREA_OPEN + " name=\"foo\" rows=\"4\">" + Constants.TEXTAREA_CLOSE;
      String actual = webResponse.getText();
      assertEquals(expected, actual);
   }	
   // Test for creating a valid yahoo.quote with some params.     
   public void testHasBody() throws Exception {
      pageContext.forward(JSP_TEST_DIR + "TextareaTagHasBody.jsp");
   }

   public void endHasBody(WebResponse webResponse)
      throws Exception {
      String actual = webResponse.getText().trim();

      String expected =
           "<textarea cols=\"7\" name=\"yabba\" rows=\"3\">red wings suck</textarea>";       

      assertEquals(expected, actual);

   }     	
}
