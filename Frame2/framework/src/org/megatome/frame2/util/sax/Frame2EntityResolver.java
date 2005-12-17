package org.megatome.frame2.util.sax;

import java.io.InputStream;

import org.megatome.frame2.Globals;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class Frame2EntityResolver implements EntityResolver {

	/**
	 * @param publicId
	 * @param systemId
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
	public InputSource resolveEntity(String publicId, String systemId) {
        
        if ((publicId == null) || (systemId == null)) {
        	return null;
        }
        
        String _dtdFile = null;
        if ((publicId.indexOf(Globals.FRAME2_DTD_PUBLIC_ID) != -1) &&
            (systemId.indexOf(Globals.FRAME2_DTD_SYSTEM_ID) != -1)) {
            _dtdFile = Globals.FRAME2_DTD_FILE;
        }
        
        if ((publicId.indexOf(Globals.FRAME2_TEMPLATE_DTD_PUBLIC_ID) != -1) &&
            (systemId.indexOf(Globals.FRAME2_TEMPLATE_DTD_SYSTEM_ID) != -1)) {
            _dtdFile = Globals.FRAME2_TEMPLATE_DTD_FILE;
        }

        if (_dtdFile == null) {
        	return null;
        }
        
        InputStream stream = getClass().getClassLoader().getResourceAsStream(_dtdFile);
        
        return (stream == null) ? null : new InputSource(stream);
	}

}
