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

/**
 * EventDef is an Object representation of the event element in the
 * configuration file. It is named EventDef and not Event because the framework
 * contains an interface called Event.
 */
public class EventDef {
    private String name;
    private String type;
    private ResolveType resolve;

    /**
     * Constructs an EventDef
     * @param name String
     * @param type String
     * @param resolve String representing the ResolveType
     */
    public EventDef(String name, String type, String resolve) {
        this.name = name;
        this.type = type;
        this.resolve = ResolveType.fromString(resolve);
    }

    /**
     * Constructs an EventDef
     * @param name String
     * @param type String
     * @param resolve a ResolveType
     */
    public EventDef(String name, String type, ResolveType resolve) {
        this.name = name;
        this.resolve = resolve;
        this.type = type;
    }

    public EventDef(EventDef event) {
        this.name = event.name;
        this.resolve = event.resolve;
        this.type = event.type;
    }

    /**
     * Returns the name.
     * @return String name of the EventDef
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the type.
     * @return String type of the EventDef
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the ResolveType.
     * @return ResolveType
     */
    public ResolveType getResolveAs() {
        return this.resolve;
    }

    /**
     * Sets the name.
     * @param name The EventDef name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the type.
     * @param type The EventDef type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the ResolveType of the EventDef.
     * @param resolve The String representation of ResolveType to set
     */
    public void setResolveAs(String resolve) {
        this.resolve = ResolveType.fromString(resolve);
    }

    /**
     * Sets the ResolveType of the EventDef.
     * @param resolve The ResolveType to set
     */
    public void setResolveAs(ResolveType resolve) {
        this.resolve = resolve;
    }
}
