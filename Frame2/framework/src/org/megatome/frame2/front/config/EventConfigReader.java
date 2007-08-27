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

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.validation.Schema;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.util.sax.Frame2SAXReader;
import org.megatome.frame2.util.sax.ParserException;

/**
 * EventConfigReader parses the Event Configuration file. It uses the Sax
 * Parser. It creates Handlers for all the elements in the config file, from
 * which run-time configuration objects are extracted.
 */
public class EventConfigReader {
    public static final String FORWARD = "forward"; //$NON-NLS-1$
    public static final String EVENT = "event"; //$NON-NLS-1$
    public static final String SCHEMA_MAPPINGS = "schema-mappings"; //$NON-NLS-1$
    public static final String SCHEMA_MAPPING = "schema-mapping"; //$NON-NLS-1$
    public static final String EVENT_NAME = "event-name"; //$NON-NLS-1$
    public static final String EVENT_MAPPING = "event-mapping"; //$NON-NLS-1$
    public static final String HANDLER = "handler"; //$NON-NLS-1$
    public static final String VIEW = "view"; //$NON-NLS-1$
    public static final String SECURITY = "security"; //$NON-NLS-1$
    public static final String ROLE = "role"; //$NON-NLS-1$
    public static final String GLOBAL_FORWARDS = "global-forwards"; //$NON-NLS-1$
    public static final String EVENT_HANDLER = "event-handler"; //$NON-NLS-1$
    public static final String INPUT_PARAM = "init-param"; //$NON-NLS-1$
    public static final String EXCEPTION = "exception"; //$NON-NLS-1$
    public static final String FRAME2_CONFIG = "frame2-config"; //$NON-NLS-1$
    public static final String EVENTS = "events"; //$NON-NLS-1$
    public static final String EVENT_MAPPINGS = "event-mappings"; //$NON-NLS-1$
    public static final String EVENT_HANDLERS = "event-handlers"; //$NON-NLS-1$
    public static final String EXCEPTIONS = "exceptions"; //$NON-NLS-1$
    public static final String PLUGINS = "plugins"; //$NON-NLS-1$
    public static final String PLUGIN = "plugin"; //$NON-NLS-1$
    public static final String REQ_PROC = "request-processors"; //$NON-NLS-1$
    public static final String SOAP_REQ_PROC = "soap-request-processor"; //$NON-NLS-1$
    public static final String HTTP_REQ_PROC = "http-request-processor"; //$NON-NLS-1$

    private String configFile;
    private InputStream is;

    private ForwardTagHandler forwardTagHandler = new ForwardTagHandler();
    private EventTagHandler eventTagHandler = new EventTagHandler();
    private RoleTagHandler roleTagHandler = new RoleTagHandler();
    
    private EventNameTagHandler eventNameTagHandler = new EventNameTagHandler();
    private SchemaMappingTagHandler schemaMappingTagHandler = new SchemaMappingTagHandler(this.eventNameTagHandler);

    // EventMapping Handlers
    private HandlerTagHandler handlerTagHandler = new HandlerTagHandler();
    private ViewTagHandler viewTagHandler = new ViewTagHandler();
    private InitParamTagHandler inputParamTagHandler = new InitParamTagHandler();
    private SecurityTagHandler securityTagHandler = new SecurityTagHandler(
            this.roleTagHandler);
    private EventMappingTagHandler eventMappingTagHandler = new EventMappingTagHandler(
            this.handlerTagHandler, this.viewTagHandler, this.securityTagHandler);
    private GlobalForwardTagHandler globalForwardTagHandler = new GlobalForwardTagHandler(
            this.forwardTagHandler);
    private EventHandlerTagHandler eventHandlerTagHandler = new EventHandlerTagHandler(
            this.inputParamTagHandler, this.forwardTagHandler);
    private ExceptionTagHandler exceptionTagHandler = new ExceptionTagHandler(
            this.viewTagHandler);
    private PluginTagHandler pluginTagHandler = new PluginTagHandler(
            this.inputParamTagHandler);

    private RequestProcessorTagHandler httpReqProcHandler = new RequestProcessorTagHandler();

    private RequestProcessorTagHandler soapReqProcHandler = new RequestProcessorTagHandler();

    /**
     * Constructs an EventConfigReader for the Configuration file passed in as
     * String identifying the location of the file.
     * @param configFile a String containing the File location
     */
    public EventConfigReader(String configFile) {
        this.configFile = configFile;
        this.is = getClass().getClassLoader().getResourceAsStream(
                this.configFile);
    }

    /**
     * Constructs an EventConfigReader for the Configuration file passed in as
     * an InputStream.
     * @param is an InputStream of the Configuration file
     */
    public EventConfigReader(InputStream is) {
        this.is = is;
    }

