/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
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

public class Frame2Config {
	private GlobalForwards _GlobalForwards;
	private Frame2Events _Frame2Events;
	private EventMappings _EventMappings;
	private EventHandlers _EventHandlers;
	private Exceptions _Exceptions;
	private Plugins _Plugins;
	private RequestProcessors _RequestProcessors;

	public Frame2Config() {
	}

	// Deep copy
	public Frame2Config(org.megatome.frame2.model.Frame2Config source) {
		_GlobalForwards = new org.megatome.frame2.model.GlobalForwards(source._GlobalForwards);
		_Frame2Events = new org.megatome.frame2.model.Frame2Events(source._Frame2Events);
		_EventMappings = new org.megatome.frame2.model.EventMappings(source._EventMappings);
		_EventHandlers = new org.megatome.frame2.model.EventHandlers(source._EventHandlers);
		_Exceptions = new org.megatome.frame2.model.Exceptions(source._Exceptions);
		_Plugins = new org.megatome.frame2.model.Plugins(source._Plugins);
		_RequestProcessors = new org.megatome.frame2.model.RequestProcessors(source._RequestProcessors);
	}

	// This attribute is optional
	public void setGlobalForwards(org.megatome.frame2.model.GlobalForwards value) {
		_GlobalForwards = value;
	}

	public org.megatome.frame2.model.GlobalForwards getGlobalForwards() {
		return _GlobalForwards;
	}

	// This attribute is optional
	public void setFrame2Events(org.megatome.frame2.model.Frame2Events value) {
		_Frame2Events = value;
	}

	public org.megatome.frame2.model.Frame2Events getFrame2Events() {
		return _Frame2Events;
	}

	// This attribute is optional
	public void setEventMappings(org.megatome.frame2.model.EventMappings value) {
		_EventMappings = value;
	}

	public org.megatome.frame2.model.EventMappings getEventMappings() {
		return _EventMappings;
	}

	// This attribute is optional
	public void setEventHandlers(org.megatome.frame2.model.EventHandlers value) {
		_EventHandlers = value;
	}

	public org.megatome.frame2.model.EventHandlers getEventHandlers() {
		return _EventHandlers;
	}

	// This attribute is optional
	public void setExceptions(org.megatome.frame2.model.Exceptions value) {
		_Exceptions = value;
	}

	public org.megatome.frame2.model.Exceptions getExceptions() {
		return _Exceptions;
	}

	// This attribute is optional
	public void setPlugins(org.megatome.frame2.model.Plugins value) {
		_Plugins = value;
	}

	public org.megatome.frame2.model.Plugins getPlugins() {
		return _Plugins;
	}

	// This attribute is optional
	public void setRequestProcessors(org.megatome.frame2.model.RequestProcessors value) {
		_RequestProcessors = value;
	}

	public org.megatome.frame2.model.RequestProcessors getRequestProcessors() {
		return _RequestProcessors;
	}

	public void write(java.io.OutputStream out) throws java.io.IOException {
		write(out, null);
	}

	public void write(java.io.OutputStream out, String encoding) throws java.io.IOException {
		java.io.Writer w;
		if (encoding == null) {
			encoding = "UTF-8";	// NOI18N
		}
		w = new java.io.BufferedWriter(new java.io.OutputStreamWriter(out, encoding));
		write(w, encoding);
		w.flush();
	}

	// Print this Java Bean to @param out including an XML header.
	// @param encoding is the encoding style that @param out was opened with.
	public void write(java.io.Writer out, String encoding) throws java.io.IOException {
		out.write("<?xml version='1.0'");	// NOI18N
		if (encoding != null)
			out.write(" encoding='"+encoding+"'");	// NOI18N
		out.write(" ?>\n");	// NOI18N
        
        // We need to manually output the DTD - sigh
        out.write("<!DOCTYPE frame2-config PUBLIC\n");
        out.write("   \"-//Megatome Technologies//DTD Frame2 Configuration 1.0//EN\"\n");
        out.write("   \"http://frame2.sourceforge.net/dtds/frame2-config_1_0.dtd\">\n");
        
		writeNode(out, "frame2-config", "");	// NOI18N
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		out.write(">\n");
		String nextIndent = indent + "	";
		if (_GlobalForwards != null) {
			_GlobalForwards.writeNode(out, "global-forwards", nextIndent);
		}
		if (_Frame2Events != null) {
			_Frame2Events.writeNode(out, "events", nextIndent);
		}
		if (_EventMappings != null) {
			_EventMappings.writeNode(out, "event-mappings", nextIndent);
		}
		if (_EventHandlers != null) {
			_EventHandlers.writeNode(out, "event-handlers", nextIndent);
		}
		if (_Exceptions != null) {
			_Exceptions.writeNode(out, "exceptions", nextIndent);
		}
		if (_Plugins != null) {
			_Plugins.writeNode(out, "plugins", nextIndent);
		}
		if (_RequestProcessors != null) {
			_RequestProcessors.writeNode(out, "request-processors", nextIndent);
		}
		out.write(indent);
		out.write("</"+nodeName+">\n");
	}

