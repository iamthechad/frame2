/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;


/**
 * The Converter class encapsulates the conversion of types for the purpose of the introspector's
 * setting of properties.
 */
final class Converter {
   static private Map _converterMap = new HashMap();

   static {
      _converterMap.put(BigDecimal.class, new BigDecimalConverter());
		_converterMap.put(BigDecimal[].class, new BigDecimalConverter());
      _converterMap.put(BigInteger.class, new BigIntegerConverter());
		_converterMap.put(BigInteger[].class, new BigIntegerConverter());
      _converterMap.put(Boolean.TYPE, new BooleanConverter());
      _converterMap.put(Boolean.class, new BooleanConverter());
		_converterMap.put(Boolean[].class, new BooleanConverter());
      _converterMap.put(Byte.TYPE, new ByteConverter());
      _converterMap.put(Byte.class, new ByteConverter());
		_converterMap.put(Byte[].class, new ByteConverter());
      _converterMap.put(Character.TYPE, new CharacterConverter());
      _converterMap.put(Character.class, new CharacterConverter());
		_converterMap.put(Character[].class, new CharacterConverter());
      _converterMap.put(Double.TYPE, new DoubleConverter());
      _converterMap.put(Double.class, new DoubleConverter());
		_converterMap.put(Double[].class, new DoubleConverter());
      _converterMap.put(Float.TYPE, new FloatConverter());
      _converterMap.put(Float.class, new FloatConverter());
		_converterMap.put(Float[].class, new FloatConverter());
      _converterMap.put(Integer.TYPE, new IntegerConverter());
      _converterMap.put(Integer.class, new IntegerConverter());
		_converterMap.put(Integer[].class, new IntegerConverter());
      _converterMap.put(Long.TYPE, new LongConverter());
      _converterMap.put(Long.class, new LongConverter());
		_converterMap.put(Long[].class, new LongConverter());
      _converterMap.put(Short.TYPE, new ShortConverter());
      _converterMap.put(Short.class, new ShortConverter());
		_converterMap.put(Short[].class, new ShortConverter());
      _converterMap.put(StringBuffer.class, new StringBufferConverter());
		_converterMap.put(StringBuffer[].class, new StringBufferConverter());
      _converterMap.put(String.class, new StringConverter());
      _converterMap.put(String[].class, new StringConverter());
   }

   private Converter() {
   }

   static Object convertValueToType(Object value, Class type) {
      if ((value == null) || (type == null)) {
         return null;
      }

      if (value.getClass() == type) {
         return value;
      }
      
      if (value instanceof FileItem) {
      	return value;
      }

      String strValue = null;

      if (value instanceof String) {
         strValue = (String) value;
      } else {
         throw new IllegalArgumentException("Conversion from non-string types not supported");
      }

      TypeConverter converter = (TypeConverter) _converterMap.get(type);

      if (converter != null) {
         return converter.convert(strValue);
      } else {
         return null;
      }
   }
   
	static Object convertValueToArrayType(Object value, Class type) {
		if ((value == null) || (type == null)) {
			return null;
		}

		if (value.getClass() == type) {
			return value;
		}
		
		if (value instanceof FileItem) {
			return new FileItem[] { (FileItem)value };
		}

		String strValue = null;

		if (value instanceof String) {
			strValue = (String) value;
		} else {
			throw new IllegalArgumentException("Conversion from non-string types not supported");
		}

		TypeConverter converter = (TypeConverter) _converterMap.get(type);

		if (converter != null) {
			return converter.convertToArray(strValue);
		} else {
			return null;
		}
	}

   abstract private static class TypeConverter {
      abstract Object convert(String fromString);
      
      Object[] convertToArray(String fromString) {
      	return null;
      }
   }

   private static class StringConverter extends TypeConverter {
      Object convert(String fromString) {
         return fromString;
      }
      
      Object[] convertToArray(String fromString) {
      	return new String[] { fromString };
      }
   }

   private static class IntegerConverter extends TypeConverter {
      Object convert(String fromString) {
         return new Integer(fromString.trim());
      }
      
      Object[] convertToArray(String fromString) {
      	return new Integer[] { new Integer(fromString.trim()) };
      }
   }

   private static class StringBufferConverter extends TypeConverter {
      Object convert(String fromString) {
         return new StringBuffer(fromString);
      }
      
      Object[] convertToArray(String fromString) {
      	return new StringBuffer[] { new StringBuffer(fromString) };
      }
   }

   private static class FloatConverter extends TypeConverter {
      Object convert(String fromString) {
         return new Float(fromString.trim());
      }
      
      Object[] convertToArray(String fromString) {
      	return new Float[] { new Float(fromString.trim()) };
      }
   }

   private static class DoubleConverter extends TypeConverter {
      Object convert(String fromString) {
         return new Double(fromString.trim());
      }
      
      Object[] convertToArray(String fromString) {
      	return new Double[] { new Double(fromString.trim()) };
      }
   }

   private static class ByteConverter extends TypeConverter {
      Object convert(String fromString) {
         return new Byte(fromString);
      }
      
      Object[] convertToArray(String fromString) {
      	return new Byte[] { new Byte(fromString) };
      }
   }

   private static class CharacterConverter extends TypeConverter {
      Object convert(String fromString) {
         return new Character(fromString.charAt(0));
      }
      
      Object[] convertToArray(String fromString) {
      	return new Character[] { new Character(fromString.charAt(0)) };
      }
   }

   private static class ShortConverter extends TypeConverter {
      Object convert(String fromString) {
         return new Short(fromString.trim());
      }
      
		Object[] convertToArray(String fromString) {
			return new Short[] { new Short(fromString.trim()) };
		}
   }

   private static class BooleanConverter extends TypeConverter {
      Object convert(String fromString) {
         return new Boolean(fromString.trim());
      }
      
		Object[] convertToArray(String fromString) {
			return new Boolean[] { new Boolean(fromString.trim()) };
		}
   }

   private static class LongConverter extends TypeConverter {
      Object convert(String fromString) {
         return new Long(fromString.trim());
      }
      
		Object[] convertToArray(String fromString) {
			return new Long[] { new Long(fromString.trim()) };
		}
   }

   private static class BigIntegerConverter extends TypeConverter {
      Object convert(String fromString) {
         return new BigInteger(fromString.trim());
      }
      
		Object[] convertToArray(String fromString) {
			return new BigInteger[] { new BigInteger(fromString.trim()) };
		}
   }

   private static class BigDecimalConverter extends TypeConverter {
      Object convert(String fromString) {
         return new BigDecimal(fromString.trim());
      }
      
		Object[] convertToArray(String fromString) {
			return new BigDecimal[] { new BigDecimal(fromString.trim()) };
		}
   }
}
