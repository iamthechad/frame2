package org.megatome.frame2.introspector;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * A JavaBean for testing with.  It encompasses all types supported by the introspector
 * as well as giving examples of nested and indexed properties.
 */

public class Bean1 {

	private String _stringP;
   private StringBuffer _stringBufferP;
   private BigDecimal _bigDecimalP;
   private BigInteger _bigIntegerP;

	private int _intP;
	private long _longP;
   private boolean _booleanP;
   private short _shortP;
	private char _charP;
   private byte _byteP;
	private double _doubleP;
   private float _floatP;
   
   private Integer _intPObj;
   private Long _longPObj;
   private Boolean _booleanPObj;
   private Short _shortPObj;
   private Character _characterPObj;
   private Byte _bytePObj;
   private Double _doublePObj;
   private Float _floatPObj;
   
   private String[] _stringArrayP1;
   private String[] _stringArrayP2;

	private Bean2 _bean2P;

   private int[] _intPArray = new int[10];

	/**
	 * Returns the bigDecimalP.
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimalP() {
		return _bigDecimalP;
	}

	/**
	 * Returns the bigIntegerP.
	 * @return BigInteger
	 */
	public BigInteger getBigIntegerP() {
		return _bigIntegerP;
	}

	/**
	 * Returns the booleanP.
	 * @return boolean
	 */
	public boolean isBooleanP() {
		return _booleanP;
	}

	/**
	 * Returns the booleanPObj.
	 * @return Boolean
	 */
	public Boolean getBooleanPObj() {
		return _booleanPObj;
	}

	/**
	 * Returns the byteP.
	 * @return byte
	 */
	public byte getByteP() {
		return _byteP;
	}

	/**
	 * Returns the bytePObj.
	 * @return Byte
	 */
	public Byte getBytePObj() {
		return _bytePObj;
	}

	/**
	 * Returns the characterPObj.
	 * @return Character
	 */
	public Character getCharacterPObj() {
		return _characterPObj;
	}

	/**
	 * Returns the charP.
	 * @return char
	 */
	public char getCharP() {
		return _charP;
	}

	/**
	 * Returns the doubleP.
	 * @return double
	 */
	public double getDoubleP() {
		return _doubleP;
	}

	/**
	 * Returns the doublePObj.
	 * @return Double
	 */
	public Double getDoublePObj() {
		return _doublePObj;
	}

	/**
	 * Returns the bean2P.
	 * @return Bean2
	 */
	public Bean2 getBean2P() {
      if ( _bean2P == null ) {
         _bean2P = new Bean2( );
      }
		return _bean2P;
	}

	/**
	 * Returns the floatP.
	 * @return float
	 */
	public float getFloatP() {
		return _floatP;
	}

	/**
	 * Returns the floatPObj.
	 * @return Float
	 */
	public Float getFloatPObj() {
		return _floatPObj;
	}

	/**
	 * Returns the intP.
	 * @return int
	 */
	public int getIntP() {
		return _intP;
	}

	/**
	 * Returns the intPObj.
	 * @return Integer
	 */
	public Integer getIntPObj() {
		return _intPObj;
	}

	/**
	 * Returns the longP.
	 * @return long
	 */
	public long getLongP() {
		return _longP;
	}

	/**
	 * Returns the longPObj.
	 * @return Long
	 */
	public Long getLongPObj() {
		return _longPObj;
	}

	/**
	 * Returns the shortP.
	 * @return short
	 */
	public short getShortP() {
		return _shortP;
	}

	/**
	 * Returns the shortPObj.
	 * @return Short
	 */
	public Short getShortPObj() {
		return _shortPObj;
	}

	/**
	 * Returns the stringBufferP.
	 * @return StringBuffer
	 */
	public StringBuffer getStringBufferP() {
		return _stringBufferP;
	}

	/**
	 * Returns the stringP.
	 * @return String
	 */
	public String getStringP() {
		return _stringP;
	}

	/**
	 * Sets the bigDecimalP.
	 * @param bigDecimalP The bigDecimalP to set
	 */
	public void setBigDecimalP(BigDecimal bigDecimalP) {
		_bigDecimalP = bigDecimalP;
	}

	/**
	 * Sets the bigIntegerP.
	 * @param bigIntegerP The bigIntegerP to set
	 */
	public void setBigIntegerP(BigInteger bigIntegerP) {
		_bigIntegerP = bigIntegerP;
	}

	/**
	 * Sets the booleanP.
	 * @param booleanP The booleanP to set
	 */
	public void setBooleanP(boolean booleanP) {
		_booleanP = booleanP;
	}