	public static Frame2Config read(java.io.InputStream in) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
		return read(new org.xml.sax.InputSource(in), false, null, null);
	}

	// Warning: in readNoEntityResolver character and entity references will
	// not be read from any DTD in the XML source.
	// However, this way is faster since no DTDs are looked up
	// (possibly skipping network access) or parsed.
	public static Frame2Config readNoEntityResolver(java.io.InputStream in) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
		return read(new org.xml.sax.InputSource(in), false,
			new org.xml.sax.EntityResolver() {
			public org.xml.sax.InputSource resolveEntity(String publicId, String systemId) {
				java.io.ByteArrayInputStream bin = new java.io.ByteArrayInputStream(new byte[0]);
				return new org.xml.sax.InputSource(bin);
			}
		}
			, null);
	}

	public static Frame2Config read(org.xml.sax.InputSource in, boolean validate, org.xml.sax.EntityResolver er, org.xml.sax.ErrorHandler eh) throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, java.io.IOException {
		javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		dbf.setValidating(validate);
		javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
		if (er != null)	db.setEntityResolver(er);
		if (eh != null)	db.setErrorHandler(eh);
		org.w3c.dom.Document doc = db.parse(in);
		return read(doc);
	}

	public static Frame2Config read(org.w3c.dom.Document document) {
	   Frame2Config aFrame2Config = new Frame2Config();
		aFrame2Config.readNode(document.getDocumentElement());
		return aFrame2Config;
	}

	public void readNode(org.w3c.dom.Node node) {
		org.w3c.dom.NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if (childNodeName == "global-forwards") {
				_GlobalForwards = new org.megatome.frame2.model.GlobalForwards();
				_GlobalForwards.readNode(childNode);
			}
			else if (childNodeName == "events") {
				_Frame2Events = new org.megatome.frame2.model.Frame2Events();
				_Frame2Events.readNode(childNode);
			}
			else if (childNodeName == "event-mappings") {
				_EventMappings = new org.megatome.frame2.model.EventMappings();
				_EventMappings.readNode(childNode);
			}
			else if (childNodeName == "event-handlers") {
				_EventHandlers = new org.megatome.frame2.model.EventHandlers();
				_EventHandlers.readNode(childNode);
			}
			else if (childNodeName == "exceptions") {
				_Exceptions = new org.megatome.frame2.model.Exceptions();
				_Exceptions.readNode(childNode);
			}
			else if (childNodeName == "plugins") {
				_Plugins = new org.megatome.frame2.model.Plugins();
				_Plugins.readNode(childNode);
			}
			else if (childNodeName == "request-processors") {
				_RequestProcessors = new org.megatome.frame2.model.RequestProcessors();
				_RequestProcessors.readNode(childNode);
			}
			else {
				// Found extra unrecognized childNode
			}
		}
	}

	// Takes some text to be printed into an XML stream and escapes any
	// characters that might make it invalid XML (like '<').
	public static void writeXML(java.io.Writer out, String msg) throws java.io.IOException {
		writeXML(out, msg, true);
	}

	public static void writeXML(java.io.Writer out, String msg, boolean attribute) throws java.io.IOException {
		if (msg == null)
			return;
		int msgLength = msg.length();
		for (int i = 0; i < msgLength; ++i) {
			char c = msg.charAt(i);
			writeXML(out, c, attribute);
		}
	}

	public static void writeXML(java.io.Writer out, char msg, boolean attribute) throws java.io.IOException {
		if (msg == '&')
			out.write("&amp;");
		else if (msg == '<')
			out.write("&lt;");
		else if (msg == '>')
			out.write("&gt;");
		else if (attribute && msg == '"')
			out.write("&quot;");
		else if (attribute && msg == '\'')
			out.write("&apos;");
		else if (attribute && msg == '\n')
			out.write("&#xA;");
		else if (attribute && msg == '\t')
			out.write("&#x9;");
		else
			out.write(msg);
	}

	public static class ValidateException extends Exception {
		private java.lang.Object failedBean;
		private String failedPropertyName;
		public ValidateException(String msg, String failedPropertyName, java.lang.Object failedBean) {
			super(msg);
			this.failedBean = failedBean;
			this.failedPropertyName = failedPropertyName;
		}
		public String getFailedPropertyName() {return failedPropertyName;}
		public java.lang.Object getFailedBean() {return failedBean;}
	}

	public void validate() throws org.megatome.frame2.model.Frame2Config.ValidateException {
		boolean restrictionFailure = false;
		// Validating property globalForwards
		if (getGlobalForwards() != null) {
			getGlobalForwards().validate();
		}
		// Validating property Frame2Events
		if (getFrame2Events() != null) {
			getFrame2Events().validate();
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

	public void changePropertyByName(String name, Object value) {
		if (name == null) return;
		name = name.intern();
		if (name == "globalForwards")
			setGlobalForwards((GlobalForwards)value);
		else if (name == "Frame2Events")
			setFrame2Events((Frame2Events)value);
		else if (name == "eventMappings")
			setEventMappings((EventMappings)value);
		else if (name == "eventHandlers")
			setEventHandlers((EventHandlers)value);
		else if (name == "exceptions")
			setExceptions((Exceptions)value);
		else if (name == "plugins")
			setPlugins((Plugins)value);
		else if (name == "requestProcessors")
			setRequestProcessors((RequestProcessors)value);
		else
			throw new IllegalArgumentException(name+" is not a valid property name for Frame2Config");
	}

	public Object fetchPropertyByName(String name) {
		if (name == "globalForwards")
			return getGlobalForwards();
		if (name == "Frame2Events")
			return getFrame2Events();
		if (name == "eventMappings")
			return getEventMappings();
		if (name == "eventHandlers")
			return getEventHandlers();
		if (name == "exceptions")
			return getExceptions();
		if (name == "plugins")
			return getPlugins();
		if (name == "requestProcessors")
			return getRequestProcessors();
		throw new IllegalArgumentException(name+" is not a valid property name for Frame2Config");
	}

	// Return an array of all of the properties that are beans and are set.
	public java.lang.Object[] childBeans(boolean recursive) {
		java.util.List children = new java.util.LinkedList();
		childBeans(recursive, children);
		java.lang.Object[] result = new java.lang.Object[children.size()];
		return (java.lang.Object[]) children.toArray(result);
	}

	// Put all child beans into the beans list.
	public void childBeans(boolean recursive, java.util.List beans) {
		if (_GlobalForwards != null) {
			if (recursive) {
				_GlobalForwards.childBeans(true, beans);
			}
			beans.add(_GlobalForwards);
		}
		if (_Frame2Events != null) {
			if (recursive) {
				_Frame2Events.childBeans(true, beans);
			}
			beans.add(_Frame2Events);
		}
		if (_EventMappings != null) {
			if (recursive) {
				_EventMappings.childBeans(true, beans);
			}
			beans.add(_EventMappings);
		}
		if (_EventHandlers != null) {
			if (recursive) {
				_EventHandlers.childBeans(true, beans);
			}
			beans.add(_EventHandlers);
		}
		if (_Exceptions != null) {
			if (recursive) {
				_Exceptions.childBeans(true, beans);
			}
			beans.add(_Exceptions);
		}
		if (_Plugins != null) {
			if (recursive) {
				_Plugins.childBeans(true, beans);
			}
			beans.add(_Plugins);
		}
		if (_RequestProcessors != null) {
			if (recursive) {
				_RequestProcessors.childBeans(true, beans);
			}
			beans.add(_RequestProcessors);
		}
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof org.megatome.frame2.model.Frame2Config))
			return false;
		org.megatome.frame2.model.Frame2Config inst = (org.megatome.frame2.model.Frame2Config) o;
		if (!(_GlobalForwards == null ? inst._GlobalForwards == null : _GlobalForwards.equals(inst._GlobalForwards)))
			return false;
		if (!(_Frame2Events == null ? inst._Frame2Events == null : _Frame2Events.equals(inst._Frame2Events)))
			return false;
		if (!(_EventMappings == null ? inst._EventMappings == null : _EventMappings.equals(inst._EventMappings)))
			return false;
		if (!(_EventHandlers == null ? inst._EventHandlers == null : _EventHandlers.equals(inst._EventHandlers)))
			return false;
		if (!(_Exceptions == null ? inst._Exceptions == null : _Exceptions.equals(inst._Exceptions)))
			return false;
		if (!(_Plugins == null ? inst._Plugins == null : _Plugins.equals(inst._Plugins)))
			return false;
		if (!(_RequestProcessors == null ? inst._RequestProcessors == null : _RequestProcessors.equals(inst._RequestProcessors)))
			return false;
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37*result + (_GlobalForwards == null ? 0 : _GlobalForwards.hashCode());
		result = 37*result + (_Frame2Events == null ? 0 : _Frame2Events.hashCode());
		result = 37*result + (_EventMappings == null ? 0 : _EventMappings.hashCode());
		result = 37*result + (_EventHandlers == null ? 0 : _EventHandlers.hashCode());
		result = 37*result + (_Exceptions == null ? 0 : _Exceptions.hashCode());
		result = 37*result + (_Plugins == null ? 0 : _Plugins.hashCode());
		result = 37*result + (_RequestProcessors == null ? 0 : _RequestProcessors.hashCode());
		return result;
	}

}