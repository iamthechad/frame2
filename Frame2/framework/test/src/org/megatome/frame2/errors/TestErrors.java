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

   public void testGets( ) {

   }

   public void testGetIfEmpty( ) {
      assertTrue(_errors.isEmpty());
      assertNull(_errors.iterator("FOO"));
      assertEquals(0,_errors.get().length);
      assertEquals(0,_errors.get("FOO").length);
   }

	public void testAdd() {
		assertTrue(_errors.isEmpty());
		assertEquals(0, _errors.size());
      
		_errors.add("KEY1", "1");

		assertFalse(_errors.isEmpty());
		assertEquals(1, _errors.size());
      
		_errors.add("KEY2", "1", "2");

		assertFalse(_errors.isEmpty());
		assertEquals(2, _errors.size());

		_errors.add("KEY3", "1", "2", "3");

		assertFalse(_errors.isEmpty());
		assertEquals(3, _errors.size());

		Iterator allKeys = _errors.iterator();

		assertError((Error) allKeys.next(), "KEY1", "1", null, null);
		assertError((Error) allKeys.next(), "KEY2", "1", "2", null);
		assertError((Error) allKeys.next(), "KEY3", "1", "2", "3");

      assertFalse(allKeys.hasNext());

      Error[] errorArray = _errors.get();
      
      assertNotNull(errorArray);
      assertEquals(3,errorArray.length);

      assertError(errorArray[0], "KEY1", "1", null, null);
      assertError(errorArray[1], "KEY2", "1", "2", null);
      assertError(errorArray[2], "KEY3", "1", "2", "3");
      
      errorArray = _errors.get("KEY2");
      
      assertNotNull(errorArray);
      assertEquals(1,errorArray.length);
      assertError(errorArray[0], "KEY2", "1", "2", null);
	}

	public void testAddDuplicateKeys() {
		_errors.add("KEY1", "1");
		_errors.add("KEY1", "1", "2");
		_errors.add("KEY1", "1", "2", "3");
		_errors.add("KEY2", "0");

		assertFalse(_errors.isEmpty());
		assertEquals(4, _errors.size());

		Iterator allKeys = _errors.iterator();

		assertError((Error) allKeys.next(), "KEY1", "1", null, null);
		assertError((Error) allKeys.next(), "KEY1", "1", "2", null);
		assertError((Error) allKeys.next(), "KEY1", "1", "2", "3");
		assertError((Error) allKeys.next(), "KEY2", "0", null, null);

		assertFalse(allKeys.hasNext());

		Iterator key1keys = _errors.iterator("KEY1");

		assertError((Error) key1keys.next(), "KEY1", "1", null, null);
		assertError((Error) key1keys.next(), "KEY1", "1", "2", null);
		assertError((Error) key1keys.next(), "KEY1", "1", "2", "3");

		assertFalse(key1keys.hasNext());
	}
   
   public void testValueList_3( ) {
      _errors.add("KEY1","1","2","3");

      Error error = (Error) _errors.iterator().next();

      Object[] valueArray = error.getValues();
      assertNotNull(valueArray);
      assertEquals(3,valueArray.length);
      assertEquals("1",valueArray[0]);
      assertEquals("2",valueArray[1]);
      assertEquals("3",valueArray[2]);

      List list = error.getValueList();
      
      assertNotNull(list);
      assertEquals(3,list.size());
      
      Iterator values = list.iterator();
      
      assertEquals("1",values.next());
      assertEquals("2",values.next());
      assertEquals("3",values.next());
   }

   public void testValueList_1() {
      _errors.add("KEY1","1");

      Error error = (Error) _errors.iterator().next();

      Object[] valueArray = error.getValues();
      assertNotNull(valueArray);
      assertEquals(1,valueArray.length);
      assertEquals("1",valueArray[0]);

      List list = error.getValueList();
      
      assertNotNull(list);
      assertEquals(1,list.size());
      
      Iterator values = list.iterator();
      
      assertEquals("1",values.next());
   }

   public void testEmptyValueList( ) {
      _errors.add("KEY1",null);

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
		_errors.add("KEY1", "1");

		assertEquals(1, _errors.size());

		_errors.release();

		assertEquals(0, _errors.size());
      assertTrue(_errors.isEmpty());
	}
   
   public void testAddUnique( ) {
      _errors.add("KEY1", "1");
      _errors.add("KEY1", "1");
      _errors.add("KEY1", "1", "2");

      assertEquals(3,_errors.size());

      _errors.addIfUnique("KEY1", "1");
      _errors.addIfUnique("KEY1", "1");
      _errors.addIfUnique("KEY1", "1", "2");

      assertEquals(3,_errors.size());
   }
   
   public void testEmpty( ) {
      _errors.add("KEY1",null);

      assertEquals(1, _errors.size());

      _errors.add(null,null);
      
      assertEquals(2, _errors.size());

      Iterator emptyKeys = _errors.iterator(Error.MISSING_KEY);
      
      assertTrue(emptyKeys.hasNext());
   }
   
   public void testAddKeyOnly() {
      assertTrue(_errors.isEmpty());
      assertEquals(0, _errors.size());
      
      _errors.add("KEY1");

      assertFalse(_errors.isEmpty());
      assertEquals(1, _errors.size());
   }
   
   public void testAddError() {
     assertTrue(_errors.isEmpty());
      assertEquals(0, _errors.size());
      
     Error mainError = new ErrorImpl("tag.question.with.parm", "really");
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
