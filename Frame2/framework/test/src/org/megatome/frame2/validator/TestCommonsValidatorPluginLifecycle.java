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
package org.megatome.frame2.validator;

import java.util.HashMap;

import org.apache.commons.validator.ValidatorResources;
import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.validator.CommonsValidatorPlugin;
import org.megatome.frame2.validator.CommonsValidatorWrapper;

import servletunit.frame2.MockFrame2ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;

public class TestCommonsValidatorPluginLifecycle extends MockFrame2TestCase {

    private MockFrame2ServletContextSimulator context;
    private CommonsValidatorPlugin plugin;

    /**
     * Constructor for TestCommonsValidatorPluginLifecycle.
     * @param name
     */
    public TestCommonsValidatorPluginLifecycle(String name) {
        super(name);
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
	protected void setUp() throws Exception {
        super.setUp();
        this.context = (MockFrame2ServletContextSimulator)getContext();
        this.plugin = new CommonsValidatorPlugin();
        CommonsValidatorWrapper.release();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void pluginInit() {
        try {
            this.plugin.init(this.context, new HashMap<String, String>());
        } catch (PluginException e) {
            fail();
        }
    }

    public void pluginDestroy() {
        this.plugin.destroy(this.context, new HashMap<String, String>());
    }

    public void testPluginDefault() {
        pluginInit();
        ValidatorResources res = CommonsValidatorWrapper
                .getValidatorResources();
        assertNotNull(res);

        pluginDestroy();
        res = CommonsValidatorWrapper.getValidatorResources();
        assertNull(res);

    }

    public void testNegativePluginDefault() {

        CommonsValidatorWrapper
                .setFilePath("/org/megatome/frame2/validator/config"); //$NON-NLS-1$
        CommonsValidatorWrapper.setMappingsFile("dude.xml"); //$NON-NLS-1$

        try {
            this.plugin.init(this.context, new HashMap<String, String>());
        } catch (PluginException e) {

            ValidatorResources res = CommonsValidatorWrapper
                    .getValidatorResources();
            assertNull(res);

            pluginDestroy();
            res = CommonsValidatorWrapper.getValidatorResources();
            assertNull(res);
            return;
        }
        fail();

    }

}
