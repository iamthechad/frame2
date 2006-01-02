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

import junit.framework.TestCase;

public class TestConverter extends TestCase {

   /**
    * 
    */
   public TestConverter() {
      super();
   }

   /**
    * @param name
    */
   public TestConverter(String name) {
      super(name);
   }

   private void verifyConversion(String fromString, Class type) {
      Object converted = Converter.convertValueToType(fromString, type);

      assertEquals(type, converted.getClass());
      assertEquals(fromString, String.valueOf(converted));
   }

   private void verifyArrayConversion(String fromString, Class type) {
      Object converted = Converter.convertValueToArrayType(fromString, type);

      assertTrue(converted instanceof Object[]);
      Object[] convertedArray = (Object[]) converted;

      assertEquals(type, converted.getClass());
      assertTrue(convertedArray.length == 1);
      assertEquals(fromString, String.valueOf(convertedArray[0]));
   }

   private void verifyNegativeConversion(String fromString, Class type) {
      try {
         Converter.convertValueToType(fromString, type);
         fail();
      } catch (Exception e) {
      }
   }

   private void verifyNegativeArrayConversion(String fromString, Class type) {
      try {
         Converter.convertValueToArrayType(fromString, type);
         fail();
      } catch (Exception e) {
      }
   }

   public void testSingleStringConverter() {
      verifyConversion("foo", String.class);
   }

   public void testSingleBigDecimalConverter() {
      verifyConversion("1000000", BigDecimal.class);
   }

   public void testSingleBigIntegerConverter() {
      verifyConversion("1000000", BigInteger.class);
   }

   public void testSingleBooleanConverter() {
      verifyConversion("true", Boolean.class);
   }

   public void testSingleByteConverter() {
      verifyConversion("10", Byte.class);
   }

   public void testSingleCharacterConverter() {
      verifyConversion("A", Character.class);
   }

   public void testSingleDoubleConverter() {
      verifyConversion("12.34", Double.class);
   }

   public void testSingleFloatConverter() {
      verifyConversion("12.34", Float.class);
   }

   public void testSingleIntegerConverter() {
      verifyConversion("1000", Integer.class);
   }

   public void testSingleLongConverter() {
      verifyConversion("100000", Long.class);
   }

   public void testSingleShortConverter() {
      verifyConversion("100", Short.class);
   }

   public void testSingleStringBufferConverter() {
      verifyConversion("ABCD", StringBuffer.class);
   }

   public void testArrayStringConverter() {
      verifyArrayConversion("foo", String[].class);
   }

   public void testArrayBigDecimalConverter() {
      verifyArrayConversion("1000000", BigDecimal[].class);
   }

   public void testArrayBigIntegerConverter() {
      verifyArrayConversion("1000000", BigInteger[].class);
   }

   public void testArrayBooleanConverter() {
      verifyArrayConversion("true", Boolean[].class);
   }

   public void testArrayByteConverter() {
      verifyArrayConversion("10", Byte[].class);
   }

   public void testArrayCharacterConverter() {
      verifyArrayConversion("A", Character[].class);
   }

   public void testArrayDoubleConverter() {
      verifyArrayConversion("12.34", Double[].class);
   }

   public void testArrayFloatConverter() {
      verifyArrayConversion("12.34", Float[].class);
   }

   public void testArrayIntegerConverter() {
      verifyArrayConversion("1000", Integer[].class);
   }

   public void testArrayLongConverter() {
      verifyArrayConversion("100000", Long[].class);
   }

   public void testArrayShortConverter() {
      verifyArrayConversion("100", Short[].class);
   }

   public void testArrayStringBufferConverter() {
      verifyArrayConversion("ABCD", StringBuffer[].class);
   }
   /*
      public void testNegativeSingleStringConverter() {
         verifyConversion("foo", String.class);
      }
   */
   public void testNegativeSingleBigDecimalConverter() {
      verifyNegativeConversion("ABCD", BigDecimal.class);
   }

   public void testNegativeSingleBigIntegerConverter() {
      verifyNegativeConversion("ABCD", BigInteger.class);
   }
   /*
      public void testNegativeSingleBooleanConverter() {
         verifyNegativeConversion("ABCD", Boolean.class);
      }
   */
   public void testNegativeSingleByteConverter() {
      verifyNegativeConversion("ABCD", Byte.class);
   }

   public void testNegativeSingleCharacterConverter() {
      verifyNegativeConversion("", Character.class);
   }

   public void testNegativeSingleDoubleConverter() {
      verifyNegativeConversion("ABCD", Double.class);
   }

   public void testNegativeSingleFloatConverter() {
      verifyNegativeConversion("ABCD", Float.class);
   }

   public void testNegativeSingleIntegerConverter() {
      verifyNegativeConversion("ABCD", Integer.class);
   }

   public void testNegativeSingleLongConverter() {
      verifyNegativeConversion("ABCD", Long.class);
   }

   public void testNegativeSingleShortConverter() {
      verifyNegativeConversion("ABCD", Short.class);
   }
   /*
      public void testNegativeSingleStringBufferConverter() {
         verifyNegativeConversion("ABCD", StringBuffer.class);
      }
   */
   /*
   public void testNegativeArrayStringConverter() {
      verifyNegativeArrayConversion("foo", String[].class);
   }
   */
   public void testNegativeArrayBigDecimalConverter() {
      verifyNegativeArrayConversion("ABCD", BigDecimal[].class);
   }

   public void testNegativeArrayBigIntegerConverter() {
      verifyNegativeArrayConversion("ABCD", BigInteger[].class);
   }
   /*
      public void testNegativeArrayBooleanConverter() {
         verifyNegativeArrayConversion("true", Boolean[].class);
      }
   */
   public void testNegativeArrayByteConverter() {
      verifyNegativeArrayConversion("ABCD", Byte[].class);
   }

   public void testNegativeArrayCharacterConverter() {
      verifyNegativeArrayConversion("", Character[].class);
   }

   public void testNegativeArrayDoubleConverter() {
      verifyNegativeArrayConversion("ABCD", Double[].class);
   }

   public void testNegativeArrayFloatConverter() {
      verifyNegativeArrayConversion("ABCD", Float[].class);
   }

   public void testNegativeArrayIntegerConverter() {
      verifyNegativeArrayConversion("ABCD", Integer[].class);
   }

   public void testNegativeArrayLongConverter() {
      verifyNegativeArrayConversion("ABCD", Long[].class);
   }

   public void testNegativeArrayShortConverter() {
      verifyNegativeArrayConversion("ABCD", Short[].class);
   }
   /*
      public void testNegativeArrayStringBufferConverter() {
         verifyNegativeArrayConversion("ABCD", StringBuffer[].class);
      }
   */
}
