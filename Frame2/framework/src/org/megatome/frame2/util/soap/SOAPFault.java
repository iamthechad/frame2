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
package org.megatome.frame2.util.soap;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.util.dom.DOMStreamConverter;
import org.megatome.frame2.util.dom.DocumentException;
import org.w3c.dom.Element;


/**
 * Models a SOAPFault and emits the fault envelope based on inputs for the detail. When an
 * error is encountered in processing a SOAP request, this object may be instantiated, its detail
 * set, then the body of the response may be set with the fault's element.
 */
public class SOAPFault {
   private static Logger LOGGER = LoggerFactory.instance(SOAPFault.class.getName());
   static public final String SOAP_URI = "http://schemas.xmlsoap.org/soap/envelope/";
   private String _detailMessage;
   private boolean _encode;

   /**
    * Get the element representation of the fault.  This representation can be assigned as the body
    * of a SOAP response.  Remember that this element will need to be imported into the response
    * document.
    *
    * @return Element
    */
   public Element getElement() throws SOAPException {
      try {
         String body = getBody(_detailMessage);

         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Creating element for SOAP fault : " + body);
         }

         return (Element) DOMStreamConverter.fromString(body, true);
      } catch (DocumentException e) {
         throw new SOAPException("Unable to create fault element", e);
      }
   }

   private String getBody(String message) {
      final String faultBodyStart =
         "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
         "<faultcode>SOAP-ENV:Server</faultcode>" + "<faultstring>Server Error</faultstring>" +
         "<detail>";

      final String faultBodyEnd = "</detail></SOAP-ENV:Fault>";

      if (message != null) {
         if (_encode == true) {
            message = DOMStreamConverter.encode(message);
         }

         return faultBodyStart + message + faultBodyEnd;
      } else {
         return faultBodyStart + faultBodyEnd;
      }
   }

   /**
    * Sets the detailed message for the fault.
    *
    * @param message
    */
   public void setDetailMessage(String message) {
      setDetailMessage(message, false);
   }

   /**
    * Sets the detailed message for the fault, but first encodes the message.
    *
    * @param string
    * @param b
    */
   public void setDetailMessage(String message, boolean encode) {
      _detailMessage = message;
      _encode = encode;
   }
}
