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
 package org.megatome.frame2.event;

/**
 * EventHandler defines the common interface for all Frame2 handlers used by the framework.
 * Frame2 developers need to implement this interface for all event handlers.
 */

// DOC: Emphasize that handlers must be thread safe; the framework will only create
// one of each.
// NIT: Do we want to allow the developers to use generic objects?
public interface EventHandler {
   /**
    * Handle the event, executing the EventHandler's specific logic, and return the path token
    * which specifies the logical view that this handler recommends.  The handler is reached by
    * being specified as part of an event mapping for an event that is received as a request.  The
    * handler must be implemented to be thread safe.
    *
    * @param event The event to handle. Normally the event will have been populated by the
    *        framework based on the data in the request. If the handler is being called as part of
    *        a forward to an event, then by default the original request data will not be present
    *        in the event.
    * @param context The context of the request.  The handler may access the request and session
    *        attributes through the context, and the request errors object.
    *
    * @return String The status token for the handler. It is allowed and common for handlers to
    *         return a null token. Otherwise the token value corresponds to the  a view directive
    *         as specified in the configuration file.
    *
    * @throws Exception
    */
   public String handle(Event event, Context context) throws Exception;
}
