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
package org.megatome.app.jaxb;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.OutputStream;

import javax.xml.bind.JAXBElement;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.megatome.app.jaxbgen.ObjectFactory;
import org.megatome.app.jaxbgen.User;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.errors.impl.ErrorsFactory;
import org.megatome.frame2.util.Helper;
import org.xml.sax.SAXException;

public class TestUser {
	private static final String TARGET_PACKAGE = "org.megatome.app.jaxbgen"; //$NON-NLS-1$
	private static final ObjectFactory of = new ObjectFactory();
	
	private Schema loadSchema() {
		SchemaFactory sf = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema"); //$NON-NLS-1$
		Schema s = null;
		try {
			s = sf.newSchema(getClass().getResource("/WEB-INF/schemas/user.xsd")); //$NON-NLS-1$
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return s;
	}

	@SuppressWarnings("boxing")
	@Test
	public void testUser() {
		User user = makeFred();
		user.setValidatingSchema(loadSchema());

		Errors errors = ErrorsFactory.newInstance();

		assertTrue(user.validate(errors));
		assertTrue(errors.isEmpty());

		user.setEmail("a-boogie-boogie-boogie"); //$NON-NLS-1$

		assertFalse(user.validate(errors));
		assertFalse(errors.isEmpty());
		assertEquals(2, errors.size());
	}

	/*
	 * public void testAckNack() {
	 *  }
	 */

	private User makeFred() {
		User user = new User();

		user.setFirstName("Fred"); //$NON-NLS-1$
		user.setLastName("Flintstone"); //$NON-NLS-1$
		user.setEmail("fred@bedrockcommunity.org"); //$NON-NLS-1$
		return user;
	}

	private JAXBElement<String> makeACK() {
		return of.createACK("It was a good thing."); //$NON-NLS-1$
	}

	private JAXBElement<String> makeNACK() {
		return of.createNACK("It was a bad thing."); //$NON-NLS-1$
	}

	@Test
	public void testMarshall() throws Exception {
		User user = makeFred();
		OutputStream os = Helper.marshall(user, TARGET_PACKAGE, getClass()
				.getClassLoader());
		assertNotNull(os);
		assertTrue(os.toString().indexOf("Flintstone") > 0); //$NON-NLS-1$

		JAXBElement<String> ack = makeACK();
		os = Helper.marshall(ack, TARGET_PACKAGE, getClass().getClassLoader());
		assertNotNull(os);
		assertTrue(os.toString().indexOf("It was a good thing.") > 0); //$NON-NLS-1$

		JAXBElement<String> nack = makeNACK();
		os = Helper.marshall(nack, TARGET_PACKAGE, getClass().getClassLoader());
		assertNotNull(os);
		assertTrue(os.toString().indexOf("It was a bad thing.") > 0); //$NON-NLS-1$
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUnmarshall() throws Exception {
		User user = (User) unmarshall("org/megatome/app/jaxb/user.xml"); //$NON-NLS-1$
		assertNotNull(user);

		JAXBElement<String> ack = (JAXBElement<String>) unmarshall("org/megatome/app/jaxb/ack.xml"); //$NON-NLS-1$
		assertNotNull(ack);

		JAXBElement<String> nack = (JAXBElement<String>) unmarshall("org/megatome/app/jaxb/nack.xml"); //$NON-NLS-1$
		assertNotNull(nack);
	}

	private Object unmarshall(String path) throws Exception {
		return Helper.unmarshall(path, TARGET_PACKAGE, getClass().getClassLoader());
	}
}
