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
package org.megatome.frame2.front;

import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.CommonsValidatorEvent;

/**
 * 
 */
public class Event1 extends CommonsValidatorEvent {

   private String parm1;
   private String parm2;
   private int    parm3;

	/**
	 * Constructor for Event1.
	 */
	public Event1() {
		super();
	}

	public boolean validate(Errors errors) {
      boolean result = true;

      if ( parm1 == null || parm1.indexOf('1') == -1 ) {
         result = false;
         if ( errors != null ) {
            errors.add("Event1.parm1",parm1);
         }
      }

      if ( parm2 == null || parm2.indexOf('2') == -1 ) {
         result = false;
         if ( errors != null ) {
            errors.add("Event1.parm2",parm2);
         }
      }

		return result;
	}

	/**
	 * Method getParm1.
	 * @return Object
	 */
	public String getParm1() {
		return parm1;
	}

	/**
	 * Returns the parm2.
	 * @return String
	 */
	public String getParm2() {
		return parm2;
	}

	/**
	 * Sets the parm1.
	 * @param parm1 The parm1 to set
	 */
	public void setParm1(String parm1) {
		this.parm1 = parm1;
	}

	/**
	 * Sets the parm2.
	 * @param parm2 The parm2 to set
	 */
	public void setParm2(String parm2) {
		this.parm2 = parm2;
	}

	/**
	 * Returns the parm3.
	 * @return int
	 */
	public int getParm3() {
		return parm3;
	}

	/**
	 * Sets the parm3.
	 * @param parm3 The parm3 to set
	 */
	public void setParm3(int parm3) {
		this.parm3 = parm3;
	}

}
