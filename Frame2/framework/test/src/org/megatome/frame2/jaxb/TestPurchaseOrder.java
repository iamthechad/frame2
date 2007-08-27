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
package org.megatome.frame2.jaxb;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import junit.framework.TestCase;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.errors.impl.ErrorsFactory;
import org.megatome.frame2.front.TranslationException;
import org.megatome.frame2.jaxb.JaxbEventBase.ValidationMonitor;
import org.megatome.frame2.jaxbgen.Items;
import org.megatome.frame2.jaxbgen.ObjectFactory;
import org.megatome.frame2.jaxbgen.PurchaseOrderType;
import org.megatome.frame2.jaxbgen.USAddress;
import org.megatome.frame2.util.Helper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 */
public class TestPurchaseOrder extends TestCase {
	private static final String TARGET_PACKAGE = "org.megatome.frame2.jaxbgen"; //$NON-NLS-1$

	public void testUnmarshall_InputStream() throws Exception {
		PurchaseOrderType po = unmarshall();

		assertNotNull(po);

		assertEquals("1999-10-20", Helper.calendarToString(po.getOrderDate())); //$NON-NLS-1$

		USAddress address = po.getShipTo();

		assertNotNull(address);
		assertEquals("Alice Smith", address.getName()); //$NON-NLS-1$

		Items items = po.getItems();

		assertNotNull(items);
		assertEquals(2, items.getItem().size());
	}

	public void testMarshall() throws Exception {
		PurchaseOrderType po = unmarshall();

		Items.Item item = po.getItems().getItem().get(0);

		item.setComment("This comment has been changed"); //$NON-NLS-1$

		ObjectFactory of = new ObjectFactory();
		OutputStream ostream = Helper.marshall(of.createPurchaseOrder(po),
				TARGET_PACKAGE, getClass().getClassLoader());

		assertTrue(ostream.toString().indexOf("This comment has been changed") > 0); //$NON-NLS-1$
	}

	public void testValidate() throws Exception {
		PurchaseOrderType po = unmarshall();
		Schema s = loadSchema();
		
		Errors errors = ErrorsFactory.newInstance();

		Items.Item item1 = po.getItems().getItem().get(0);
		
		//assertTrue(po.validate(errors));
		roundTripValidate(po, s, errors);
		assertEquals(0, errors.size());

		item1.setPartNum("AAAAAA"); //$NON-NLS-1$

		roundTripValidate(po, s, errors);
		//assertFalse(po.validate(errors));
		assertEquals(2, errors.size());

		item1.setQuantity(101);

		errors.release();
		errors = ErrorsFactory.newInstance();

		roundTripValidate(po, s, errors);
		//assertFalse(po.validate(errors));
		assertEquals(4, errors.size());
	}
	
	private boolean roundTripValidate(JaxbEventBase event, Schema s, Errors errors) {
    	try {
			ValidationMonitor monitor = new ValidationMonitor();
			JAXBContext context = JAXBContext.newInstance(event.getClass().getPackage().getName());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setSchema(s);
			marshaller.setEventHandler(monitor);
			Document doc = getTargetDocument();
			
			marshaller.marshal(event.getMarshallableType(), doc);
			//dumpDocument(doc, "c:/doc.xml");
			
			/*Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(s);
			unmarshaller.setEventHandler(monitor);
			Object obj = unmarshaller.unmarshal(doc.getDocumentElement());*/
			
			monitor.populate(event, errors);
		} catch (TranslationException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errors.isEmpty();
    }
	
	private void dumpDocument(Document doc, String filename) {
        try {
            // Prepare the DOM document for writing
            Source source = new DOMSource(doc);
    
            // Prepare the output file
            File file = new File(filename);
            Result result = new StreamResult(file);
    
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
        	e.printStackTrace();
        } catch (TransformerException e) {
        	e.printStackTrace();
        }
    }
	
	private Document getTargetDocument() throws TranslationException {
		Document result = null;

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			dbf.setNamespaceAware(true);

			DocumentBuilder db = dbf.newDocumentBuilder();

			result = db.newDocument();
		} catch (Exception e) {
			throw new TranslationException("Unable to create target document", //$NON-NLS-1$
					e);
		}

		return result;
	}

	private Schema loadSchema() {
		SchemaFactory sf = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema s = null;
		try {
			s = sf.newSchema(getClass().getResource("/WEB-INF/schemas/po.xsd"));
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return s;
	}

	private PurchaseOrderType unmarshall() throws Exception {
		JAXBElement<PurchaseOrderType> element = (JAXBElement<PurchaseOrderType>) Helper
				.unmarshall(
						"org/megatome/frame2/jaxb/po.xml", TARGET_PACKAGE, getClass().getClassLoader()); //$NON-NLS-1$
		return element.getValue();
	}

	class ValidationMonitor implements ValidationEventHandler {
		private List<ValidationEvent> events = new ArrayList<ValidationEvent>();

		/**
		 * @see javax.xml.bind.ValidationEventHandler#handleEvent(ValidationEvent)
		 */
		public boolean handleEvent(ValidationEvent evt) {
			this.events.add(evt);

			return true;
		}

		void populate(JaxbEventBase event, final Errors errors) {
			if ((errors != null) && (!this.events.isEmpty())) {
					for (ValidationEvent ve : this.events) {
						errors.add(event.getClass().getCanonicalName(), getMessage(ve));
					}
				this.events.clear();
			}
			/*if ((errors != null) && !this.events.isEmpty()) {
				String className = proxy.getEvent().getClass()
						.getCanonicalName();
				for (int i = 0; i < this.events.size(); i++) {
					ValidationEvent event = this.events.get(i);

					String key = className;
					String msg = getMessage(event);

					errors.add(key, msg);
				}
			}*/
		}

		String getMessage(ValidationEvent event) {
			String message = event.getMessage();

			if (isAttribute(event)) {
				return message.substring(message.indexOf(":") + 2); //$NON-NLS-1$
			}

			return message;
		}

		String getAttributeName(ValidationEvent event) {
			// Bug 953538
			// Remove hardcoded "partNum" and instead parse
			// the attribute name from the message
			String attributeName = null;
			if (isAttribute(event)) {
				String message = event.getMessage();
				String quotedAttributeRE = "[^\"]*\"([^\"]+)\""; //$NON-NLS-1$
				Pattern pat = Pattern.compile(quotedAttributeRE,
						Pattern.CASE_INSENSITIVE);
				Matcher m = pat.matcher(message);

				if (m.find()) {
					attributeName = m.group(1);
				}
			}

			return attributeName;
		}

		boolean isAttribute(ValidationEvent event) {
			boolean retval = false;
			String msg = event.getMessage();
			if (msg != null) {
				retval = msg.indexOf("attribute") == 0; //$NON-NLS-1$
			}

			return retval;
		}
	}
}
