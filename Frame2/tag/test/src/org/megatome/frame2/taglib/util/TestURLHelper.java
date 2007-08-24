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
package org.megatome.frame2.taglib.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.megatome.frame2.taglib.util.URLHelper;

public class TestURLHelper extends TestCase {

	final static String PARAM_1_NAME = "param1"; //$NON-NLS-1$

	final static String PARAM_1_VALUE = "param1Value"; //$NON-NLS-1$

	final static String PARAM_2_NAME = "param2"; //$NON-NLS-1$

	final static String PARAM_2_VALUE = "param2Value"; //$NON-NLS-1$

	final static String PARAM_3_NAME = "param3"; //$NON-NLS-1$

	final static String PARAM_3_VALUE = "param3Value"; //$NON-NLS-1$

	final static String BASE_URI = "test.f2"; //$NON-NLS-1$

	public void testURLEncoding() throws Exception {

		String expected = "The+string+%C3%BC%40foo-bar" //$NON-NLS-1$
				+ URLEncoder.encode(URLHelper.SEP, URLHelper.ENC_UTF_8)
				+ URLEncoder.encode(URLHelper.QUESTION_MARK,
						URLHelper.ENC_UTF_8);

		String actual = URLHelper
				.encodeURL("The string ü@foo-bar" + URLHelper.SEP + URLHelper.QUESTION_MARK); //$NON-NLS-1$
		assertEquals(expected, actual);
	}

	public void testNullHashMap() throws Exception {
		String uri = BASE_URI;
		String expected = BASE_URI;
		String actual = URLHelper.appendQueryParams(uri, null);
		assertEquals(expected, actual);
	}

	public void testEmptyHashMap() throws Exception {
		String uri = BASE_URI;
		String expected = BASE_URI;
		String actual = URLHelper.appendQueryParams(uri, new HashMap<Object, Object>());
		assertEquals(expected, actual);
	}

	public void testOneEmptyEntryHashMap() throws Exception {
		String uri = BASE_URI;
		String expected = BASE_URI + URLHelper.QUESTION_MARK + PARAM_1_NAME
				+ "="; //$NON-NLS-1$
		Map<Object, Object> queryParams = new HashMap<Object, Object>();
		queryParams.put(PARAM_1_NAME, null);

		String actual = URLHelper.appendQueryParams(uri, queryParams);
		assertEquals(expected, actual);
	}

	public void testOneEntryHashMap() throws Exception {
		String uri = BASE_URI;
		String expected = BASE_URI + URLHelper.QUESTION_MARK + PARAM_1_NAME
				+ "=" + PARAM_1_VALUE; //$NON-NLS-1$
		Map<Object, Object> queryParams = new HashMap<Object, Object>();
		queryParams.put(PARAM_1_NAME, PARAM_1_VALUE);
		String actual = URLHelper.appendQueryParams(uri, queryParams);
		assertEquals(expected, actual);
	}

	public void testMultipleEntryHashMap() throws Exception {
		String uri = BASE_URI;
		// String expected = BASE_URI + URLHelper.QUESTION_MARK + PARAM_2_NAME +
		// "=" + PARAM_2_VALUE +
		// URLHelper.SEP + PARAM_1_NAME + "=" + PARAM_1_VALUE +
		// URLHelper.SEP + PARAM_3_NAME + "=" + PARAM_3_VALUE ;
		String expected = BASE_URI + URLHelper.QUESTION_MARK + PARAM_1_NAME
				+ "=" + PARAM_1_VALUE + //$NON-NLS-1$
				URLHelper.SEP + PARAM_2_NAME + "=" + PARAM_2_VALUE + //$NON-NLS-1$
				URLHelper.SEP + PARAM_3_NAME + "=" + PARAM_3_VALUE; //$NON-NLS-1$
		Map<Object, Object> queryParams = new TreeMap<Object, Object>();
		queryParams.put(PARAM_1_NAME, PARAM_1_VALUE);
		queryParams.put(PARAM_2_NAME, PARAM_2_VALUE);
		queryParams.put(PARAM_3_NAME, PARAM_3_VALUE);
		String actual = URLHelper.appendQueryParams(uri, queryParams);
		assertEquals(expected, actual);
	}

	public void testStringArrayAndBooleanObjectsInHashMap() throws Exception {
		String uri = BASE_URI;
		String expected = BASE_URI + URLHelper.QUESTION_MARK + 
				PARAM_1_NAME + "=" + PARAM_1_VALUE + //$NON-NLS-1$
				URLHelper.SEP + PARAM_1_NAME + "=" + PARAM_2_VALUE + //$NON-NLS-1$
				URLHelper.SEP + PARAM_2_NAME + "=true"; //$NON-NLS-1$

		Map<Object, Object> queryParams = new TreeMap<Object, Object>();
		String[] params = { PARAM_1_VALUE, PARAM_2_VALUE };
		queryParams.put(PARAM_1_NAME, params);
		queryParams.put(PARAM_2_NAME, new Boolean("true")); //$NON-NLS-1$
		String actual = URLHelper.appendQueryParams(uri, queryParams);
		assertEquals(expected, actual);
	}

}
