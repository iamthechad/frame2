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
package org.megatome.frame2.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.megatome.frame2.tagsupport.TagConstants;

/**
 * URLHelper provides simple services for encoding and appending parameters to
 * URLs.
 */

public final class URLHelper {

    static final protected String ENC_UTF_8 = "UTF-8"; //$NON-NLS-1$

    static final protected String QUESTION_MARK = "?"; //$NON-NLS-1$

    static final protected String SEP = "&"; //$NON-NLS-1$
    
    private URLHelper() {
    	// Not Public
    }

    /**
     * Encode the provided URL to UTF-8.
     * @param value value to encode.
     * @return UTF-8 encoded version of the input value.
     * @throws UnsupportedEncodingException if the enoding type is invalid.
     */
    protected static String encodeURL(String value)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(value, ENC_UTF_8);
    }

    /**
     * Append a query parameter string to the input href. For example, if the
     * input href =http://finance.yahoo.com/q and the params contained the key
     * value pairs of "s", "msft"; "d", "v2, this method would return the
     * following url: <code>http://finance.yahoo.com/q?s=msft&d=v2</code>
     * @param href a tag's href attribute
     * @param params map containing the query parameters key, value pairs.
     * @return Href with appended query parameters. If input params is null or
     *         is empty, the input href is returned.
     * @throws UnsupportedEncodingException
     */
    public static String appendQueryParams(String href, Map<String, Object> params)
            throws UnsupportedEncodingException {

        if (params == null || params.keySet().size() == 0) {
            return href;
        }
        StringBuffer url = new StringBuffer();
        url.append(href);

        // Add the query parameters to the href.
        boolean firstParam = true;
        for (Entry<String, Object> entry : params.entrySet()) {
        	String key = entry.getKey();
        	Object value = params.get(key);
            if (value instanceof String[]) {
                String values[] = (String[])value;
                for (int i = 0; i < values.length; i++) {
                    firstParam = appendOneQueryParam(url, firstParam, key,
                            encodeURL(values[i]));
                }
            } else {
                firstParam = appendOneQueryParam(url, firstParam, key, value);
            }
        }

        return (url.toString());
    }

    /**
     * Appends one query parameter to the input url.
     * @param url contains the url and processed query parameters.
     * @param firstParam true if this is the first query parameter processed,
     *        otherwise false.
     * @param key key of the query parameter
     * @param value value of the query parameter
     * @return False after the first query parameter is processed.
     * @throws UnsupportedEncodingException
     */
    protected static boolean appendOneQueryParam(StringBuffer url,
            boolean firstParam, String key, Object value)
            throws UnsupportedEncodingException {
        String paramSep = SEP;
        boolean processFirstParam = firstParam;
        if (processFirstParam) {
            paramSep = QUESTION_MARK;
            processFirstParam = false;
        }

        url.append(paramSep);
        url.append(URLEncoder.encode(key, ENC_UTF_8));
        url.append(TagConstants.EQUAL);

        // If value is null, then done.
        if (value != null) {
            if (value instanceof String) {
                url.append(encodeURL((String)value));
            } else {
                url.append(encodeURL(value.toString()));
            }
        }

        return processFirstParam;
    }

}