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
 package org.megatome.frame2.front.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.util.sax.ParserException;
import org.megatome.frame2.util.sax.Frame2SAXReader;

/**
 * EventConfigReader parses the Event Configuration file. It uses the Sax Parser.
 * It creates Handlers for all the elements in the config file, from which run-time
 * configuration objects are extracted.
 */

public class EventConfigReader {
   public static final String FORWARD = "forward";
   public static final String EVENT = "event";
   public static final String EVENT_MAPPING = "event-mapping";
   public static final String HANDLER = "handler";
   public static final String VIEW = "view";
   public static final String SECURITY = "security";
   public static final String ROLE = "role";
   public static final String GLOBAL_FORWARDS = "global-forwards";
   public static final String EVENT_HANDLER = "event-handler";
   public static final String INPUT_PARAM = "init-param";
   public static final String EXCEPTION = "exception";
   public static final String FRAME2_CONFIG = "frame2-config";
   public static final String EVENTS = "events";
   public static final String EVENT_MAPPINGS = "event-mappings";
   public static final String EVENT_HANDLERS = "event-handlers";
   public static final String EXCEPTIONS = "exceptions";
   public static final String PLUGINS    = "plugins";
   public static final String PLUGIN    = "plugin";
   public static final String REQ_PROC    = "request-processors";
   public static final String SOAP_REQ_PROC    = "soap-request-processor";
   public static final String HTTP_REQ_PROC    = "http-request-processor";
   
   private String _configFile;
   private InputStream _is;
   private ForwardTagHandler _forwardTagHandler = new ForwardTagHandler();
   private EventTagHandler _eventTagHandler = new EventTagHandler();
   private RoleTagHandler _roleTagHandler = new RoleTagHandler();

   //EventMapping Handlers
   private HandlerTagHandler _handlerTagHandler = new HandlerTagHandler();
   private ViewTagHandler _viewTagHandler = new ViewTagHandler();
   private InitParamTagHandler _inputParamTagHandler = new InitParamTagHandler();
   private SecurityTagHandler _securityTagHandler = new SecurityTagHandler(_roleTagHandler);
   private EventMappingTagHandler _eventMappingTagHandler = new EventMappingTagHandler(_handlerTagHandler,
         _viewTagHandler, _securityTagHandler);
   private GlobalForwardTagHandler _globalForwardTagHandler = new GlobalForwardTagHandler(_forwardTagHandler);
   private EventHandlerTagHandler _eventHandlerTagHandler = new EventHandlerTagHandler(_inputParamTagHandler,
         _forwardTagHandler);
   private ExceptionTagHandler _exceptionTagHandler = new ExceptionTagHandler(_viewTagHandler);
   private PluginTagHandler _pluginTagHandler = new PluginTagHandler(_inputParamTagHandler);
   private RequestProcessorTagHandler _httpReqProcHandler = new RequestProcessorTagHandler();
   private RequestProcessorTagHandler _soapReqProcHandler = new RequestProcessorTagHandler();

   /**
    * Constructs an EventConfigReader for the Configuration file passed in as String identifying 
    * the location of the file.
    *
    * @param configFile a String containing the File location
    *
    */

   public EventConfigReader(String configFile) {
      _configFile = configFile;
      _is = getClass().getClassLoader().getResourceAsStream(_configFile);
   }

   /**
    * Constructs an EventConfigReader for the Configuration file passed in as an InputStream.
    *
    * @param is an InputStream of the Configuration file
    *
    */
   public EventConfigReader(InputStream is) {
      _is = is;
   }

/**
 * Saves the Elements handlers used for parsing the configuration file 
 * and then parses the file.
 * 
 * @exception Frame2Exception
 */  
   public void execute() throws Frame2Exception {
      try {
         Frame2SAXReader reader = new Frame2SAXReader();

         reader.setElementHandler(FORWARD, _forwardTagHandler);
         reader.setElementHandler(EVENT, _eventTagHandler);
         reader.setElementHandler(EVENT_MAPPING, _eventMappingTagHandler);
         reader.setElementHandler(HANDLER, _handlerTagHandler);
         reader.setElementHandler(VIEW, _viewTagHandler);
         reader.setElementHandler(SECURITY, _securityTagHandler);
         reader.setElementHandler(ROLE, _roleTagHandler);
         reader.setElementHandler(GLOBAL_FORWARDS, _globalForwardTagHandler);
         reader.setElementHandler(EVENT_HANDLER, _eventHandlerTagHandler);
         reader.setElementHandler(INPUT_PARAM, _inputParamTagHandler);         
         reader.setElementHandler(EXCEPTION, _exceptionTagHandler);
         reader.setElementHandler(PLUGIN, _pluginTagHandler);
         reader.setElementHandler(SOAP_REQ_PROC, _soapReqProcHandler);
         reader.setElementHandler(HTTP_REQ_PROC, _httpReqProcHandler);


         // Now set the Elements which do not have handlers
         reader.setElement(FRAME2_CONFIG);
         reader.setElement(EVENTS);
         reader.setElement(EVENT_MAPPINGS);
         reader.setElement(EVENT_HANDLERS);
         reader.setElement(EXCEPTIONS);
         reader.setElement(PLUGINS);
         reader.setElement(REQ_PROC);

         if (_is == null) {
            throw new ParserException("Error finding config file ");
         } else {
            reader.parse(_is);
         }
      } catch (ParserException e) {
         throw new Frame2Exception("Unable to load configuration", e);
      }
   }

   /**
    * Returns the XML Global Forwards.
    *
    * @return HashMap containing the XML Global Forwards.
    */
   public HashMap getGlobalXMLForwards() {
      return _globalForwardTagHandler.getXMLForwards();
   }

   /**
    * Returns the HTML Global Forwards.
    *
    * @return HashMap containing the HTML Global Forwards.
    */
   public HashMap getGlobalHTMLForwards() {
      return _globalForwardTagHandler.getHTMLForwards();
   }

   /**
    * Returns the Configured EventDef.
    *
    * @return HashMap containing the all the EventDef(s).
    */
   public HashMap getEvents() {
      return _eventTagHandler.getEvents();
   }

   /**
    * Returns the Configured EventMappings.
    *
    * @return HashMap containing the Event Mappings.
    */
   public HashMap getEventMappings() {
      return _eventMappingTagHandler.getEventMappings();
   }

   /**
    * Returns the Configured EventHandlers.
    *
    * @return HashMap containing the EventHandlerDef(s).
    */
   public HashMap getEventHandlers() {
      return _eventHandlerTagHandler.getEventHandlers();
   }


   /**
    * Returns the Configured Exceptions
    *
    * @return ArrayList containing the Exceptions.
    */
   public ArrayList getExceptions() {
      return _exceptionTagHandler.getExceptions();
   }
   
   public List getPlugins() {
      return _pluginTagHandler.getPlugins();
   }
   
   /**
    *  Returns soap request processor Handler's requestProcessorDef.
    */
   public RequestProcessorDef getSoapReqProcHandler () {
      return _soapReqProcHandler.getRequestProcessorDef();
   }
   
   /**
    *  Returns http request processor Handler's requestProcessorDef.
    */
   public RequestProcessorDef getHttpReqProcHandler () {
      return _httpReqProcHandler.getRequestProcessorDef();
   }
}
