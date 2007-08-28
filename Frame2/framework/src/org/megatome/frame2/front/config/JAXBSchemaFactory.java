package org.megatome.frame2.front.config;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class JAXBSchemaFactory {
	private static Logger LOGGER = LoggerFactory.instance(JAXBSchemaFactory.class
            .getName());
	private static ServletContext context = null;
	private static SchemaFactory SF = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema"); //$NON-NLS-1$
	
	private JAXBSchemaFactory() {
		// Not Public
	}
	
	public static void setServletContext(final ServletContext context) {
		JAXBSchemaFactory.context = context;
	}
	
	public static void clearContext() {
		JAXBSchemaFactory.context = null;
	}
	
	public static Schema loadSchema(final String location, final Class<?> clazz) {
		InputStream is = null;
		if (context != null) {
			is = context.getResourceAsStream(location);
			if (is != null) {
				return getSchemaFromStream(is, location);
			}
		}
		
		is = clazz.getResourceAsStream(location);
		if (is != null) {
			return getSchemaFromStream(is, location);
		} 
		is = clazz.getClassLoader().getResourceAsStream(location);
		if (is != null) {
			return getSchemaFromStream(is, location);
		}
		
		return null;
	}
	
	private static Schema getSchemaFromStream(final InputStream is, final String location) {
		try {
			Schema s = SF.newSchema(new SAXSource(new InputSource(is)));
			if (s != null) {
				LOGGER.info("Successfully loaded schema at: " + location); //$NON-NLS-1$
			} else {
				LOGGER.severe("Schema failed to load: " + location); //$NON-NLS-1$
			}
			return s;
		} catch (SAXException e) {
			LOGGER.severe("Error loading schema at location: " + location,e); //$NON-NLS-1$
		}
		
		return null;
	}
}
