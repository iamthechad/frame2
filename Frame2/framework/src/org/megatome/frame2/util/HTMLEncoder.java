/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2006 Megatome Technologies.  All rights
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

/**
 * HTMLEncoder.java
 */
public class HTMLEncoder {
   public static final char LT = '<';
   public static final char GT = '>';
   public static final char AMP = '&';
   public static final char QUOTE = '"';
   public static final char TICK = '\'';
   public static final String LTSTR = "&lt;";
   public static final String GTSTR = "&gt;";
   public static final String AMPSTR = "&amp;";
   public static final String QUOTESTR = "&quot;";
   public static final String TICKSTR = "&#39;";
   public static final int XSIZE = 100;

   public static String encode(String input) {
      int len = input.length();
      StringBuffer buffer = new StringBuffer(len + XSIZE);

      for (int index = 0; index < len; index++) {
         char c = input.charAt(index);

         switch (c) {
         case GT:
            buffer.append(GTSTR);

            break;

         case LT:
            buffer.append(LTSTR);

            break;

         case AMP:
            buffer.append(AMPSTR);

            break;

         case QUOTE:
            buffer.append(QUOTESTR);

            break;

         case TICK:
            buffer.append(TICKSTR);

            break;

         default:
            buffer.append(c);

            break;
         }
      }

      return buffer.toString();
   }
}
