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
package org.megatome.frame2.introspector;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.megatome.frame2.Globals;
import org.megatome.frame2.introspector.Introspector;
import org.megatome.frame2.introspector.IntrospectorFactory;
import org.megatome.frame2.introspector.MappingException;
import org.megatome.frame2.introspector.MappingsException;

/**
 * TestIntrospector 
 */

public class TestIntrospector extends TestCase {

	static private final double DELTA = 0.0001;

	/**
	 * Constructor for TestIntrospector.
	 */
	public TestIntrospector() {
		super();
	}

	/**
	 * Constructor for TestIntrospector.
	 * @param name
	 */
	public TestIntrospector(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Method testSimplePopulate.
	 * @throws Exception
	 */
	public void testSimplePopulate() throws Exception {
		Map map = new HashMap();
		Bean1 bean = new Bean1();

		map.put("stringP", "bar1");
		map.put("stringBufferP", "bar2");
		map.put("bigDecimalP", "12345.67890");
		map.put("bigIntegerP", "1234567890");

		map.put("intP", "5");
		map.put("longP", "55");
		map.put("booleanP", "true");
		map.put("shortP", "34");
		map.put("charP", "P");
		map.put("byteP", "1");
		map.put("doubleP", "5.5");
		map.put("floatP", "5.0");

		map.put("intPObj", "6");
		map.put("longPObj", "66");
		map.put("booleanPObj", "TRUE");
		map.put("shortPObj", "45");
		map.put("characterPObj", "Q");
		map.put("bytePObj", "2");
		map.put("doublePObj", "6.6");
		map.put("floatPObj", "6.0");

		map.put("stringArrayP1", new String[] { "bar3" });
		map.put("stringArrayP2", new String[] { "bar4", "bar5" });

		Introspector introspector = IntrospectorFactory.instance();

		introspector.mapProperties(map, bean);

		assertEquals("bar1", bean.getStringP());
		assertEquals("bar2", bean.getStringBufferP().toString());
		assertEquals(new BigDecimal("12345.67890"), bean.getBigDecimalP());
		assertEquals(new BigInteger("1234567890"), bean.getBigIntegerP());

		assertEquals(5, bean.getIntP());
		assertEquals(55, bean.getLongP());
		assertEquals(true, bean.isBooleanP());
		assertEquals(34, bean.getShortP());
		assertEquals('P', bean.getCharP());
		assertEquals(1, bean.getByteP());
		assertEquals(5.5, bean.getDoubleP(), DELTA);
		assertEquals(5.0, bean.getFloatP(), DELTA);

		assertEquals(6, bean.getIntPObj().intValue());
		assertEquals(66, bean.getLongPObj().longValue());
		assertEquals(true, bean.getBooleanPObj().booleanValue());
		assertEquals(45, bean.getShortPObj().shortValue());
		assertEquals('Q', bean.getCharacterPObj().charValue());
		assertEquals(2, bean.getBytePObj().byteValue());
		assertEquals(6.6, bean.getDoublePObj().doubleValue(), DELTA);
		assertEquals(6.0, bean.getFloatPObj().floatValue(), DELTA);

		String[] array1 = bean.getStringArrayP1();
		assertNotNull(array1);
		assertTrue(array1.length == 1);
		assertEquals("bar3", array1[0]);
		//assertEquals("bar3", bean.getStringArrayP1());
		String[] array2 = bean.getStringArrayP2();
		assertNotNull(array2);
		assertTrue(array2.length == 2);
		assertEquals("bar4", array2[0]);
		assertEquals("bar5", array2[1]);
		//assertEquals("bar4", bean.getStringArrayP2());
	}

	public void testMisalignment() throws Exception {
		Map map = new HashMap();
		Bean1 bean = new Bean1();

		map.put("stringP", "bar1");
		map.put("intPnot", "5");
		map.put("longP", "55");
		map.put("charPnot", "P");
		map.put("floatP", "5.0");
		map.put("doublePnot", "5.5");
		map.put("bean2P.xxxNot", "XX");
		map.put("bean2P.xxNot[1]", "XX");

		Introspector introspector = IntrospectorFactory.instance();

		introspector.mapProperties(map, bean);

		assertEquals("bar1", bean.getStringP());
		assertEquals(0, bean.getIntP());
		assertEquals(55, bean.getLongP());
		assertEquals('\0', bean.getCharP());
		assertEquals(5.0, bean.getFloatP(), DELTA);
		assertEquals(0.0, bean.getDoubleP(), DELTA);
	}

	public void testNestedProperty() throws Exception {
		Map map = new HashMap();
		Bean1 bean = new Bean1();

		map.put("bean2P.stringP", "bar2");
		map.put("bean2P.bean3P.doubleP", "1.5");

		Introspector introspector = IntrospectorFactory.instance();

		introspector.mapProperties(map, bean);

		assertEquals("bar2", bean.getBean2P().getStringP());
		assertEquals(1.5, bean.getBean2P().getBean3P().getDoubleP(), DELTA);
	}

	public void testSimpleIndexedProperty() throws Exception {
		Map map = new HashMap();
		Bean1 bean = new Bean1();

		map.put("intPArray[5]", "5");
		map.put("intPArray[6]", "6");
		map.put("intPArray[2]", "2");
		map.put("intPArray[0]", "0");
		map.put("intPArray[1]", "1");
		map.put("intPArray[3]", "3");
		map.put("intPArray[4]", "4");

		Introspector introspector = IntrospectorFactory.instance();

		introspector.mapProperties(map, bean);

		assertEquals(0, bean.getIntPArray(0));
		assertEquals(1, bean.getIntPArray(1));
		assertEquals(2, bean.getIntPArray(2));
		assertEquals(3, bean.getIntPArray(3));
		assertEquals(4, bean.getIntPArray(4));
		assertEquals(5, bean.getIntPArray(5));
		assertEquals(6, bean.getIntPArray(6));
	}

	public void testNestedIndexedProperty() throws Exception {
		Map map = new HashMap();
		Bean1 bean = new Bean1();

		map.put("bean2P.bean3PArray[1].doubleP", "1.0");
		map.put("bean2P.bean3PArray[2].doubleP", "2.0");
		map.put("bean2P.bean3PArray[0].doubleP", "0.0");
		map.put("bean2P.bean3PArray[3].doubleP", "3.0");

		Introspector introspector = IntrospectorFactory.instance();

		introspector.mapProperties(map, bean);

		assertEquals(0.0, bean.getBean2P().getBean3PArray(0).getDoubleP(), DELTA);
		assertEquals(1.0, bean.getBean2P().getBean3PArray(1).getDoubleP(), DELTA);
		assertEquals(2.0, bean.getBean2P().getBean3PArray(2).getDoubleP(), DELTA);
		assertEquals(3.0, bean.getBean2P().getBean3PArray(3).getDoubleP(), DELTA);
	}

	public void testNonConvertableValue() {
		Map map = new HashMap();
		Bean1 bean = new Bean1();
      Object value = new Object();

		map.put("shortP",value);
		Introspector introspector = IntrospectorFactory.instance();

		// This should pass until the conversion

		try {
			introspector.mapProperties(map, bean);
			fail();
		} catch (MappingsException e) {
         assertMappingsException(bean, value, e);
		} catch (Exception e ) {
         fail();
      }
	}

   public void testNonParsableValue() {
      Map map = new HashMap();
      Bean1 bean = new Bean1();
      String value = "ax111";

      map.put("shortP", value );
      Introspector introspector = IntrospectorFactory.instance();

      // This should pass until the conversion

      try {
         introspector.mapProperties(map, bean);
         fail();
      } catch (MappingsException e) {
         assertMappingsException(bean, value, e);
      } catch (Exception e ) {
         fail();
      }
   }

   public void testParseWithStringArray() {
      Map map = new HashMap();
      Bean1 bean = new Bean1();
      String value = "ax111";

      map.put("shortP", new String[] { value } );
      Introspector introspector = IntrospectorFactory.instance();

      // This should pass until the conversion

      try {
         introspector.mapProperties(map, bean);
         fail();
      } catch (MappingsException e) {
         assertMappingsException(bean, value, e);
      } catch (Exception e ) {
         fail();
      }
   }

   private void assertMappingsException(Bean1 bean, Object value, MappingsException e) {
      MappingException[] mapexcs = e.getMappingExceptions();
      
      assertNotNull(mapexcs);
      assertEquals(1,mapexcs.length);
      
      assertEquals(bean.getClass().getName(),mapexcs[0].getBeanName());
      assertEquals(Globals.MAPPING_KEY_PREFIX + "org.megatome.frame2.introspector.Bean1.shortP",mapexcs[0].getKey());
      assertEquals("shortP",mapexcs[0].getProperty());
      Object mapValue = mapexcs[0].getValue();
      if (mapValue instanceof Object[]) {
      	assertSame(value, ((Object[])mapValue)[0]);
      } else {
      	assertSame(value,mapValue);
      }
      assertTrue(mapexcs[0].getCause() instanceof IllegalArgumentException);
   }
   
   public void testHiddenJunkOnData( ) throws Exception {
      Map map = new HashMap();
      Bean1 bean = new Bean1();

      map.put("intP","  100000  \n");
      Introspector introspector = IntrospectorFactory.instance();

      introspector.mapProperties(map, bean);

      assertEquals(100000, bean.getIntP());
   }
}
