package servletunit.frame2;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import servletunit.ServletContextSimulator;

/**
 * @author mday
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MockFrame2ServletContextSimulator extends ServletContextSimulator {
	public String getRealPath(String path)
	{
		String realPath = null;
		URL templateDirURL = null;
		try {
		   templateDirURL = getResource(path);		
		} catch (MalformedURLException e) {
			return null;
		}
		File f = new File(templateDirURL.getFile().replace('/', File.separatorChar));
		realPath = f.getPath();
		return realPath;
	}
}
