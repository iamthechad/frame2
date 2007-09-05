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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class TestConverter {

   private void verifyConversion(String fromString, Class<?> type) {
      Object converted = Converter.convertValueToType(fromString, type);

      assertEquals(type, converted.getClass());
      assertEquals(fromString, String.valueOf(converted));
   }

   private void verifyArrayConversion(String fromString, Class<?> type) {
      Object converted = Converter.convertValueToArrayType(fromString, type);

      assertTrue(converted instanceof Object[]);
      Object[] convertedArray = (Object[]) converted;

      assertEquals(type, converted.getClass());
      assertTrue(convertedArray.length == 1);
      assertEquals(fromString, String.valueOf(convertedArray[0]));
   }

   private void verifyNegativeConversion(String fromString, Class<?> type) {
      try {
         Converter.convertValueToType(fromString, type);
         fail();
      } catch (Exception expected) {
    	  //expected
      }
   }

   private void verifyNegativeArrayConversion(String fromString, Class<?> type) {
      try {
         Converter.convertValueToArrayType(fromString, type);
         fail();
      } catch (Exception expected) {
    	  //expected
      }
   }

   @Test
   public void testSingleStringConverter() {
      verifyConversion("foo", String.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleBigDecimalConverter() {
      verifyConversion("1000000", BigDecimal.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleBigIntegerConverter() {
      verifyConversion("1000000", BigInteger.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleBooleanConverter() {
      verifyConversion("true", Boolean.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleByteConverter() {
      verifyConversion("10", Byte.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleCharacterConverter() {
      verifyConversion("A", Character.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleDoubleConverter() {
      verifyConversion("12.34", Double.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleFloatConverter() {
      verifyConversion("12.34", Float.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleIntegerConverter() {
      verifyConversion("1000", Integer.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleLongConverter() {
      verifyConversion("100000", Long.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleShortConverter() {
      verifyConversion("100", Short.class); //$NON-NLS-1$
   }

   @Test
   public void testSingleStringBufferConverter() {
      verifyConversion("ABCD", StringBuffer.class); //$NON-NLS-1$
   }

   @Test
   public void testArrayStringConverter() {
      verifyArrayConversion("foo", String[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayBigDecimalConverter() {
      verifyArrayConversion("1000000", BigDecimal[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayBigIntegerConverter() {
      verifyArrayConversion("1000000", BigInteger[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayBooleanConverter() {
      verifyArrayConversion("true", Boolean[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayByteConverter() {
      verifyArrayConversion("10", Byte[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayCharacterConverter() {
      verifyArrayConversion("A", Character[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayDoubleConverter() {
      verifyArrayConversion("12.34", Double[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayFloatConverter() {
      verifyArrayConversion("12.34", Float[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayIntegerConverter() {
      verifyArrayConversion("1000", Integer[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayLongConverter() {
      verifyArrayConversion("100000", Long[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayShortConverter() {
      verifyArrayConversion("100", Short[].class); //$NON-NLS-1$
   }

   @Test
   public void testArrayStringBufferConverter() {
      verifyArrayConversion("ABCD", StringBuffer[].class); //$NON-NLS-1$
   }
   /*
      public void testNegativeSingleStringConverter() {
         verifyConversion("foo", String.class);
      }
   */
   @Test
   public void testNegativeSingleBigDecimalConverter() {
      verifyNegativeConversion("ABCD", BigDecimal.class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeSingleBigIntegerConverter() {
      verifyNegativeConversion("ABCD", BigInteger.class); //$NON-NLS-1$
   }
   /*
      public void testNegativeSingleBooleanConverter() {
         verifyNegativeConversion("ABCD", Boolean.class);
      }
   */
   @Test
   public void testNegativeSingleByteConverter() {
      verifyNegativeConversion("ABCD", Byte.class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeSingleCharacterConverter() {
      verifyNegativeConversion("", Character.class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeSingleDoubleConverter() {
      verifyNegativeConversion("ABCD", Double.class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeSingleFloatConverter() {
      verifyNegativeConversion("ABCD", Float.class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeSingleIntegerConverter() {
      verifyNegativeConversion("ABCD", Integer.class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeSingleLongConverter() {
      verifyNegativeConversion("ABCD", Long.class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeSingleShortConverter() {
      verifyNegativeConversion("ABCD", Short.class); //$NON-NLS-1$
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
   @Test
   public void testNegativeArrayBigDecimalConverter() {
      verifyNegativeArrayConversion("ABCD", BigDecimal[].class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeArrayBigIntegerConverter() {
      verifyNegativeArrayConversion("ABCD", BigInteger[].class); //$NON-NLS-1$
   }
   /*
      public void testNegativeArrayBooleanConverter() {
         verifyNegativeArrayConversion("true", Boolean[].class);
      }
   */
   @Test
   public void testNegativeArrayByteConverter() {
      verifyNegativeArrayConversion("ABCD", Byte[].class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeArrayCharacterConverter() {
      verifyNegativeArrayConversion("", Character[].class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeArrayDoubleConverter() {
      verifyNegativeArrayConversion("ABCD", Double[].class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeArrayFloatConverter() {
      verifyNegativeArrayConversion("ABCD", Float[].class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeArrayIntegerConverter() {
      verifyNegativeArrayConversion("ABCD", Integer[].class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeArrayLongConverter() {
      verifyNegativeArrayConversion("ABCD", Long[].class); //$NON-NLS-1$
   }

   @Test
   public void testNegativeArrayShortConverter() {
      verifyNegativeArrayConversion("ABCD", Short[].class); //$NON-NLS-1$
   }
   /*
      public void testNegativeArrayStringBufferConverter() {
         verifyNegativeArrayConversion("ABCD", StringBuffer[].class);
      }
   */
}
