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
package org.megatome.frame2.util.dom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TestDOMStreamConverter {
	private Node node;

	private DocumentBuilder builder;

	@Before
	public void setUp() throws Exception {
		this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		this.node = toNode(ClassLoader
				.getSystemResourceAsStream("org/megatome/frame2/util/dom/dom1.xml")); //$NON-NLS-1$
	}

	@Test
	public void testToInputStream() throws Exception {
		InputStream is = DOMStreamConverter.toInputStream(this.node);

		assertNotNull(is);

		Node n = toNode(is);

		assertNotNull(n);

		assertEquals("purchaseOrder", n.getFirstChild().getNodeName()); //$NON-NLS-1$
	}

	@Test
	public void testToInputStream_Null() throws Exception {
		InputStream is = DOMStreamConverter.toInputStream(null);

		assertNotNull(is);

		try {
			toNode(is);
			fail();
		} catch (Exception expected) {
			// expected
		}
	}

	@Test
	public void testToInputStream_Empty() throws Exception {
		assertNotNull(DOMStreamConverter.toInputStream(this.builder.newDocument()));
	}

	@Test
	public void testToOutputStream() throws Exception {
		OutputStream os = DOMStreamConverter.toOutputStream(this.node);

		assertNotNull(os);

		assertTrue(os.toString().indexOf("purchaseOrder") > 0); //$NON-NLS-1$
	}

	@Test
	public void testToOutputStream_Empty() throws Exception {
		assertNotNull(DOMStreamConverter.toOutputStream(this.builder.newDocument()));
	}

	@Test
	public void testToOutputStream_Null() throws Exception {
		OutputStream os = DOMStreamConverter.toOutputStream(null);
		assertNotNull(os);
	}

	@Test
	public void testStringToNode() throws Exception {
		final String xml = "<tt:test xmlns:tt=\"http://test-uri/\">Test Value</tt:test>"; //$NON-NLS-1$

		Element element = (Element) DOMStreamConverter.fromString(xml, true);

		assertNotNull(element);

		DOMStreamConverter.toOutputStream(element).toString();

		assertEquals("tt:test", element.getNodeName()); //$NON-NLS-1$
		assertEquals("test", element.getLocalName()); //$NON-NLS-1$
		assertEquals("http://test-uri/", element.getNamespaceURI()); //$NON-NLS-1$
		assertEquals("tt", element.getPrefix()); //$NON-NLS-1$
		assertEquals("Test Value", element.getFirstChild().getNodeValue()); //$NON-NLS-1$
	}

	@Test
	public void testStringToNode_NoNamespace() throws Exception {
		final String xml = "<tt:test xmlns:tt=\"http://test-uri/\">Test Value</tt:test>"; //$NON-NLS-1$

		Element element = (Element) DOMStreamConverter.fromString(xml, false);

		assertNotNull(element);

		DOMStreamConverter.toOutputStream(element).toString();

		assertEquals("tt:test", element.getNodeName()); //$NON-NLS-1$
		assertNull(element.getLocalName());
		assertNull(element.getNamespaceURI());
		assertNull(element.getPrefix());
		assertEquals("Test Value", element.getFirstChild().getNodeValue()); //$NON-NLS-1$
	}

	private Node toNode(InputStream istream) throws Exception {
		return this.builder.parse(istream);
	}

	@Test
	public void testEncodeString() throws Exception {
		final String data1 = "a&b<c>d'e\"f"; //$NON-NLS-1$
		final String data2 = ""; //$NON-NLS-1$

		assertEquals("a&amp;b&lt;c&gt;d&apos;e&quot;f", DOMStreamConverter //$NON-NLS-1$
				.encode(data1));
		assertEquals("", DOMStreamConverter.encode(data2)); //$NON-NLS-1$
		assertNull(DOMStreamConverter.encode(null));
	}
}
