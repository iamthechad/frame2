/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2007 Megatome Technologies.  All rights
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
   static final String JSP_TEST_DIR = "/test/tags/"; //$NON-NLS-1$
   String _testMultJspName;
   public TestSelectOptionsTags(String name) {
      super(name);
      this._testJspName = "SelectOptionsTag.jsp"; //$NON-NLS-1$
      this._expectedLiveJsp = "<select name=\"select\">" + //$NON-NLS-1$
      						 "<option value=\"one\">one</option>" + //$NON-NLS-1$
                         "<option value=\"two\">two</option>" + //$NON-NLS-1$
                         "</select>"; //$NON-NLS-1$
   }
   
   // Test out tag in a real live jsp.
   public void testLiveJsp()
      throws Exception {
      this.pageContext.forward(JSP_TEST_DIR + this._testJspName);
   }

   public void endLiveJsp(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

      assertEquals(this._expectedLiveJsp, actual);

   }
   
   public void testSelectedValueInSelect() throws Exception {
      try {
         this.pageContext.forward(JSP_TEST_DIR + "SelectOptionsSelected.jsp"); //$NON-NLS-1$
      } catch (Exception e) {
         fail("Unexpected error parsing tag"); //$NON-NLS-1$
      }
   }
   
   public void endSelectedValueInSelect(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
      
      String expected = "<select name=\"select\">" + //$NON-NLS-1$
      "<option value=\"1\">one</option>" + //$NON-NLS-1$
      "<option selected value=\"2\">two</option>" + //$NON-NLS-1$
      "<option value=\"3\">three</option>" + //$NON-NLS-1$
      "</select>"; //$NON-NLS-1$
      assertEquals(expected, actual);
   }
   
   public void testSelectedValueInOptions() throws Exception {
      try {
         this.pageContext.forward(JSP_TEST_DIR + "SelectOptionsSelected2.jsp"); //$NON-NLS-1$
      } catch (Exception e) {
         fail("Unexpected error parsing tag"); //$NON-NLS-1$
      }
   }
   
   public void endSelectedValueInOptions(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
      
      String expected = "<select name=\"select\">" + //$NON-NLS-1$
      "<option value=\"1\">one</option>" + //$NON-NLS-1$
      "<option selected value=\"2\">two</option>" + //$NON-NLS-1$
      "<option value=\"3\">three</option>" + //$NON-NLS-1$
      "</select>"; //$NON-NLS-1$
      assertEquals(expected, actual);
   }
   
   public void testSelectedCollectionValueInSelect() throws Exception {
      try {
         this.pageContext.forward(JSP_TEST_DIR + "SelectOptionsCollection.jsp"); //$NON-NLS-1$
      } catch (Exception e) {
         fail("Unexpected error parsing tag: " + e.getMessage()); //$NON-NLS-1$
      }
   }
   
   public void endSelectedCollectionValueInSelect(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
      
      String expected = "<select name=\"select\">" + //$NON-NLS-1$
      "<option value=\"1\">one</option>" + //$NON-NLS-1$
      "<option selected value=\"2\">two</option>" + //$NON-NLS-1$
      "<option selected value=\"3\">three</option>" + //$NON-NLS-1$
      "</select>"; //$NON-NLS-1$
      assertEquals(expected, actual);
   }
   
   public void testSelectedCollectionValueInOptions() throws Exception {
      try {
         this.pageContext.forward(JSP_TEST_DIR + "SelectOptionsCollection2.jsp"); //$NON-NLS-1$
      } catch (Exception e) {
         fail("Unexpected error parsing tag: " + e.getMessage()); //$NON-NLS-1$
      }
   }
   
   public void endSelectedCollectionValueInOptions(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
      
      String expected = "<select name=\"select\">" + //$NON-NLS-1$
      "<option value=\"1\">one</option>" + //$NON-NLS-1$
      "<option selected value=\"2\">two</option>" + //$NON-NLS-1$
      "<option selected value=\"3\">three</option>" + //$NON-NLS-1$
      "</select>"; //$NON-NLS-1$
      assertEquals(expected, actual);
   }
   
   public void testSelectedCollectionArrayInSelect() throws Exception {
      try {
         this.pageContext.forward(JSP_TEST_DIR + "SelectOptionsArray.jsp"); //$NON-NLS-1$
      } catch (Exception e) {
         fail("Unexpected error parsing tag: " + e.getMessage()); //$NON-NLS-1$
      }
   }
   
   public void endSelectedCollectionArrayInSelect(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
      
      String expected = "<select name=\"select\">" + //$NON-NLS-1$
      "<option value=\"1\">one</option>" + //$NON-NLS-1$
      "<option selected value=\"2\">two</option>" + //$NON-NLS-1$
      "<option selected value=\"3\">three</option>" + //$NON-NLS-1$
      "</select>"; //$NON-NLS-1$
      assertEquals(expected, actual);
   }
   
   public void testSelectedCollectionArrayInOptions() throws Exception {
      try {
         this.pageContext.forward(JSP_TEST_DIR + "SelectOptionsArray2.jsp"); //$NON-NLS-1$
      } catch (Exception e) {
         fail("Unexpected error parsing tag: " + e.getMessage()); //$NON-NLS-1$
      }
   }
   
   public void endSelectedCollectionArrayInOptions(WebResponse webResponse) throws Exception {
      String actual = webResponse.getText().trim();
      actual = actual.replaceAll("\r\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
      
      String expected = "<select name=\"select\">" + //$NON-NLS-1$
      "<option value=\"1\">one</option>" + //$NON-NLS-1$
      "<option selected value=\"2\">two</option>" + //$NON-NLS-1$
      "<option selected value=\"3\">three</option>" + //$NON-NLS-1$
      "</select>"; //$NON-NLS-1$
      assertEquals(expected, actual);
   }
}
