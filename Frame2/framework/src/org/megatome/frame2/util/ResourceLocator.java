/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Megatome Technologies.  All rights
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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;

/**
 * The ResourceLocator provides services for retrieving resource bundles from the class path.
 * It also provides caching of the bundles for different locales.  The resource
 * bundle basename is typically set through the <code>Frame2ContextListener</code>.  Once set, the
 * locator will serve up any configured bundle for a given locale as implemented in the Java
 * <code>ResourceBundle</code> class.<br>
 * <br>
 * The locator is used by the framework when it needs to resolve resource bundles to perform
 * services (usually in the presentation layer, for example, in the taglibs).  To enable the use
 * of internationalization by the framework the Frame2 eveloper must configure resource bundles for
 * the necessary locales and configure the framework to read them.
 */
public class ResourceLocator {
	static private Logger LOGGER = LoggerFactory.instance(ResourceLocator.class.getName());
	static private ResourceBundle _emptyBundle = new EmptyBundle();
	static private String _basename;
	static private Map _bundleCache = new HashMap();

	private ResourceLocator() {
	}

   /**
    * Return the default resource bundle for the application.
    * @return ResourceBundle the default resource bundle for the application.
    */
	static public ResourceBundle getBundle() {
		return getBundle(null);
	}

   /**
    * Return the resource bundle for the specified locale.  If locale is null, will return the
    * default bundle.
    * @param locale desired locale
    * @return ResourceBundle resource bundle for that locale, null if not found.
    */
	static public ResourceBundle getBundle(Locale locale) {
		if (_basename == null) {
			LOGGER.warn("Empty (default) resource being used");
			return _emptyBundle;
		}

		ResourceBundle result = null;

		if (locale == null) {
			locale = Locale.getDefault();
		}

		result = (ResourceBundle) _bundleCache.get(locale);

		if (result == null) {
			result = ResourceBundle.getBundle(_basename, locale);

			if (result != null) {
				_bundleCache.put(locale, result);
			}
		}

		return result;
	}

   /**
    * Set the basename for the framework to use.  The basename will normally be set by the framework
    * at start-up.
    * @param name
    */
	static public void setBasename(String basename) {
      _bundleCache.clear();
		_basename = basename;
	}

	private static class EmptyBundle extends ResourceBundle {
		Enumeration _emptyEnumeration = new EmptyEnumeration();

		protected Object handleGetObject(String key) {
			return null;
		}

		public Enumeration getKeys() {
			return _emptyEnumeration;
		}

		private class EmptyEnumeration implements Enumeration {
			public boolean hasMoreElements() {
				return false;
			}

			public Object nextElement() {
				return null;
			}
		}
	}

   /**
    * Get the current basename in use.
    * @return String
    */
	public static String getBasename() {
		return _basename;
	}

}
