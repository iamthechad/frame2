/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;
import org.megatome.frame2.front.config.EventConfigReader;
import org.megatome.frame2.front.config.EventDef;
import org.megatome.frame2.front.config.EventHandlerDef;
import org.megatome.frame2.front.config.EventMapping;
import org.megatome.frame2.front.config.ExceptionDef;
import org.megatome.frame2.front.config.Forward;
import org.megatome.frame2.front.config.PluginDef;
import org.megatome.frame2.front.config.RequestProcessorDef;
import org.megatome.frame2.front.config.ViewType;
import org.megatome.frame2.plugin.PluginInterface;

/**
 * A Configuration populates and holds the data for a Frame2 configuration
 * instance. Once the data is loaded, the configuration object acts as a factory
 * and cache for the runtime instances of the configuration.
 */
public class Configuration {
    /** Token type used when processing forwards */
    public static final String XML_TOKEN = "xml";

    /** Token type used when processing forwards */
    public static final String HTML_TOKEN = "html";

    private Map _globalXMLForwards;

    private Map _globalHTMLForwards;

    private Map _events;

    private Map _eventMappings;

    private Map _eventHandlers;

    private List _exceptions;

    private List _plugins;

    private Map _eventHandlerCache = new HashMap();

    private String _path;

    private Date _initTime;

    private EventConfigReader _reader;

    private RequestProcessorDef _soapRequestProcessor;

    private RequestProcessorDef _httpRequestProcessor;

    private List _pluginProxies = null;

    /**
     * Constructor for Configuration. The configuration will be loaded from the
     * file specified with the path, searching through the local classpath.
     * @param path The path of the configuration file to load from.
     * @throws ConfigException If loading the configuration fails.
     */
    public Configuration(String path) throws ConfigException {
        _reader = new EventConfigReader(path);
        _path = path;
        processConfig();
    }

    /**
     * Constructor for Configuration. The configuration will be loaded from the
     * InputStream
     * @param is The InputStream of the configuration file.
     * @throws Exception If loading the configuration fails.
     */
    public Configuration(InputStream is) throws ConfigException {
        _reader = new EventConfigReader(is);
        processConfig();
    }

    /**
     * Process the configuration file.
     * @throws ConfigException
     */
    void processConfig() throws ConfigException {
        try {
            _reader.execute();
        } catch (Frame2Exception e) {
            throw new ConfigException(e);
        }

        _globalXMLForwards = _reader.getGlobalXMLForwards();
        _globalHTMLForwards = _reader.getGlobalHTMLForwards();
        _events = _reader.getEvents();
        _eventMappings = _reader.getEventMappings();
        _eventHandlers = _reader.getEventHandlers();
        _exceptions = _reader.getExceptions();

        _plugins = _reader.getPlugins();
        loadPluginProxies();

        _soapRequestProcessor = _reader.getSoapReqProcHandler();
        _httpRequestProcessor = _reader.getHttpReqProcHandler();

        _initTime = new Date();
    }

    /**
     * Get the path of the configuration file.
     * @return Configuration file path
     */
    String getPath() {
        return _path;
    }

    /**
     * Get the time the configuration was initialized
     * @return Date
     */
    Date getInitTime() {
        return _initTime;
    }

    /**
     * Method getGlobalForwards.
     * @return Map
     */
    Map getGlobalXMLForwards() {
        return _globalXMLForwards;
    }

    Map getGlobalHTMLForwards() {
        return _globalHTMLForwards;
    }

    /**
     * Method getGlobalForwards.
     * @return Map
     */
    Forward getGlobalXMLForward(String token) {
        return (Forward)_globalXMLForwards.get(token);
    }

    Forward getGlobalHTMLForward(String token) {
        return (Forward)_globalHTMLForwards.get(token);
    }

    /**
     * Returns the eventHandlers.
     * @return Map
     */
    Map getEventHandlers() {
        return _eventHandlers;
    }

    /**
     * Returns the eventMappings.
     * @return Map
     */
    Map getEventMappings() {
        return _eventMappings;
    }

    /**
     * Returns the events.
     * @return Map
     */
    Map getEvents() {
        return _events;
    }

