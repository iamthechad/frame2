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
 package org.megatome.frame2.event;

import org.megatome.frame2.errors.Errors;


/**
 * Event defines the common interface for all Frame2 events processed by the framework.  Frame2
 * developers need to implement this interface for all form models to be provided to the event
 * handlers.  Events are instantiated and processed in response to requests from the client, as
 * declared in the framework configuration.
 */

// NIT: the event package should define an interface for errors so that
// the event is not tied to our specific implementation.
public interface Event {
   /**
    * Validate the state of the event.  This method is implemented to allow the Frame2 framework
    * to check the state of an event so that the client may be directed to the correct view.  If
    * the event mapping is configured to validate the event when a request is received, the
    * framework will instantiate and populate (or unmarshall) the event based on the request,
    * then call the validate method.
    *
    * @param errors If the event does not pass validation, the errors object should be populated by
    *        the method with error data describing the nature of the failure.
    *
    * @return True if the event passed validation, false if the validation failed (i.e. if
    *         there were errors).
    */
   public boolean validate(Errors errors);
   
   /**
    * Set the name of this event. This method is commonly used only by the framework,
    * but there are occasions where changing an event's name is useful.
    * @param name The name of the event
    */
   public void setName(String name);
   
   /**
    * Get the name of the event.
    * @return Event name
    */
   public String getName();
}
