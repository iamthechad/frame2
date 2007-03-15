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
package org.megatome.frame2.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.w3c.dom.Element;

/**
 * This class holds static methods helpful to writing Frame2 tests.
 */
public class Helper {

    private static Logger LOGGER = LoggerFactory.instance(Helper.class.getName());

    /**
     * Returns a string for the calendar with the format '2002-12-25'.
     * @param cal
     * @return String
     */
    public static String calendarToString(Calendar cal) {
       Calendar now = Calendar.getInstance();
       TimeZone tz = now.getTimeZone();
       int offset = tz.getRawOffset();

       if (tz.inDaylightTime(now.getTime())) {
          offset -= tz.getDSTSavings();
       }
       
       if (tz.inDaylightTime(cal.getTime())) {
          offset += tz.getDSTSavings();
       }

       Date d = new Date(cal.getTimeInMillis() - offset);
       return new SimpleDateFormat("yyyy-MM-dd").format(d); //$NON-NLS-1$
    }

    static public Object unmarshall(String path,String pkg,ClassLoader loader) throws Exception {
        LOGGER.debug("Unmarshalling " + path + " in package " + pkg); //$NON-NLS-1$ //$NON-NLS-2$
        JAXBContext jc = JAXBContext.newInstance(pkg, loader);
        Unmarshaller u = jc.createUnmarshaller();
		  InputStream istream = ClassLoader.getSystemResourceAsStream(path);

        return u.unmarshal(istream);
    }

    static public OutputStream marshall(Object obj,String pkg,ClassLoader loader) throws Exception {
        LOGGER.debug("Marshalling " + obj + " in package " + pkg); //$NON-NLS-1$ //$NON-NLS-2$
        JAXBContext jc = JAXBContext.newInstance(pkg, loader);
        Marshaller m = jc.createMarshaller();
        OutputStream ostream = new ByteArrayOutputStream();   
      
        m.marshal(obj, ostream);

        return ostream;
    }

	static public InputStream getInputStreamFor(String path,Class<?> clazz) {
      return clazz.getClassLoader().getResourceAsStream(path);
   }

	static public Element[] loadEvents(String path,Class<?> clazz) throws Exception {
      Element[] result = new Element[1];
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

      result[0] = builder.parse(Helper.getInputStreamFor(path,clazz)).getDocumentElement();

      return result;
   }






}
