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
package org.megatome.frame2.taglib.html.el;

import org.apache.cactus.JspTestCase;
import org.apache.cactus.WebResponse;

public class TestSelectOptionsTags extends JspTestCase {

   String _testJspName;
   String _expectedLiveJsp;
   static final String JSP_TEST_DIR = "/test/tags/";
   String _testMultJspName;
   public TestSelectOptionsTags(String name) {
      super(name);
      _testJspName = "SelectOptionsTag.jsp";
      _expectedLiveJsp = "<select name=\"select\">" +
      						 "<option value=\"one\">one</option>" +
                         "<option value=\"two\">two</option>" +
                         "</select>";
   }
   
   // Test out tag in a real live jsp.
   public void testLiveJsp()
      throws Exception {
      pageContext.forward(JSP_TEST_DIR + _testJspName);
   }

   public void endLiveJsp(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual.replaceAll("\r\n", "");

      assertEquals(_expectedLiveJsp, actual);

   }
   
   public void testSelectedValueInSelect() throws Exception {
      try {
         pageContext.forward(JSP_TEST_DIR + "SelectOptionsSelected.jsp");
      } catch (Exception e) {
         fail("Unexpected error parsing tag");
      }
   }
   
   public void endSelectedValueInSelect(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", "");
      
      String expected = "<select name=\"select\">" +
      "<option value=\"1\">one</option>" +
      "<option selected value=\"2\">two</option>" +
      "<option value=\"3\">three</option>" +
      "</select>";
      assertEquals(expected, actual);
   }
   
   public void testSelectedValueInOptions() throws Exception {
      try {
         pageContext.forward(JSP_TEST_DIR + "SelectOptionsSelected2.jsp");
      } catch (Exception e) {
         fail("Unexpected error parsing tag");
      }
   }
   
   public void endSelectedValueInOptions(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", "");
      
      String expected = "<select name=\"select\">" +
      "<option value=\"1\">one</option>" +
      "<option selected value=\"2\">two</option>" +
      "<option value=\"3\">three</option>" +
      "</select>";
      assertEquals(expected, actual);
   }
   
   public void testSelectedCollectionValueInSelect() throws Exception {
      try {
         pageContext.forward(JSP_TEST_DIR + "SelectOptionsCollection.jsp");
      } catch (Exception e) {
         fail("Unexpected error parsing tag: " + e.getMessage());
      }
   }
   
   public void endSelectedCollectionValueInSelect(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", "");
      
      String expected = "<select name=\"select\">" +
      "<option value=\"1\">one</option>" +
      "<option selected value=\"2\">two</option>" +
      "<option selected value=\"3\">three</option>" +
      "</select>";
      assertEquals(expected, actual);
   }
   
   public void testSelectedCollectionValueInOptions() throws Exception {
      try {
         pageContext.forward(JSP_TEST_DIR + "SelectOptionsCollection2.jsp");
      } catch (Exception e) {
         fail("Unexpected error parsing tag: " + e.getMessage());
      }
   }
   
   public void endSelectedCollectionValueInOptions(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", "");
      
      String expected = "<select name=\"select\">" +
      "<option value=\"1\">one</option>" +
      "<option selected value=\"2\">two</option>" +
      "<option selected value=\"3\">three</option>" +
      "</select>";
      assertEquals(expected, actual);
   }
   
   public void testSelectedCollectionArrayInSelect() throws Exception {
      try {
         pageContext.forward(JSP_TEST_DIR + "SelectOptionsArray.jsp");
      } catch (Exception e) {
         fail("Unexpected error parsing tag: " + e.getMessage());
      }
   }
   
   public void endSelectedCollectionArrayInSelect(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", "");
      
      String expected = "<select name=\"select\">" +
      "<option value=\"1\">one</option>" +
      "<option selected value=\"2\">two</option>" +
      "<option selected value=\"3\">three</option>" +
      "</select>";
      assertEquals(expected, actual);
   }
   
   public void testSelectedCollectionArrayInOptions() throws Exception {
      try {
         pageContext.forward(JSP_TEST_DIR + "SelectOptionsArray2.jsp");
      } catch (Exception e) {
         fail("Unexpected error parsing tag: " + e.getMessage());
      }
   }
   
   public void endSelectedCollectionArrayInOptions(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", "");
      
      String expected = "<select name=\"select\">" +
      "<option value=\"1\">one</option>" +
      "<option selected value=\"2\">two</option>" +
      "<option selected value=\"3\">three</option>" +
      "</select>";
      assertEquals(expected, actual);
   }
}
