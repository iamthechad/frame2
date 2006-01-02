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
package org.megatome.frame2.introspector;

/**
 * A nested bean that also nests another bean, both as a simple
 * property as well as an indexed property.
 */

// DOC: Need to remind user that set/get in beans need to provide live objects;
// introspector won't manufacture non-standard types.

public class Bean2 {

   private static int DEFAULT_ARRAY_SIZE = 10;

	private String stringP;
	private Bean3 bean3P;
	private Bean3[] bean3PArray = new Bean3[DEFAULT_ARRAY_SIZE];

   {
      for ( int i = 0 ; i < DEFAULT_ARRAY_SIZE ; i++ ) {
         bean3PArray[i] = new Bean3( );
      }
   }

	/**
	 * Returns the nestedStringP.
	 * @return String
	 */
	public String getStringP() {
		return stringP;
	}

	/**
	 * Sets the nestedStringP.
	 * @param nestedStringP The nestedStringP to set
	 */
	public void setStringP(String nestedStringP) {
		stringP = nestedStringP;
	}

	public Bean3 getBean3P() {
		if (bean3P == null) {
			bean3P = new Bean3();
		}
		return bean3P;
	}

	public void setBean3P(Bean3 nestedBean3P) {
		bean3P = nestedBean3P;
	}

	public Bean3[] getBean3PArray() {
		return bean3PArray;
	}

	public void setBean3PArray(Bean3[] bean3PArray) {
		this.bean3PArray = bean3PArray;
	}

	public Bean3 getBean3PArray(int index) {
		return bean3PArray[index];
	}

	public void setBean3PArray(int index, Bean3 indexedBean) {
		bean3PArray[index] = indexedBean;
	}

}
