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
package org.megatome.frame2.front;

import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.front.config.ViewType;
import org.megatome.frame2.jaxbgen.Items;
import org.megatome.frame2.jaxbgen.PurchaseOrder;
import org.megatome.frame2.jaxbgen.impl.PurchaseOrderImpl;
import org.megatome.frame2.util.Helper;
import org.megatome.frame2.util.ResourceLocator;
import org.megatome.frame2.util.dom.DOMStreamConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class TestSoapRequestProcessor extends TestCase {
   final private String TARGET_PKG = "org.megatome.frame2.jaxbgen";
   private Element[] elements;
   private Configuration config;

   protected void setUp() throws Exception {
      config = new Configuration("org/megatome/frame2/front/test-wsconfig.xml");
      elements = Helper.loadEvents("org/megatome/frame2/jaxb/po.xml",getClass());
      ResourceLocator.setBasename("frame2-resource");
   }

	public void testUnmarshallElements() throws Exception {
		SoapRequestProcessor processor =
			(SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);

      assertNotNull(processor);

      List events = processor.getEvents();

      assertNotNull(events);
      assertEquals(1, events.size());
      SoapEventMap event = (SoapEventMap)events.get(0);
      Object obj = event.getEventsIterator().next();
      

      assertTrue(obj instanceof PurchaseOrder);
      assertTrue(obj instanceof PurchaseOrderImpl);

      PurchaseOrder po = (PurchaseOrder) obj;

      assertEquals("1999-10-20", Helper.calendarToString(po.getOrderDate()));
   }

   public void testUnmarshallElements_Empty() throws Exception {
      SoapRequestProcessor processor =
         (SoapRequestProcessor) RequestProcessorFactory.instance(config,new Element[3],TARGET_PKG);

      assertNotNull(processor);

      List events = processor.getEvents();

      assertNotNull(events);
      assertEquals(0, events.size());
   }
   
   public void testValidateEvent() throws Exception {
      SoapRequestProcessor processor = (SoapRequestProcessor) RequestProcessorFactory.instance(config,
            elements,TARGET_PKG);

      List events = processor.getEvents();
      SoapEventMap event = (SoapEventMap)events.get(0);
      Object obj = event.getEventsIterator().next();
      
      PurchaseOrder po = (PurchaseOrder) obj;
      
      Items.ItemType item = (Items.ItemType) po.getItems().getItem().get(0);

      item.setPartNum("AAAAA");

      
      assertFalse(processor.validateEvent((Event)obj));
      
      assertEquals(1,processor.getContextWrapper().getRequestErrors().size());
   }

   public void testCallHandler() throws Exception {
      SoapRequestProcessor processor = 
             (SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);

      PurchaseOrderImpl poi = new PurchaseOrderImpl();

      ForwardProxy response = processor.callHandlers(elements[0].getNodeName(), poi,ViewType.XML);

      assertEquals("key1", response.getPath());

      Context context = processor.getContextWrapper();

      assertSame(poi,context.getRequestAttribute(response.getPath()));
      assertEquals(PurchaseOrderHandler.NEW_COMMENT,poi.getComment());
   }
   
   public void testCallHandlerReponder() throws Exception {
      SoapRequestProcessor processor = 
             (SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);

      PurchaseOrderImpl poi = new PurchaseOrderImpl();

      ForwardProxy response = processor.callHandlers("POResponderOrder", poi,ViewType.XML);
      assertTrue(response.isResponderType());

      assertEquals("org.megatome.frame2.front.AckResponder", response.getPath());
   }
   
   public void testCallHandlerReponderChildren() throws Exception {
      elements = Helper.loadEvents("org/megatome/frame2/jaxb/pochildren.xml",getClass());
      SoapRequestProcessor processor = 
             (SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);

      PurchaseOrderImpl poi = new PurchaseOrderImpl();

      ForwardProxy response = processor.callHandlers("POTestChildren", poi,ViewType.XML);
      assertTrue(response.isResponderType());

      assertEquals("org.megatome.frame2.front.AckResponder", response.getPath());
   }
   
   public void testProcessRequestChildren() throws Exception {
      elements = Helper.loadEvents("org/megatome/frame2/jaxb/pochildren.xml",getClass());
      SoapRequestProcessor processor = 
             (SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);
      Element[] response = (Element[]) processor.processRequest();
      
      assertEquals(response[0].getNodeName(),"POTestChildren");
   }
   
   //  POTestChildrenMixedData.xml, 3 po orders, 3rd has bad date
   public void testProcessRequestChildrenMixedData() throws Exception {
      ResourceLocator.setBasename("frame2-resource");

      elements = Helper.loadEvents("org/megatome/frame2/jaxb/POTestChildrenMixedData.xml",getClass());
      SoapRequestProcessor processor = 
             (SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);
      Element[] response = (Element[]) processor.processRequest();
      
      // expect 1 parent element with 
      // 2 acks and one soap fault as children
      assertEquals(response.length,1); 
      assertEquals(response[0].getNodeName(),"POTestChildrenMixedData");
      
      Element parent = response[0];
      NodeList children = parent.getChildNodes();
      assertEquals(children.getLength(),3);
      for (int i=0; i<children.getLength(); i++){
         if (i == 0 || i ==1){
            assertEquals(((Element)children.item(i)).getNodeName(),"ack");
         }
         else {
            assertEquals(((Element)children.item(i)).getNodeName(),"SOAP-ENV:Fault");
            OutputStream os = DOMStreamConverter.toOutputStream(children.item(i));
            assertTrue(os.toString().indexOf("This part number bites, dude") > 1);
         }
      }        
   }
   
   public void testCallHandlerInvalidEvent() throws Exception {
      SoapRequestProcessor processor = 
             (SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);

      PurchaseOrderImpl poi = new PurchaseOrderImpl();

      try {
         processor.callHandlers("InvalidEvent", poi, ViewType.XML);
         fail();
      }
      catch (Frame2Exception expected) {
      }

   }    
   public void testMarshalResponse() throws Exception {
      SoapRequestProcessor processor = (SoapRequestProcessor) RequestProcessorFactory.instance(config,
            elements,TARGET_PKG);

      PurchaseOrder poi = getResponseObject("org/megatome/frame2/jaxb/po.xml");

      Element element = processor.marshallResponse(poi);

      assertNotNull(element);
   }
   
   public void testMarshalResponse_Null() throws Exception {
      SoapRequestProcessor processor = (SoapRequestProcessor) RequestProcessorFactory.instance(config,
            elements,TARGET_PKG);

      Element element = processor.marshallResponse(null);

      assertNull(element);
   }

/*
 * This test appears to be redundant now
 * 
   public void testJaxbDomUnmarshallNotFixed() throws Exception {
      JAXBContext jc = JAXBContext.newInstance(TARGET_PKG);
      Unmarshaller u = jc.createUnmarshaller();

      try {
         Object obj = u.unmarshal(elements[0]);

         fail();
      } catch (NullPointerException e) {
         // In JAXB 1.1 the unmarshall will barf out this exception if passed a DOM
         // element of document rather than an input stream.  If this test fails, it
         // may indicate that the unmarshalling client (request processor?) can be
         // rewritten to simply pass in the DOM element.
      }
   }
*/

   public void testProcessRequest() throws Exception {
      SoapRequestProcessor processor =
         (SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);

      Element[] result = (Element[]) processor.processRequest();
      
      assertNotNull(result);
      assertEquals("purchaseOrder",result[0].getLocalName());
   }
   
   public void testProcessRequest_Batch() throws Exception {
      Element[] batchElements = new Element[] { elements[0], elements[0], elements[0] };
      
      assertEquals(3,batchElements.length);
      
      SoapRequestProcessor processor =
         (SoapRequestProcessor) RequestProcessorFactory.instance(config, batchElements,TARGET_PKG);

      Element[] result =  (Element[]) processor.processRequest();
      
      assertNotNull(result);
      assertEquals(3,result.length);
   }
   
   public void testProcessRequest_Passthru() throws Exception {
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      Element element = doc.createElement("passthruEvent");
      element.appendChild(doc.importNode(elements[0],true));
      
      DOMStreamConverter.toOutputStream(element);
      
      SoapRequestProcessor processor =
         (SoapRequestProcessor) RequestProcessorFactory.instance(config, new Element[] { element },TARGET_PKG);

      Element[] result = (Element[]) processor.processRequest();

      DOMStreamConverter.toOutputStream(result[0]);
      
      assertNotNull(result);
      assertEquals("purchaseOrder",result[0].getNodeName());      
   }
   
   /*
   public void testHandlerExceptionForwardToEvent() throws Exception{
      SoapRequestProcessor processor = 
             (SoapRequestProcessor) RequestProcessorFactory.instance(config, elements,TARGET_PKG);

      CommentImpl ci = new CommentImpl();

      processor.getContextWrapper().setRequestAttribute("exception", "throwAnException");
      ForwardProxy response = processor.callHandlers("comment", ci,ViewType.XML);
      assertEquals( "testXmlResource", response.getPath());
   }

*/
   private PurchaseOrder getResponseObject(String path)
      throws Exception {
      JAXBContext jc = JAXBContext.newInstance(TARGET_PKG);
      Unmarshaller u = jc.createUnmarshaller();

      return (PurchaseOrder) u.unmarshal(Helper.getInputStreamFor(path,getClass()));
   }
   
   
    public void testNegativeSoapRequestProcessorClass () throws Exception {  
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      Element element = doc.createElement("passthruEvent"); 
      Configuration cfg = new Configuration("org/megatome/frame2/front/soapRequestNegativeClass.xml");  
      RequestProcessor requestProcessor=  RequestProcessorFactory.instance(cfg,new Element[] { element },TARGET_PKG);       
      assertNull(requestProcessor);                     
   }
   
   
   public void testNegativeSoapRequestProcessorClassImplementRequestProcessor () throws Exception {  
     Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
     Element element = doc.createElement("passthruEvent"); 
     Configuration cfg = new Configuration("org/megatome/frame2/front/soapRequestNegativeClassReqProc.xml");   
     RequestProcessor requestProcessor=  RequestProcessorFactory.instance(cfg,new Element[] { element },TARGET_PKG);       
     assertNull(requestProcessor);                     
   }
   
   public void testSoapRequestProcessorDefaultRequestProcessors () throws Exception {  
     Configuration cfg = new Configuration("org/megatome/frame2/front/ReqProcDefaults.xml");  
     Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
     Element element = doc.createElement("passthruEvent"); 
     RequestProcessor requestProcessor=  RequestProcessorFactory.instance(cfg,new Element[] { element },TARGET_PKG);       
     
     assertNotNull(requestProcessor);         
     String className = "org.megatome.frame2.front.SoapRequestProcessor";   
     assertEquals(className,requestProcessor.getClass().getName());           
     
   }
    
   public void testCustomSoapRequestProcessor () throws Exception {       
     Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
     Element element = doc.createElement("passthruEvent"); 
     Configuration cfg = new Configuration("org/megatome/frame2/front/soapRequestCustom.xml"); 
     RequestProcessor requestProcessor=  RequestProcessorFactory.instance(cfg,new Element[] { element },TARGET_PKG);                 
                 
     assertNotNull(requestProcessor);         
     String className = "org.megatome.frame2.front.SoapRequestProcessorCustom";   
     assertEquals(className,requestProcessor.getClass().getName());                 
   }
}
