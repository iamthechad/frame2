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
package org.megatome.frame2.errors.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.megatome.frame2.errors.Error;
import org.megatome.frame2.util.MessageFormatter;
import org.megatome.frame2.util.ResourceLocator;

/**
 * Models an error. The model consists of a key (which identifies the source of
 * the error, and may be a resource key), and up to three object values that
 * represent the data in error.
 */
final class ErrorImpl implements Error {
    private static final int MAX_VALUES = 3;

    /**
     * This key is used if an error is created with a null key.
     */
    public static final String MISSING_KEY = "org.megatome.frame2.errors.MISSING_KEY"; //$NON-NLS-1$

    private String key;

    private Object[] value = new Object[MAX_VALUES];

    /**
     * Construct an error object with the specified key.
     * 
     * @param key Error key
     */
    public ErrorImpl(final String key) {
        this(key, null, null, null);
    }

    /**
     * Construct an error object with the specified key, and one value to insert
     * in the message.
     * 
     * @param key Error key
     * @param value1 Value to insert into message
     */
    public ErrorImpl(final String key, final Object value1) {
        this(key, value1, null, null);
    }

    /**
     * Construct an error object with the specified key, and two values to
     * insert in the message.
     * 
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     */
    public ErrorImpl(final String key, final Object value1, final Object value2) {
        this(key, value1, value2, null);
    }

    /**
     * Construct an error object
     */
    public ErrorImpl() {
        super();
    }

    /**
     * Construct an error object with the specified key, and three values to
     * insert in the message.
     * 
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     * @param value3 Third value to insert into message
     */
    public ErrorImpl(final String key, final Object value1, final Object value2,
            final Object value3) {
        this.key = (key != null) ? key : MISSING_KEY;
        this.value[0] = value1;
        this.value[1] = value2;
        this.value[2] = value3;
    }

    /**
     * Get the key for this error.
     * 
     * @return The key associated with this error.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Return a list of the values.
     * 
     * @return All values associated with this error in a list.
     */
    public List<Object> getValueList() {
        return Arrays.asList(this.value).subList(0, getTopIndex());
    }

    private int getTopIndex() {
        int topIndex = 0;

        if (this.value[2] != null) {
            topIndex = 3;
        } else if (this.value[1] != null) {
            topIndex = 2;
        } else if (this.value[0] != null) {
            topIndex = 1;
        }

        return topIndex;
    }

    /**
     * Get all values for this error object as an array.
     * 
     * @return Array containing value objects. May be empty, but will not be
     *         null.
     */
    public Object[] getValues() {
        Object[] result = new Object[getTopIndex()];

        System.arraycopy(this.value, 0, result, 0, result.length);

        return result;
    }

    /**
     * Return the first value (this is an alias for <code>getValue1</code>).
     * 
     * @return The first value. May be null.
     */
    public Object getValue() {
        return this.value[0];
    }

    /**
     * Return the first value.
     * 
     * @return The first value. May be null.
     */
    public Object getValue1() {
        return this.value[0];
    }

    /**
     * Return the second value.
     * 
     * @return The second value. May be null.
     */
    public Object getValue2() {
        return this.value[1];
    }

    /**
     * Return the third value.
     * 
     * @return The third value. May be null.
     */
    public Object getValue3() {
        return this.value[2];
    }

    /**
     * Return the number of non-null values.
     * 
     * @return Count of non-null values.
     */
    public int numValues() {
        if (this.value[2] != null) {
            return 3;
        } else if (this.value[1] != null) {
            return 2;
        } else if (this.value[0] != null) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Compare with another object.
     * 
     * @return False if the other object is not an Error, otherwise equality is
     *         tested across the key and values.
     * @see java.lang.Object#equals(Object)
     */
    @Override
	public boolean equals(final Object obj) {
        if ((obj == null) || (!(obj instanceof ErrorImpl))) {
            return false;
        }
        
        ErrorImpl e = (ErrorImpl)obj;
        if (this == e) {
            return true;
        }
        
        return (this.key.equals(e.key) &&
                compareValues(this.value[0], e.value[0]) &&
                compareValues(this.value[1], e.value[1]) &&
                compareValues(this.value[2], e.value[2]));
    }

    @Override
	public int hashCode() {
        int result = 17;
        result = 37 * result + this.key.hashCode();
        result = 37 * result + (this.value[0] == null ? 0 : this.value[0].hashCode());
        result = 37 * result + (this.value[1] == null ? 0 : this.value[1].hashCode());
        result = 37 * result + (this.value[2] == null ? 0 : this.value[2].hashCode());
        
        return result;
    }

    private String lookupMessage(final Locale locale) {
        return lookupMessage(this.key, locale);
    }

    private String lookupMessage(final String messageKey, final Locale locale) {
        ResourceBundle bundle = ResourceLocator.getBundle(locale);
        String msg = bundle.getString(messageKey);
        Object[] values = getValues();
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Error) {
                    values[i] = ((Error) values[i]).getMessage(locale);
                }
            }
        }
        return MessageFormatter.format(msg, locale, values);
    }

    private boolean compareValues(final Object obj1, final Object obj2) {
        return (obj1 == null) ? (obj2 == null) : obj1.equals(obj2);
    }

    /**
     * Create a message from the error.
     * 
     * @param locale The locale to create the error message for.
     * @return The constructed error message, with the values substituted in
     *         appropriate spots.
     */
    public String getMessage(final Locale locale) {
        return lookupMessage(locale);
    }

}