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
 package org.megatome.frame2.front;

import java.util.List;
import java.util.Map;

import org.megatome.frame2.Globals;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.errors.impl.ErrorsFactory;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.front.config.ViewType;


/**
 * The request processor base is the abstract base class for the HTML and SOAP implementations.
 */
abstract class RequestProcessorBase implements RequestProcessor {
   private Configuration config;
   protected ContextWrapper context;
   protected Errors errors = ErrorsFactory.newInstance();

   protected RequestProcessorBase(Configuration config) {
      this.config = config;
   }

   /**
    * Method callHandlers.
    *
    * @param eventName
    * @param event
    * @param vtype
    *
    * @return ForwardProxy
    */
   protected ForwardProxy callHandlers(String eventName, Event event, ViewType vtype)
      throws Exception {
      String token = null;
      List<EventHandlerProxy> handlerList = null;
      ForwardProxy fwd = null;

      ContextWrapper ctx = getContextWrapper();

      if (event != null) {
         event.setEventName(eventName);
      } 
      ctx.setRequestAttribute(eventName, event);

      handlerList = this.config.getHandlers(eventName);
      if (handlerList != null && (handlerList.size() >0)) {
         if(event == null) {
            throw new ConfigException("No event Type specified for its Handlers"); //$NON-NLS-1$
         }
         for (int i = 0; i < handlerList.size(); i++) {
            EventHandlerProxy handler = handlerList.get(i);

            token = processHandler(event, handler, ctx);

            if (token != null) {
               fwd = this.config.resolveForward(handler, token, configResourceType());

               if (fwd.isEventType()) {
                  return callHandlers(fwd.getPath(),
                     this.config.getEventProxy(fwd.getPath()).getEvent(), vtype);
               }

               break; // not an event
            }
         }
      }

      if (token == null) {
         token = this.config.getEventMappingView(eventName, vtype);
         fwd = this.config.resolveForward(token, configResourceType());

         if (fwd.isEventType()) {
            return callHandlers(fwd.getPath(), this.config.getEventProxy(fwd.getPath()).getEvent(),
               vtype);
         }
      }
    
      return fwd;
   }

   abstract protected ContextWrapper getContextWrapper();

   public Configuration getConfig() {
      return this.config;
   }

   private String processHandler(Event event, EventHandlerProxy proxyHandler, ContextWrapper ctx)
      throws Exception {
      setInitParmsForHandler(proxyHandler, ctx);

      return proxyHandler.handle(event, ctx);
   }

   private void setInitParmsForHandler(EventHandlerProxy proxy, ContextWrapper ctx) {
      ctx.setInitParameters(proxy.getDefinition().getInitParams());
   }

   public void release() {
      this.config = null;
      this.errors = null;
   }

   abstract protected String configResourceType();

   abstract protected boolean isUserAuthorizedForEvent(String event)
      throws ConfigException;

	/**
	 * Determine if the request to be processed is a cancel request.
 	 * @param requestParameters All request parameters
	 * @return True if this request should be cancelled
	 */
	protected boolean isCancelRequest(Map<String, Object> requestParameters) {
		return requestParameters.get(Globals.CANCEL) != null;
	}
}
