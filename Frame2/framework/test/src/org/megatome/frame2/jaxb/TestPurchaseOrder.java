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

import java.io.OutputStream;

import javax.xml.bind.JAXBElement;

import junit.framework.TestCase;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.errors.impl.ErrorsFactory;
import org.megatome.frame2.jaxbgen.Items;
import org.megatome.frame2.jaxbgen.ObjectFactory;
import org.megatome.frame2.jaxbgen.PurchaseOrderType;
import org.megatome.frame2.jaxbgen.USAddress;
import org.megatome.frame2.util.Helper;


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
      PurchaseOrderType po = unmarshall( );

      Items.Item item = po.getItems().getItem().get(0);

      item.setComment("This comment has been changed"); //$NON-NLS-1$

      ObjectFactory of = new ObjectFactory();
      OutputStream ostream = Helper.marshall(of.createPurchaseOrder(po),TARGET_PACKAGE,getClass().getClassLoader());

      assertTrue(ostream.toString().indexOf("This comment has been changed") > 0); //$NON-NLS-1$
   }

   public void testValidate() throws Exception {
      PurchaseOrderType po = unmarshall( );

      Errors errors = ErrorsFactory.newInstance();

      Items.Item item1 = po.getItems().getItem().get(0);

      assertTrue(item1.validate(errors));
      assertEquals(0, errors.size());

      item1.setPartNum("AAAAAA"); //$NON-NLS-1$

      assertFalse(item1.validate(errors));
      assertEquals(1, errors.size());

      item1.setQuantity(101);

      errors.release();
      errors = ErrorsFactory.newInstance();

      assertFalse(item1.validate(errors));
      assertEquals(2, errors.size());

      errors.release();
      errors = ErrorsFactory.newInstance();

      assertFalse(po.validate(errors));
      assertEquals(2, errors.size());
   }

   private PurchaseOrderType unmarshall( ) throws Exception {
	  JAXBElement<PurchaseOrderType> element = (JAXBElement<PurchaseOrderType>)Helper.unmarshall("org/megatome/frame2/jaxb/po.xml",TARGET_PACKAGE,getClass().getClassLoader()); //$NON-NLS-1$
      return element.getValue(); 
   }

}
