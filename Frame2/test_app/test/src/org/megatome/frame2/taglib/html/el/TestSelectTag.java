/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Virtuas Holdings, Inc.  All rights
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

import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;
import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.TagConstants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;

public class TestSelectTag extends BaseHtmlTagTest {

   static String SELECT_VALUE = "${bean}";
   
   public TestSelectTag(String theName) {
      super(theName);   
      _type = TagConstants.QUOTE + Constants.SELECT + TagConstants.QUOTE;
      _testJspName = "SelectTag.jsp";
      _expectedLiveJsp = "<select multiple=\"true\" name=\"yabba\">body</select>"; 
   }
   
   public  BaseHtmlTag createTag() {
      return new SelectTag();      
   }

   public void beginSelectTag(WebRequest request) throws Exception {
   }

   public void testSelectTag() throws Exception{
      SelectTag tag = (SelectTag) createTag();
      
      pageContext.setAttribute(Constants.SELECTED, SELECT_VALUE);
      tag.setPageContext(pageContext);
      tag.setSelected(HTMLHelpers.buildExprAttr(Constants.SELECTED));
      tag.doStartTag();
      tag.doAfterBody();
      tag.doEndTag();
   }

   public void endSelectTag(WebResponse webResponse) throws Exception {
      String expected =
         Constants.SELECT_TAG + TagConstants.RT_ANGLE +
         Constants.SELECT_CLOSE;     
      String actual = webResponse.getText();
      
      assertEquals(expected, actual);
   }

   public void testXhtmlTerminator() throws Exception {
      SelectTag tag = (SelectTag) createTag();
      pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
      tag.setPageContext(pageContext);
      tag.setName("foo");
      tag.doStartTag();
      tag.doEndTag();
   }

   public void endXhtmlTerminator(WebResponse webResponse) throws Exception {
      String expected = "<select name=\"foo\"></select>";
      String actual = webResponse.getText();
      assertEquals(expected, actual);
   }
   
          
}
