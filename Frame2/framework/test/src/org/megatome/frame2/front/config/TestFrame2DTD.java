package org.megatome.frame2.front.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.megatome.frame2.util.sax.Frame2EntityResolver;
import org.megatome.frame2.util.sax.Frame2ParseErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * TestWAMConfigReader.java
 */
public class TestFrame2DTD extends TestCase {

	private DocumentBuilder _documentBuilder = null;
	/**
	 * Constructor for TestFrame2DTD_1.0
	 */
	public TestFrame2DTD() {
		super();
	}

	/**
	 * Constructor for TestFrame2DTD_1.0
	 * @param name
	 */
	public TestFrame2DTD(String name) {
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);

		_documentBuilder = dbf.newDocumentBuilder();
		_documentBuilder.setErrorHandler(new Frame2ParseErrorHandler());
		_documentBuilder.setEntityResolver(new Frame2EntityResolver());
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDTD() {
		parseConfig("org/megatome/frame2/front/config/emptyTags-config1_1.xml");
	}

	public void testNegativeDTD() {
		parseNegativeConfig("org/megatome/frame2/front/config/invalidDTD-config1_1.xml");
	}

	public void testNegativeDTDMultiPlugins() {
		parseNegativeConfig("org/megatome/frame2/front/config/invalidDTDMultiPlugins-config1_1.xml");
	}

	public void testSinglePlugin() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Single-1_1.xml");
	}

	public void testNegativeSinglePlugin() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-1_1.xml");
	}

	public void testNegativeNameSinglePlugin() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Name-1_1.xml");
	}

	public void testNegativeEmptySinglePlugin() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Empty-1_1.xml");
	}

	public void testNegativeSingleTypePlugin() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-PluginType1_1.xml");
	}

	public void testSinglePluginParams() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Single-Param-1_1.xml");
	}

	public void testNegativeSinglePluginParams() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Param1_1.xml");
	}

	public void testNegativeSinglePluginParamsName() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Param-Name1_1.xml");
	}

	public void testNegativeSinglePluginParamsValue() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Param-Value1_1.xml");
	}

	public void testNegativeSinglePluginParamsEmpty() {
		parseNegativeConfig("org/megatome/frame2/front/config/pluginTag-Single-Negative-Param-Empty1_1.xml");
	}

	public void testMultiplePlugin() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Multiple-1_1.xml");
	}

	public void testMultiplePluginParams() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Mult-Param-1_1.xml");
	}

	public void testMultiplePluginDupParams() {
		parseConfig("org/megatome/frame2/front/config/pluginTag-Dup-Negative-Param-1_1.xml");
	}

	public void testEmptyRP() {
		parseConfig("org/megatome/frame2/front/config/RPTag-empty-1_1.xml");
	}

	public void testHttpSingleRP() {
		parseConfig("org/megatome/frame2/front/config/RPTag-http-single-1_1.xml");
	}

	public void testSoapSingleRP() {
		parseConfig("org/megatome/frame2/front/config/RPTag-soap-single-1_1.xml");
	}

	public void testHttpSoapSingleRP() {
		parseConfig("org/megatome/frame2/front/config/RPTag-http-soap-single-1_1.xml");
	}

	public void testNegativeSoapHttpSingleRP() {
		parseNegativeConfig("org/megatome/frame2/front/config/RPTag-soap-http-single-Negative1_1.xml");
	}

	public void testNegative2soap1httpRP() {
		parseNegativeConfig("org/megatome/frame2/front/config/RPTag-2soap-http-single-Negative1_1.xml");
	}

	private void parseConfig(String configFile) {
		try {
			InputStream is =
				getClass().getClassLoader().getResourceAsStream(configFile);
			_documentBuilder.parse(is);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			fail("Unexpected IOException");
		} catch (SAXParseException spe) {
			spe.printStackTrace();
			fail("Unexpected SAXParseException");
		} catch (SAXException se) {
			se.printStackTrace();
			fail("Unexpected SAXException");
		}
	}

	private void parseNegativeConfig(String configFile) {
		try {
			InputStream is =
				getClass().getClassLoader().getResourceAsStream(configFile);
			_documentBuilder.parse(is);
		} catch (IOException ioe) {
			fail("Unexpected IO Exception");
		} catch (SAXParseException spe) {
			return;
		} catch (SAXException se) {
			return;
		}

		fail("Expected SAXException did not occur");
	}
}
