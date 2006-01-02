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
package org.megatome.frame2.errors;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.megatome.frame2.errors.impl.ErrorFactory;
import org.megatome.frame2.errors.impl.ErrorsFactory;

/**
 * 
 */
public class TestErrors extends TestCase {

   private static final String ZERO = "0";
private static final String THREE = "3";
private static final String TWO = "2";
private static final String ONE = "1";
private static final String KEY3 = "KEY3";
private static final String KEY2 = "KEY2";
private static final String KEY1 = "KEY1";
private static final String FOO = "FOO";
private Errors errors;

	/**
	 * Constructor for TestErrors.
	 * @param name
	 */
	public TestErrors(String name) {
		super(name);
	}
   
   protected void setUp( ) {
      errors = ErrorsFactory.newInstance();
   }
   
   protected void tearDown()  {
      errors.release();
   }

   public void testGetIfEmpty( ) {
      assertTrue(errors.isEmpty());
      assertNull(errors.iterator(FOO));
      assertEquals(0,errors.get().length);
      assertEquals(0,errors.get(FOO).length);
   }

	public void testAdd() {
		assertTrue(errors.isEmpty());
		assertEquals(0, errors.size());
      
		errors.add(KEY1, ONE);

		assertFalse(errors.isEmpty());
		assertEquals(1, errors.size());
      
		errors.add(KEY2, ONE, TWO);

		assertFalse(errors.isEmpty());
		assertEquals(2, errors.size());

		errors.add(KEY3, ONE, TWO, THREE);

		assertFalse(errors.isEmpty());
		assertEquals(3, errors.size());

		Iterator allKeys = errors.iterator();

		assertError((Error) allKeys.next(), KEY1, ONE, null, null);
		assertError((Error) allKeys.next(), KEY2, ONE, TWO, null);
		assertError((Error) allKeys.next(), KEY3, ONE, TWO, THREE);

      assertFalse(allKeys.hasNext());

      Error[] errorArray = errors.get();
      
      assertNotNull(errorArray);
      assertEquals(3,errorArray.length);

      assertError(errorArray[0], KEY1, ONE, null, null);
      assertError(errorArray[1], KEY2, ONE, TWO, null);
      assertError(errorArray[2], KEY3, ONE, TWO, THREE);
      
      errorArray = errors.get(KEY2);
      
      assertNotNull(errorArray);
      assertEquals(1,errorArray.length);
      assertError(errorArray[0], KEY2, ONE, TWO, null);
	}

	public void testAddDuplicateKeys() {
		errors.add(KEY1, ONE);
		errors.add(KEY1, ONE, TWO);
		errors.add(KEY1, ONE, TWO, THREE);
		errors.add(KEY2, ZERO);

		assertFalse(errors.isEmpty());
		assertEquals(4, errors.size());

		Iterator allKeys = errors.iterator();

		assertError((Error) allKeys.next(), KEY1, ONE, null, null);
		assertError((Error) allKeys.next(), KEY1, ONE, TWO, null);
		assertError((Error) allKeys.next(), KEY1, ONE, TWO, THREE);
		assertError((Error) allKeys.next(), KEY2, ZERO, null, null);

		assertFalse(allKeys.hasNext());

		Iterator key1keys = errors.iterator(KEY1);

		assertError((Error) key1keys.next(), KEY1, ONE, null, null);
		assertError((Error) key1keys.next(), KEY1, ONE, TWO, null);
		assertError((Error) key1keys.next(), KEY1, ONE, TWO, THREE);

		assertFalse(key1keys.hasNext());
	}
   
   public void testValueList_3( ) {
      errors.add(KEY1,ONE,TWO,THREE);

      Error error = (Error) errors.iterator().next();

      Object[] valueArray = error.getValues();
      assertNotNull(valueArray);
      assertEquals(3,valueArray.length);
      assertEquals(ONE,valueArray[0]);
      assertEquals(TWO,valueArray[1]);
      assertEquals(THREE,valueArray[2]);

      List list = error.getValueList();
      
      assertNotNull(list);
      assertEquals(3,list.size());
      
      Iterator values = list.iterator();
      
      assertEquals(ONE,values.next());
      assertEquals(TWO,values.next());
      assertEquals(THREE,values.next());
   }

   public void testValueList_1() {
      errors.add(KEY1,ONE);

      Error error = (Error) errors.iterator().next();

      Object[] valueArray = error.getValues();
      assertNotNull(valueArray);
      assertEquals(1,valueArray.length);
      assertEquals(ONE,valueArray[0]);

      List list = error.getValueList();
      
      assertNotNull(list);
      assertEquals(1,list.size());
      
      Iterator values = list.iterator();
      
      assertEquals(ONE,values.next());
   }

   public void testEmptyValueList( ) {
      errors.add(KEY1,null);

      Error error = (Error) errors.iterator().next();

      Object[] valueArray = error.getValues();
      assertNotNull(valueArray);
      assertEquals(0,valueArray.length);

      List list = error.getValueList();
      
      assertNotNull(list);
      assertEquals(0,list.size());
      
      Iterator values = list.iterator();
      
      assertFalse(values.hasNext());
   }

	public void testRelease() {
		errors.add(KEY1, ONE);

		assertEquals(1, errors.size());

		errors.release();

		assertEquals(0, errors.size());
      assertTrue(errors.isEmpty());
	}
   
   public void testAddUnique( ) {
      errors.add(KEY1, ONE);
      errors.add(KEY1, ONE);
      errors.add(KEY1, ONE, TWO);

      assertEquals(3,errors.size());

      errors.addIfUnique(KEY1, ONE);
      errors.addIfUnique(KEY1, ONE);
      errors.addIfUnique(KEY1, ONE, TWO);

      assertEquals(3,errors.size());
   }
   
   public void testEmpty( ) {
      errors.add(KEY1,null);

      assertEquals(1, errors.size());

      errors.add(null,null);
      
      assertEquals(2, errors.size());

      Iterator emptyKeys = errors.iterator(Error.MISSING_KEY);
      
      assertTrue(emptyKeys.hasNext());
   }
   
   public void testAddKeyOnly() {
      assertTrue(errors.isEmpty());
      assertEquals(0, errors.size());
      
      errors.add(KEY1);

      assertFalse(errors.isEmpty());
      assertEquals(1, errors.size());
   }
   
   public void testAddError() {
     assertTrue(errors.isEmpty());
      assertEquals(0, errors.size());
      
     Error mainError = ErrorFactory.createError(FOO, ONE);
     errors.add(mainError); 
      
     assertFalse(errors.isEmpty());
     assertEquals(1, errors.size());
           
   }
   
	private void assertError(Error error, String key, Object obj1, Object obj2, Object obj3) {
		assertEquals(error.getKey(), key);
		assertEquals(error.getValue(), obj1);
		assertEquals(error.getValue1(), obj1);
		assertEquals(error.getValue2(), obj2);
		assertEquals(error.getValue3(), obj3);
	}
}
