package servletunit.frame2;

import javax.servlet.ServletContext;

import servletunit.ServletConfigSimulator;


public class MockFrame2ServletConfigSimulator extends ServletConfigSimulator {
	ServletContext _frame2ServletContext;
	
	public MockFrame2ServletConfigSimulator()
	{
		super();
		_frame2ServletContext = new MockFrame2ServletContextSimulator();
	}

	public ServletContext getServletContext()
	{
		 return _frame2ServletContext;
	}
}
