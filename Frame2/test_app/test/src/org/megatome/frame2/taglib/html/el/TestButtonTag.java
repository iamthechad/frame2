/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
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
import org.megatome.frame2.tagsupport.TagConstants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;

public class TestButtonTag extends BaseInputTagTest { 

	public TestButtonTag(String theName) {
		super(theName);   
		_type = TagConstants.QUOTE + Constants.BUTTON + TagConstants.QUOTE;
		_testJspName = "ButtonTag.jsp";
		_expectedLiveJsp = "<input type=\"button\" name=\"foo\" onfocus=\"true\" value=\"Stop clicking me\">";                      
	}
	
	public  BaseHtmlTag createTag() {
		return new ButtonTag();      
	}

   public void testButtonTag_Default() throws Exception{
      ButtonTag tag = (ButtonTag) createTag();

      tag.setPageContext(pageContext);
      tag.doStartTag();
      tag.doEndTag();
   }

   public void endButtonTag_Default(WebResponse webResponse) throws Exception {
      String expected = "<input type=\"button\">";
      String actual = webResponse.getText();
      
      assertEquals(expected, actual);
   }

   public void testButtonTag() throws Exception{
      ButtonTag tag = (ButtonTag) createTag();

      tag.setPageContext(pageContext);
      tag.setValue("MY_BUTTON");
      tag.doStartTag();
      tag.doEndTag();
   }

   public void endButtonTag(WebResponse webResponse) throws Exception {
      String expected = "<input type=\"button\" value=\"MY_BUTTON\">";
      String actual = webResponse.getText();
      
      assertEquals(expected, actual);
   }

   public void testButtonTag_WhiteSpace() throws Exception{
      ButtonTag tag = (ButtonTag) createTag();

      tag.setPageContext(pageContext);
      tag.setValue("   ");
      tag.doStartTag();
      tag.doEndTag();
   }

   public void endButtonTag_WhiteSpace(WebResponse webResponse) throws Exception {
      String expected = "<input type=\"button\">";
      String actual = webResponse.getText();
      
      assertEquals(expected, actual);
   }
   
   public void testXhtmlTerminator() throws Exception {
      ButtonTag tag = (ButtonTag) createTag();
      pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
      tag.setPageContext(pageContext);
      tag.setValue("MY_BUTTON");
      tag.doStartTag();
      tag.doEndTag();

   }

   public void endXhtmlTerminator(WebResponse webResponse) throws Exception {
      String expected = "<input type=\"button\" value=\"MY_BUTTON\"/>";
      String actual = webResponse.getText();
      assertEquals(expected, actual);
   }

   public void endAnAttribute(WebResponse webResponse) throws Exception {

		String expected =
			Constants.INPUT_TYPE + _type 
				+ HTMLHelpers.buildHtmlAttr(Constants.ACCESS_KEY, ATTR_VALUE_1)
     			+ HTMLHelpers.buildHtmlAttr(Constants.VALUE, "")
				+ TagConstants.RT_ANGLE;		

		String actual = webResponse.getText();

		assertEquals(expected, actual);

	}
}
