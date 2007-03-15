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
package org.megatome.frame2.util.sax;

import junit.framework.TestCase;

import org.megatome.frame2.Globals;
import org.xml.sax.InputSource;

public class TestFrame2EntityParser extends TestCase {

    private Frame2EntityResolver resolver = null;

    // private static final String GOOD_FRAME2_PUBLIC_ID = "-//Megatome
    // Technologies//DTD Frame2 Configuration 1.0//EN";
    // private static final String GOOD_FRAME2_SYSTEM_ID =
    // "http://frame2.sourceforge.net/dtds/frame2-config_1_0.dtd";

    private static final String BAD_FRAME2_PUBLIC_ID = "-//Megatome Technologies//DTD Bob2 Configuration 1.0//EN"; //$NON-NLS-1$
    private static final String BAD_FRAME2_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/bob2-config_1_0.dtd"; //$NON-NLS-1$

    // private static final String GOOD_TEMPLATE_PUBLIC_ID = "-//Megatome
    // Technologies//DTD Frame2 Template Plugin 1.0//EN";
    // private static final String GOOD_TEMPLATE_SYSTEM_ID =
    // "http://frame2.sourceforge.net/dtds/frame2-template_1_0.dtd";

    private static final String BAD_TEMPLATE_PUBLIC_ID = "-//Megatome Technologies//DTD Frame2 Bogus Plugin 1.0//EN"; //$NON-NLS-1$
    private static final String BAD_TEMPLATE_SYSTEM_ID = "http://frame2.sourceforge.net/dtds/frame2-bogus_1_0.dtd"; //$NON-NLS-1$

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
	protected void setUp() throws Exception {
        super.setUp();
        this.resolver = new Frame2EntityResolver();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testResolveDTD() {
        InputSource is = null;
        is = this.resolver.resolveEntity(Globals.FRAME2_DTD_PUBLIC_ID,
                Globals.FRAME2_DTD_SYSTEM_ID);

        assertNotNull("InputSource should not be null", is); //$NON-NLS-1$
    }

    public void testNegativeResolveDTD() {
        InputSource is = null;
        is = this.resolver.resolveEntity(BAD_FRAME2_PUBLIC_ID, BAD_FRAME2_SYSTEM_ID);

        assertNull("InputSource should be null", is); //$NON-NLS-1$
    }

    public void testNegativePublicIdResolveDTD() {
        InputSource is = null;
        is = this.resolver.resolveEntity(BAD_FRAME2_PUBLIC_ID,
                Globals.FRAME2_DTD_SYSTEM_ID);

        assertNull("InputSource should be null", is); //$NON-NLS-1$
    }

    public void testNegativeSystemIdResolveDTD() {
        InputSource is = null;
        is = this.resolver.resolveEntity(Globals.FRAME2_DTD_PUBLIC_ID,
                BAD_FRAME2_SYSTEM_ID);

        assertNull("InputSource should be null", is); //$NON-NLS-1$
    }

    public void testResolveTemplateDTD() {
        InputSource is = null;
        is = this.resolver.resolveEntity(Globals.FRAME2_TEMPLATE_DTD_PUBLIC_ID,
                Globals.FRAME2_TEMPLATE_DTD_SYSTEM_ID);

        assertNotNull("InputSource should not be null", is); //$NON-NLS-1$
    }

    public void testNegativeResolveTemplateDTD() {
        InputSource is = null;
        is = this.resolver.resolveEntity(BAD_TEMPLATE_PUBLIC_ID,
                BAD_TEMPLATE_SYSTEM_ID);

        assertNull("InputSource should be null", is); //$NON-NLS-1$
    }

    public void testNegativePublicIdResolveTemplateDTD() {
        InputSource is = null;
        is = this.resolver.resolveEntity(BAD_TEMPLATE_PUBLIC_ID,
                Globals.FRAME2_TEMPLATE_DTD_SYSTEM_ID);

        assertNull("InputSource should be null", is); //$NON-NLS-1$
    }

    public void testNegativeSystemIdResolveTemplateDTD() {
        InputSource is = null;
        is = this.resolver.resolveEntity(Globals.FRAME2_TEMPLATE_DTD_PUBLIC_ID,
                BAD_TEMPLATE_SYSTEM_ID);

        assertNull("InputSource should be null", is); //$NON-NLS-1$
    }

    public void testNullPublicId() {
        InputSource is = null;
        is = this.resolver.resolveEntity(null, Globals.FRAME2_DTD_SYSTEM_ID);

        assertNull("InputSource should be null", is); //$NON-NLS-1$
    }

    public void testNullSystemId() {
        InputSource is = null;
        is = this.resolver.resolveEntity(Globals.FRAME2_DTD_PUBLIC_ID, null);

        assertNull("InputSource should be null", is); //$NON-NLS-1$
    }

}