    /**
     * Returns the exceptions.
     * @return Map
     */
    List getExceptions() {
        return _exceptions;
    }

    List getPlugins() {
        return _plugins;
    }

    List getPluginProxies() {
        return _pluginProxies;
    }

    void loadPluginProxies() throws ConfigException {
        _pluginProxies = new ArrayList();
        for (Iterator iter = _plugins.iterator(); iter.hasNext();) {
            PluginDef pluginDef = (PluginDef)iter.next();
            PluginInterface plugin = null;

            try {
                String type = pluginDef.getType();
                //plugin = (PluginInterface)
                // getClass().forName(type).newInstance();
                plugin = (PluginInterface)Class.forName(type).newInstance();
            } catch (Exception e) {
                throw new ConfigException(e);
            }

            _pluginProxies.add(new PluginProxy(pluginDef, plugin));
        }
    }

    PluginProxy getPluginProxy(String name) {
        List pluginProxys = getPluginProxies();
        PluginProxy proxy = null;
        boolean found = false;
        for (Iterator iter = pluginProxys.iterator(); iter.hasNext();) {
            proxy = (PluginProxy)iter.next();
            if (proxy.getName().equals(name)) {
                found = true;
                break;
            }
        }
        return (found) ? proxy : null;
    }

    /**
     * Method getEvent.
     * @param eventName
     * @return DOCUMENT ME!
     * @throws ConfigException DOCUMENT ME!
     */
    EventProxy getEventProxy(String eventName) throws ConfigException {
        EventDef eventDef = (EventDef)_events.get(eventName);
        Event event = null;

        if (eventDef == null) {
            throw new ConfigException("Invalid Event Name: " + eventName);
        }

        try {
            String type = eventDef.getType();

            if (type != null) {
                //event = (Event) getClass().forName(type).newInstance();
                event = (Event)Class.forName(type).newInstance();
            }
        } catch (Exception e) {
            throw new ConfigException("Invalid Event ", e);
        }

        return new EventProxy(eventDef, event);
    }

    /**
     * Method getHandlers.
     * @param eventName The name of the event for which to get the handlers.
     * @return List An ordered list of the event handlers for this event.
     */
    List getHandlers(String eventName) throws ConfigException {
        if ((eventName == null) || (eventName.length() == 0)
                || (_events.get(eventName) == null)) {
            throw new ConfigException("Invalid event name " + eventName);
        }

        List result = (List)_eventHandlerCache.get(eventName);

        if (result == null) {
            result = Collections.unmodifiableList(makeHandlers(eventName));
            _eventHandlerCache.put(eventName, result);
        }

        return result;
    }

    private List makeHandlers(String eventName) throws ConfigException {
        List handlerNames = getHandlerNames(eventName);
        List handlers = new ArrayList();

        handlers = makeHandlers(handlerNames);

        return handlers;
    }

    private List makeHandlers(List handlerNames) throws ConfigException {
        List result = new ArrayList();

        try {
            if (handlerNames != null) {
                for (int i = 0; i < handlerNames.size(); i++) {
                    String handlerName = (String)handlerNames.get(i);
                    EventHandlerDef handlerDef = (EventHandlerDef)_eventHandlers
                            .get(handlerName);

                    if (handlerDef == null) {
                        throw new ConfigException("Handler : " + handlerName
                                + " is not configured");
                    }

                    //EventHandler handler = (EventHandler)
                    // getClass().forName(handlerDef.getType())
                    //                                         .newInstance();
                    EventHandler handler = (EventHandler)Class.forName(
                            handlerDef.getType()).newInstance();

                    result.add(new EventHandlerProxy(handlerName, handler,
                            handlerDef));
                }
            }
        } catch (InstantiationException e) {
            throw new ConfigException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigException(e);
        } catch (ClassNotFoundException e) {
            throw new ConfigException(e);
        }

        return result;
    }

    private List getHandlerNames(String eventName) throws ConfigException {
        List handlers = new ArrayList();
        EventMapping mapping = (EventMapping)_eventMappings.get(eventName);

        if (mapping != null) {
            handlers = mapping.getHandlers();
        } else {
            throw new ConfigException("No mapping defined for event "
                    + eventName);
        }

        return handlers;
    }

