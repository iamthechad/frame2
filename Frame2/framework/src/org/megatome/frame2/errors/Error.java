/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
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
package org.megatome.frame2.errors;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.megatome.frame2.util.MessageFormatter;
import org.megatome.frame2.util.ResourceLocator;

/**
 * Models an error.  The model consists of a key (which identifies the source of the error, and may
 * be a resource key), and up to three object values that represent the data in error.
 */
final public class Error {
	private static final int MAX_VALUES = 3;

	/**
	 * This key is used if an error is created with a null key.
	 */
	public static final String MISSING_KEY =
		"org.megatome.frame2.errors.MISSING_KEY";
	private String _key;
	private Object[] _value = new Object[MAX_VALUES];

	/**
	* @param key
	*/
	public Error(String key) {
		this(key, null, null, null);
	}

	/**
	* @param key
	* @param value1
	*/
	public Error(String key, Object value1) {
		this(key, value1, null, null);
	}

	/**
	* @param key
	* @param value1
	* @param value2
	*/
	public Error(String key, Object value1, Object value2) {
		this(key, value1, value2, null);
	}

	/**
	 * 
	 */
	public Error() {
		super();
	}

	/**
	* @param key
	* @param value1
	* @param value2
	* @param value3
	*/
	public Error(String key, Object value1, Object value2, Object value3) {
		_key = (key != null) ? key : MISSING_KEY;
		_value[0] = value1;
		_value[1] = value2;
		_value[2] = value3;
	}

	/**
	 * Create a new error with the following key and values.  Any may be null.
	 *
	 * @param key
	 * @param value1
	 * @param value2
	 * @param value3
	 *
	 * @return Error
	 */
	static Error create(
		String key,
		Object value1,
		Object value2,
		Object value3) {
		return new Error(key, value1, value2, value3);
	}

	/**
	 * Get the key for this error.
	 *
	 * @return String
	 */
	public String getKey() {
		return _key;
	}

	/**
	 * Return a list of the values.
	 *
	 * @return List
	 */
	public List getValueList() {
		return Arrays.asList(_value).subList(0, getTopIndex());
	}

	private int getTopIndex() {
		int topIndex = 0;

		if (_value[2] != null) {
			topIndex = 3;
		} else if (_value[1] != null) {
			topIndex = 2;
		} else if (_value[0] != null) {
			topIndex = 1;
		}

		return topIndex;
	}

	/**
	* @return
	*/
	public Object[] getValues() {
		Object[] result = new Object[getTopIndex()];

		System.arraycopy(_value, 0, result, 0, result.length);

		return result;
	}

	/**
	 * Return the first value (this is an alias for <code>getValue1</code>.
	 *
	 * @return Object
	 */
	public Object getValue() {
		return _value[0];
	}

	/**
	 * Return the first value.
	 *
	 * @return Object
	 */
	public Object getValue1() {
		return _value[0];
	}

	/**
	 * Return the second value.
	 *
	 * @return Object
	 */
	public Object getValue2() {
		return _value[1];
	}

	/**
	 * Return the third value.
	 *
	 * @return Object
	 */
	public Object getValue3() {
		return _value[2];
	}

	/**
	 * Return the number of non-null values.
	 *
	 * @return int
	 */
	public int numValues() {
		if (_value[2] != null) {
			return 3;
		} else if (_value[1] != null) {
			return 2;
		} else if (_value[0] != null) {
			return 1;
		} else {
			return 0;
		}
	}

	void release() {
		clear();
	}

	private void clear() {
		_key = null;
		_value[0] = null;
		_value[1] = null;
		_value[2] = null;
	}

	/**
	 * Compare with another object.  This returns false if the other object is not an Error,
	 * otherwise equality is tested across the key and values.
	 *
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Error) {
			return equals((Error)obj);
		} else {
			return false;
		}
	}

	boolean equals(Error error) {
		boolean result = true;

		result &= _key.equals(error.getKey());

		result &= compareValues(_value[0], error.getValue1());
		result &= compareValues(_value[1], error.getValue2());
		result &= compareValues(_value[2], error.getValue3());

		return result;
	}

	private String lookupMessage(Locale locale) {
		return lookupMessage(_key, locale);
	}

	private String lookupMessage(String key, Locale locale) {
		ResourceBundle bundle = ResourceLocator.getBundle(locale);
		String msg = bundle.getString(key);
		Object[] values = getValues();
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				if (values[i] instanceof Error) {
					values[i] = ((Error)values[i]).getMessage(locale);
				}
			}
		}
		return MessageFormatter.format(msg, locale, values);
	}

	private boolean compareValues(Object obj1, Object obj2) {
		return (obj1 == null) ? (obj2 == null) : obj1.equals(obj2);
	}

	/**
	 * Create a message from the error.
	 */
	public String getMessage(Locale locale) {
		return lookupMessage(locale);
	}

}
