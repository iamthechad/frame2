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
package org.megatome.app.user;

import java.text.DateFormat;
import java.util.Date;


public class User{
   String  _creationDate;
   String  _firstName;
   String  _lastName;
   String  _email;


   static public String today() {
      Date date = new Date();

      DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
      DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.LONG);

      return dateFormat.format(date) + " " + timeFormat.format(date);
   }
   
   public void setCreationDate(String cd) {
      _creationDate = cd;
   }

   public String getCreationDate() {
      return _creationDate;
   }

   /**
    * Returns the email.
    * @return String
    */
   public String getEmail() {
      return _email;
   }

   /**
    * Returns the firstName.
    * @return String
    */
   public String getFirstName() {
      return _firstName;
   }

   /**
    * Returns the lastName.
    * @return String
    */
   public String getLastName() {
      return _lastName;
   }

   /**
    * Sets the email.
    * @param email The email to set
    */
   public void setEmail(String email) {
      _email = email;
   }

   /**
    * Sets the firstName.
    * @param firstName The firstName to set
    */
   public void setFirstName(String firstName) {
      _firstName = firstName;
   }

   /**
    * Sets the lastName.
    * @param lastName The lastName to set
    */
   public void setLastName(String lastName) {
      _lastName = lastName;
   }

}
