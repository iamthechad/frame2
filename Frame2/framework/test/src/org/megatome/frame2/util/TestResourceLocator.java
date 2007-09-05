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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Test;
import org.megatome.frame2.Globals;

import servletunit.ServletContextSimulator;

public class TestResourceLocator {

   @After
public void tearDown() {
      ResourceLocator.setBasename(null);
   }

   @Test
   public void testDefaultBundle() throws Exception {
      ResourceBundle bundle = ResourceLocator.getBundle();

      assertNotNull(bundle);
      Enumeration<String> keys = bundle.getKeys();
      assertFalse(keys.hasMoreElements());
   }

   @Test
   public void testDefaultBundle_FR() throws Exception {
      ResourceBundle bundle = ResourceLocator.getBundle(Locale.FRENCH);

      assertNotNull(bundle);
      Enumeration<String> keys = bundle.getKeys();
      assertFalse(keys.hasMoreElements());
   }

   @Test
   public void testResetBundleName() throws Exception {
      ResourceLocator.setBasename("frame2-resource"); //$NON-NLS-1$
      
      ResourceBundle bundle = ResourceLocator.getBundle();
      assertHasHasNot(bundle,"tag.question","alt.tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
      bundle = ResourceLocator.getBundle(Locale.FRENCH);
      assertHasHasNot(bundle,"tag.question","alt.tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
      
      ResourceLocator.setBasename("alt-resource"); //$NON-NLS-1$
      
      bundle = ResourceLocator.getBundle();
      
      assertHasHasNot(bundle,"alt.tag.question","tag.question");       //$NON-NLS-1$ //$NON-NLS-2$
      bundle = ResourceLocator.getBundle(Locale.FRENCH);
      assertHasHasNot(bundle,"alt.tag.question","tag.question"); //$NON-NLS-1$ //$NON-NLS-2$
   }

   static public void assertHasHasNot(ResourceBundle bundle,String hasKey,String hasNotKey) {
      assertNotNull(bundle);
      assertNotNull(bundle.getString(hasKey));
      try {
         bundle.getString(hasNotKey);
         fail();
      } catch ( MissingResourceException expected ) {
    	  //expected
      }
   }

   @Test
   public void testSetResourceBundle() throws Exception {
      ServletContextSimulator context = new ServletContextSimulator();
      context.setInitParameter(Globals.RESOURCE_BUNDLE,"frame2-resource"); //$NON-NLS-1$

      ResourceLocator.setBasename("frame2-resource"); //$NON-NLS-1$

      ResourceBundle bundle = ResourceLocator.getBundle();
      
      assertNotNull(bundle);

      try {
			assertEquals("{0}Is this Frame2?",bundle.getString("tag.question")); //$NON-NLS-1$ //$NON-NLS-2$
         assertEquals("{0}Is this {1} Frame2?",bundle.getString("tag.question.with.parm")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (MissingResourceException e) {
         fail();
		}
   }

   @Test
   public void testSetResourceBundle_FR() throws Exception {
      ResourceLocator.setBasename("frame2-resource"); //$NON-NLS-1$

      ResourceBundle bundle = ResourceLocator.getBundle(Locale.FRENCH);

      assertNotNull(bundle);

      try {
         assertEquals("{0}Êtes-vous Bob?",bundle.getString("tag.question")); //$NON-NLS-1$ //$NON-NLS-2$
         assertEquals("{0}Êtes-vous {1} Bob?",bundle.getString("tag.question.with.parm")); //$NON-NLS-1$ //$NON-NLS-2$
      } catch (MissingResourceException e) {
         fail();
      }
      
      assertSame(bundle,ResourceLocator.getBundle(Locale.FRENCH));
   }

   @Test
   public void testSetResourceBundle_DE() throws Exception {
      ResourceLocator.setBasename("frame2-resource"); //$NON-NLS-1$

      ResourceBundle bundle = ResourceLocator.getBundle(Locale.GERMANY);

      assertNotNull(bundle);

      try {
         assertEquals("{0}Sind Sie Bob?",bundle.getString("tag.question")); //$NON-NLS-1$ //$NON-NLS-2$
         assertEquals("{0}Sind Sie {1} Bob?",bundle.getString("tag.question.with.parm")); //$NON-NLS-1$ //$NON-NLS-2$
      } catch (MissingResourceException e) {
         fail();
      }
      
      assertSame(bundle,ResourceLocator.getBundle(Locale.GERMAN));
   }
}
