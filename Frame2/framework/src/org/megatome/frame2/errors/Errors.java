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
package org.megatome.frame2.errors;

import java.util.Iterator;

/**
 * A container for Error objects, which can be stored and retrieved by key. The
 * implementation maintains ordering behaviors (results are returned sorted by
 * key).
 */
public interface Errors {

    /**
     * Add an error with the key only.
     * @param key Error key
     * @return Newly created Error
     */
    public abstract Error add(final String key);

    /**
     * Add an error with the key and value.
     * @param key Error key
     * @param value Value to insert into message
     * @return Newly Created Error
     */
    public abstract Error add(final String key, final Object value);

    /**
     * Add an error with the key and values.
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     * @return Newly created Error
     */
    public abstract Error add(final String key, final Object value1,
            final Object value2);

    /**
     * Add an error with the key and values.
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     * @param value3 Third value to insert into message
     * @return Newly created Error
     */
    public abstract Error add(final String key, final Object value1,
            final Object value2, final Object value3);

    /**
     * Add an Error object to the collection
     * @param error Error to add
     */
    public abstract void add(final Error error);

    /**
     * Test if the following error (key and values) is already in the Errors
     * object.
     * @param error Error to look for
     * @return boolean True if the error is in the collection
     */
    public abstract boolean contains(final Error error);

    /**
     * As with <code>add</code> but only adds the error if an equivalent error
     * is not already in the collection.
     * @param key Error key
     * @param value Value to insert into message
     * @return The Error, whether newly created or existing.
     * @see org.megatome.frame2.errors.Errors#add(String,Object)
     */
    public abstract Error addIfUnique(final String key, final Object value);

    /**
     * As with <code>add</code> but only adds the error if an equivalent error
     * is not already in the collection.
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     * @return The Error, whether newly created or existing.
     * @see org.megatome.frame2.errors.Errors#add(String,Object,Object)
     */
    public abstract Error addIfUnique(final String key, final Object value1,
            final Object value2);

    /**
     * As with <code>add</code> but only adds the error if an equivalent error
     * is not already in the collection.
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     * @param value3 Third value to insert into message
     * @return The Error, whether newly created or existing.
     * @see org.megatome.frame2.errors.Errors#add(String,Object,Object,Object)
     */
    public abstract Error addIfUnique(final String key, final Object value1,
            final Object value2, final Object value3);

    /**
     * Get an iterator of all errors for this key.
     * @param key Error key to retrieve Error objects for
     * @return Iterator of all found Error objects, or null if none found.
     */
    public abstract Iterator iterator(String key);

    /**
     * Get an iterator of all errors in this object.
     * @return Iterator of all errors in this collection.
     */
    public abstract Iterator iterator();

    /**
     * Get all errors in the collection in an array
     * @return Array of Error objects
     */
    public abstract Error[] get();

    /**
     * Get all Error objects associated with the specified key
     * @param key Key to search on. Passing null will return all Error objects
     *        in the collection.
     * @return Array of Error objects
     */
    public abstract Error[] get(String key);

    /**
     * Test to see if te object contains any errors.
     * @return boolean Returns true if the object contains no errors, false
     *         otherwise.
     */
    public abstract boolean isEmpty();

    /**
     * Release this object and the underlying data. This clears the object as
     * well.
     */
    public abstract void release();

    /**
     * Return the number of error objects in this object.
     * @return Number of Error objects in the collection
     */
    public abstract int size();
}