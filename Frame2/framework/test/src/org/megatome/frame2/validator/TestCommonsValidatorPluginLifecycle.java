package org.megatome.frame2.validator;

import java.util.HashMap;

import org.apache.commons.validator.ValidatorResources;
import org.megatome.frame2.plugin.PluginException;
import org.megatome.frame2.validator.CommonsValidatorPlugin;
import org.megatome.frame2.validator.CommonsValidatorWrapper;

import servletunit.frame2.MockFrame2ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;

/**
 * @author hmilligan To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class TestCommonsValidatorPluginLifecycle extends MockFrame2TestCase {

    private MockFrame2ServletContextSimulator _context;
    private CommonsValidatorPlugin _plugin;

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
    protected void setUp() throws Exception {
        super.setUp();
        _context = (MockFrame2ServletContextSimulator)getContext();
        _plugin = new CommonsValidatorPlugin();
        CommonsValidatorWrapper.release();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void pluginInit() {
        try {
            _plugin.init(_context, new HashMap());
        } catch (PluginException e) {
            fail();
        }
    }

    public void pluginDestroy() {
        _plugin.destroy(_context, new HashMap());
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
                .setFilePath("/org/megatome/frame2/validator/config");
        CommonsValidatorWrapper.setMappingsFile("dude.xml");

        try {
            _plugin.init(_context, new HashMap());
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
