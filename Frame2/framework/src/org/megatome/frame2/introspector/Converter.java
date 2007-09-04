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
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;

/**
 * The Converter class encapsulates the conversion of types for the purpose of
 * the introspector's setting of properties.
 */
final class Converter {
	private static final Logger LOGGER = LoggerFactory.instance(Converter.class.getName());
	static private Map<Class<?>, TypeConverter> converterMap = new HashMap<Class<?>, TypeConverter>();

	static {
		converterMap.put(BigDecimal.class, new BigDecimalConverter());
		converterMap.put(BigDecimal[].class, new BigDecimalConverter());
		converterMap.put(BigInteger.class, new BigIntegerConverter());
		converterMap.put(BigInteger[].class, new BigIntegerConverter());
		converterMap.put(Boolean.TYPE, new BooleanConverter());
		converterMap.put(Boolean.class, new BooleanConverter());
		converterMap.put(Boolean[].class, new BooleanConverter());
		converterMap.put(Byte.TYPE, new ByteConverter());
		converterMap.put(Byte.class, new ByteConverter());
		converterMap.put(Byte[].class, new ByteConverter());
		converterMap.put(Character.TYPE, new CharacterConverter());
		converterMap.put(Character.class, new CharacterConverter());
		converterMap.put(Character[].class, new CharacterConverter());
		converterMap.put(Double.TYPE, new DoubleConverter());
		converterMap.put(Double.class, new DoubleConverter());
		converterMap.put(Double[].class, new DoubleConverter());
		converterMap.put(Float.TYPE, new FloatConverter());
		converterMap.put(Float.class, new FloatConverter());
		converterMap.put(Float[].class, new FloatConverter());
		converterMap.put(Integer.TYPE, new IntegerConverter());
		converterMap.put(Integer.class, new IntegerConverter());
		converterMap.put(Integer[].class, new IntegerConverter());
		converterMap.put(Long.TYPE, new LongConverter());
		converterMap.put(Long.class, new LongConverter());
		converterMap.put(Long[].class, new LongConverter());
		converterMap.put(Short.TYPE, new ShortConverter());
		converterMap.put(Short.class, new ShortConverter());
		converterMap.put(Short[].class, new ShortConverter());
		converterMap.put(StringBuffer.class, new StringBufferConverter());
		converterMap.put(StringBuffer[].class, new StringBufferConverter());
		converterMap.put(String.class, new StringConverter());
		converterMap.put(String[].class, new StringConverter());
	}

	private Converter() { // Non-public ctor
	}

	static Object convertValueToType(Object value, Class<?> type) {
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
			strValue = String.valueOf(value);
			LOGGER.warn("Trying to convert from a non String type. Results may be varied."); //$NON-NLS-1$
		}

		TypeConverter converter = converterMap.get(type);

		if (converter != null) {
			return converter.convert(strValue);
		}

		return null;
	}

	static Object convertValueToArrayType(Object value, Class<?> type) {
		if ((value == null) || (type == null)) {
			return null;
		}

		if (value.getClass() == type) {
			return value;
		}

		if (value instanceof FileItem) {
			return new FileItem[] { (FileItem) value };
		}

		String strValue = null;

		if (value instanceof String) {
			strValue = (String) value;
		} else {
			throw new IllegalArgumentException(
					"Conversion from non-string types not supported"); //$NON-NLS-1$
		}

		TypeConverter converter = converterMap.get(type);

		if (converter != null) {
			return converter.convertToArray(strValue);
		}

		return null;
	}

	static class StringConverter implements TypeConverter {
		public Object convert(String fromString) {
			return fromString;
		}

		public Object[] convertToArray(String fromString) {
			return new String[] { fromString };
		}
	}

	static class IntegerConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new Integer(fromString.trim());
		}

		public Object[] convertToArray(String fromString) {
			return new Integer[] { new Integer(fromString.trim()) };
		}
	}

	static class StringBufferConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new StringBuffer(fromString);
		}

		public Object[] convertToArray(String fromString) {
			return new StringBuffer[] { new StringBuffer(fromString) };
		}
	}

	static class FloatConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new Float(fromString.trim());
		}

		public Object[] convertToArray(String fromString) {
			return new Float[] { new Float(fromString.trim()) };
		}
	}

	static class DoubleConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new Double(fromString.trim());
		}

		public Object[] convertToArray(String fromString) {
			return new Double[] { new Double(fromString.trim()) };
		}
	}

	static class ByteConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new Byte(fromString);
		}

		public Object[] convertToArray(String fromString) {
			return new Byte[] { new Byte(fromString) };
		}
	}

	static class CharacterConverter implements TypeConverter {
		public Object convert(String fromString) {
			return Character.valueOf(fromString.charAt(0));
		}

		public Object[] convertToArray(String fromString) {
			return new Character[] { Character.valueOf(fromString.charAt(0)) };
		}
	}

	static class ShortConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new Short(fromString.trim());
		}

		public Object[] convertToArray(String fromString) {
			return new Short[] { new Short(fromString.trim()) };
		}
	}

	static class BooleanConverter implements TypeConverter {
		public Object convert(String fromString) {
			return Boolean.valueOf(fromString.trim());
		}

		public Object[] convertToArray(String fromString) {
			return new Boolean[] { Boolean.valueOf(fromString.trim()) };
		}
	}

	static class LongConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new Long(fromString.trim());
		}

		public Object[] convertToArray(String fromString) {
			return new Long[] { new Long(fromString.trim()) };
		}
	}

	static class BigIntegerConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new BigInteger(fromString.trim());
		}

		public Object[] convertToArray(String fromString) {
			return new BigInteger[] { new BigInteger(fromString.trim()) };
		}
	}

	static class BigDecimalConverter implements TypeConverter {
		public Object convert(String fromString) {
			return new BigDecimal(fromString.trim());
		}

		public Object[] convertToArray(String fromString) {
			return new BigDecimal[] { new BigDecimal(fromString.trim()) };
		}
	}
}
