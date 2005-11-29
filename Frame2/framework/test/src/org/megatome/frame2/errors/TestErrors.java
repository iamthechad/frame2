package org.megatome.frame2.errors;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.errors.impl.ErrorImpl;
import org.megatome.frame2.errors.impl.ErrorsImpl;

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
private Errors _errors;

	/**
	 * Constructor for TestErrors.
	 * @param name
	 */
	public TestErrors(String name) {
		super(name);
	}
   
   protected void setUp( ) {
      _errors = new ErrorsImpl();
   }
   
   protected void tearDown()  {
      _errors.release();
   }

   public void testGetIfEmpty( ) {
      assertTrue(_errors.isEmpty());
      assertNull(_errors.iterator(FOO));
      assertEquals(0,_errors.get().length);
      assertEquals(0,_errors.get(FOO).length);
   }

	public void testAdd() {
		assertTrue(_errors.isEmpty());
		assertEquals(0, _errors.size());
      
		_errors.add(KEY1, ONE);

		assertFalse(_errors.isEmpty());
		assertEquals(1, _errors.size());
      
		_errors.add(KEY2, ONE, TWO);

		assertFalse(_errors.isEmpty());
		assertEquals(2, _errors.size());

		_errors.add(KEY3, ONE, TWO, THREE);

		assertFalse(_errors.isEmpty());
		assertEquals(3, _errors.size());

		Iterator allKeys = _errors.iterator();

		assertError((Error) allKeys.next(), KEY1, ONE, null, null);
		assertError((Error) allKeys.next(), KEY2, ONE, TWO, null);
		assertError((Error) allKeys.next(), KEY3, ONE, TWO, THREE);

      assertFalse(allKeys.hasNext());

      Error[] errorArray = _errors.get();
      
      assertNotNull(errorArray);
      assertEquals(3,errorArray.length);

      assertError(errorArray[0], KEY1, ONE, null, null);
      assertError(errorArray[1], KEY2, ONE, TWO, null);
      assertError(errorArray[2], KEY3, ONE, TWO, THREE);
      
      errorArray = _errors.get(KEY2);
      
      assertNotNull(errorArray);
      assertEquals(1,errorArray.length);
      assertError(errorArray[0], KEY2, ONE, TWO, null);
	}

	public void testAddDuplicateKeys() {
		_errors.add(KEY1, ONE);
		_errors.add(KEY1, ONE, TWO);
		_errors.add(KEY1, ONE, TWO, THREE);
		_errors.add(KEY2, ZERO);

		assertFalse(_errors.isEmpty());
		assertEquals(4, _errors.size());

		Iterator allKeys = _errors.iterator();

		assertError((Error) allKeys.next(), KEY1, ONE, null, null);
		assertError((Error) allKeys.next(), KEY1, ONE, TWO, null);
		assertError((Error) allKeys.next(), KEY1, ONE, TWO, THREE);
		assertError((Error) allKeys.next(), KEY2, ZERO, null, null);

		assertFalse(allKeys.hasNext());

		Iterator key1keys = _errors.iterator(KEY1);

		assertError((Error) key1keys.next(), KEY1, ONE, null, null);
		assertError((Error) key1keys.next(), KEY1, ONE, TWO, null);
		assertError((Error) key1keys.next(), KEY1, ONE, TWO, THREE);

		assertFalse(key1keys.hasNext());
	}
   
   public void testValueList_3( ) {
      _errors.add(KEY1,ONE,TWO,THREE);

      Error error = (Error) _errors.iterator().next();

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
      _errors.add(KEY1,ONE);

      Error error = (Error) _errors.iterator().next();

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
      _errors.add(KEY1,null);

      Error error = (Error) _errors.iterator().next();

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
		_errors.add(KEY1, ONE);

		assertEquals(1, _errors.size());

		_errors.release();

		assertEquals(0, _errors.size());
      assertTrue(_errors.isEmpty());
	}
   
   public void testAddUnique( ) {
      _errors.add(KEY1, ONE);
      _errors.add(KEY1, ONE);
      _errors.add(KEY1, ONE, TWO);

      assertEquals(3,_errors.size());

      _errors.addIfUnique(KEY1, ONE);
      _errors.addIfUnique(KEY1, ONE);
      _errors.addIfUnique(KEY1, ONE, TWO);

      assertEquals(3,_errors.size());
   }
   
   public void testEmpty( ) {
      _errors.add(KEY1,null);

      assertEquals(1, _errors.size());

      _errors.add(null,null);
      
      assertEquals(2, _errors.size());

      Iterator emptyKeys = _errors.iterator(Error.MISSING_KEY);
      
      assertTrue(emptyKeys.hasNext());
   }
   
   public void testAddKeyOnly() {
      assertTrue(_errors.isEmpty());
      assertEquals(0, _errors.size());
      
      _errors.add(KEY1);

      assertFalse(_errors.isEmpty());
      assertEquals(1, _errors.size());
   }
   
   public void testAddError() {
     assertTrue(_errors.isEmpty());
      assertEquals(0, _errors.size());
      
     Error mainError = new ErrorImpl(FOO, ONE);
     _errors.add(mainError); 
      
     assertFalse(_errors.isEmpty());
     assertEquals(1, _errors.size());
           
   }
   
	private void assertError(Error error, String key, Object obj1, Object obj2, Object obj3) {
		assertEquals(error.getKey(), key);
		assertEquals(error.getValue(), obj1);
		assertEquals(error.getValue1(), obj1);
		assertEquals(error.getValue2(), obj2);
		assertEquals(error.getValue3(), obj3);
	}
}
