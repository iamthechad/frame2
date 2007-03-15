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
package org.megatome.frame2.introspector;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * A JavaBean for testing with.  It encompasses all types supported by the introspector
 * as well as giving examples of nested and indexed properties.
 */

public class Bean1 {

	private String stringP;
   private StringBuffer stringBufferP;
   private BigDecimal bigDecimalP;
   private BigInteger bigIntegerP;

	private int intP;
	private long longP;
   private boolean booleanP;
   private short shortP;
	private char charP;
   private byte byteP;
	private double doubleP;
   private float floatP;
   
   private Integer intPObj;
   private Long longPObj;
   private Boolean booleanPObj;
   private Short shortPObj;
   private Character characterPObj;
   private Byte bytePObj;
   private Double doublePObj;
   private Float floatPObj;
   
   private String[] stringArrayP1;
   private String[] stringArrayP2;

	private Bean2 bean2P;

   private int[] intPArray = new int[10];

	/**
	 * Returns the bigDecimalP.
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimalP() {
		return this.bigDecimalP;
	}

	/**
	 * Returns the bigIntegerP.
	 * @return BigInteger
	 */
	public BigInteger getBigIntegerP() {
		return this.bigIntegerP;
	}

	/**
	 * Returns the booleanP.
	 * @return boolean
	 */
	public boolean isBooleanP() {
		return this.booleanP;
	}

	/**
	 * Returns the booleanPObj.
	 * @return Boolean
	 */
	public Boolean getBooleanPObj() {
		return this.booleanPObj;
	}

	/**
	 * Returns the byteP.
	 * @return byte
	 */
	public byte getByteP() {
		return this.byteP;
	}

	/**
	 * Returns the bytePObj.
	 * @return Byte
	 */
	public Byte getBytePObj() {
		return this.bytePObj;
	}

	/**
	 * Returns the characterPObj.
	 * @return Character
	 */
	public Character getCharacterPObj() {
		return this.characterPObj;
	}

	/**
	 * Returns the charP.
	 * @return char
	 */
	public char getCharP() {
		return this.charP;
	}

	/**
	 * Returns the doubleP.
	 * @return double
	 */
	public double getDoubleP() {
		return this.doubleP;
	}

	/**
	 * Returns the doublePObj.
	 * @return Double
	 */
	public Double getDoublePObj() {
		return this.doublePObj;
	}

	/**
	 * Returns the bean2P.
	 * @return Bean2
	 */
	public Bean2 getBean2P() {
      if ( this.bean2P == null ) {
         this.bean2P = new Bean2( );
      }
		return this.bean2P;
	}

	/**
	 * Returns the floatP.
	 * @return float
	 */
	public float getFloatP() {
		return this.floatP;
	}

	/**
	 * Returns the floatPObj.
	 * @return Float
	 */
	public Float getFloatPObj() {
		return this.floatPObj;
	}

	/**
	 * Returns the intP.
	 * @return int
	 */
	public int getIntP() {
		return this.intP;
	}

	/**
	 * Returns the intPObj.
	 * @return Integer
	 */
	public Integer getIntPObj() {
		return this.intPObj;
	}

	/**
	 * Returns the longP.
	 * @return long
	 */
	public long getLongP() {
		return this.longP;
	}

	/**
	 * Returns the longPObj.
	 * @return Long
	 */
	public Long getLongPObj() {
		return this.longPObj;
	}

	/**
	 * Returns the shortP.
	 * @return short
	 */
	public short getShortP() {
		return this.shortP;
	}

	/**
	 * Returns the shortPObj.
	 * @return Short
	 */
	public Short getShortPObj() {
		return this.shortPObj;
	}

	/**
	 * Returns the stringBufferP.
	 * @return StringBuffer
	 */
	public StringBuffer getStringBufferP() {
		return this.stringBufferP;
	}

	/**
	 * Returns the stringP.
	 * @return String
	 */
	public String getStringP() {
		return this.stringP;
	}

	/**
	 * Sets the bigDecimalP.
	 * @param bigDecimalP The bigDecimalP to set
	 */
	public void setBigDecimalP(BigDecimal bigDecimalP) {
		this.bigDecimalP = bigDecimalP;
	}

	/**
	 * Sets the bigIntegerP.
	 * @param bigIntegerP The bigIntegerP to set
	 */
	public void setBigIntegerP(BigInteger bigIntegerP) {
		this.bigIntegerP = bigIntegerP;
	}

	/**
	 * Sets the booleanP.
	 * @param booleanP The booleanP to set
	 */
	public void setBooleanP(boolean booleanP) {
		this.booleanP = booleanP;
	}

