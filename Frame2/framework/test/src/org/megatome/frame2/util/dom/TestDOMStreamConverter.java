package org.megatome.frame2.util.dom;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.megatome.frame2.util.dom.DOMStreamConverter;
import org.megatome.frame2.util.dom.DocumentException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class TestDOMStreamConverter extends TestCase {
   private Node _node;
   private DocumentBuilder _builder;

   protected void setUp() throws Exception {
      _builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		_node = toNode(ClassLoader.getSystemResourceAsStream("org/megatome/frame2/util/dom/dom1.xml"));
   }

   public void testToInputStream() throws Exception {
      InputStream is = DOMStreamConverter.toInputStream(_node);

      assertNotNull(is);

      Node node = toNode(is);

      assertNotNull(node);

      assertEquals("purchaseOrder", node.getFirstChild().getNodeName());
   }

   public void testToInputStream_Null() throws Exception {
      try {
         DOMStreamConverter.toInputStream(null);
         fail();
      } catch (DocumentException e) {
      }
   }

   public void testToInputStream_Empty() throws Exception {
      assertNotNull(DOMStreamConverter.toInputStream(_builder.newDocument()));
   }

   public void testToOutputStream() throws Exception {
      OutputStream os = DOMStreamConverter.toOutputStream(_node);

      assertNotNull(os);

      assertTrue(os.toString().indexOf("purchaseOrder") > 0);
   }

   public void testToOutputStream_Empty() throws Exception {
      assertNotNull(DOMStreamConverter.toOutputStream(_builder.newDocument()));
   }

   public void testToOutputStream_Null() throws Exception {
      try {
         DOMStreamConverter.toOutputStream(null);
         fail();
      } catch (DocumentException e) {
      }
   }

   public void testStringToNode() throws Exception {
      final String xml = "<tt:test xmlns:tt=\"http://test-uri/\">Test Value</tt:test>";

      Element element = (Element) DOMStreamConverter.fromString(xml,true);
      
      assertNotNull(element);

      String val = DOMStreamConverter.toOutputStream(element).toString();
      
      assertEquals("tt:test",element.getNodeName());
      assertEquals("test",element.getLocalName());
      assertEquals("http://test-uri/",element.getNamespaceURI());
      assertEquals("tt",element.getPrefix());
      assertEquals("Test Value",element.getFirstChild().getNodeValue());
   }

   public void testStringToNode_NoNamespace() throws Exception {
      final String xml = "<tt:test xmlns:tt=\"http://test-uri/\">Test Value</tt:test>";

      Element element = (Element) DOMStreamConverter.fromString(xml,false);
      
      assertNotNull(element);

      String val = DOMStreamConverter.toOutputStream(element).toString();
      
      assertEquals("tt:test",element.getNodeName());
      assertNull(element.getLocalName());
      assertNull(element.getNamespaceURI());
      assertNull(element.getPrefix());
      assertEquals("Test Value",element.getFirstChild().getNodeValue());
   }

   private Node toNode(InputStream istream) throws Exception {
      return _builder.parse(istream);
   }
   
   public void testEncodeString() throws Exception {
      final String data1 = "a&b<c>d'e\"f";
      final String data2 = "";
      
      assertEquals("a&amp;b&lt;c&gt;d&apos;e&quot;f",DOMStreamConverter.encode(data1));
      assertEquals("",DOMStreamConverter.encode(data2));
      assertNull(DOMStreamConverter.encode(null));
   }
}
