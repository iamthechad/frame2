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
package org.megatome.frame2.front.config;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

/**
 * EventMappingTagHandler handles all the eventMapping elements of the
 * Configuration file.
 */
class EventMappingTagHandler extends ConfigElementHandler {
    public static final String INPUT_VIEW = "inputView";
    public static final String CANCEL_VIEW = "cancelView";
    public static final String VALIDATE = "validate";

    private String eventName;
    private HandlerTagHandler handlerTagHandler;
    private ViewTagHandler viewTagHandler;
    private SecurityTagHandler securityTagHandler;
    private Map eventMappings = new HashMap();

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

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) {
        eventName = attributes.getValue("eventName");

        EventMapping eventMapping = new EventMapping(eventName);

        eventMapping.setInputView(attributes.getValue(INPUT_VIEW));
        eventMapping.setCancelView(attributes.getValue(CANCEL_VIEW));
        eventMapping.setValidate(Boolean.valueOf(attributes.getValue(VALIDATE))
                .booleanValue());
        eventMappings.put(eventName, eventMapping);
    }

    public void endElement(String uri, String localName, String qName) {
        EventMapping eventMapping = (EventMapping)eventMappings
                .get(eventName);

        // build event mapping here
        eventMapping.setHandlers(handlerTagHandler.getHandlers());
        handlerTagHandler.clear();
        eventMapping.setSecurity(securityTagHandler.getSecurity());
        securityTagHandler.clear();
        eventMapping.setView(ViewType.XML.toString(), viewTagHandler
                .getXMLForwardName());
        eventMapping.setView(ViewType.HTML.toString(), viewTagHandler
                .getHTMLForwardName());
        viewTagHandler.clear();
    }

    /**
     * @return returns a clone of the Events List
     */
    public Map getEventMappings() {
        return eventMappings;
    }

    /**
     * clear the Events List
     */
    public void clear() {
        eventMappings.clear();
    }
}