	/**
	 * Sets the booleanPObj.
	 * @param booleanPObj The booleanPObj to set
	 */
	public void setBooleanPObj(Boolean booleanPObj) {
		this.booleanPObj = booleanPObj;
	}

	/**
	 * Sets the byteP.
	 * @param byteP The byteP to set
	 */
	public void setByteP(byte byteP) {
		this.byteP = byteP;
	}

	/**
	 * Sets the bytePObj.
	 * @param bytePObj The bytePObj to set
	 */
	public void setBytePObj(Byte bytePObj) {
		this.bytePObj = bytePObj;
	}

	/**
	 * Sets the characterPObj.
	 * @param characterPObj The characterPObj to set
	 */
	public void setCharacterPObj(Character characterPObj) {
		this.characterPObj = characterPObj;
	}

	/**
	 * Sets the charP.
	 * @param charP The charP to set
	 */
	public void setCharP(char charP) {
		this.charP = charP;
	}

	/**
	 * Sets the doubleP.
	 * @param doubleP The doubleP to set
	 */
	public void setDoubleP(double doubleP) {
		this.doubleP = doubleP;
	}

	/**
	 * Sets the doublePObj.
	 * @param doublePObj The doublePObj to set
	 */
	public void setDoublePObj(Double doublePObj) {
		this.doublePObj = doublePObj;
	}

	/**
	 * Sets the bean2P.
	 * @param bean2P The bean2P to set
	 */
	public void setBean2P(Bean2 bean2P) {
		this.bean2P = bean2P;
	}

	/**
	 * Sets the floatP.
	 * @param floatP The floatP to set
	 */
	public void setFloatP(float floatP) {
		this.floatP = floatP;
	}

	/**
	 * Sets the floatPObj.
	 * @param floatPObj The floatPObj to set
	 */
	public void setFloatPObj(Float floatPObj) {
		this.floatPObj = floatPObj;
	}

	/**
	 * Sets the intP.
	 * @param intP The intP to set
	 */
	public void setIntP(int intP) {
		this.intP = intP;
	}

	/**
	 * Sets the intPObj.
	 * @param intPObj The intPObj to set
	 */
	public void setIntPObj(Integer intPObj) {
		this.intPObj = intPObj;
	}

	/**
	 * Sets the longP.
	 * @param longP The longP to set
	 */
	public void setLongP(long longP) {
		this.longP = longP;
	}

	/**
	 * Sets the longPObj.
	 * @param longPObj The longPObj to set
	 */
	public void setLongPObj(Long longPObj) {
		this.longPObj = longPObj;
	}

	/**
	 * Sets the shortP.
	 * @param shortP The shortP to set
	 */
	public void setShortP(short shortP) {
		this.shortP = shortP;
	}

	/**
	 * Sets the shortPObj.
	 * @param shortPObj The shortPObj to set
	 */
	public void setShortPObj(Short shortPObj) {
		this.shortPObj = shortPObj;
	}

	/**
	 * Sets the stringBufferP.
	 * @param stringBufferP The stringBufferP to set
	 */
	public void setStringBufferP(StringBuffer stringBufferP) {
		this.stringBufferP = stringBufferP;
	}

	/**
	 * Sets the stringP.
	 * @param stringP The stringP to set
	 */
	public void setStringP(String stringP) {
		this.stringP = stringP;
	}

	/**
	 * Returns the intPArray.
	 * @return int[]
	 */
	public int[] getIntPArray() {
		return this.intPArray;
	}

	/**
	 * Sets the intPArray.
	 * @param intPArray The intPArray to set
	 */
	public void setIntPArray(int[] intPArray) {
		this.intPArray = intPArray;
	}
   
   public int getIntPArray(int index) {
      if ( this.intPArray == null ) {
         this.intPArray = new int[index+1];
      }
      return this.intPArray[index];
   }
   
   public void setIntPArray(int index,int indexedInt) {
      this.intPArray[index] = indexedInt;
   }
	/**
	 * Returns the stringArrayP1.
	 * @return String
	 */
	public String[] getStringArrayP1() {
		return this.stringArrayP1;
	}

	/**
	 * Returns the stringArrayP2.
	 * @return String
	 */
	public String[] getStringArrayP2() {
		return this.stringArrayP2;
	}

	/**
	 * Sets the stringArrayP1.
	 * @param stringArrayP1 The stringArrayP1 to set
	 */
	public void setStringArrayP1(String[] stringArrayP1) {
		this.stringArrayP1 = stringArrayP1;
	}

	/**
	 * Sets the stringArrayP2.
	 * @param stringArrayP2 The stringArrayP2 to set
	 */
	public void setStringArrayP2(String[] stringArrayP2) {
		this.stringArrayP2 = stringArrayP2;
	}

}
