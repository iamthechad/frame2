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

import java.util.ArrayList;

import org.apache.cactus.JspTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;
import org.megatome.frame2.taglib.html.Constants;
import org.megatome.frame2.tagsupport.util.HTMLHelpers;

public class TestOptionsTag extends JspTestCase {

   String _testJspName;
   String _expectedLiveJsp;
   static final String JSP_TEST_DIR = "/test/tags/";
   String _testMultJspName;
   public TestOptionsTag(String name) {
      super(name);
      _testJspName = "OptionsTag.jsp";
      _testMultJspName = "OptionsMultTag.jsp";
      _expectedLiveJsp = "<option value=\"one\">one</option>" +
                         "<option value=\"two\">two</option>" +
                         "<option value=\"radioValue\">radioValue</option>" +
                         "<option value=\"me\">me</option>";
   }
   
   public  BaseHtmlTag createTag() {
      return new OptionsTag();      
   }
   
   public void beginOptionsTag(WebRequest request) throws Exception {
   }

   public void testOptionsTagList() throws Exception{
      OptionsTag tag = (OptionsTag) createTag();
      ArrayList valueList = new ArrayList();
      ArrayList displayValueList = new ArrayList();
      valueList.add("one");
      valueList.add("two");
      displayValueList.add("me");
      displayValueList.add("you");
      pageContext.setAttribute(Constants.VALUE, valueList);
      pageContext.setAttribute(Constants.DISPLAY_VALUE, displayValueList);      
      tag.setPageContext(pageContext);
      tag.setValue(HTMLHelpers.buildExprAttr(Constants.VALUE));
      tag.setDisplayvalue(HTMLHelpers.buildExprAttr(Constants.DISPLAY_VALUE));
      tag.doStartTag();
      tag.doEndTag();

   }

   public void endOptionsTagList(WebResponse webResponse) throws Exception{
      String expected =
         Constants.OPTION_TAG + " value=\"one\">me" + Constants.OPTION_CLOSE 
         + Constants.OPTION_TAG + " value=\"two\">you" + Constants.OPTION_CLOSE;
      String actual = webResponse.getText();
      
      assertEquals(expected, actual);
   }

   public void testOptionsTagStringArray() throws Exception{
      OptionsTag tag = (OptionsTag) createTag();
      String[] valueArr = {"one", "two"};
      String[] displayValueArr = {"me", "you"};
      pageContext.setAttribute(Constants.VALUE, valueArr);
      pageContext.setAttribute(Constants.DISPLAY_VALUE, displayValueArr);      
      tag.setPageContext(pageContext);
      tag.setValue(HTMLHelpers.buildExprAttr(Constants.VALUE));
      tag.setDisplayvalue(HTMLHelpers.buildExprAttr(Constants.DISPLAY_VALUE));
      tag.doStartTag();
      tag.doEndTag();

   }

   public void endOptionsTagStringArray(WebResponse webResponse) throws Exception{
      String expected =
         Constants.OPTION_TAG + " value=\"one\">me" + Constants.OPTION_CLOSE 
         + Constants.OPTION_TAG + " value=\"two\">you" + Constants.OPTION_CLOSE;
      String actual = webResponse.getText();
      
      assertEquals(expected, actual);
   }

   public void testXhtmlTerminator_withBody() throws Exception {
      OptionsTag tag = (OptionsTag) createTag();
      ArrayList valueList = new ArrayList();
      ArrayList displayValueList = new ArrayList();
      valueList.add("one");
      valueList.add("two");
      displayValueList.add("me");
      displayValueList.add("you");
      pageContext.setAttribute(Constants.VALUE, valueList);
      pageContext.setAttribute(Constants.DISPLAY_VALUE, displayValueList);      
      pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
      tag.setPageContext(pageContext);
      tag.setValue(HTMLHelpers.buildExprAttr(Constants.VALUE));
      tag.setDisplayvalue(HTMLHelpers.buildExprAttr(Constants.DISPLAY_VALUE));
      tag.doStartTag();
      tag.doEndTag();

   }

   public void endXhtmlTerminator_withBody(WebResponse webResponse) throws Exception {
      String expected =
         Constants.OPTION_TAG + " value=\"one\">me" + Constants.OPTION_CLOSE 
         + Constants.OPTION_TAG + " value=\"two\">you" + Constants.OPTION_CLOSE;
      String actual = webResponse.getText();
      
      assertEquals(expected, actual);
   }
   public void testXhtmlTerminator_withoutBody() throws Exception {
      OptionsTag tag = (OptionsTag) createTag();
      ArrayList valueList = new ArrayList();
      valueList.add("one");
      valueList.add("two");
      pageContext.setAttribute(Constants.VALUE, valueList);
      pageContext.setAttribute(Constants.XHTML_KEY, Constants.TRUE);
      tag.setPageContext(pageContext);
      tag.setValue(HTMLHelpers.buildExprAttr(Constants.VALUE));
      tag.doStartTag();
      tag.doEndTag();

   }

   public void endXhtmlTerminator_withoutBody(WebResponse webResponse) throws Exception {
      String expected =
         Constants.OPTION_TAG + " value=\"one\"/>"  
         + Constants.OPTION_TAG + " value=\"two\"/>";
      String actual = webResponse.getText();
      
      assertEquals(expected, actual);
   }

   // Test out tag in a real live jsp.
   public void testLiveJsp()
      throws Exception {
      pageContext.forward(JSP_TEST_DIR + _testJspName);
   }

   public void endLiveJsp(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();

      assertEquals(_expectedLiveJsp, actual);

   }
   
   /*
    * This test verifies a bug fix. The bug involved the options tag
    * not resetting content and having it available to subsequent uses
    * of the tag.
    */
   public void testNegativeLiveJsp()
      throws Exception {
      
      try {
         pageContext.forward(JSP_TEST_DIR + _testMultJspName);
         fail("Second options tag should throw exception");
      } catch (Exception e) {
         assertTrue(e.getMessage().indexOf("Evaluation attribute failed") != -1);
      } 
   }
   
   /*
    * This test verifies a bug fix. The bug involved the value and
    * displayvalue attributes only accepting String values.
    */
   public void testLiveJspNonStringCollections()
      throws Exception {
   
      try {
         pageContext.forward(JSP_TEST_DIR + "OptionsTagIntValues.jsp");
      } catch (Exception e) {
         fail("Options tag failed to parse collection of non-String values");
      } 
   }
   
   public void endLiveJspNonStringCollections(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", "");

      String expected = "<option value=\"1\">one</option>" +
      "<option value=\"2\">two</option>" +
      "<option value=\"3\">three</option>";
      assertEquals(expected, actual);

   }
   
}
