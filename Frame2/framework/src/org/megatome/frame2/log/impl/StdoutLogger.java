/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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
package org.megatome.frame2.log.impl;

import org.megatome.frame2.log.Logger;

/**
 * 
 */
public class StdoutLogger implements Logger {

   private String _name;

   final private String DEBUG = "DEBUG: ";
   final private String INFO = "INFO: ";
   final private String WARN = "WARN: ";
   final private String SEVERE = "SEVERE: ";

   public StdoutLogger(String name) {
      _name = name;
   }

   public String getName() {
      return _name;
   }

   public void debug(String message) {
      if (isDebugEnabled()) {
         System.out.println(DEBUG + message);
      }
   }

   public void debug(String message, Throwable t) {
      if (isDebugEnabled()) {
         System.out.println(DEBUG + message);
         if (t != null) {
            t.printStackTrace();
         }
      }
   }

   public void info(String message) {
      if (isInfoEnabled()) {
         System.out.println(INFO + message);
      }
   }

   public void info(String message, Throwable t) {
      if (isInfoEnabled()) {
         System.out.println(INFO + message);
         if (t != null) {
            t.printStackTrace();
         }
      }
   }

   public void warn(String message) {
      if (isInfoEnabled()) {
         System.out.println(WARN + message);
      }
   }

   public void warn(String message, Throwable t) {
      if (isWarnEnabled()) {
         System.out.println(WARN + message);
         if (t != null) {
            t.printStackTrace();
         }
      }
   }

   public void severe(String message) {
      if (isSevereEnabled()) {
         System.out.println(SEVERE + message);
      }
   }

   public void severe(String message, Throwable t) {
      if (isSevereEnabled()) {
         System.out.println(SEVERE + message);
         if (t != null) {
            t.printStackTrace();
         }
      }
   }

   public boolean isDebugEnabled() {
      return false;
   }

   public boolean isInfoEnabled() {
      return false;
   }

   public boolean isWarnEnabled() {
      return true;
   }

   public boolean isSevereEnabled() {
      return true;
   }
}
