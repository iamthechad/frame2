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
package org.megatome.frame2.front;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import junitx.framework.FileAssert;

import org.apache.commons.fileupload.FileItem;
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

   public TestHttpRequestProcessor(String name) {
      super(name);
      
      origTmpDir = FileUploadConfig.getFileTempDir();
      origBufferSize = FileUploadConfig.getBufferSize();
      origMaxFileSize = FileUploadConfig.getMaxFileSize();
   }
  
   public void testGetEvent() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");

      setServletPath("/event1");

      HttpRequestProcessor request = createHelper(config);

      Event event = request.getEvent();

      assertNotNull(event);
      assertTrue(event instanceof Event1);
   }

   public void testMapRequestToEvent() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");

      addRequestParameter("parm1", "value1");
      addRequestParameter("parm2", "value2");

      setServletPath("/event1");

      HttpRequestProcessor request = createHelper(config);

      Event1 event = (Event1) request.getEvent();

      assertTrue(request.mapRequestToEvent(event,true));

      assertEquals("value1",event.getParm1());
      assertEquals("value2",event.getParm2());
   }
   
   private HttpRequestProcessor doMultipartUpload(Object[] formParms, Object[] fileParms, String eventName) 
   throws Exception {
		Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");

		NVPair[] hdrs = new NVPair[1];
		Frame2HttpServletRequestSimulator request = (Frame2HttpServletRequestSimulator)getRequest();
		
		NVPair[] opts;
		opts = new NVPair[formParms.length];
		for (int i = 0; i < formParms.length; i++) {
			HashMap parm = (HashMap)formParms[i];
			for (Iterator it = parm.keySet().iterator(); it.hasNext(); ) {
				String key = (String)it.next();
				String value = (String)parm.get(key);
				opts[i] = new NVPair(key, value);
			}
		}
		
		NVPair[] file;
		file = new NVPair[fileParms.length];
		for (int i = 0; i < fileParms.length; i++) {
			HashMap parm = (HashMap)fileParms[i];
			for (Iterator it = parm.keySet().iterator(); it.hasNext();)  {
				String key = (String)it.next();
				String value = (String)parm.get(key);
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
			 request.setHeader(HttpRequestProcessor.CONTENT_TYPE, hdrs[0].getValue());
			 is.close();
		} catch (IOException e) {
			 fail();
		}

		setServletPath(eventName);

		return createHelper(config);
   }
   
   private void verifyFiles(FileItem fi, String uploadFileName, String origFileName) {
		try {
         String fileDir = FileUploadConfig.getFileTempDir();
         File f = new File(fileDir + uploadFileName);
         fi.write(f);
         
         File origFile = new File(getContext().getResource(origFileName).getFile());
         assertTrue(f.exists());
         FileAssert.assertEquals(origFile, f);
      } catch (MalformedURLException e) {
			fail();
      } catch (Exception e) {
         fail();
      }
   }
   
   public void testMultipartUploadSingle() throws Exception {
   	HashMap parms = new HashMap();
   	HashMap files = new HashMap();
   	
   	String fileName = "/WEB-INF/frame2-config.xml";
   	parms.put("parm1", "value1");
		files.put("fileparm", fileName);

		HttpRequestProcessor requestProcessor = 
		   doMultipartUpload(new Object[] { parms }, new Object[] { files }, "/eventFileUpload");

		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event,true));

		assertEquals("value1",event.getParm1());
		FileItem fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals("fileparm",fi.getFieldName());
		String fullFileName = fi.getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1);
		
		verifyFiles(fi, "testSingleFileUpload.xml", fileName);
   }
   
   public void testMultipartUploadLargeSingle() throws Exception {
		HashMap parms = new HashMap();
		HashMap files = new HashMap();

		String fileName = "/WEB-INF/commonsvalidator/commons-validator-rules.xml";
		parms.put("parm1", "value1");
		files.put("fileparm", fileName);

		HttpRequestProcessor requestProcessor = 
		   doMultipartUpload(new Object[] { parms }, new Object[] { files }, "/eventFileUpload");
		
		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event,true));

		assertEquals("value1",event.getParm1());
		FileItem fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals("fileparm",fi.getFieldName());
		String fullFileName = fi.getName();
		assertTrue(fullFileName.indexOf("commons-validator-rules.xml") != -1);
		
		verifyFiles(fi, "testSingleBigFileUpload.xml", fileName);
   }
   
	public void testMultipartUploadMultipleDiffParam() throws Exception {
		HashMap parms = new HashMap();
		HashMap files1 = new HashMap();
		HashMap files2 = new HashMap();

		String fileName1 = "/WEB-INF/frame2-config.xml";
		String fileName2 = "/WEB-INF/commonsvalidator/commons-validator-rules.xml";
		parms.put("parm1", "value1");
		files1.put("fileparm", fileName1);
		files2.put("fileparm2", fileName2);

		HttpRequestProcessor requestProcessor = 
		   doMultipartUpload(new Object[] { parms }, new Object[] { files1, files2 }, "/eventFileUpload");
		
		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event,true));

		FileItem fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals("fileparm",fi.getFieldName());
		String fullFileName = fi.getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1);
		
		verifyFiles(fi, "testMultFileUpload1.xml", fileName1);
			
		fi = event.getFileparm2();
		assertNotNull(fi);
		assertEquals("fileparm2",fi.getFieldName());
		fullFileName = fi.getName();
		assertTrue(fullFileName.indexOf("commons-validator-rules.xml") != -1);

		verifyFiles(fi, "testMultFileUpload2.xml", fileName2);
	}
		
	public void testMultipartUploadMultipleSameParam() throws Exception {
		HashMap parms = new HashMap();
		HashMap files1 = new HashMap();
		HashMap files2 = new HashMap();

		String fileName1 = "/WEB-INF/frame2-config.xml";
		String fileName2 = "/WEB-INF/commonsvalidator/commons-validator-rules.xml";
		parms.put("parm1", "value1");
		files1.put("fileparm", fileName1);
		files2.put("fileparm", fileName2);

		HttpRequestProcessor requestProcessor = 
		   doMultipartUpload(new Object[] { parms }, new Object[] { files1, files2 }, "/eventMultFileUpload");

		EventMultFileUpload event = (EventMultFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event,true));

		FileItem[] fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals(2, fi.length);
		assertEquals("fileparm",fi[0].getFieldName());
		String fullFileName = fi[0].getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1);
		
		verifyFiles(fi[0], "testMultFileSameParamUpload1.xml", fileName1);
	
		assertEquals("fileparm",fi[1].getFieldName());
		fullFileName = fi[1].getName();
		assertTrue(fullFileName.indexOf("commons-validator-rules.xml") != -1);

		verifyFiles(fi[1], "testMultFileSameParamUpload2.xml", fileName2);
	}
	
	public void testSingleParamToArray() throws Exception {
		HashMap parms = new HashMap();
		HashMap files1 = new HashMap();

		String fileName1 = "/WEB-INF/frame2-config.xml";
		parms.put("parm1", "value1");
		files1.put("fileparm", fileName1);

		HttpRequestProcessor requestProcessor = 
			doMultipartUpload(new Object[] { parms }, new Object[] { files1 }, "/eventMultFileUpload");

		EventMultFileUpload event = (EventMultFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event,true));

		assertTrue("value1".equals(event.getParm1()));

		FileItem[] fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals(1, fi.length);
		assertEquals("fileparm",fi[0].getFieldName());
		String fullFileName = fi[0].getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1);
	
		verifyFiles(fi[0], "testParmToArrayUpload.xml", fileName1);
	}
	
	public void testSingleArrayParamToSingle() throws Exception {
		HashMap parms = new HashMap();
		HashMap parms2 = new HashMap();
		HashMap files1 = new HashMap();

		String fileName1 = "/WEB-INF/frame2-config.xml";
		parms.put("parm1", "value1");
		parms2.put("parm1", "invalidValue");
		files1.put("fileparm", fileName1);

		HttpRequestProcessor requestProcessor = 
			doMultipartUpload(new Object[] { parms, parms2 }, new Object[] { files1 }, "/eventMultFileUpload");

		EventMultFileUpload event = (EventMultFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event,true));
		
		assertTrue("value1".equals(event.getParm1()));

		FileItem[] fi = event.getFileparm();
		assertNotNull(fi);
		assertEquals(1, fi.length);
		assertEquals("fileparm",fi[0].getFieldName());
		String fullFileName = fi[0].getName();
		assertTrue(fullFileName.indexOf("frame2-config.xml") != -1);

		verifyFiles(fi[0], "testArrayToSingleParmUpload.xml", fileName1);
	}
			
   public void testOverrideFileUploadValues() {
   	resetFileUploadValues();
   	
   	sendContextInitializedEvent(Globals.FILE_UPLOAD_DIR, "C:\\");
   	String newTmpDir = FileUploadConfig.getFileTempDir();
   	assertFalse(origTmpDir.equals(newTmpDir));
   	assertTrue(newTmpDir.startsWith("C:\\"));
   	
		sendContextInitializedEvent(Globals.FILE_BUFFER_SIZE, "1024");
		int newFileBuffer = FileUploadConfig.getBufferSize();
		assertFalse(origBufferSize == newFileBuffer);
		assertEquals(1024, newFileBuffer);
		
		sendContextInitializedEvent(Globals.MAX_FILE_SIZE, "238");
		long newMaxFileSize = FileUploadConfig.getMaxFileSize();
		assertFalse(origMaxFileSize == newMaxFileSize);
		assertEquals(238, newMaxFileSize);
   }
   
   public void testNegativeOverrideFileUploadValues() {
		resetFileUploadValues();

		sendContextInitializedEvent(Globals.FILE_BUFFER_SIZE, "ABCD");
		int newFileBuffer = FileUploadConfig.getBufferSize();
		assertTrue(origBufferSize == newFileBuffer);

		sendContextInitializedEvent(Globals.MAX_FILE_SIZE, "ABCD");
		long newMaxFileSize = FileUploadConfig.getMaxFileSize();
		assertTrue(origMaxFileSize == newMaxFileSize);
   }

   public void testUploadOverrideTmpDir() throws Exception {
   	String userDir = System.getProperty("user.home") + File.separator;
   	FileUploadConfig.setFileTempDir(userDir);
   	
   	testMultipartUploadSingle();
   }

   public void testNegativeUploadOverrideTmpDir() throws Exception {
   	String tmpDir = "Q:\\bogus";
   	FileUploadConfig.setFileTempDir(tmpDir);
   	
		HashMap parms = new HashMap();
		HashMap files = new HashMap();

		String fileName = "/WEB-INF/commonsvalidator/commons-validator-rules.xml";
		parms.put("parm1", "value1");
		files.put("fileparm", fileName);

		HttpRequestProcessor requestProcessor = 
		   doMultipartUpload(new Object[] { parms }, new Object[] { files }, "/eventFileUpload");
		
		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event,true));

		assertNull(event.getParm1());
		FileItem fi = event.getFileparm();
		assertNull(fi);
   }
   
   public void testNegativeUploadOverrideMaxSize() throws Exception {
		long fileSize = 1024;
		FileUploadConfig.setMaxFileSize(fileSize);

		HashMap parms = new HashMap();
		HashMap files = new HashMap();

		String fileName = "/WEB-INF/commonsvalidator/commons-validator-rules.xml";
		parms.put("parm1", "value1");
		files.put("fileparm", fileName);

		HttpRequestProcessor requestProcessor = 
			doMultipartUpload(new Object[] { parms }, new Object[] { files }, "/eventFileUpload");

		EventFileUpload event = (EventFileUpload) requestProcessor.getEvent();

		assertTrue(requestProcessor.mapRequestToEvent(event,true));

		assertNull(event.getParm1());
		FileItem fi = event.getFileparm();
		assertNull(fi);
   }

   public void testMapRequestToEvent_Fails() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");

      addRequestParameter("parm1","value3");
      addRequestParameter("parm2","value4");

      setServletPath("/event1");

      HttpRequestProcessor request = createHelper(config);

      Event1 event = (Event1) request.getEvent();
      
      assertFalse(request.mapRequestToEvent(event,true));

      assertEquals("value3",event.getParm1());
      assertEquals("value4",event.getParm2());
   }   

   public void testMapRequestToEvent_FailsButDisabled() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");

      addRequestParameter("parm1","value3");
      addRequestParameter("parm2","value4");

      setServletPath("/event1.f2");

      HttpRequestProcessor request = createHelper(config);

      Event1 event = (Event1) request.getEvent();
      
      assertTrue(request.mapRequestToEvent(event,false));

      assertEquals("value3",event.getParm1());
      assertEquals("value4",event.getParm2());
   }
      
   public void testMapRequestToEvent_WithNoMapping() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/event8.f2");
      HttpRequestProcessor request = createHelper(config);
      
      try{
         request.processRequest();
      }
      catch(Throwable ex) {
         fail("We should not have gotten an exception");
      }
      verifyForwardPath("/view4.jsp");
   }   
   
   public void testForwardNameReturnedByHandlerAvailableAtGlobalOnly() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      ForwardProxy view = null;
      setServletPath("/event1.f2");
      HttpRequestProcessor request = createHelper(config);
      Event1 event = (Event1) request.getEvent();
      try {
         view = request.callHandlers("event1",event,ViewType.HTML);
      } catch (ViewException ex) {
         fail("We should have Not gotten an exception");
      }
      assertNotNull(view);
      assertEquals(view.getPath(),"/view1.jsp");
   }
   
   public void testIncorrectForwardNameReturnedByHandler() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/event7.f2");
      HttpRequestProcessor request = createHelper(config);
      
      try{
         request.processRequest();
      }
      catch(Throwable ex) {
         fail("We should not have gotten an exception");
      }
      verifyForwardPath("/view4.jsp");   
   }
   
   public void testForwardNameReturnedByHandler() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      ForwardProxy view = null;
      setServletPath("/event4.f2");
      HttpRequestProcessor request = createHelper(config);
      Event4 event = (Event4) request.getEvent();
      try {
         view = request.callHandlers("event4",event,ViewType.HTML);
      } catch (ViewException ex) {
         fail("We should Not have gotten an exception");
      }
      assertNotNull(view);
      assertEquals(view.getPath(),"/ev2.jsp");
   }

   public void testViewForHTMLEventMapping() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      ForwardProxy view = null;
      setServletPath("/event2.f2");
      HttpRequestProcessor request = createHelper(config);
      Event2 event = (Event2) request.getEvent();
      try {
         view = request.callHandlers("event2",event,ViewType.HTML);
      } catch (ViewException ex) {
         fail("We should Not have gotten an exception");
      }
      assertNotNull(view);
      assertEquals(view.getPath(),"/view4.jsp");
   }

   public void testNonExistentViewForEventMapping() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/event3.f2");
      HttpRequestProcessor request = createHelper(config);
     
      try{
         request.processRequest();
      }
      catch(Throwable ex) {
         fail("We should not have gotten an exception");
      }
      verifyForwardPath("/view4.jsp");   
   }
   
   public void testUsingCorrectPathOfLocalForwardInsteadOfGlobal() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      ForwardProxy view = null;
      setServletPath("/event5.f2");
      HttpRequestProcessor request = createHelper(config);
      Event5 event = (Event5) request.getEvent();
      try {
         view = request.callHandlers("event5",event,ViewType.HTML);
      } catch (ViewException ex) {
         fail("We should Not have gotten an exception");
      }
      assertNotNull(view);
      assertEquals(view.getPath(),"/ev2.jsp");
   }

   public void testLocalForwardToAnEvent() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      ForwardProxy view = null;
      setServletPath("/event12.f2");
      HttpRequestProcessor request = createHelper(config);
      Event12 event = (Event12) request.getEvent();
      try {
         view = request.callHandlers("event12",event,ViewType.HTML);
      } catch (ViewException ex) {
         fail("We should Not have gotten an exception");
      }
      assertNotNull(view);
      assertEquals(view.getPath(),"/view1.jsp");
   }
   
   public void testGlobalForwardToAnEvent() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      ForwardProxy view = null;
      setServletPath("/event13.f2");
      HttpRequestProcessor request = createHelper(config);
      Event13 event = (Event13) request.getEvent();
      try {
         view = request.callHandlers("event13",event,ViewType.HTML);
      } catch (ViewException ex) {
         fail("We should Not have gotten an exception");
      }
      assertNotNull(view);
      assertEquals(view.getPath(),"/view4.jsp");
   }

   public void testEventMappingViewToAnEvent() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      ForwardProxy view = null;
      setServletPath("/event14.f2");
      HttpRequestProcessor request = createHelper(config);
      Event14 event = (Event14) request.getEvent();
      try {
         view = request.callHandlers("event14",event,ViewType.HTML);
      } catch (ViewException ex) {
         fail("We should Not have gotten an exception");
      }
      assertNotNull(view);
      assertEquals(view.getPath(),"/view1.jsp");
   }

   public void testRequestDispatcherHasCorrectView() throws Exception {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      ForwardProxy view = null;
      setServletPath("/event5.f2");
      HttpRequestProcessor request = createHelper(config);
      Event5 event = (Event5) request.getEvent();
      try {
         view = request.callHandlers("event5",event,ViewType.HTML);
      } catch (ViewException ex) {
         fail("We should Not have gotten an exception");
      }
      assertNotNull(view);
      assertEquals(view.getPath(),"/ev2.jsp");
      
      request.forwardTo(view.getPath());
      verifyForwardPath("/ev2.jsp");
   }
   
   public void testProcessRequest( ) throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/event5.f2");
      HttpRequestProcessor request = createHelper(config);
      
      request.processRequest();
      
      verifyForwardPath("/ev2.jsp");
   }

   public void testProcessRequest_EventValidationFails( ) throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");

      addRequestParameter("parm1","value3");
      addRequestParameter("parm2","value4");

      setServletPath("/event1");

      HttpRequestProcessor request = createHelper(config);
      
      request.processRequest();

      assertEquals(2,request.getContextWrapper().getRequestErrors().size());

      verifyForwardPath("/view4.jsp");
   }

   public void testProcessRequest_ParseValidation( ) throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");

      addRequestParameter("parm1","value1");
      addRequestParameter("parm2","value2");
      addRequestParameter("parm3","azzzz");

      setServletPath("/event1");

      HttpRequestProcessor request = createHelper(config);
      
      try {
         request.processRequest();
         fail();
      } catch (Throwable e) {
         assertTrue(e instanceof MappingsException);
         MappingsException mes = (MappingsException)e;
         
         assertTrue((mes.getMappingExceptions()).length == 1);
         MappingException me = (mes.getMappingExceptions())[0];
         
         assertTrue(me.getBeanName().equals("org.megatome.frame2.front.Event1"));
         assertTrue(me.getProperty().equals("parm3"));
         
         assertNotNull(me.getCause());
         assertTrue(me.getCause() instanceof NumberFormatException);
      }
   }

   public void testProcessRequest_CancelToHTMLResource( ) throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-cancel.xml");

      addRequestParameter(Globals.CANCEL,"anything");

      setServletPath("/event1");

      HttpRequestProcessor request = createHelper(config);
      
      request.processRequest();

      verifyForwardPath("/cancel.jsp");
   }

   public void testProcessRequest_CancelToEvent( ) throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-cancel.xml");

      addRequestParameter(Globals.CANCEL,"anything");

      setServletPath("/event2");

      HttpRequestProcessor request = createHelper(config);
      
      request.processRequest();

      verifyForwardPath("/chained.jsp");
   }
   
   /*
    * This test verifies a bug fix. The bug would incorrectly throw an exception of
    * not being able to find an input view for events that fail introspection
    * and do not have an input view. Very confusing for the app developer.
    */
   public void testProcessRequestIntrospectionFails( ) throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/introspectFailEvent.f2");
      addRequestParameter("parm3","foo");
      HttpRequestProcessor request = createHelper(config);
      
      try {
         request.processRequest();
         fail();
      } catch (Throwable e) {
         assertTrue(e instanceof MappingsException);
         MappingsException mes = (MappingsException)e;
         
         assertTrue((mes.getMappingExceptions()).length == 1);
         MappingException me = (mes.getMappingExceptions())[0];
         
         assertTrue(me.getBeanName().equals("org.megatome.frame2.front.Event1"));
         assertTrue(me.getProperty().equals("parm3"));
         
         assertNotNull(me.getCause());
         assertTrue(me.getCause() instanceof NumberFormatException);
      }
   }
   
   /*
    * This test verifies a bug fix. The bug occurrs when chained events are used. Any event
    * after the initial one does not gets it name set correctly.
    */
   public void testProcessRequestEventChain() throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/eventChain1.f2");
      HttpRequestProcessor request = createHelper(config);
   
      request.processRequest();
   
      verifyForwardPath("/view1.jsp");
      
      Event evt1 = (Event)request.getContextWrapper().getRequestAttribute("eventChain1");
      assertNotNull(evt1);
      
      String eventChainName = (String)request.getContextWrapper().getRequestAttribute("eventChainName");
      assertNotNull("Chained Event name is null", eventChainName);
      assertTrue("Chained Event name is incorrect",eventChainName.equals("eventChain2"));
   }
   
   /*
    * This test verifies a bug fix. The bug is that the context objct available in
    * an event handler does not have access to the servlet context.
    */
   public void testProcessRequestServletContext() throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/servletContextEvent.f2");
      HttpRequestProcessor request = createHelper(config);

      request.processRequest();

      verifyForwardPath("/view1.jsp");
   
      String msg = (String)request.getContextWrapper().getServletContext().getAttribute("foo");
      assertNotNull(msg);
      assertTrue(msg.equals("bar"));
   }
   

   public void testViewExceptionForwardToPath() throws Throwable{
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/event15.f2");
      HttpRequestProcessor request = createHelper(config);
      try{
         request.processRequest();
      }
      catch(Throwable ex) {
         fail("We should not have gotten an exception");
      }
      verifyForwardPath("/view4.jsp");   
   }

   public void testConfigExceptionForwardToEvent() throws Exception{
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/event16.f2");
      HttpRequestProcessor request = createHelper(config);
      try{
         request.processRequest();
      }
      catch(Throwable ex) {
         fail("We should not have gotten an exception");
      }
      verifyForwardPath("/view4.jsp");   
   }
   
   public void testHandlerExceptionForwardToEvent() throws Exception{
      Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
      setServletPath("/event17.f2");
      HttpRequestProcessor request = createHelper(config);
      try{
         request.processRequest();
         fail("We should have gotten an exception");
      }
      catch(Throwable ex) {
         
      }
     
   }

   
   public void testRedirectHasCorrectView() throws Throwable {
      Configuration config = new Configuration("org/megatome/frame2/front/test-redirect-config.xml");
      setServletPath("/redirectEvent.f2");
      HttpRequestProcessor request = createHelper(config);

      request.processRequest();      
      verifyForwardPath("http://frame2.org/redirect.jsp?firstName=Barney&lastName=Jones&middle=Jeff");
   }

   public void testNoEventNeeded()  {
      try{
      Configuration config = new Configuration("org/megatome/frame2/front/test-noEventNeeded.xml");
      setServletPath("/noEventNeeded.f2");
      HttpRequestProcessor request = createHelper(config);
      
    
         request.processRequest();
      }
      catch(Throwable ex) {
         fail("We should not have gotten an exception");
      }
      verifyForwardPath("/view4.jsp");
   }
   
 
   HttpRequestProcessor createHelper(Configuration config) {
      return (HttpRequestProcessor) RequestProcessorFactory.instance(config,getContext(),getRequest(),getResponse());
   }
   
   
   public void testNegativeHttpRequestProcessorClass () throws Exception {  
     Configuration config = new Configuration("org/megatome/frame2/front/httpRequestNegativeClass.xml");  
     HttpRequestProcessor requestProcessor= (HttpRequestProcessor) RequestProcessorFactory.instance(config,getContext(),getRequest(),getResponse());       
     assertNull(requestProcessor);                     
   }
   
   
   public void testNegativeHttpRequestProcessorClassImplementRequestProcessor () throws Exception {  
     Configuration config = new Configuration("org/megatome/frame2/front/httpRequestNegativeClassReqProc.xml");  
     HttpRequestProcessor requestProcessor= (HttpRequestProcessor) RequestProcessorFactory.instance(config,getContext(),getRequest(),getResponse());       
     assertNull(requestProcessor);                     
   }
   
   public void testHttpRequestProcessorDefaultRequestProcessors () throws Exception {  
     Configuration config = new Configuration("org/megatome/frame2/front/ReqProcDefaults.xml");  
     HttpRequestProcessor requestProcessor= (HttpRequestProcessor) RequestProcessorFactory.instance(config,getContext(),getRequest(),getResponse());       
     
     assertNotNull(requestProcessor);         
     String className = "org.megatome.frame2.front.HttpRequestProcessor";   
     assertEquals(className,requestProcessor.getClass().getName());
          
     
   }
   
   public void testCustomHttpRequestProcessor () throws Exception {  
     Configuration config = new Configuration("org/megatome/frame2/front/httpRequestCustom.xml");  
     RequestProcessor requestProcessor=  RequestProcessorFactory.instance(config,getContext(),getRequest(),getResponse());       
     assertNotNull(requestProcessor);         
     String className = "org.megatome.frame2.front.HttpRequestProcessorCustom";   
     assertEquals(className,requestProcessor.getClass().getName());                 
   }
   
   
   public void testEventNameFromContext () {
      try {
        String EVENT_NAME = "event5";
        Configuration config = new Configuration("org/megatome/frame2/front/test-config.xml");
        setServletPath("/" +EVENT_NAME + ".f2");
        HttpRequestProcessor requestProc = createHelper(config);
      
        requestProc.processRequest();       
        HttpServletRequest request = getRequest();
        Event event = (Event) request.getAttribute(EVENT_NAME);
             
        assertEquals(EVENT_NAME, event.getName());
      }
      catch (Throwable e) {
         fail();
      }                  
      
   }
   
   public void testEventFailValidationEventStillInRequest() throws Throwable{
      Configuration config = new Configuration("org/megatome/frame2/front/httpRequestProc.xml");
      String EVENT_NAME = "event1";
      addRequestParameter("stillThere","BABY");
      setServletPath("/" +EVENT_NAME + ".f2");
      HttpRequestProcessor requestProc = createHelper(config);
      try{
         requestProc.processRequest();
         
        HttpServletRequest request = getRequest();
        FailsValidationEvent event = (FailsValidationEvent) request.getAttribute(EVENT_NAME);
        String stillThere = event.getStillThere();
             
        assertEquals("BABY", stillThere);
      }
      catch(Throwable ex) {
         fail("We should not have gotten an exception");
      }
   }
   
   private void resetFileUploadValues() {
   	FileUploadConfig.setFileTempDir(origTmpDir);
   	FileUploadConfig.setBufferSize(origBufferSize);
   	FileUploadConfig.setMaxFileSize(origMaxFileSize);
   }

}
