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
package org.megatome.frame2.util.soap;

import junit.framework.TestCase;

import org.megatome.frame2.util.dom.DOMStreamConverter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 */
public class TestSOAPFault extends TestCase {

//       <SOAP-ENV:Fault>
//           <faultcode>SOAP-ENV:Server</faultcode>
//           <faultstring>Server Error</faultstring>
//           <detail>
//               <e:myfaultdetails xmlns:e="Some-URI">
//                 <message>
//                   My application didn't work
//                 </message>
//                 <errorcode>
//                   1001
//                 </errorcode>
//               </e:myfaultdetails>
//           </detail>
//       </SOAP-ENV:Fault>

   //private DocumentBuilderFactory factory;
   //private DocumentBuilder builder;

   protected void setUp() throws Exception {
      //factory = DocumentBuilderFactory.newInstance();
      //builder = factory.newDocumentBuilder();
   }

   public void testCreateFault() throws Exception {
      SOAPFault fault = new SOAPFault();
      
      Element faultElement = fault.getElement();
      
		assertFaultBody(faultElement);

      Node detail = faultElement.getChildNodes().item(2);
      assertNotNull(detail);
      assertEquals("detail",detail.getNodeName());
      assertEquals("detail",detail.getLocalName());
      assertNull(detail.getFirstChild());      
   }


   public void testCreateFault_StringDetail() throws Exception {
      final String MESSAGE = "I'm going to eat my cheese, Ladies.";

      SOAPFault fault = new SOAPFault();

      fault.setDetailMessage(MESSAGE);

      Element faultElement = fault.getElement();

      assertFaultBody(faultElement);
      
      Node detail = faultElement.getChildNodes().item(2);
      assertNotNull(detail.getFirstChild());
      assertEquals(MESSAGE,detail.getFirstChild().getNodeValue());
   }

   public void testCreateFault_StringAsXmlDetail() throws Exception {
      final String MESSAGE = "I'm going to eat my cheese, Ladies.";

      SOAPFault fault = new SOAPFault();

      fault.setDetailMessage("<br>" + MESSAGE + "</br>");

      Element faultElement = fault.getElement();

      DOMStreamConverter.toOutputStream(faultElement).toString();

      assertFaultBody(faultElement);
      
      Node detail = faultElement.getChildNodes().item(2);
      Node br = detail.getFirstChild();

		assertNotNull(br);

      assertEquals("br",br.getNodeName());
      assertNull(br.getNodeValue());

      assertEquals(MESSAGE,br.getFirstChild().getNodeValue());
   }

   // NIT: Is this really correct?  May need to actually encapsulate as unparsed
   // character data.

   public void testCreateFault_StringAsEncodedDetail() throws Exception {
      final String MESSAGE = "I'm going to eat my cheese, Ladies.";

      SOAPFault fault = new SOAPFault();

      fault.setDetailMessage("<br>" + MESSAGE + "</br>",true);

      Element faultElement = fault.getElement();

      String val = DOMStreamConverter.toOutputStream(faultElement).toString();

      assertFaultBody(faultElement);

      // This is sort of sleezy, but between os platforms the index will change slightly

      assertTrue(198 < val.indexOf("&lt;br&gt;I'm going to eat my cheese, Ladies.&lt;/br&gt;"));
      assertTrue(201 > val.indexOf("&lt;br&gt;I'm going to eat my cheese, Ladies.&lt;/br&gt;"));
   }

   private void assertFaultBody(Element faultElement) {
      assertNotNull(faultElement);
      
      assertEquals("Fault",faultElement.getLocalName());
      assertEquals("SOAP-ENV:Fault",faultElement.getNodeName());
      assertEquals("http://schemas.xmlsoap.org/soap/envelope/",faultElement.getNamespaceURI());
      assertEquals("SOAP-ENV",faultElement.getPrefix());
      
      NodeList faultChildren = faultElement.getChildNodes();
      
      assertEquals(3,faultChildren.getLength());
      
      Node faultCode = faultChildren.item(0);
      assertNotNull(faultCode);
      assertEquals("faultcode",faultCode.getNodeName());
      assertEquals("faultcode",faultCode.getLocalName());
      assertEquals("SOAP-ENV:Server",faultCode.getFirstChild().getNodeValue());
      
      Node faultString = faultChildren.item(1);
      assertNotNull(faultString);
      assertEquals("faultstring",faultString.getNodeName());
      assertEquals("faultstring",faultString.getLocalName());
      assertEquals("Server Error",faultString.getFirstChild().getNodeValue());      

      Node detail = faultChildren.item(2);
      assertNotNull(detail);
      assertEquals("detail",detail.getNodeName());
      assertEquals("detail",detail.getLocalName());
   }
}
