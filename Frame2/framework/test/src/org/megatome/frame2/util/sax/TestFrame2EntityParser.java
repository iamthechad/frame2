package org.megatome.frame2.util.sax;

import java.io.IOException;

import org.megatome.frame2.Globals;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class TestFrame2EntityParser extends TestCase {

	private Frame2EntityResolver resolver = null;
	
	//private static final String GOOD_FRAME2_PUBLIC_ID = "-//Megatome Technologies//DTD Frame2 Configuration 1.0//EN";
	//private static final String GOOD_FRAME2_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/frame2-config_1_0.dtd";
	
	private static final String BAD_FRAME2_PUBLIC_ID = "-//Megatome Technologies//DTD Bob2 Configuration 1.0//EN";
	private static final String BAD_FRAME2_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/bob2-config_1_0.dtd";
	
	//private static final String GOOD_TEMPLATE_PUBLIC_ID = "-//Megatome Technologies//DTD Frame2 Template Plugin 1.0//EN";
	//private static final String GOOD_TEMPLATE_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/frame2-template_1_0.dtd";
	
	private static final String BAD_TEMPLATE_PUBLIC_ID = "-//Megatome Technologies//DTD Frame2 Bogus Plugin 1.0//EN";
	private static final String BAD_TEMPLATE_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/frame2-bogus_1_0.dtd";
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		resolver = new Frame2EntityResolver();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testResolveDTD() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(Globals.FRAME2_DTD_PUBLIC_ID, Globals.FRAME2_DTD_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNotNull("InputSource should not be null", is);
	}
	
	public void testNegativeResolveDTD() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(BAD_FRAME2_PUBLIC_ID, BAD_FRAME2_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNull("InputSource should be null", is);
	}
	
	public void testNegativePublicIdResolveDTD() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(BAD_FRAME2_PUBLIC_ID, Globals.FRAME2_DTD_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNull("InputSource should be null", is);
	}
	
	public void testNegativeSystemIdResolveDTD() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(Globals.FRAME2_DTD_PUBLIC_ID, BAD_FRAME2_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNull("InputSource should be null", is);
	}
	
	public void testResolveTemplateDTD() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(Globals.FRAME2_TEMPLATE_DTD_PUBLIC_ID, Globals.FRAME2_TEMPLATE_DTD_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNotNull("InputSource should not be null", is);
	}
	
	public void testNegativeResolveTemplateDTD() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(BAD_TEMPLATE_PUBLIC_ID, BAD_TEMPLATE_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNull("InputSource should be null", is);
	}
	
	public void testNegativePublicIdResolveTemplateDTD() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(BAD_TEMPLATE_PUBLIC_ID, Globals.FRAME2_TEMPLATE_DTD_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNull("InputSource should be null", is);
	}
	
	public void testNegativeSystemIdResolveTemplateDTD() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(Globals.FRAME2_TEMPLATE_DTD_PUBLIC_ID, BAD_TEMPLATE_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNull("InputSource should be null", is);
	}
	
	public void testNullPublicId() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(null, Globals.FRAME2_DTD_SYSTEM_ID);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNull("InputSource should be null", is);
	}
	
	public void testNullSystemId() {
		InputSource is = null;
		try {
			is = resolver.resolveEntity(Globals.FRAME2_DTD_PUBLIC_ID, null);
		} catch (SAXException e) {
			fail("Unexpected SAXexception: " + e.getMessage());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
		
		assertNull("InputSource should be null", is);
	}

}