    /**
     * Method getDefaultView.
     * @param eventName
     * @return String
     */
    String getEventMappingView(String eventName, ViewType type)
            throws ViewException {
        String view = null;
        EventMapping eventMapping = (EventMapping)getEventMappings().get(
                eventName);

        view = eventMapping.getView(type.toString());

        if (view == null) {
            throw new ViewException(
                    "There is no view defined in the config file for EventMapping: "
                            + eventName);
        }

        return view;
    }

    ForwardProxy resolveForward(EventHandlerProxy handler, String token,
            String tokenType) throws ViewException {
        // look in local forward first
        // then in global forward
        Forward fwd = null;

        if (tokenType.equals(HTML_TOKEN)) {
            fwd = handler.getDefinition().getHTMLForward(token);
        } else if (tokenType.equals(XML_TOKEN)) {
            fwd = handler.getDefinition().getXMLForward(token);
        }

        return (fwd == null) ? resolveForward(token, tokenType)
                : new ForwardProxy(fwd);
    }

    ForwardProxy resolveForward(String token, String tokenType)
            throws ViewException {
        // global forward
        Forward fwd = resolveGlobalForward(token, tokenType);

        return new ForwardProxy(fwd);
    }

    Forward resolveGlobalForward(String token, String tokenType)
            throws ViewException {
        // global forward
        Forward fwd = null;

        if (tokenType.equals(HTML_TOKEN)) {
            fwd = getGlobalHTMLForward(token);
        } else if (tokenType.equals(XML_TOKEN)) {
            fwd = getGlobalXMLForward(token);
        }

        if (fwd == null) {
            throw new ViewException("Error, The forward name " + token
                    + "does not exist");
        }

        return fwd;
    }

    boolean validateFor(String eventName) throws ConfigException {
        return mappingFor(eventName).isValidate();
    }

    String inputViewFor(String eventName, String tokenType)
            throws ConfigException, ViewException {
        String result = mappingFor(eventName).getInputView();

        if (result == null) {
            throw new ConfigException("No input view for event " + eventName);
        }

        return resolveForward(result, tokenType).getPath();
    }

    ForwardProxy cancelViewFor(String eventName, String tokenType)
            throws ConfigException, ViewException {
        String result = mappingFor(eventName).getCancelView();

        if (result == null) {
            throw new ConfigException("No cancel view for event " + eventName);
        }

        return resolveForward(result, tokenType);
    }

    private EventMapping mappingFor(String eventName) throws ConfigException {
        EventMapping mapping = (EventMapping)_eventMappings.get(eventName);

        if (mapping != null) {
            return mapping;
        }

        throw new ConfigException("No mapping for event " + eventName);
    }

    ExceptionProxy resolveException(Throwable ex, String tokenType,
            ViewType vtype) throws ViewException {

        ExceptionDef edef = null;
        boolean found = false;
        Iterator iter = _exceptions.iterator();
        while (iter.hasNext()) {
            edef = (ExceptionDef)iter.next();
            String classtype = edef.getType();
            try {
                if (Class.forName(classtype).isInstance(ex)) {
                    found = true;
                    break;
                }
            } catch (ClassNotFoundException e) { // Not concerned with this
            }
        }
        if (found) {
            String view = edef.getView(vtype.toString());
            Forward fwd = resolveGlobalForward(view, tokenType);
            return new ExceptionProxy(fwd, edef);
        }

        return null;
    }

    String[] rolesfor(String event) throws ConfigException {
        final String[] TYPE = new String[0];

        EventMapping mapping = mappingFor(event);

        if (mapping != null) {
            return mapping.getRoles();
        }

        return TYPE;
    }

    /**
     * Returns the httpRequestProcessor.
     * @return RequestProcessorDef
     */
    RequestProcessorDef getHttpRequestProcessor() {
        return _httpRequestProcessor;
    }

    /**
     * Returns the soapRequestProcessor.
     * @return RequestProcessorDef
     */
    RequestProcessorDef getSoapRequestProcessor() {
        return _soapRequestProcessor;
    }

}