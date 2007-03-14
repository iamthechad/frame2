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

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

/**
 * EventMappingTagHandler handles all the eventMapping elements of the
 * Configuration file.
 */
class EventMappingTagHandler extends ConfigElementHandler {
    public static final String INPUT_VIEW = "inputView"; //$NON-NLS-1$
    public static final String CANCEL_VIEW = "cancelView"; //$NON-NLS-1$
    public static final String VALIDATE = "validate"; //$NON-NLS-1$

    private String eventName;
    private HandlerTagHandler handlerTagHandler;
    private ViewTagHandler viewTagHandler;
    private SecurityTagHandler securityTagHandler;
    private Map<String, EventMapping> eventMappings = new HashMap<String, EventMapping>();

    /**
     * Constructs an EventMappingTagHandler.
     * @param HandlerTagHandler
     * @param ViewTagHandler
     * @param SecurityTagHandler
     */
    EventMappingTagHandler(HandlerTagHandler handlerTagHandler,
            ViewTagHandler viewTagHandler, SecurityTagHandler securityTagHandler) {
        this.handlerTagHandler = handlerTagHandler;
        this.viewTagHandler = viewTagHandler;
        this.securityTagHandler = securityTagHandler;
    }

    @Override
	public void startElement(@SuppressWarnings("unused")
	String uri, @SuppressWarnings("unused")
	String localName, @SuppressWarnings("unused")
	String qName,
            Attributes attributes) {
        this.eventName = attributes.getValue("eventName"); //$NON-NLS-1$

        EventMapping eventMapping = new EventMapping(this.eventName);

        eventMapping.setInputView(attributes.getValue(INPUT_VIEW));
        eventMapping.setCancelView(attributes.getValue(CANCEL_VIEW));
        eventMapping.setValidate(Boolean.valueOf(attributes.getValue(VALIDATE))
                .booleanValue());
        this.eventMappings.put(this.eventName, eventMapping);
    }

    @Override
	public void endElement(@SuppressWarnings("unused")
	String uri, @SuppressWarnings("unused")
	String localName, @SuppressWarnings("unused")
	String qName) {
        EventMapping eventMapping = this.eventMappings
                .get(this.eventName);

        // build event mapping here
        eventMapping.setHandlers(this.handlerTagHandler.getHandlers());
        this.handlerTagHandler.clear();
        eventMapping.setSecurity(this.securityTagHandler.getSecurity());
        this.securityTagHandler.clear();
        eventMapping.setView(ViewType.XML.toString(), this.viewTagHandler
                .getXMLForwardName());
        eventMapping.setView(ViewType.HTML.toString(), this.viewTagHandler
                .getHTMLForwardName());
        this.viewTagHandler.clear();
    }

    /**
     * @return returns a clone of the Events List
     */
    public Map<String, EventMapping> getEventMappings() {
        return this.eventMappings;
    }

    /**
     * clear the Events List
     */
    @Override
	public void clear() {
        this.eventMappings.clear();
    }
}