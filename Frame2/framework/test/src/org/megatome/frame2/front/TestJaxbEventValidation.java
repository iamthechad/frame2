package org.megatome.frame2.front;

import java.math.BigInteger;

import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.jaxbgen.Items;
import org.megatome.frame2.jaxbgen.PurchaseOrder;
import org.megatome.frame2.util.Helper;
import org.w3c.dom.Element;

import servletunit.frame2.Frame2TestCase;

/**
 * 
 */
public class TestJaxbEventValidation extends Frame2TestCase {

	final private String TARGET_PKG = "org.megatome.frame2.jaxbgen";
	private PurchaseOrder _po;
	private SoapRequestProcessor _processor;

	protected void setUp() throws Exception {
		Configuration config = new Configuration("org/megatome/frame2/front/test-wsconfig.xml");
		Element[] elements = Helper.loadEvents("org/megatome/frame2/jaxb/po.xml", getClass());

		_processor =
			(SoapRequestProcessor) RequestProcessorFactory.instance(config, elements, TARGET_PKG);

		SoapEventMap event = (SoapEventMap) _processor.getEvents().get(0);
		_po = (PurchaseOrder) event.getEventsIterator().next();
	}
   
   public void testEventInstanceOfCommonsValidatorEvent(){
      try {
			assertTrue(Class.forName("org.megatome.frame2.event.CommonsValidatorEvent").isInstance(_po));
      } catch (ClassNotFoundException e) {
         fail();
      } 
   }

	public void testValidateError_InvalidPartNum() throws Exception {

		Items.ItemType item = (Items.ItemType) _po.getItems().getItem().get(0);

		item.setPartNum("AAAAA");

		assertFalse(_processor.validateEvent((Event) _po));

		Errors errors = _processor.getContextWrapper().getRequestErrors();

		assertEquals(1, errors.size());

		Error error = (Error) errors.iterator().next();
		assertEquals("org.megatome.frame2.jaxbgen.impl.ItemsImpl.partNum", error.getKey());
		assertEquals(
			"the value does not match the regular expression \"\\d{3}-[A-Z]{2}\".",
			error.getValue1());
		assertNull(error.getValue2());
	}

	public void testValidateError_InvalidDate() throws Exception {

		Items.ItemType item = (Items.ItemType) _po.getItems().getItem().get(0);

		item.setQuantity(new BigInteger("101"));

		assertFalse(_processor.validateEvent((Event) _po));

		Errors errors = _processor.getContextWrapper().getRequestErrors();

		assertEquals(1, errors.size());

		Error error = (Error) errors.iterator().next();
		assertEquals("org.megatome.frame2.jaxbgen.impl.ItemsImpl", error.getKey());
		assertEquals(
			"the value is out of the range (maxExclusive specifies 100).",
			error.getValue1());
		assertNull(error.getValue2());
	}


   public void testValidateError_NullAddress() throws Exception {

      _po.setBillTo(null);

      assertFalse(_processor.validateEvent((Event) _po));

      Errors errors = _processor.getContextWrapper().getRequestErrors();

      assertEquals(2, errors.size());

      Error error = (Error) errors.iterator().next();
      assertEquals("org.megatome.frame2.jaxbgen.impl.PurchaseOrderImpl", error.getKey());
      assertEquals(
         "a required field \"BillTo\" is missing an object",
         error.getValue1());
      assertNull(error.getValue2());
   }
}
