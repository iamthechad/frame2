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

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The MessageFormatter lightly wraps the MessageFormat class to provide caching
 * of format objects.
 * @see java.text.MessageFormat
 */
public class MessageFormatter {
    static private Map<Locale, Map<String, MessageFormat>> formats = Collections.synchronizedMap(new HashMap<Locale, Map<String, MessageFormat>>());

    private MessageFormatter() {
    	// not public
    }

    /**
     * Return a formatted string using the given pattern and argument, and using
     * the default locale.
     * @param pattern
     * @param args
     * @return String
     */
    static public String format(String pattern, Object[] args) {
        return format(pattern, Locale.getDefault(), args);
    }

    /**
     * Return a formatted string using the given pattern, locale and argument.
     * @param pattern
     * @param locale
     * @param args
     * @return String
     */
    static public String format(String pattern, Locale locale, Object[] args) {
        MessageFormat format = getFormat(pattern, locale);

        return format.format(args);
    }

    /**
     * Return a MessageFormat object for the given pattern and locale. Because
     * these objects are cached and shared between threads it is important that
     * clients not set the formats or locales on these objects. If that is
     * necessary, then it is required that the client clone the object then use
     * the clone.
     * @param pattern
     * @param locale
     * @return MessageFormat
     */
    static public MessageFormat getFormat(String pattern, Locale locale) {
        MessageFormat result = getFromCache(pattern, locale);

        if (result == null) {
            result = new MessageFormat(pattern, locale);
            storeInCache(result, pattern, locale);
        }

        return result;
    }

    static private MessageFormat getFromCache(String pattern, Locale locale) {
        Map<String, MessageFormat> localeMap = formats.get(locale);

        if (localeMap != null) {
            return localeMap.get(pattern);
        }

        return null;
    }

    static private void storeInCache(MessageFormat format, String pattern,
            Locale locale) {
        Map<String, MessageFormat> localeMap = formats.get(locale);

        if (localeMap == null) {
            localeMap = Collections.synchronizedMap(new HashMap<String, MessageFormat>());

            formats.put(locale, localeMap);
        }

        localeMap.put(pattern, format);
    }
}