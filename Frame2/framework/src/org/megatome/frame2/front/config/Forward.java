/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2006 Megatome Technologies.  All rights
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
 * Forward is an Object representation of a forward element in the configuration
 * file.
 */
public class Forward {
    private String name;
    private ForwardType type;
    private String path;

    /**
     * Constructs an Forward
     * @param name String
     * @param name type (HTMLResource, XMLResource, XMLResponder, or event)
     * @param name path if type = HTMLResource, then path is an HTTP Resource if
     *        type = XMLResource, then path is the key to a request attribute
     *        which contains the response to be marshalled. if type =
     *        XMLResponder, then path specifies the Responder Object to call if
     *        type = event, then path specifies the event to execute.
     */
    public Forward(String name, String type, String path) {
        this.name = name;
        this.type = ForwardType.getValueByString(type);
        this.path = path;
    }

    /**
     * Constructs an Forward
     * @param name String
     * @param name ForwardType
     * @param name path
     */
    public Forward(String name, ForwardType type, String path) {
        this.name = name;
        this.type = type;
        this.path = path;
    }

    public Forward(Forward forward) {
        this.name = forward.name;
        this.type = forward.type;
        this.path = forward.path;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the path.
     * @return String
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the type.
     * @return ForwardType
     */
    public ForwardType getType() {
        return type;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the path.
     * @param path The path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Sets the type.
     * @param type The type to set (HTMLResource, XMLResource, XMLResponder, or
     *        event)
     */
    public void setType(String type) {
        this.type = ForwardType.getValueByString(type);
    }

    /**
     * Sets the type.
     * @param type The ForwardType
     */
    public void setType(ForwardType type) {
        this.type = type;
    }
}
