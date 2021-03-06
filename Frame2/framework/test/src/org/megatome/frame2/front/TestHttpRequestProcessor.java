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
package org.megatome.frame2.front;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import junitx.framework.FileAssert;

import org.apache.commons.fileupload.FileItem;
import org.junit.After;
import org.junit.Test;
import org.megatome.frame2.Globals;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.front.config.ViewType;
import org.megatome.frame2.introspector.MappingException;
import org.megatome.frame2.introspector.MappingsException;

import servletunit.frame2.Frame2HttpServletRequestSimulator;
import servletunit.frame2.MockFrame2TestCase;
import HTTPClient.Codecs;
import HTTPClient.NVPair;

public class TestHttpRequestProcessor extends MockFrame2TestCase {

	private String origTmpDir;

	private int origBufferSize;

	private long origMaxFileSize;

	public TestHttpRequestProcessor() {
		this.origTmpDir = FileUploadConfig.getFileTempDir();
		this.origBufferSize = FileUploadConfig.getBufferSize();
		this.origMaxFileSize = FileUploadConfig.getMaxFileSize();
	}

	@Test
	public void testGetEvent() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		setServletPath("/event1"); //$NON-NLS-1$

		HttpRequestProcessor request = createHelper(config);

		Event event = request.getEvent();

