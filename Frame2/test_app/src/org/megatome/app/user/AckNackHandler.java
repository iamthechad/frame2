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
package org.megatome.app.user;

import org.megatome.app.jaxbgen.ACK;
import org.megatome.app.jaxbgen.NACK;
import org.megatome.app.jaxbgen.impl.ACKImpl;
import org.megatome.app.jaxbgen.impl.NACKImpl;
import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;

/**
 * 
 */
public class AckNackHandler implements EventHandler {

	/**
	 * @see org.megatome.frame2.event.EventHandler#handle(Event, Context)
	 */
	public String handle(Event event, Context context) throws Frame2Exception {
		User user = (User) event;

		Errors errors = context.getRequestErrors();

		if (errors.isEmpty()) {
			System.out.println("Adding " + user.getFirstName());

			ACK ack = new ACKImpl();
			ack.setValue(user.getFirstName() + " was added");
			context.setRequestAttribute("ack", ack);

			return "success";

		} else {
			System.out.println("Not adding " + user.getFirstName());

			NACK nack = new NACKImpl();

         Error[] error = errors.get();

         for ( int i = 0 ; i < error.length ; i++ ) {
				String report = nack.getValue() + " : " + error[i].getValue();
				nack.setValue(report);
				context.setRequestAttribute("nack", nack);
			}
         
         return "fail";
		}
	}
}

