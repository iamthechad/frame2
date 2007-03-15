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

	final private static String TARGET_PKG = "org.megatome.frame2.jaxbgen"; //$NON-NLS-1$
	private PurchaseOrder po;
	private SoapRequestProcessor processor;

	@Override
	protected void setUp() throws Exception {
		Configuration config = new Configuration("org/megatome/frame2/front/test-wsconfig.xml"); //$NON-NLS-1$
		Element[] elements = Helper.loadEvents("org/megatome/frame2/jaxb/po.xml", getClass()); //$NON-NLS-1$

		this.processor =
			(SoapRequestProcessor) RequestProcessorFactory.instance(config, elements, TARGET_PKG);

		SoapEventMap event = this.processor.getEvents().get(0);
		this.po = (PurchaseOrder) event.getEventsIterator().next();
	}
   
   public void testEventInstanceOfCommonsValidatorEvent(){
      try {
			assertTrue(Class.forName("org.megatome.frame2.event.CommonsValidatorEvent").isInstance(this.po)); //$NON-NLS-1$
      } catch (ClassNotFoundException e) {
         fail();
      } 
   }

	public void testValidateError_InvalidPartNum() throws Exception {

		Items.ItemType item = (Items.ItemType) this.po.getItems().getItem().get(0);

		item.setPartNum("AAAAA"); //$NON-NLS-1$

		assertFalse(this.processor.validateEvent((Event) this.po));

		Errors errors = this.processor.getContextWrapper().getRequestErrors();

		assertEquals(1, errors.size());

		Error error = errors.iterator().next();
		assertEquals("org.megatome.frame2.jaxbgen.impl.ItemsImpl.partNum", error.getKey()); //$NON-NLS-1$
		assertEquals(
			"the value does not match the regular expression \"\\d{3}-[A-Z]{2}\".", //$NON-NLS-1$
			error.getValue1());
		assertNull(error.getValue2());
	}

	public void testValidateError_InvalidDate() throws Exception {

		Items.ItemType item = (Items.ItemType) this.po.getItems().getItem().get(0);

		item.setQuantity(new BigInteger("101")); //$NON-NLS-1$

		assertFalse(this.processor.validateEvent((Event) this.po));

		Errors errors = this.processor.getContextWrapper().getRequestErrors();

		assertEquals(1, errors.size());

		Error error = errors.iterator().next();
		assertEquals("org.megatome.frame2.jaxbgen.impl.ItemsImpl", error.getKey()); //$NON-NLS-1$
		assertEquals(
			"the value is out of the range (maxExclusive specifies 100).", //$NON-NLS-1$
			error.getValue1());
		assertNull(error.getValue2());
	}


   public void testValidateError_NullAddress() throws Exception {

      this.po.setBillTo(null);

      assertFalse(this.processor.validateEvent((Event) this.po));

      Errors errors = this.processor.getContextWrapper().getRequestErrors();

      assertEquals(2, errors.size());

      Error error = errors.iterator().next();
      assertEquals("org.megatome.frame2.jaxbgen.impl.PurchaseOrderImpl", error.getKey()); //$NON-NLS-1$
      assertEquals(
         "a required field \"BillTo\" is missing an object", //$NON-NLS-1$
         error.getValue1());
      assertNull(error.getValue2());
   }
}
