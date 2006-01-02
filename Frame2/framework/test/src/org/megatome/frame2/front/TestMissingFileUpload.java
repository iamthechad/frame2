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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;

import org.megatome.frame2.Globals;

import servletunit.frame2.Frame2HttpServletRequestSimulator;
import servletunit.frame2.MockFrame2TestCase;
import HTTPClient.Codecs;
import HTTPClient.NVPair;

public class TestMissingFileUpload extends MockFrame2TestCase {
	
   public TestMissingFileUpload(String name) {
      super(name);
   }
   
	private void postNegativeHttpReqProc() {
		HttpFrontController servlet = initializeServlet();

		try {
			servlet.doPost(getRequest(), getResponse());
		} catch (Exception e) {
		   return;
		}
		fail("Failed to catch expected exception");
	}


	private HttpFrontController initializeServlet() {
		HttpFrontController servlet = getServlet();
		try {
			servlet.init();
		} catch (ServletException e) {
			fail("Unexpected ServletException: " + e.getMessage());
		}
		Configuration config = getServlet().getConfiguration();

		assertNotNull(config);

		return servlet;
	}
	
	public void testPrerequisites() {
	   try {
	      Class.forName("org.apache.commons.fileupload.DiskFileUpload");
          fail("Did not catch expected ClassDef exception");
	   } catch (ClassNotFoundException expected) {
	      // This is the expected result
	   } catch (Exception e) {
	      fail("Unexpected Exception");
	   }
	}
	
	public void testUploadWithoutLibrary() throws Exception {

		sendContextInitializedEvent(
			Globals.CONFIG_FILE,
			"org/megatome/frame2/front/test-config.xml");
		
		Map parms = new HashMap();
	   	Map files = new HashMap();
	   	
	   	String fileName = "/WEB-INF/frame2-config.xml";
	   	parms.put("parm1", "value1");
		files.put("fileparm", fileName);

		prepareMultipartUpload(new Object[] { parms }, new Object[] { files }, "/eventFileUpload");
		
		postNegativeHttpReqProc();
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
   
   private void prepareMultipartUpload(Object[] formParms, Object[] fileParms, String eventName) 
   throws Exception {
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
   }

   HttpRequestProcessor createHelper(Configuration config) {
      return (HttpRequestProcessor) RequestProcessorFactory.instance(config,getContext(),getRequest(),getResponse());
   }
}
