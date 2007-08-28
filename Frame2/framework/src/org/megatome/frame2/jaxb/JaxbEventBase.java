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
package org.megatome.frame2.jaxb;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.CommonsValidatorEvent;
import org.megatome.frame2.front.TranslationException;
import org.w3c.dom.Document;

/**
 * The JaxbEventBase is a base class that helps bind JavaBeans generated through
 * JAXB into Frame2 framework. By specifying this as base class for JAXB
 * generated classes they become Frame2 events complete with appropriate
 * validation behaviors. See the JAXB User Guide for instructions on how to
 * customize the bindings for an external superclass.
 */
public abstract class JaxbEventBase extends CommonsValidatorEvent {
	private Schema validatingSchema = null;
	
	public void setValidatingSchema(final Schema validatingSchema) {
		this.validatingSchema = validatingSchema;
	}
	
	public Object getMarshallableType() throws Exception {
		String objectFactoryClass = this.getClass().getPackage().getName() + ".ObjectFactory"; //$NON-NLS-1$
		Class<?> ofClass = Class.forName(objectFactoryClass);
		Object of = ofClass.newInstance();
		for (Method m : ofClass.getMethods()) {
			for (Class<?> param : m.getParameterTypes()) {
				if (this.getClass().equals(param)) {
					// This is our method?
					return m.invoke(of, new Object[] { this });
				}
			}
		}
		
		return this;
	}

    /**
     * Validate the state of the object using the internal JAXB validator.
     * @param errors Errors object that will be populated with any validation
     *        errors.
     * @return The result of validation
     * @see org.megatome.frame2.event.Event#validate(Errors)
     */
    @Override
	public boolean validate(Errors errors) {
        boolean result = true;
        String evtName = this.getEventName();
        this.setEventName(null);
        try {
			ValidationMonitor monitor = new ValidationMonitor();
			JAXBContext context = JAXBContext.newInstance(this.getClass().getPackage().getName());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setSchema(this.validatingSchema);
			marshaller.setEventHandler(monitor);
			marshaller.marshal(getMarshallableType(), getTargetDocument());
			
			monitor.populate(errors);
			result = errors.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
        
		this.setEventName(evtName);
        // calling super's validate after jaxb validate
        // per customer requirement
        result &= super.validate(errors);

        return result;
    }
    
    private Document getTargetDocument() throws TranslationException {
		Document result = null;

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			dbf.setNamespaceAware(true);

			DocumentBuilder db = dbf.newDocumentBuilder();

			result = db.newDocument();
		} catch (Exception e) {
			throw new TranslationException("Unable to create target document", //$NON-NLS-1$
					e);
		}

		return result;
	}
    
    class ValidationMonitor implements ValidationEventHandler {
		private List<ValidationEvent> events = new ArrayList<ValidationEvent>();

		/**
		 * @see javax.xml.bind.ValidationEventHandler#handleEvent(ValidationEvent)
		 */
		public boolean handleEvent(ValidationEvent evt) {
			this.events.add(evt);

			return true;
		}

		void populate(final Errors errors) {
			if ((errors != null) && !this.events.isEmpty()) {

				for (ValidationEvent event : this.events) {
					String key = getKey(event);
                    String msg = getMessage(event);

                    errors.add(key, msg);
				}
				this.events.clear();
            }
		}
		
		// DOC: In the present JAXB implementation we can't resolve from the
        // ValidationEvent
        String getKey(ValidationEvent event) {
            String className = event.getLocator().getObject().getClass()
                    .getName();

            int delimiterIndex = className.indexOf("$"); //$NON-NLS-1$

            StringBuffer buffer = null;

            if (delimiterIndex == -1) {
                buffer = new StringBuffer(className);
            } else {
                buffer = new StringBuffer(className.substring(0, className
                        .indexOf("$"))); //$NON-NLS-1$
            }

            String attr = getAttributeName(event);

            if (attr != null) {
                buffer.append("."); //$NON-NLS-1$
                buffer.append(attr);
            }

            return buffer.toString();
        }

		String getMessage(ValidationEvent event) {
			String message = event.getMessage();

			if (isAttribute(event)) {
				return message.substring(message.indexOf(":") + 2); //$NON-NLS-1$
			}

			return message;
		}

		String getAttributeName(ValidationEvent event) {
			// Bug 953538
			// Remove hardcoded "partNum" and instead parse
			// the attribute name from the message
			String attributeName = null;
			if (isAttribute(event)) {
				String message = event.getMessage();
				String quotedAttributeRE = "[^\"]*\"([^\"]+)\""; //$NON-NLS-1$
				Pattern pat = Pattern.compile(quotedAttributeRE,
						Pattern.CASE_INSENSITIVE);
				Matcher m = pat.matcher(message);

				if (m.find()) {
					attributeName = m.group(1);
				}
			}

			return attributeName;
		}

		boolean isAttribute(ValidationEvent event) {
			boolean retval = false;
			String msg = event.getMessage();
			if (msg != null) {
				retval = msg.indexOf("attribute") == 0; //$NON-NLS-1$
			}

			return retval;
		}
	}
}