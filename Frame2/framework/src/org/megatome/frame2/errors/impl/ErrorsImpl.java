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
package org.megatome.frame2.errors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.Errors;

/**
 * A container for Error objects, which can be stored and retrieved by key. The
 * implementation maintains ordering behaviors (results are returned sorted by
 * key).
 */
final public class ErrorsImpl implements Errors {
    private Map _errors = new TreeMap();

    private int _count;

    static private Error[] _typeArray = new Error[0];

    public ErrorsImpl() { // Non-public ctor
    }

    /**
     * @param key
     * @return
     * @see org.megatome.frame2.errors.Errors#add(java.lang.String)
     */
    public Error add(final String key) {
        return add(key, null, null, null);
    }

    /**
     * Add an error with the key and value.
     * @param key Error key
     * @param value Value to insert into message
     */
    public Error add(final String key, final Object value) {
        return add(key, value, null, null);
    }

    /**
     * Add an error with the key and values.
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     */
    public Error add(final String key, final Object value1, final Object value2) {
        return add(key, value1, value2, null);
    }

    /**
     * Add an error with the key and values.
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     * @param value3 Third value to insert into message
     */
    public Error add(final String key, final Object value1,
            final Object value2, final Object value3) {
        Error e = new ErrorImpl(key, value1, value2, value3);
        add(e);
        return e;
    }

    /**
     * Add an Error object to the collection
     * @param error Error to add
     */
    public void add(final Error error) {
        Collection errorsForKey = errorsForKey(error.getKey());

        if (errorsForKey == null) {
            errorsForKey = new ArrayList();
        }

        errorsForKey.add(error);
        _errors.put(error.getKey(), errorsForKey);
        _count++;
    }

    /**
     * Test if the following error (key and values) is already in the Errors
     * object.
     * @param error Error to look for
     * @return boolean True if the error is in the collection
     */
    public boolean contains(final Error error) {
        Collection errorsForKey = errorsForKey(error.getKey());

        return (errorsForKey != null) && errorsForKey.contains(error);
    }

    private Collection errorsForKey(final String key) {
        return (Collection)_errors.get(key);
    }

    /**
     * As with <code>add</code> but only adds the error if an equivalent error
     * is not already in the collection.
     * @param key Error key
     * @param value Value to insert into message
     * @see org.megatome.frame2.errors.Errors#add(String,Object)
     */
    public Error addIfUnique(final String key, final Object value) {
        return addIfUnique(key, value, null, null);
    }

    /**
     * As with <code>add</code> but only adds the error if an equivalent error
     * is not already in the collection.
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     * @see org.megatome.frame2.errors.Errors#add(String,Object,Object)
     */
    public Error addIfUnique(final String key, final Object value1,
            final Object value2) {
        return addIfUnique(key, value1, value2, null);
    }

    /**
     * As with <code>add</code> but only adds the error if an equivalent error
     * is not already in the collection.
     * @param key Error key
     * @param value1 First value to insert into message
     * @param value2 Second value to insert into message
     * @param value3 Third value to insert into message
     * @see org.megatome.frame2.errors.Errors#add(String,Object,Object,Object)
     */
    public Error addIfUnique(final String key, final Object value1,
            final Object value2, final Object value3) {
        Error error = new ErrorImpl(key, value1, value2, value3);

        if (!contains(error)) {
            add(error);
        }

        return error;
    }

    /**
     * Get an iterator of all errors for this key.
     * @param key Error key to retrieve Error objects for
     * @return Iterator of all found Error objects, or null if none found.
     */
    public Iterator iterator(String key) {
        Collection errorsForKey = (Collection)_errors.get(key);

        return (errorsForKey == null) ? null : errorsForKey.iterator();
    }

    /**
     * Get an iterator of all errors in this object.
     * @return Iterator of all errors in this collection.
     */
    public Iterator iterator() {
        return allErrors().iterator();
    }

    /**
     * Get all errors in the collection in an array
     * @return Array of Error objects
     */
    public Error[] get() {
        return (Error[])allErrors().toArray(_typeArray);
    }

    /**
     * Get all Error objects associated with the specified key
     * @param key Key to search on. Passing null will return all Error objects
     *        in the collection.
     * @return Array of Error objects
     */
    public Error[] get(String key) {
        if (key == null) {
            return get();
        }

        Collection col = errorsForKey(key);
        if (col != null) {
            return (Error[])col.toArray(_typeArray);
        }

        return _typeArray;
    }

    private Collection allErrors() {
        Collection result = new ArrayList();
        Iterator errorsForKeys = _errors.values().iterator();

        while (errorsForKeys.hasNext()) {
            result.addAll((Collection)errorsForKeys.next());
        }

        return result;
    }

    /**
     * Test to see if te object contains any errors.
     * @return boolean Returns true if the object contains no errors, false
     *         otherwise.
     */
    public boolean isEmpty() {
        return _errors.isEmpty();
    }

    /**
     * Release this object and the underlying data. This clears the object as
     * well.
     */
    public void release() {
        clear();
    }

    private void clear() {
        Iterator errors = iterator();

        while (errors.hasNext()) {
            Error error = (Error)errors.next();
            error = null;
        }

        _errors.clear();
        _count = 0;
    }

    /**
     * Return the number of error objects in this object.
     * @return Number of Error objects in the collection
     */
    public int size() {
        return _count;
    }

    /**
     * Generate a human readable version of this collection. Useful for
     * debugging during application development.
     * @return String representation of all contained Errors
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        Iterator errors = iterator();

        while (errors.hasNext()) {
            Error error = (Error)errors.next();

            buffer.append("Errors: Key[" + error.getKey() + "] Value["
                    + error.getValue() + "]\n");
        }

        return buffer.toString();
    }
}