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
package org.megatome.frame2.template;

import java.util.HashMap;
import java.util.Map;

import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;
import org.megatome.frame2.template.TemplatePlugin;
import org.megatome.frame2.template.config.TemplateConfiguration;
import org.megatome.frame2.template.config.TemplateDef;

import servletunit.frame2.MockFrame2ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;

public class TestTemplatePluginLifecycle extends MockFrame2TestCase {

    private MockFrame2ServletContextSimulator context;
    private TemplatePlugin plugin;

    /**
     * Constructor for TestHttpFrontController.
     * @param name
     */
    public TestTemplatePluginLifecycle(String name) {
        super(name);
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        context = (MockFrame2ServletContextSimulator)getContext();
        plugin = new TemplatePlugin();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testTemplatePluginSingleLoadDefaultDir() {
        TemplateConfiguration config = testTemplatePlugin(null);

        TemplateDef def = config.getDefinition("foo");
        assertNotNull(def);
        assertTrue(def.getPath().equals("xxx.jsp"));
        assertTrue(def.getPutParams().isEmpty());

        def = config.getDefinition("bar");
        assertNotNull(def);
        assertTrue(def.getPath().equals("xxx.jsp"));
        Map params = def.getPutParams();
        assertTrue(params.size() == 1);
        assertNotNull(params.get("bar-put"));
        assertTrue(((String)params.get("bar-put")).equals("yyy.jsp"));

        def = config.getDefinition("baz");
        assertNotNull(def);
        assertTrue(def.getPath().equals("xxx.jsp"));
        assertTrue(def.getPutParams().isEmpty());
    }

    public void testTemplatePluginSingleLoad() {
        TemplateConfiguration config = testTemplatePlugin("/templates/good/single/");

        TemplateDef def = config.getDefinition("foo");
        assertNotNull(def);
        assertTrue(def.getPath().equals("xxx.jsp"));

        Map params = def.getPutParams();
        assertTrue(params.size() == 1);
        assertNotNull(params.get("yyy"));
        assertTrue(((String)params.get("yyy")).equals("yyy.jsp"));
    }

    public void testTemplatePluginDestroy() {
        testTemplatePluginSingleLoad();
        plugin.destroy(context, new HashMap());

        try {
            TemplateConfigFactory.instance();
            fail();
        } catch (TemplateException expected) {
            // Expected
        }
    }

    public void testNegativeTemplatePluginSingleLoad() {
        testNegativeTemplatePlugin("/templates/bad/single/");
    }

    public void testNegativeTemplatePluginTemplatePath() {
        String dir = "/templates/bad/validateTemplatePaths/";
        String exceptionMsg = testNegativeTemplatePlugin(dir);
        assertEquals(TemplatePlugin.PLUGIN_INIT_ERROR
                + TemplateConfiguration.TEMPLATE_PATH_EXCEPTION_MSG + dir
                + "badpath", exceptionMsg);
    }

    public void testNegativeTemplatePluginPutPath() {
        String dir = "/templates/bad/validateTemplatePutPaths/";
        String exceptionMsg = testNegativeTemplatePlugin(dir);
        assertEquals(TemplatePlugin.PLUGIN_INIT_ERROR
                + TemplateConfiguration.TEMPLATE_PUT_PATH_EXCEPTION_MSG + dir
                + "badpath", exceptionMsg);
    }

    public String testNegativeTemplatePlugin(String dir) {
        plugin.setConfigDir(dir);
        try {
            plugin.init(context, new HashMap());
        } catch (PluginException e) {
            return e.getMessage();
        }
        fail();
        return null;
    }

    private TemplateConfiguration testTemplatePlugin(String dir) {

        if (dir != null) {
            plugin.setConfigDir(dir);
        }

        try {
            plugin.init(context, new HashMap());
        } catch (PluginException e) {
            e.printStackTrace();
            fail();
        }

        TemplateConfiguration config = null;
        try {
            config = TemplateConfigFactory.instance();
        } catch (TemplateException e1) {
            fail();
        }
        return config;
    }

}