    /**
     * Saves the Elements handlers used for parsing the configuration file and
     * then parses the file.
     * @exception Frame2Exception
     */
    public void execute() throws Frame2Exception {
        try {
            Frame2SAXReader reader = new Frame2SAXReader();

            reader.setElementHandler(FORWARD, this.forwardTagHandler);
            reader.setElementHandler(EVENT, this.eventTagHandler);
            reader.setElementHandler(SCHEMA_MAPPING, this.schemaMappingTagHandler);
            reader.setElementHandler(EVENT_NAME, this.eventNameTagHandler);
            reader.setElementHandler(EVENT_MAPPING, this.eventMappingTagHandler);
            reader.setElementHandler(HANDLER, this.handlerTagHandler);
            reader.setElementHandler(VIEW, this.viewTagHandler);
            reader.setElementHandler(SECURITY, this.securityTagHandler);
            reader.setElementHandler(ROLE, this.roleTagHandler);
            reader.setElementHandler(GLOBAL_FORWARDS, this.globalForwardTagHandler);
            reader.setElementHandler(EVENT_HANDLER, this.eventHandlerTagHandler);
            reader.setElementHandler(INPUT_PARAM, this.inputParamTagHandler);
            reader.setElementHandler(EXCEPTION, this.exceptionTagHandler);
            reader.setElementHandler(PLUGIN, this.pluginTagHandler);
            reader.setElementHandler(SOAP_REQ_PROC, this.soapReqProcHandler);
            reader.setElementHandler(HTTP_REQ_PROC, this.httpReqProcHandler);

            // Now set the Elements which do not have handlers
            reader.setElement(FRAME2_CONFIG);
            reader.setElement(EVENTS);
            reader.setElement(SCHEMA_MAPPINGS);
            reader.setElement(EVENT_MAPPINGS);
            reader.setElement(EVENT_HANDLERS);
            reader.setElement(EXCEPTIONS);
            reader.setElement(PLUGINS);
            reader.setElement(REQ_PROC);

            if (this.is == null) {
                throw new ParserException("Error finding config file "); //$NON-NLS-1$
            }

            reader.parse(this.is);
        } catch (ParserException e) {
            throw new Frame2Exception("Unable to load configuration", e); //$NON-NLS-1$
        }
    }

    /**
     * Returns the XML Global Forwards.
     * @return Map containing the XML Global Forwards.
     */
    public Map<String, Forward> getGlobalXMLForwards() {
        return Collections.unmodifiableMap(this.globalForwardTagHandler
                .getXMLForwards());
    }

    /**
     * Returns the HTML Global Forwards.
     * @return Map containing the HTML Global Forwards.
     */
    public Map<String, Forward> getGlobalHTMLForwards() {
        return Collections.unmodifiableMap(this.globalForwardTagHandler
                .getHTMLForwards());
    }

    /**
     * Returns the Configured EventDef.
     * @return Map containing the all the EventDef(s).
     */
    public Map<String, EventDef> getEvents() {
        return Collections.unmodifiableMap(this.eventTagHandler.getEvents());
    }

    /**
     * Returns the Configured EventMappings.
     * @return Map containing the Event Mappings.
     */
    public Map<String, EventMapping> getEventMappings() {
        return Collections.unmodifiableMap(this.eventMappingTagHandler
                .getEventMappings());
    }

    /**
     * Returns the Configured EventHandlers.
     * @return Map containing the EventHandlerDef(s).
     */
    public Map<String, EventHandlerDef> getEventHandlers() {
        return Collections.unmodifiableMap(this.eventHandlerTagHandler
                .getEventHandlers());
    }

    /**
     * Returns the Configured Exceptions
     * @return List containing the Exceptions.
     */
    public List<ExceptionDef> getExceptions() {
        return Collections
                .unmodifiableList(this.exceptionTagHandler.getExceptions());
    }

    public List<PluginDef> getPlugins() {
        return Collections.unmodifiableList(this.pluginTagHandler.getPlugins());
    }

    /**
     * Returns soap request processor Handler's requestProcessorDef.
     */
    public RequestProcessorDef getSoapReqProcHandler() {
        return this.soapReqProcHandler.getRequestProcessorDef();
    }

    /**
     * Returns http request processor Handler's requestProcessorDef.
     */
    public RequestProcessorDef getHttpReqProcHandler() {
        return this.httpReqProcHandler.getRequestProcessorDef();
    }

	public Map<String, Schema> getSchemaMappings() {
		return this.schemaMappingTagHandler.getSchemaMappings();
	}
}