	/**
	 * Sets the booleanPObj.
	 * @param booleanPObj The booleanPObj to set
	 */
	public void setBooleanPObj(Boolean booleanPObj) {
		_booleanPObj = booleanPObj;
	}

	/**
	 * Sets the byteP.
	 * @param byteP The byteP to set
	 */
	public void setByteP(byte byteP) {
		_byteP = byteP;
	}

	/**
	 * Sets the bytePObj.
	 * @param bytePObj The bytePObj to set
	 */
	public void setBytePObj(Byte bytePObj) {
		_bytePObj = bytePObj;
	}

	/**
	 * Sets the characterPObj.
	 * @param characterPObj The characterPObj to set
	 */
	public void setCharacterPObj(Character characterPObj) {
		_characterPObj = characterPObj;
	}

	/**
	 * Sets the charP.
	 * @param charP The charP to set
	 */
	public void setCharP(char charP) {
		_charP = charP;
	}

	/**
	 * Sets the doubleP.
	 * @param doubleP The doubleP to set
	 */
	public void setDoubleP(double doubleP) {
		_doubleP = doubleP;
	}

	/**
	 * Sets the doublePObj.
	 * @param doublePObj The doublePObj to set
	 */
	public void setDoublePObj(Double doublePObj) {
		_doublePObj = doublePObj;
	}

	/**
	 * Sets the bean2P.
	 * @param bean2P The bean2P to set
	 */
	public void setBean2P(Bean2 bean2P) {
		_bean2P = bean2P;
	}

	/**
	 * Sets the floatP.
	 * @param floatP The floatP to set
	 */
	public void setFloatP(float floatP) {
		_floatP = floatP;
	}

	/**
	 * Sets the floatPObj.
	 * @param floatPObj The floatPObj to set
	 */
	public void setFloatPObj(Float floatPObj) {
		_floatPObj = floatPObj;
	}

	/**
	 * Sets the intP.
	 * @param intP The intP to set
	 */
	public void setIntP(int intP) {
		_intP = intP;
	}

	/**
	 * Sets the intPObj.
	 * @param intPObj The intPObj to set
	 */
	public void setIntPObj(Integer intPObj) {
		_intPObj = intPObj;
	}

	/**
	 * Sets the longP.
	 * @param longP The longP to set
	 */
	public void setLongP(long longP) {
		_longP = longP;
	}

	/**
	 * Sets the longPObj.
	 * @param longPObj The longPObj to set
	 */
	public void setLongPObj(Long longPObj) {
		_longPObj = longPObj;
	}

	/**
	 * Sets the shortP.
	 * @param shortP The shortP to set
	 */
	public void setShortP(short shortP) {
		_shortP = shortP;
	}

	/**
	 * Sets the shortPObj.
	 * @param shortPObj The shortPObj to set
	 */
	public void setShortPObj(Short shortPObj) {
		_shortPObj = shortPObj;
	}

	/**
	 * Sets the stringBufferP.
	 * @param stringBufferP The stringBufferP to set
	 */
	public void setStringBufferP(StringBuffer stringBufferP) {
		_stringBufferP = stringBufferP;
	}

	/**
	 * Sets the stringP.
	 * @param stringP The stringP to set
	 */
	public void setStringP(String stringP) {
		_stringP = stringP;
	}

	/**
	 * Returns the intPArray.
	 * @return int[]
	 */
	public int[] getIntPArray() {
		return _intPArray;
	}

	/**
	 * Sets the intPArray.
	 * @param intPArray The intPArray to set
	 */
	public void setIntPArray(int[] intPArray) {
		_intPArray = intPArray;
	}
   
   public int getIntPArray(int index) {
      if ( _intPArray == null ) {
         _intPArray = new int[index+1];
      }
      return _intPArray[index];
   }
   
   public void setIntPArray(int index,int indexedInt) {
      _intPArray[index] = indexedInt;
   }
	/**
	 * Returns the stringArrayP1.
	 * @return String
	 */
	public String[] getStringArrayP1() {
		return _stringArrayP1;
	}

	/**
	 * Returns the stringArrayP2.
	 * @return String
	 */
	public String[] getStringArrayP2() {
		return _stringArrayP2;
	}

	/**
	 * Sets the stringArrayP1.
	 * @param stringArrayP1 The stringArrayP1 to set
	 */
	public void setStringArrayP1(String[] stringArrayP1) {
		_stringArrayP1 = stringArrayP1;
	}

	/**
	 * Sets the stringArrayP2.
	 * @param stringArrayP2 The stringArrayP2 to set
	 */
	public void setStringArrayP2(String[] stringArrayP2) {
		_stringArrayP2 = stringArrayP2;
	}

}
