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

import javax.servlet.ServletException;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.util.soap.SOAPException;
import org.megatome.frame2.util.soap.SOAPFault;
import org.w3c.dom.Element;


/**
 * WebServiceFrontController.java
 */
public abstract class SoapFrontController {
   private static Logger LOGGER = LoggerFactory.instance(SoapFrontController.class.getName());

   public Element[] processEvent(Element[] elements) {
      Element[] result = null;
      RequestProcessor processor = null;

      try {
         String eventPkg = getEventPackageSpecifier();
         processor = RequestProcessorFactory.instance(ConfigFactory.instance(),
               elements, eventPkg);
			if (processor == null){
				String error = "Unable to instantiate Request Processor";
				LOGGER.severe(error);
				throw new ServletException(error);
			}
         
			try {
				processor.preProcess();
			}
			catch (RuntimeException re) {
				LOGGER.severe("Caught exception in RequestProcessor:preProcess() " + re);
				re.printStackTrace();
			}
         result = (Element[]) processor.processRequest();
      } catch (ConfigException e) {
         result[0] = createFault(e);
      } catch (Throwable e) {
         result[0] = createFault(e);
      } finally {
         if (processor != null) {
			   try {
			      processor.postProcess();
				}
				catch (RuntimeException re) {
					 LOGGER.severe("Caught exception in RequestProcessor:postProcess() " + re);
					 re.printStackTrace();
				}
            processor.release();
         }
      } 

      return result;
   }

   private Element createFault(Throwable e) {
      SOAPFault fault = new SOAPFault();

      fault.setDetailMessage(e.getMessage(), true);

      Element elem = null;

      try {
         elem = fault.getElement();
      } catch (SOAPException se) {
         LOGGER.warn("Unable to generate SOAP fault", se);
      }

      return elem;
   }

   /**
    * This message must be overriden to return the package specifier that the
    * marshalling framework will use to unmarshall the contents of the SOAP
    * message body (see the JAXB documentation).
    * @return String
    */
   public abstract String getEventPackageSpecifier();
}
