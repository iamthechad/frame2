package org.megatome.frame2.template.config;

import org.megatome.frame2.template.TemplateConfigFactory;
import org.megatome.frame2.template.TemplateException;

import servletunit.ServletContextSimulator;
import servletunit.frame2.MockFrame2TestCase;

public class TestTemplateConfigFactory extends MockFrame2TestCase {

	private ServletContextSimulator _context;
   /**
    * Constructor for TestTemplateConfigFactory.
    * @param name
    */
   public TestTemplateConfigFactory(String name) {
      super(name);
   }
   
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		_context = (ServletContextSimulator) getContext();
	}
   
   public void testGetConfiguration() {
      try {
         TemplateConfigFactory.loadTemplateFile(_context, "/org/megatome/frame2/template/config/multTag-Put-Template.xml");
         
         assertNotNull( TemplateConfigFactory.instance());     
      } catch (TemplateException e) {
      	e.printStackTrace();
         fail(e.getMessage());
      }
   }
   

}
