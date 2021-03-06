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
package org.megatome.frame2.errors;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.megatome.frame2.errors.impl.ErrorFactory;
import org.megatome.frame2.errors.impl.ErrorsFactory;

/**
 * 
 */
public class TestErrors {

	private static final String ZERO = "0"; //$NON-NLS-1$
	private static final String THREE = "3"; //$NON-NLS-1$
	private static final String TWO = "2"; //$NON-NLS-1$
	private static final String ONE = "1"; //$NON-NLS-1$
	private static final String KEY3 = "KEY3"; //$NON-NLS-1$
	private static final String KEY2 = "KEY2"; //$NON-NLS-1$
	private static final String KEY1 = "KEY1"; //$NON-NLS-1$
	private static final String FOO = "FOO"; //$NON-NLS-1$

	private Errors errors;

	@Before
	public void setUpErrors() {
		this.errors = ErrorsFactory.newInstance();
	}

	@After
	public void releaseErrors() {
		this.errors.release();
	}

	@Test
	public void testGetIfEmpty() {
		assertTrue(this.errors.isEmpty());
		assertTrue(this.errors.get(FOO).isEmpty());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testAdd() {
		assertTrue(this.errors.isEmpty());
		assertEquals(0, this.errors.size());

		this.errors.add(KEY1, ONE);

		assertFalse(this.errors.isEmpty());
		assertEquals(1, this.errors.size());

		this.errors.add(KEY2, ONE, TWO);

		assertFalse(this.errors.isEmpty());
		assertEquals(2, this.errors.size());

		this.errors.add(KEY3, ONE, TWO, THREE);

		assertFalse(this.errors.isEmpty());
		assertEquals(3, this.errors.size());

		Iterator<Error> allKeys = this.errors.get().iterator();

		assertError(allKeys.next(), KEY1, ONE, null, null);
		assertError(allKeys.next(), KEY2, ONE, TWO, null);
		assertError(allKeys.next(), KEY3, ONE, TWO, THREE);

		assertFalse(allKeys.hasNext());

		allKeys = this.errors.get(KEY2).iterator();
		//errorArray = this.errors.get(KEY2);

		assertTrue(allKeys.hasNext());
		assertError(allKeys.next(), KEY2, ONE, TWO, null);
		assertFalse(allKeys.hasNext());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testAddDuplicateKeys() {
		this.errors.add(KEY1, ONE);
		this.errors.add(KEY1, ONE, TWO);
		this.errors.add(KEY1, ONE, TWO, THREE);
		this.errors.add(KEY2, ZERO);

		assertFalse(this.errors.isEmpty());
		assertEquals(4, this.errors.size());

		Iterator<Error> allKeys = this.errors.get().iterator();

		assertError(allKeys.next(), KEY1, ONE, null, null);
		assertError(allKeys.next(), KEY1, ONE, TWO, null);
		assertError(allKeys.next(), KEY1, ONE, TWO, THREE);
		assertError(allKeys.next(), KEY2, ZERO, null, null);

		assertFalse(allKeys.hasNext());

		Iterator<Error> key1keys = this.errors.get(KEY1).iterator();

		assertError(key1keys.next(), KEY1, ONE, null, null);
		assertError(key1keys.next(), KEY1, ONE, TWO, null);
		assertError(key1keys.next(), KEY1, ONE, TWO, THREE);

		assertFalse(key1keys.hasNext());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testValueList_3() {
		this.errors.add(KEY1, ONE, TWO, THREE);

		Error error = this.errors.get().iterator().next();

		Object[] valueArray = error.getValues();
		assertNotNull(valueArray);
		assertEquals(3, valueArray.length);
		assertEquals(ONE, valueArray[0]);
		assertEquals(TWO, valueArray[1]);
		assertEquals(THREE, valueArray[2]);

		List<Object> list = error.getValueList();

		assertNotNull(list);
		assertEquals(3, list.size());

		Iterator<Object> values = list.iterator();

		assertEquals(ONE, values.next());
		assertEquals(TWO, values.next());
		assertEquals(THREE, values.next());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testValueList_1() {
		this.errors.add(KEY1, ONE);

		Error error = this.errors.get().iterator().next();

		Object[] valueArray = error.getValues();
		assertNotNull(valueArray);
		assertEquals(1, valueArray.length);
		assertEquals(ONE, valueArray[0]);

		List<Object> list = error.getValueList();

		assertNotNull(list);
		assertEquals(1, list.size());

		Iterator<Object> values = list.iterator();

		assertEquals(ONE, values.next());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testEmptyValueList() {
		this.errors.add(KEY1, null);

		Error error = this.errors.get().iterator().next();

		Object[] valueArray = error.getValues();
		assertNotNull(valueArray);
		assertEquals(0, valueArray.length);

		List<Object> list = error.getValueList();

		assertNotNull(list);
		assertEquals(0, list.size());

		Iterator<Object> values = list.iterator();

		assertFalse(values.hasNext());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testRelease() {
		this.errors.add(KEY1, ONE);

		assertEquals(1, this.errors.size());

		this.errors.release();

		assertEquals(0, this.errors.size());
		assertTrue(this.errors.isEmpty());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testAddUnique() {
		this.errors.add(KEY1, ONE);
		this.errors.add(KEY1, ONE);
		this.errors.add(KEY1, ONE, TWO);

		assertEquals(3, this.errors.size());

		this.errors.addIfUnique(KEY1, ONE);
		this.errors.addIfUnique(KEY1, ONE);
		this.errors.addIfUnique(KEY1, ONE, TWO);

		assertEquals(3, this.errors.size());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testEmpty() {
		this.errors.add(KEY1, null);

		assertEquals(1, this.errors.size());

		this.errors.add(null, null);

		assertEquals(2, this.errors.size());

		Iterator<Error> emptyKeys = this.errors.get(Error.MISSING_KEY).iterator();

		assertTrue(emptyKeys.hasNext());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testAddKeyOnly() {
		assertTrue(this.errors.isEmpty());
		assertEquals(0, this.errors.size());

		this.errors.add(KEY1);

		assertFalse(this.errors.isEmpty());
		assertEquals(1, this.errors.size());
	}

	@SuppressWarnings("boxing")
	@Test
	public void testAddError() {
		assertTrue(this.errors.isEmpty());
		assertEquals(0, this.errors.size());

		Error mainError = ErrorFactory.createError(FOO, ONE);
		this.errors.add(mainError);

		assertFalse(this.errors.isEmpty());
		assertEquals(1, this.errors.size());

	}

	private void assertError(Error error, String key, Object obj1, Object obj2,
			Object obj3) {
		assertEquals(error.getKey(), key);
		assertEquals(error.getValue(), obj1);
		assertEquals(error.getValue1(), obj1);
		assertEquals(error.getValue2(), obj2);
		assertEquals(error.getValue3(), obj3);
	}
}
