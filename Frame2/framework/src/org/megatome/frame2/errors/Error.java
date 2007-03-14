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
package org.megatome.frame2.errors;

import java.util.List;
import java.util.Locale;

/**
 * Models an error. The model consists of a key (which identifies the source of
 * the error, and may be a resource key), and up to three object values that
 * represent the data in error.
 */
public interface Error {
    /**
     * This key is used if an error is created with a null key.
     */
    public static final String MISSING_KEY = "org.megatome.frame2.errors.MISSING_KEY"; //$NON-NLS-1$

    /**
     * Get the key for this error.
     * 
     * @return The key associated with this error.
     */
    public abstract String getKey();

    /**
     * Return a list of the values.
     * 
     * @return All values associated with this error in a list.
     */
    public abstract List<Object> getValueList();

    /**
     * Get all values for this error object as an array.
     * 
     * @return Array containing value objects. May be empty, but will not be
     *         null.
     */
    public abstract Object[] getValues();
    
    /**
     * Return the first value (this is an alias for <code>getValue1</code>).
     * 
     * @return The first value. May be null.
     */
    public abstract Object getValue();

    /**
     * Return the first value.
     * 
     * @return The first value. May be null.
     */
    public abstract Object getValue1();

    /**
     * Return the second value.
     * 
     * @return The second value. May be null.
     */
    public abstract Object getValue2();

    /**
     * Return the third value.
     * 
     * @return The third value. May be null.
     */
    public abstract Object getValue3();

    /**
     * Return the number of non-null values.
     * 
     * @return Count of non-null values.
     */
    public abstract int numValues();

    /**
     * Create a message from the error.
     * 
     * @param locale The locale to create the error message for.
     * @return The constructed error message, with the values substituted in
     *         appropriate spots.
     */
    public abstract String getMessage(final Locale locale);
}