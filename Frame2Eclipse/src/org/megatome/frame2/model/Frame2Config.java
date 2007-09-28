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
package org.megatome.frame2.model;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.megatome.frame2.Frame2Plugin;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Frame2Config extends Frame2DomainObject {

	private GlobalForwards globalForwards = new GlobalForwards();

	private Frame2Events events = new Frame2Events();
	
	private SchemaMappings schemaMappings = new SchemaMappings();

	private EventMappings eventMappings = new EventMappings();

	private EventHandlers eventHandlers = new EventHandlers();

	private Exceptions exceptions = new Exceptions();

	private Plugins plugins = new Plugins();

	private RequestProcessors requestProcessors = new RequestProcessors();

	public Frame2Config() {
		// Default
	}
	
	@Override
	public Frame2Config copy() {
		return new Frame2Config(this);
	}

	// Deep copy
	private Frame2Config(final Frame2Config source) {
		this.globalForwards = source.globalForwards.copy();
		this.events = source.events.copy();
		this.schemaMappings = source.schemaMappings.copy();
		this.eventMappings = source.eventMappings.copy();
		this.eventHandlers = source.eventHandlers.copy();
		this.exceptions = source.exceptions.copy();
		this.plugins = source.plugins.copy();
		this.requestProcessors = source.requestProcessors.copy();
	}

	// This attribute is optional
	public void setGlobalForwards(final GlobalForwards value) {
		this.globalForwards = value;
	}

	public GlobalForwards getGlobalForwards() {
		return this.globalForwards;
	}

	// This attribute is optional
	public void setFrame2Events(final Frame2Events value) {
		this.events = value;
	}

	public Frame2Events getFrame2Events() {
		return this.events;
	}
	
	public void setSchemaMappings(final SchemaMappings value) {
		this.schemaMappings = value;
	}
	
	public SchemaMappings getSchemaMappings() {
		return this.schemaMappings;
	}

	// This attribute is optional
	public void setEventMappings(final EventMappings value) {
		this.eventMappings = value;
	}

	public EventMappings getEventMappings() {
		return this.eventMappings;
	}

	// This attribute is optional
	public void setEventHandlers(final EventHandlers value) {
		this.eventHandlers = value;
	}

	public EventHandlers getEventHandlers() {
		return this.eventHandlers;
	}

	// This attribute is optional
	public void setExceptions(final Exceptions value) {
		this.exceptions = value;
	}

	public Exceptions getExceptions() {
		return this.exceptions;
	}

	// This attribute is optional
	public void setPlugins(final Plugins value) {
		this.plugins = value;
	}

	public Plugins getPlugins() {
		return this.plugins;
	}

	// This attribute is optional
	public void setRequestProcessors(final RequestProcessors value) {
		this.requestProcessors = value;
	}

	public RequestProcessors getRequestProcessors() {
		return this.requestProcessors;
	}

	public void write(final OutputStream out) throws IOException {
		write(out, null);
	}

	public void write(final OutputStream out, final String encoding)
			throws IOException {
		Writer w;
		String outEncoding = encoding;
		if (outEncoding == null) {
			outEncoding = Frame2Plugin.getString("Frame2Model.utf8"); //$NON-NLS-1$
		}
		w = new BufferedWriter(new OutputStreamWriter(out, outEncoding));
		write(w, encoding);
		w.flush();
	}

	// Print this Java Bean to @param out including an XML header.
	// @param encoding is the encoding style that @param out was opened with.
	public void write(final Writer out, final String encoding)
			throws IOException {
		out.write(Frame2Plugin.getString("Frame2Model.xmlTagStart")); //$NON-NLS-1$
		if (encoding != null) {
			out
					.write(Frame2Plugin
							.getString("Frame2Model.encodingAttribute") + encoding + Frame2Plugin.getString("Frame2Model.attributeValueEnd")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		out.write(Frame2Plugin.getString("Frame2Model.xmlTagEnd")); //$NON-NLS-1$

		// We need to manually output the DTD - sigh
		out.write(Frame2Plugin
				.getString("Frame2Model.frame2DocTypeStart")); //$NON-NLS-1$
		out.write(Frame2Plugin
				.getString("Frame2Model.frame2DocTypeSystemID")); //$NON-NLS-1$
		out.write(Frame2Plugin
				.getString("Frame2Model.frame2DocTypePublicID")); //$NON-NLS-1$

		writeNode(out, Frame2Plugin
				.getString("Frame2Model.frame2-config"), ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void writeNode(final Writer out, final String nodeName,
			final String indent) throws IOException {
		out.write(indent);
		out.write(Frame2Plugin.getString("Frame2Model.tagStart")); //$NON-NLS-1$
		out.write(nodeName);
		out.write(Frame2Plugin.getString("Frame2Model.tagFinish")); //$NON-NLS-1$
		final String nextIndent = indent
				+ Frame2Plugin.getString("Frame2Model.indentTabValue"); //$NON-NLS-1$
		//if (!this.globalForwards.isEmpty()) {
			this.globalForwards
					.writeNode(
							out,
							Frame2Plugin
									.getString("Frame2Model.global-forwards"), nextIndent); //$NON-NLS-1$
		//}
		//if (!this.events.isEmpty()) {
			this.events.writeNode(out, Frame2Plugin
					.getString("Frame2Model.events"), nextIndent); //$NON-NLS-1$
		//}
		//if (!this.schemaMappings.isEmpty()) {
			this.schemaMappings.writeNode(out, Frame2Plugin.getString("Frame2Model.schema-mappings"), nextIndent); //$NON-NLS-1$
		//}
		//if (!this.eventMappings.isEmpty()) {
			this.eventMappings
					.writeNode(
							out,
							Frame2Plugin
									.getString("Frame2Model.event-mappings"), nextIndent); //$NON-NLS-1$
		//}
		//if (!this.eventHandlers.isEmpty()) {
			this.eventHandlers
					.writeNode(
							out,
							Frame2Plugin
									.getString("Frame2Model.event-handlers"), nextIndent); //$NON-NLS-1$
		//}
		//if (!this.exceptions.isEmpty()) {
			this.exceptions.writeNode(out, Frame2Plugin
					.getString("Frame2Model.exceptions"), nextIndent); //$NON-NLS-1$
		//}
		//if (!this.plugins.isEmpty()) {
			this.plugins.writeNode(out, Frame2Plugin
					.getString("Frame2Model.plugins"), nextIndent); //$NON-NLS-1$
		//}
		//if (!this.requestProcessors.isEmpty()) {
			this.requestProcessors
					.writeNode(
							out,
							Frame2Plugin
									.getString("Frame2Model.request-processors"), nextIndent); //$NON-NLS-1$
		//}
		out.write(indent);
		out
				.write(Frame2Plugin
						.getString("Frame2Model.endTagStart") + nodeName + Frame2Plugin.getString("Frame2Model.tagFinish")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static Frame2Config read(final InputStream in)
			throws ParserConfigurationException, SAXException, IOException {
		return read(new InputSource(in), false, null, null);
	}

	// Warning: in readNoEntityResolver character and entity references will
	// not be read from any DTD in the XML source.
	// However, this way is faster since no DTDs are looked up
	// (possibly skipping network access) or parsed.
	public static Frame2Config readNoEntityResolver(final InputStream in)
			throws ParserConfigurationException, SAXException, IOException {
		return read(new InputSource(in), false, new EntityResolver() {

			public InputSource resolveEntity(@SuppressWarnings("unused")
			final String publicId, @SuppressWarnings("unused")
			final String systemId) {
				final ByteArrayInputStream bin = new ByteArrayInputStream(
						new byte[0]);
				return new InputSource(bin);
			}
		}, null);
	}

	public static Frame2Config read(final InputSource in,
			final boolean validate, final EntityResolver er,
			final ErrorHandler eh) throws ParserConfigurationException,
			SAXException, IOException {
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(validate);
		final DocumentBuilder db = dbf.newDocumentBuilder();
		if (er != null) {
			db.setEntityResolver(er);
		}
		if (eh != null) {
			db.setErrorHandler(eh);
		}
		final Document doc = db.parse(in);
		return read(doc);
	}

	public static Frame2Config read(final Document document) {
		final Frame2Config aFrame2Config = new Frame2Config();
		aFrame2Config.readNode(document.getDocumentElement());
		return aFrame2Config;
	}

	public void readNode(final Node node) {
		final NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode
					.getNodeName().intern()
					: childNode.getLocalName().intern());
			/*
			 * String childNodeValue = ""; //$NON-NLS-1$ if
			 * (childNode.getFirstChild() != null) { childNodeValue =
			 * childNode.getFirstChild().getNodeValue(); }
			 */
			if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.global-forwards"))) { //$NON-NLS-1$
				this.globalForwards = new GlobalForwards();
				this.globalForwards.readNode(childNode);
			} else if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.events"))) { //$NON-NLS-1$
				this.events = new Frame2Events();
				this.events.readNode(childNode);
			} else if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.schema-mappings"))) { //$NON-NLS-1$
				this.schemaMappings = new SchemaMappings();
				this.schemaMappings.readNode(childNode);
			} else if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.event-mappings"))) { //$NON-NLS-1$
				this.eventMappings = new EventMappings();
				this.eventMappings.readNode(childNode);
			} else if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.event-handlers"))) { //$NON-NLS-1$
				this.eventHandlers = new EventHandlers();
				this.eventHandlers.readNode(childNode);
			} else if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.exceptions"))) { //$NON-NLS-1$
				this.exceptions = new Exceptions();
				this.exceptions.readNode(childNode);
			} else if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.plugins"))) { //$NON-NLS-1$
				this.plugins = new Plugins();
				this.plugins.readNode(childNode);
			} else if (childNodeName.equals(Frame2Plugin
					.getString("Frame2Model.request-processors"))) { //$NON-NLS-1$
				this.requestProcessors = new RequestProcessors();
				this.requestProcessors.readNode(childNode);
			} else {
				// Found extra unrecognized childNode
			}
		}
	}

	// Takes some text to be printed into an XML stream and escapes any
	// characters that might make it invalid XML (like '<').
	public static void writeXML(final Writer out, final String msg)
			throws IOException {
		writeXML(out, msg, true);
	}

	public static void writeXML(final Writer out, final String msg,
			final boolean attribute) throws IOException {
		if (msg == null) {
			return;
		}
		final int msgLength = msg.length();
		for (int i = 0; i < msgLength; ++i) {
			final char c = msg.charAt(i);
			writeXML(out, c, attribute);
		}
	}

	public static void writeXML(final Writer out, final char msg,
			final boolean attribute) throws IOException {
		if (msg == '&') {
			out.write(Frame2Plugin
					.getString("Frame2Model.ampersandEntity")); //$NON-NLS-1$
		} else if (msg == '<') {
			out.write(Frame2Plugin.getString("Frame2Model.ltEntity")); //$NON-NLS-1$
		} else if (msg == '>') {
			out.write(Frame2Plugin.getString("Frame2Model.gtEntity")); //$NON-NLS-1$
		} else if (attribute && msg == '"') {
			out
					.write(Frame2Plugin
							.getString("Frame2Model.quoteEntity")); //$NON-NLS-1$
		} else if (attribute && msg == '\'') {
			out.write(Frame2Plugin
					.getString("Frame2Model.apostropheEntity")); //$NON-NLS-1$
		} else if (attribute && msg == '\n') {
			out.write(Frame2Plugin
					.getString("Frame2Model.newlineEntity")); //$NON-NLS-1$
		} else if (attribute && msg == '\t') {
			out.write(Frame2Plugin.getString("Frame2Model.tabEntity")); //$NON-NLS-1$
		} else {
			out.write(msg);
		}
	}

	public static class ValidateException extends Exception {

		private static final long serialVersionUID = -5625370971154283802L;

		private final Object failedBean;

		private final String failedPropertyName;

		public ValidateException(final String msg,
				final String failedPropertyName, final Object failedBean) {
			super(msg);
			this.failedBean = failedBean;
			this.failedPropertyName = failedPropertyName;
		}

		public String getFailedPropertyName() {
			return this.failedPropertyName;
		}

		public Object getFailedBean() {
			return this.failedBean;
		}
	}

	public void validate() throws Frame2Config.ValidateException {
		// boolean restrictionFailure = false;
		// Validating property globalForwards
		if (getGlobalForwards() != null) {
			getGlobalForwards().validate();
		}
		// Validating property Frame2Events
		if (getFrame2Events() != null) {
			getFrame2Events().validate();
		}
		
		if (getSchemaMappings() != null) {
			getSchemaMappings().validate();
		}
		// Validating property eventMappings
		if (getEventMappings() != null) {
			getEventMappings().validate();
		}
		// Validating property eventHandlers
		if (getEventHandlers() != null) {
			getEventHandlers().validate();
		}
		// Validating property exceptions
		if (getExceptions() != null) {
			getExceptions().validate();
		}
		// Validating property plugins
		if (getPlugins() != null) {
			getPlugins().validate();
		}
		// Validating property requestProcessors
		if (getRequestProcessors() != null) {
			getRequestProcessors().validate();
		}
	}

	/*public void changePropertyByName(final String name, final Object value) {
		if (name == null) {
			return;
		}
		final String intName = name.intern();
		if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.globalForwards"))) { //$NON-NLS-1$
			setGlobalForwards((GlobalForwards) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.Frame2Events"))) { //$NON-NLS-1$
			setFrame2Events((Frame2Events) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.schema-mappings"))) { //$NON-NLS-1$
			setSchemaMappings((SchemaMappings) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.eventMappings"))) { //$NON-NLS-1$
			setEventMappings((EventMappings) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.eventHandlers"))) { //$NON-NLS-1$
			setEventHandlers((EventHandlers) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.exceptions"))) { //$NON-NLS-1$
			setExceptions((Exceptions) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.plugins"))) { //$NON-NLS-1$
			setPlugins((Plugins) value);
		} else if (intName.equals(Frame2Plugin
				.getResourceString("Frame2Model.requestProcessors"))) { //$NON-NLS-1$
			setRequestProcessors((RequestProcessors) value);
		} else {
			throw new IllegalArgumentException(
					intName
							+ Frame2Plugin
									.getResourceString("Frame2Model.invalidFrame2ConfigProperty")); //$NON-NLS-1$
		}
	}*/

	/*public Object fetchPropertyByName(final String name) {
		if (name.equals(Frame2Plugin
				.getResourceString("Frame2Model.globalForwards"))) { //$NON-NLS-1$
			return getGlobalForwards();
		}
		if (name.equals(Frame2Plugin
				.getResourceString("Frame2Model.Frame2Events"))) { //$NON-NLS-1$
			return getFrame2Events();
		}
		if (name.equals(Frame2Plugin
				.getResourceString("Frame2Model.schema-mappings"))) { //$NON-NLS-1$
			return getSchemaMappings();
		}
		if (name.equals(Frame2Plugin
				.getResourceString("Frame2Model.eventMappings"))) { //$NON-NLS-1$
			return getEventMappings();
		}
		if (name.equals(Frame2Plugin
				.getResourceString("Frame2Model.eventHandlers"))) { //$NON-NLS-1$
			return getEventHandlers();
		}
		if (name.equals(Frame2Plugin
				.getResourceString("Frame2Model.exceptions"))) { //$NON-NLS-1$
			return getExceptions();
		}
		if (name.equals(Frame2Plugin.getResourceString("Frame2Model.plugins"))) { //$NON-NLS-1$
			return getPlugins();
		}
		if (name.equals(Frame2Plugin
				.getResourceString("Frame2Model.requestProcessors"))) { //$NON-NLS-1$
			return getRequestProcessors();
		}
		throw new IllegalArgumentException(
				name
						+ Frame2Plugin
								.getResourceString("Frame2Model.invalidFrame2ConfigProperty")); //$NON-NLS-1$
	}*/

	// Put all child beans into the beans list.
	/*public void childBeans(final boolean recursive, final List<Object> beans) {
		if (this.globalForwards != null) {
			if (recursive) {
				this.globalForwards.childBeans(true, beans);
			}
			beans.add(this.globalForwards);
		}
		if (this.events != null) {
			if (recursive) {
				this.events.childBeans(true, beans);
			}
			beans.add(this.events);
		}
		if (this.schemaMappings != null) {
			if (recursive) {
				this.schemaMappings.childBeans(true, beans);
			}
			beans.add(this.schemaMappings);
		}
		if (this.eventMappings != null) {
			if (recursive) {
				this.eventMappings.childBeans(true, beans);
			}
			beans.add(this.eventMappings);
		}
		if (this.eventHandlers != null) {
			if (recursive) {
				this.eventHandlers.childBeans(true, beans);
			}
			beans.add(this.eventHandlers);
		}
		if (this.exceptions != null) {
			if (recursive) {
				this.exceptions.childBeans(true, beans);
			}
			beans.add(this.exceptions);
		}
		if (this.plugins != null) {
			if (recursive) {
				this.plugins.childBeans(true, beans);
			}
			beans.add(this.plugins);
		}
		if (this.requestProcessors != null) {
			if (recursive) {
				this.requestProcessors.childBeans(true, beans);
			}
			beans.add(this.requestProcessors);
		}
	}*/

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Frame2Config)) {
			return false;
		}
		final Frame2Config inst = (Frame2Config) o;
		if (!(this.globalForwards == null ? inst.globalForwards == null
				: this.globalForwards.equals(inst.globalForwards))) {
			return false;
		}
		if (!(this.events == null ? inst.events == null : this.events
				.equals(inst.events))) {
			return false;
		}
		if (!(this.eventMappings == null ? inst.eventMappings == null
				: this.eventMappings.equals(inst.eventMappings))) {
			return false;
		}
		if (!(this.eventHandlers == null ? inst.eventHandlers == null
				: this.eventHandlers.equals(inst.eventHandlers))) {
			return false;
		}
		if (!(this.exceptions == null ? inst.exceptions == null
				: this.exceptions.equals(inst.exceptions))) {
			return false;
		}
		if (!(this.plugins == null ? inst.plugins == null : this.plugins
				.equals(inst.plugins))) {
			return false;
		}
		if (!(this.requestProcessors == null ? inst.requestProcessors == null
				: this.requestProcessors.equals(inst.requestProcessors))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37
				* result
				+ (this.globalForwards == null ? 0 : this.globalForwards
						.hashCode());
		result = 37 * result
				+ (this.events == null ? 0 : this.events.hashCode());
		result = 37
				* result
				+ (this.eventMappings == null ? 0 : this.eventMappings
						.hashCode());
		result = 37
				* result
				+ (this.eventHandlers == null ? 0 : this.eventHandlers
						.hashCode());
		result = 37 * result
				+ (this.exceptions == null ? 0 : this.exceptions.hashCode());
		result = 37 * result
				+ (this.plugins == null ? 0 : this.plugins.hashCode());
		result = 37
				* result
				+ (this.requestProcessors == null ? 0 : this.requestProcessors
						.hashCode());
		return result;
	}

}