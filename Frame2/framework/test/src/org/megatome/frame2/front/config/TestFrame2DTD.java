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
package org.megatome.frame2.front.config;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.megatome.frame2.util.sax.Frame2EntityResolver;
import org.megatome.frame2.util.sax.Frame2ParseErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TestFrame2DTD {

	private DocumentBuilder documentBuilder = null;

	@Before
	public void setUp() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);

		this.documentBuilder = dbf.newDocumentBuilder();
		this.documentBuilder.setErrorHandler(new Frame2ParseErrorHandler());
		this.documentBuilder.setEntityResolver(new Frame2EntityResolver());
	}

	@Test
	public void testDTD() {
		parseConfig("org/megatome/frame2/front/config/emptyTags-config1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeDTD() {
		parseNegativeConfig("org/megatome/frame2/front/config/invalidDTD-config1_1.xml"); //$NON-NLS-1$
	}
	
	@Test
	public void testNegativeSchemaMapping() {
		parseNegativeConfig("org/megatome/frame2/front/config/invalidSchemaLocation.xml"); //$NON-NLS-1$
	}
	
	@Test
	public void testNegativeSchemaMappingContents() {
		parseNegativeConfig("org/megatome/frame2/front/config/invalidSchemaContents.xml"); //$NON-NLS-1$
	}
	
	@Test
	public void testSchemaMapping() {
		parseConfig("org/megatome/frame2/front/config/schemaMappings.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeDTDMultiPlugins() {
		parseNegativeConfig("org/megatome/frame2/front/config/invalidDTDMultiPlugins-config1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testSinglePlugin() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Single-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeSinglePlugin() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeNameSinglePlugin() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Name-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeEmptySinglePlugin() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Empty-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeSingleTypePlugin() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-PluginType1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testSinglePluginParams() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Single-Param-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeSinglePluginParams() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Param1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeSinglePluginParamsName() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Param-Name1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeSinglePluginParamsValue() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Param-Value1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeSinglePluginParamsEmpty() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Param-Empty1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testMultiplePlugin() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Multiple-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testMultiplePluginParams() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Mult-Param-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testMultiplePluginDupParams() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Dup-Negative-Param-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testEmptyRP() {
		parseConfig("org/megatome/frame2/front/config/RPTag-empty-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testHttpSingleRP() {
		parseConfig("org/megatome/frame2/front/config/RPTag-http-single-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testSoapSingleRP() {
		parseConfig("org/megatome/frame2/front/config/RPTag-soap-single-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testHttpSoapSingleRP() {
		parseConfig("org/megatome/frame2/front/config/RPTag-http-soap-single-1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegativeSoapHttpSingleRP() {
		parseNegativeConfig("org/megatome/frame2/front/config/RPTag-soap-http-single-Negative1_1.xml"); //$NON-NLS-1$
	}

	@Test
	public void testNegative2soap1httpRP() {
		parseNegativeConfig("org/megatome/frame2/front/config/RPTag-2soap-http-single-Negative1_1.xml"); //$NON-NLS-1$
	}

	private void parseConfig(String configFile) {
		try {
			InputStream is =
				getClass().getClassLoader().getResourceAsStream(configFile);
			this.documentBuilder.parse(is);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			fail("Unexpected IOException"); //$NON-NLS-1$
		} catch (SAXParseException spe) {
			spe.printStackTrace();
			fail("Unexpected SAXParseException"); //$NON-NLS-1$
		} catch (SAXException se) {
			se.printStackTrace();
			fail("Unexpected SAXException"); //$NON-NLS-1$
		}
	}

	private void parseNegativeConfig(String configFile) {
		try {
			InputStream is =
				getClass().getClassLoader().getResourceAsStream(configFile);
			this.documentBuilder.parse(is);
			fail("Expected SAXException did not occur"); //$NON-NLS-1$
		} catch (IOException ioe) {
			fail("Unexpected IO Exception"); //$NON-NLS-1$
		} catch (SAXParseException expected) {
			//Expected
		} catch (SAXException expected) {
			//Expected
		}

		
	}
}