		assertNotNull(event);
		assertTrue(event instanceof Event1);
	}

	@Test
	public void testMapRequestToEvent() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		addRequestParameter("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		addRequestParameter("parm2", "value2"); //$NON-NLS-1$ //$NON-NLS-2$

		setServletPath("/event1"); //$NON-NLS-1$

		HttpRequestProcessor request = createHelper(config);

		Event1 event = (Event1) request.getEvent();

		assertTrue(request.mapRequestToEvent(event, true));

		assertEquals("value1", event.getParm1()); //$NON-NLS-1$
		assertEquals("value2", event.getParm2()); //$NON-NLS-1$
	}

	@SuppressWarnings("unchecked")
	private HttpRequestProcessor doMultipartUpload(Object[] formParms,
			Object[] fileParms, String eventName) throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		NVPair[] hdrs = new NVPair[1];
		Frame2HttpServletRequestSimulator request = (Frame2HttpServletRequestSimulator) getRequest();

		NVPair[] opts;
		opts = new NVPair[formParms.length];
		for (int i = 0; i < formParms.length; i++) {
			Map<String, String> parm = (HashMap<String, String>) formParms[i];
			for (Entry<String, String> entry : parm.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				opts[i] = new NVPair(key, value);
			}
		}

		NVPair[] file;
		file = new NVPair[fileParms.length];
		for (int i = 0; i < fileParms.length; i++) {
			Map<String, String> parm = (HashMap<String, String>) fileParms[i];
			for (Entry<String, String> entry : parm.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				URL configFile = getContext().getResource(value);
				file[i] = new NVPair(key, configFile.getFile());
			}
		}

		try {
			byte[] data = Codecs.mpFormDataEncode(opts, file, hdrs);
			String rawData = new String(data);
			System.out.println(rawData);
			InputStream is = new ByteArrayInputStream(data);
			request.setDataInputStream(data.length, is);
			request.setContentType(hdrs[0].getValue());
			request.setHeader(HttpRequestProcessor.CONTENT_TYPE, hdrs[0]
					.getValue());
			is.close();
		} catch (IOException e) {
			fail();
		}

		setServletPath(eventName);

		return createHelper(config);
	}

	private void verifyFiles(FileItem fi, String uploadFileName,
			String origFileName) {
		try {
			String fileDir = FileUploadConfig.getFileTempDir();
			File f = new File(fileDir + uploadFileName);
			fi.write(f);

			File origFile = new File(getContext().getResource(origFileName)
					.getFile());
			assertTrue(f.exists());
			FileAssert.assertEquals(origFile, f);
		} catch (MalformedURLException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testMultipartUploadSingle() throws Exception {
		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String> files = new HashMap<String, String>();

		String fileName = "/WEB-INF/frame2-config.xml"; //$NON-NLS-1$
		parms.put("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		files.put("fileparm", fileName); //$NON-NLS-1$

		HttpRequestProcessor requestProcessor = doMultipartUpload(
				new Object[] { parms }, new Object[] { files },
				"/eventFileUpload"); //$NON-NLS-1$

		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event, true));

		assertEquals("value1", event.getParm1()); //$NON-NLS-1$
		FileItem fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals("fileparm", fi.getFieldName()); //$NON-NLS-1$
		String fullFileName = fi.getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1); //$NON-NLS-1$

		verifyFiles(fi, "testSingleFileUpload.xml", fileName); //$NON-NLS-1$
	}

	@Test
	public void testMultipartUploadLargeSingle() throws Exception {
		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String> files = new HashMap<String, String>();

		String fileName = "/WEB-INF/commonsvalidator/commons-validation.xml"; //$NON-NLS-1$
		parms.put("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		files.put("fileparm", fileName); //$NON-NLS-1$

		HttpRequestProcessor requestProcessor = doMultipartUpload(
				new Object[] { parms }, new Object[] { files },
				"/eventFileUpload"); //$NON-NLS-1$

		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event, true));

		assertEquals("value1", event.getParm1()); //$NON-NLS-1$
		FileItem fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals("fileparm", fi.getFieldName()); //$NON-NLS-1$
		String fullFileName = fi.getName();
		assertTrue(fullFileName.indexOf("commons-validation.xml") != -1); //$NON-NLS-1$

		verifyFiles(fi, "testSingleBigFileUpload.xml", fileName); //$NON-NLS-1$
	}

	@Test
	public void testMultipartUploadMultipleDiffParam() throws Exception {
		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String> files1 = new HashMap<String, String>();
		Map<String, String> files2 = new HashMap<String, String>();

		String fileName1 = "/WEB-INF/frame2-config.xml"; //$NON-NLS-1$
		String fileName2 = "/WEB-INF/commonsvalidator/commons-validation.xml"; //$NON-NLS-1$
		parms.put("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		files1.put("fileparm", fileName1); //$NON-NLS-1$
		files2.put("fileparm2", fileName2); //$NON-NLS-1$

		HttpRequestProcessor requestProcessor = doMultipartUpload(
				new Object[] { parms }, new Object[] { files1, files2 },
				"/eventFileUpload"); //$NON-NLS-1$

		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event, true));

		FileItem fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals("fileparm", fi.getFieldName()); //$NON-NLS-1$
		String fullFileName = fi.getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1); //$NON-NLS-1$

		verifyFiles(fi, "testMultFileUpload1.xml", fileName1); //$NON-NLS-1$

		fi = event.getFileparm2();
		assertNotNull(fi);
		assertEquals("fileparm2", fi.getFieldName()); //$NON-NLS-1$
		fullFileName = fi.getName();
		assertTrue(fullFileName.indexOf("commons-validation.xml") != -1); //$NON-NLS-1$

		verifyFiles(fi, "testMultFileUpload2.xml", fileName2); //$NON-NLS-1$
	}

	@SuppressWarnings("boxing")
	@Test
	public void testMultipartUploadMultipleSameParam() throws Exception {
		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String> files1 = new HashMap<String, String>();
		Map<String, String> files2 = new HashMap<String, String>();

		String fileName1 = "/WEB-INF/frame2-config.xml"; //$NON-NLS-1$
		String fileName2 = "/WEB-INF/commonsvalidator/commons-validation.xml"; //$NON-NLS-1$
		parms.put("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		files1.put("fileparm", fileName1); //$NON-NLS-1$
		files2.put("fileparm", fileName2); //$NON-NLS-1$

		HttpRequestProcessor requestProcessor = doMultipartUpload(
				new Object[] { parms }, new Object[] { files1, files2 },
				"/eventMultFileUpload"); //$NON-NLS-1$

		EventMultFileUpload event = (EventMultFileUpload) requestProcessor
				.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event, true));

		FileItem[] fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals(2, fi.length);
		assertEquals("fileparm", fi[0].getFieldName()); //$NON-NLS-1$
		String fullFileName = fi[0].getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1); //$NON-NLS-1$

		verifyFiles(fi[0], "testMultFileSameParamUpload1.xml", fileName1); //$NON-NLS-1$

		assertEquals("fileparm", fi[1].getFieldName()); //$NON-NLS-1$
		fullFileName = fi[1].getName();
		assertTrue(fullFileName.indexOf("commons-validation.xml") != -1); //$NON-NLS-1$

		verifyFiles(fi[1], "testMultFileSameParamUpload2.xml", fileName2); //$NON-NLS-1$
	}

	@SuppressWarnings("boxing")
	@Test
	public void testSingleParamToArray() throws Exception {
		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String> files1 = new HashMap<String, String>();

		String fileName1 = "/WEB-INF/frame2-config.xml"; //$NON-NLS-1$
		parms.put("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		files1.put("fileparm", fileName1); //$NON-NLS-1$

		HttpRequestProcessor requestProcessor = doMultipartUpload(
				new Object[] { parms }, new Object[] { files1 },
				"/eventMultFileUpload"); //$NON-NLS-1$

		EventMultFileUpload event = (EventMultFileUpload) requestProcessor
				.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event, true));

		assertTrue("value1".equals(event.getParm1())); //$NON-NLS-1$

		FileItem[] fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals(1, fi.length);
		assertEquals("fileparm", fi[0].getFieldName()); //$NON-NLS-1$
		String fullFileName = fi[0].getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1); //$NON-NLS-1$

		verifyFiles(fi[0], "testParmToArrayUpload.xml", fileName1); //$NON-NLS-1$
	}

	@SuppressWarnings("boxing")
	@Test
	public void testSingleArrayParamToSingle() throws Exception {
		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String> parms2 = new HashMap<String, String>();
		Map<String, String> files1 = new HashMap<String, String>();

		String fileName1 = "/WEB-INF/frame2-config.xml"; //$NON-NLS-1$
		parms.put("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		parms2.put("parm1", "invalidValue"); //$NON-NLS-1$ //$NON-NLS-2$
		files1.put("fileparm", fileName1); //$NON-NLS-1$

		HttpRequestProcessor requestProcessor = doMultipartUpload(new Object[] {
				parms, parms2 }, new Object[] { files1 },
				"/eventMultFileUpload"); //$NON-NLS-1$

		EventMultFileUpload event = (EventMultFileUpload) requestProcessor
				.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event, true));

		assertTrue("value1".equals(event.getParm1())); //$NON-NLS-1$

		FileItem[] fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals(1, fi.length);
		assertEquals("fileparm", fi[0].getFieldName()); //$NON-NLS-1$
		String fullFileName = fi[0].getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1); //$NON-NLS-1$

		verifyFiles(fi[0], "testArrayToSingleParmUpload.xml", fileName1); //$NON-NLS-1$
	}

	@SuppressWarnings("boxing")
	@Test
	public void testOverrideFileUploadValues() {
		resetFileUploadValues();

		sendContextInitializedEvent(Globals.FILE_UPLOAD_DIR, "C:\\"); //$NON-NLS-1$
		String newTmpDir = FileUploadConfig.getFileTempDir();
		assertFalse(this.origTmpDir.equals(newTmpDir));
		assertTrue(newTmpDir.startsWith("C:\\")); //$NON-NLS-1$

		sendContextInitializedEvent(Globals.FILE_BUFFER_SIZE, "1024"); //$NON-NLS-1$
		int newFileBuffer = FileUploadConfig.getBufferSize();
		assertFalse(this.origBufferSize == newFileBuffer);
		assertEquals(1024, newFileBuffer);

		sendContextInitializedEvent(Globals.MAX_FILE_SIZE, "238"); //$NON-NLS-1$
		long newMaxFileSize = FileUploadConfig.getMaxFileSize();
		assertFalse(this.origMaxFileSize == newMaxFileSize);
		assertEquals(238, newMaxFileSize);
	}

	@Test
	public void testNegativeOverrideFileUploadValues() {
		resetFileUploadValues();

		sendContextInitializedEvent(Globals.FILE_BUFFER_SIZE, "ABCD"); //$NON-NLS-1$
		int newFileBuffer = FileUploadConfig.getBufferSize();
		assertTrue(this.origBufferSize == newFileBuffer);

		sendContextInitializedEvent(Globals.MAX_FILE_SIZE, "ABCD"); //$NON-NLS-1$
		long newMaxFileSize = FileUploadConfig.getMaxFileSize();
		assertTrue(this.origMaxFileSize == newMaxFileSize);
	}

	@Test
	public void testUploadOverrideTmpDir() throws Exception {
		String userDir = System.getProperty("user.home") + File.separator; //$NON-NLS-1$
		FileUploadConfig.setFileTempDir(userDir);

		testMultipartUploadSingle();
	}

	@Test
	public void testNegativeUploadOverrideTmpDir() throws Exception {
		String tmpDir = "Q:\\bogus"; //$NON-NLS-1$
		FileUploadConfig.setFileTempDir(tmpDir);

		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String> files = new HashMap<String, String>();

		String fileName = "/WEB-INF/commonsvalidator/commons-validation.xml"; //$NON-NLS-1$
		parms.put("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		files.put("fileparm", fileName); //$NON-NLS-1$

		HttpRequestProcessor requestProcessor = doMultipartUpload(
				new Object[] { parms }, new Object[] { files },
				"/eventFileUpload"); //$NON-NLS-1$

		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event, true));

		assertNull(event.getParm1());
		FileItem fi = event.getFileparm();
		assertNull(fi);
	}

	@Test
	public void testNegativeUploadOverrideMaxSize() throws Exception {
		long fileSize = 1024;
		FileUploadConfig.setMaxFileSize(fileSize);

		Map<String, String> parms = new HashMap<String, String>();
		Map<String, String> files = new HashMap<String, String>();

		String fileName = "/WEB-INF/commonsvalidator/commons-validation.xml"; //$NON-NLS-1$
		parms.put("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		files.put("fileparm", fileName); //$NON-NLS-1$

		HttpRequestProcessor requestProcessor = doMultipartUpload(
				new Object[] { parms }, new Object[] { files },
				"/eventFileUpload"); //$NON-NLS-1$

		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event, true));

		assertNull(event.getParm1());
		FileItem fi = event.getFileparm();
		assertNull(fi);
	}

	@Test
	public void testMapRequestToEvent_Fails() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		addRequestParameter("parm1", "value3"); //$NON-NLS-1$ //$NON-NLS-2$
		addRequestParameter("parm2", "value4"); //$NON-NLS-1$ //$NON-NLS-2$

		setServletPath("/event1"); //$NON-NLS-1$

		HttpRequestProcessor request = createHelper(config);

		Event1 event = (Event1) request.getEvent();

		assertFalse(request.mapRequestToEvent(event, true));

		assertEquals("value3", event.getParm1()); //$NON-NLS-1$
		assertEquals("value4", event.getParm2()); //$NON-NLS-1$
	}

	@Test
	public void testMapRequestToEvent_FailsButDisabled() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		addRequestParameter("parm1", "value3"); //$NON-NLS-1$ //$NON-NLS-2$
		addRequestParameter("parm2", "value4"); //$NON-NLS-1$ //$NON-NLS-2$

		setServletPath("/event1.f2"); //$NON-NLS-1$

		HttpRequestProcessor request = createHelper(config);

		Event1 event = (Event1) request.getEvent();

		assertTrue(request.mapRequestToEvent(event, false));

		assertEquals("value3", event.getParm1()); //$NON-NLS-1$
		assertEquals("value4", event.getParm2()); //$NON-NLS-1$
	}

	@Test
	public void testMapRequestToEvent_WithNoMapping() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event8.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		try {
			request.processRequest();
		} catch (Throwable ex) {
			fail("We should not have gotten an exception"); //$NON-NLS-1$
		}
		verifyForwardPath("/view4.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testForwardNameReturnedByHandlerAvailableAtGlobalOnly()
			throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		ForwardProxy view = null;
		setServletPath("/event1.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		Event1 event = (Event1) request.getEvent();
		try {
			view = request.callHandlers("event1", event, ViewType.HTML); //$NON-NLS-1$
		} catch (ViewException ex) {
			fail("We should have Not gotten an exception"); //$NON-NLS-1$
		}
		assertNotNull(view);
		assertEquals(view.getPath(), "/view1.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testIncorrectForwardNameReturnedByHandler() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event7.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		try {
			request.processRequest();
		} catch (Throwable ex) {
			fail("We should not have gotten an exception"); //$NON-NLS-1$
		}
		verifyForwardPath("/view4.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testForwardNameReturnedByHandler() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		ForwardProxy view = null;
		setServletPath("/event4.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		Event4 event = (Event4) request.getEvent();
		try {
			view = request.callHandlers("event4", event, ViewType.HTML); //$NON-NLS-1$
		} catch (ViewException ex) {
			fail("We should Not have gotten an exception"); //$NON-NLS-1$
		}
		assertNotNull(view);
		assertEquals(view.getPath(), "/ev2.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testViewForHTMLEventMapping() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		ForwardProxy view = null;
		setServletPath("/event2.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		Event2 event = (Event2) request.getEvent();
		try {
			view = request.callHandlers("event2", event, ViewType.HTML); //$NON-NLS-1$
		} catch (ViewException ex) {
			fail("We should Not have gotten an exception"); //$NON-NLS-1$
		}
		assertNotNull(view);
		assertEquals(view.getPath(), "/view4.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testNonExistentViewForEventMapping() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event3.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		try {
			request.processRequest();
		} catch (Throwable ex) {
			fail("We should not have gotten an exception"); //$NON-NLS-1$
		}
		verifyForwardPath("/view4.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testUsingCorrectPathOfLocalForwardInsteadOfGlobal()
			throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		ForwardProxy view = null;
		setServletPath("/event5.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		Event5 event = (Event5) request.getEvent();
		try {
			view = request.callHandlers("event5", event, ViewType.HTML); //$NON-NLS-1$
		} catch (ViewException ex) {
			fail("We should Not have gotten an exception"); //$NON-NLS-1$
		}
		assertNotNull(view);
		assertEquals(view.getPath(), "/ev2.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testLocalForwardToAnEvent() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event12.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		request.processRequest();
		verifyForward("/view1.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testGlobalForwardToAnEvent() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event13.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		request.processRequest();
		verifyForward("/view4.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testEventMappingViewToAnEvent() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event14.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		request.processRequest();
		verifyForwardPath("/view1.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("null")
	@Test
	public void testRequestDispatcherHasCorrectView() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		ForwardProxy view = null;
		setServletPath("/event5.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		Event5 event = (Event5) request.getEvent();
		try {
			view = request.callHandlers("event5", event, ViewType.HTML); //$NON-NLS-1$
		} catch (ViewException ex) {
			fail("We should Not have gotten an exception"); //$NON-NLS-1$
		}
		assertNotNull(view);
		assertEquals(view.getPath(), "/ev2.jsp"); //$NON-NLS-1$

		request.forwardTo(view.getPath());
		verifyForwardPath("/ev2.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testProcessRequest() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event5.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		verifyForwardPath("/ev2.jsp"); //$NON-NLS-1$
	}

	@SuppressWarnings("boxing")
	@Test
	public void testProcessRequest_EventValidationFails() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		addRequestParameter("parm1", "value3"); //$NON-NLS-1$ //$NON-NLS-2$
		addRequestParameter("parm2", "value4"); //$NON-NLS-1$ //$NON-NLS-2$

		setServletPath("/event1"); //$NON-NLS-1$

		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		assertEquals(2, request.getContextWrapper().getRequestErrors().size());

		verifyForwardPath("/view4.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testProcessRequest_ParseValidation() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$

		addRequestParameter("parm1", "value1"); //$NON-NLS-1$ //$NON-NLS-2$
		addRequestParameter("parm2", "value2"); //$NON-NLS-1$ //$NON-NLS-2$
		addRequestParameter("parm3", "azzzz"); //$NON-NLS-1$ //$NON-NLS-2$

		setServletPath("/event1"); //$NON-NLS-1$

		HttpRequestProcessor request = createHelper(config);

		try {
			request.processRequest();
			fail();
		} catch (Throwable e) {
			assertTrue(e instanceof MappingsException);
			MappingsException mes = (MappingsException) e;

			List<MappingException> mec = mes.getMappingExceptions();
			assertTrue(mec.size() == 1);
			MappingException me = mec.get(0);

			assertTrue(me.getBeanName().equals(
					"org.megatome.frame2.front.Event1")); //$NON-NLS-1$
			assertTrue(me.getProperty().equals("parm3")); //$NON-NLS-1$

			assertNotNull(me.getCause());
			assertTrue(me.getCause() instanceof NumberFormatException);
		}
	}

	@Test
	public void testProcessRequest_CancelToHTMLResource() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-cancel.xml"); //$NON-NLS-1$

		addRequestParameter(Globals.CANCEL, "anything"); //$NON-NLS-1$

		setServletPath("/event1"); //$NON-NLS-1$

		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		verifyForwardPath("/cancel.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testProcessRequest_CancelToEvent() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-cancel.xml"); //$NON-NLS-1$

		addRequestParameter(Globals.CANCEL, "anything"); //$NON-NLS-1$

		setServletPath("/event2"); //$NON-NLS-1$

		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		verifyForwardPath("/normal.jsp"); //$NON-NLS-1$
	}

	/*
	 * This test verifies a bug fix. The bug would incorrectly throw an
	 * exception of not being able to find an input view for events that fail
	 * introspection and do not have an input view. Very confusing for the app
	 * developer.
	 */
	@Test
	public void testProcessRequestIntrospectionFails() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/introspectFailEvent.f2"); //$NON-NLS-1$
		addRequestParameter("parm3", "foo"); //$NON-NLS-1$ //$NON-NLS-2$
		HttpRequestProcessor request = createHelper(config);

		try {
			request.processRequest();
			fail();
		} catch (Throwable e) {
			assertTrue(e instanceof MappingsException);
			MappingsException mes = (MappingsException) e;

			List<MappingException> mec = mes.getMappingExceptions();
			assertTrue(mec.size() == 1);
			MappingException me = mec.get(0);

			assertTrue(me.getBeanName().equals(
					"org.megatome.frame2.front.Event1")); //$NON-NLS-1$
			assertTrue(me.getProperty().equals("parm3")); //$NON-NLS-1$

			assertNotNull(me.getCause());
			assertTrue(me.getCause() instanceof NumberFormatException);
		}
	}

	/*
	 * This test verifies a bug fix. The bug occurs when chained events are
	 * used. Any event after the initial one does not gets it name set
	 * correctly.
	 */
	@Test
	public void testProcessRequestEventChain() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/eventChain1.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		verifyForwardPath("/view1.jsp"); //$NON-NLS-1$

		Event evt1 = (Event) request.getContextWrapper().getRequestAttribute(
				"eventChain1"); //$NON-NLS-1$
		assertNotNull(evt1);

		String eventChainName = (String) request.getContextWrapper()
				.getRequestAttribute("eventChainName"); //$NON-NLS-1$
		assertNotNull("Chained Event name is null", eventChainName); //$NON-NLS-1$
		assertTrue(
				"Chained Event name is incorrect", eventChainName.equals("eventChain2")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/*
	 * This test verifies a bug fix. The bug is that the context object
	 * available in an event handler does not have access to the servlet
	 * context.
	 */
	@Test
	public void testProcessRequestServletContext() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/servletContextEvent.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		verifyForwardPath("/view1.jsp"); //$NON-NLS-1$

		String msg = (String) request.getContextWrapper().getServletContext()
				.getAttribute("foo"); //$NON-NLS-1$
		assertNotNull(msg);
		assertTrue(msg.equals("bar")); //$NON-NLS-1$
	}

	@Test
	public void testAppendAttributes() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/attributeEvent.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		verifyForwardPath("/success.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testValidateChainedEvent() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/attributeEvent2.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		verifyForwardPath("/fail.jsp"); //$NON-NLS-1$
	}
	
	@Test
	public void testEventAsInputView() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/attributeEvent3.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		request.processRequest();

		verifyForwardPath("/fail.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testViewExceptionForwardToPath() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event15.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		try {
			request.processRequest();
		} catch (Throwable ex) {
			fail("We should not have gotten an exception"); //$NON-NLS-1$
		}
		verifyForwardPath("/view4.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testConfigExceptionForwardToEvent() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event16.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		try {
			request.processRequest();
		} catch (Throwable ex) {
			fail("We should not have gotten an exception"); //$NON-NLS-1$
		}
		verifyForwardPath("/view4.jsp"); //$NON-NLS-1$
	}

	@Test
	public void testHandlerExceptionForwardToEvent() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
		setServletPath("/event17.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);
		try {
			request.processRequest();
			fail("We should have gotten an exception"); //$NON-NLS-1$
		} catch (Throwable expected) {
			// expected
		}

	}

	@Test
	public void testRedirectHasCorrectView() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/test-redirect-config.xml"); //$NON-NLS-1$
		setServletPath("/redirectEvent.f2"); //$NON-NLS-1$
		HttpRequestProcessor request = createHelper(config);

		request.processRequest();
		verifyForwardPath("http://frame2.org/redirect.jsp?firstName=Barney&lastName=Jones&middle=Jeff"); //$NON-NLS-1$
	}

	@Test
	public void testNoEventNeeded() {
		try {
			Configuration config = new Configuration(
					"org/megatome/frame2/front/test-noEventNeeded.xml"); //$NON-NLS-1$
			setServletPath("/noEventNeeded.f2"); //$NON-NLS-1$
			HttpRequestProcessor request = createHelper(config);

			request.processRequest();
		} catch (Throwable ex) {
			fail("We should not have gotten an exception"); //$NON-NLS-1$
		}
		verifyForwardPath("/view4.jsp"); //$NON-NLS-1$
	}

	HttpRequestProcessor createHelper(Configuration config) {
		return (HttpRequestProcessor) RequestProcessorFactory.instance(config,
				getContext(), getRequest(), getResponse());
	}

	@Test
	public void testNegativeHttpRequestProcessorClass() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/httpRequestNegativeClass.xml"); //$NON-NLS-1$
		HttpRequestProcessor requestProcessor = (HttpRequestProcessor) RequestProcessorFactory
				.instance(config, getContext(), getRequest(), getResponse());
		assertNull(requestProcessor);
	}

	@Test
	public void testNegativeHttpRequestProcessorClassImplementRequestProcessor()
			throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/httpRequestNegativeClassReqProc.xml"); //$NON-NLS-1$
		HttpRequestProcessor requestProcessor = (HttpRequestProcessor) RequestProcessorFactory
				.instance(config, getContext(), getRequest(), getResponse());
		assertNull(requestProcessor);
	}

	@Test
	public void testHttpRequestProcessorDefaultRequestProcessors()
			throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/ReqProcDefaults.xml"); //$NON-NLS-1$
		HttpRequestProcessor requestProcessor = (HttpRequestProcessor) RequestProcessorFactory
				.instance(config, getContext(), getRequest(), getResponse());

		assertNotNull(requestProcessor);
		String className = "org.megatome.frame2.front.HttpRequestProcessor"; //$NON-NLS-1$
		assertEquals(className, requestProcessor.getClass().getName());
	}

	@Test
	public void testCustomHttpRequestProcessor() throws Exception {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/httpRequestCustom.xml"); //$NON-NLS-1$
		RequestProcessor requestProcessor = RequestProcessorFactory.instance(
				config, getContext(), getRequest(), getResponse());
		assertNotNull(requestProcessor);
		String className = "org.megatome.frame2.front.HttpRequestProcessorCustom"; //$NON-NLS-1$
		assertEquals(className, requestProcessor.getClass().getName());
	}

	@Test
	public void testEventNameFromContext() {
		try {
			String EVENT_NAME = "event5"; //$NON-NLS-1$
			Configuration config = new Configuration(
					"org/megatome/frame2/front/test-config.xml"); //$NON-NLS-1$
			setServletPath("/" + EVENT_NAME + ".f2"); //$NON-NLS-1$ //$NON-NLS-2$
			HttpRequestProcessor requestProc = createHelper(config);

			requestProc.processRequest();
			HttpServletRequest request = getRequest();
			Event event = (Event) request.getAttribute(EVENT_NAME);

			assertEquals(EVENT_NAME, event.getEventName());
		} catch (Throwable e) {
			fail();
		}

	}

	@Test
	public void testEventFailValidationEventStillInRequest() throws Throwable {
		Configuration config = new Configuration(
				"org/megatome/frame2/front/httpRequestProc.xml"); //$NON-NLS-1$
		String EVENT_NAME = "event1"; //$NON-NLS-1$
		addRequestParameter("stillThere", "BABY"); //$NON-NLS-1$ //$NON-NLS-2$
		setServletPath("/" + EVENT_NAME + ".f2"); //$NON-NLS-1$ //$NON-NLS-2$
		HttpRequestProcessor requestProc = createHelper(config);
		try {
			requestProc.processRequest();

			HttpServletRequest request = getRequest();
			FailsValidationEvent event = (FailsValidationEvent) request
					.getAttribute(EVENT_NAME);
			assertNotNull(event);
			String stillThere = event.getStillThere();

			assertEquals("BABY", stillThere); //$NON-NLS-1$
		} catch (Exception ex) {
			fail("We should not have gotten an exception"); //$NON-NLS-1$
		}
	}

	@After
	public void resetFileUploadValues() {
		FileUploadConfig.setFileTempDir(this.origTmpDir);
		FileUploadConfig.setBufferSize(this.origBufferSize);
		FileUploadConfig.setMaxFileSize(this.origMaxFileSize);
	}

}
