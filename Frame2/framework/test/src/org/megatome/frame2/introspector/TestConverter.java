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
