package org.megatome.frame2.util.soap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.megatome.frame2.util.dom.DOMStreamConverter;
import org.megatome.frame2.util.soap.SOAPFault;
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

   DocumentBuilderFactory _factory;
   DocumentBuilder _builder;

   protected void setUp() throws Exception {
      _factory = DocumentBuilderFactory.newInstance();
      _builder = _factory.newDocumentBuilder();
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

      String val = DOMStreamConverter.toOutputStream(faultElement).toString();

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
