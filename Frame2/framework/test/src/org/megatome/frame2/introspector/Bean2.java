package org.megatome.frame2.introspector;

/**
 * A nested bean that also nests another bean, both as a simple
 * property as well as an indexed property.
 */

// DOC: Need to remind user that set/get in beans need to provide live objects;
// introspector won't manufacture non-standard types.

public class Bean2 {

   private static int DEFAULT_ARRAY_SIZE = 10;

	private String _stringP;
	private Bean3 _bean3P;
	private Bean3[] _bean3PArray = new Bean3[DEFAULT_ARRAY_SIZE];

   {
      for ( int i = 0 ; i < DEFAULT_ARRAY_SIZE ; i++ ) {
         _bean3PArray[i] = new Bean3( );
      }
   }

	/**
	 * Returns the nestedStringP.
	 * @return String
	 */
	public String getStringP() {
		return _stringP;
	}

	/**
	 * Sets the nestedStringP.
	 * @param nestedStringP The nestedStringP to set
	 */
	public void setStringP(String nestedStringP) {
		_stringP = nestedStringP;
	}

	public Bean3 getBean3P() {
		if (_bean3P == null) {
			_bean3P = new Bean3();
		}
		return _bean3P;
	}

	public void setBean3P(Bean3 nestedBean3P) {
		_bean3P = nestedBean3P;
	}

	public Bean3[] getBean3PArray() {
		return _bean3PArray;
	}

	public void setBean3PArray(Bean3[] bean3PArray) {
		_bean3PArray = bean3PArray;
	}

	public Bean3 getBean3PArray(int index) {
		return _bean3PArray[index];
	}

	public void setBean3PArray(int index, Bean3 indexedBean) {
		_bean3PArray[index] = indexedBean;
	}

}
