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
package org.megatome.frame2.validator;

public class CommonsValidatorBean {

   /**
    * Constructor for CommonsValidatorBean.
    */
   public CommonsValidatorBean() {
      super();
   }
   
   String empty;
   String email;
   String required;
   String phone;
   String byteCV;
   String shortCV;
   String integer;
   String longCV;
   String floatCV;
   String doubleCV;
   String date;
   String creditCard;
   String minLength;
   String maxLength;
   String twoField1;
   String twoField2;
         

   /**
    * Returns the email.
    * @return String
    */
   public String getEmail() {
      return email;
   }

   /**
    * Returns the empty.
    * @return String
    */
   public String getEmpty() {
      return empty;
   }

   /**
    * Sets the email.
    * @param email The email to set
    */
   public void setEmail(String email) {
      this.email = email;
   }
   
   

   /**
    * Sets the empty.
    * @param empty The empty to set
    */
   public void setEmpty(String empty) {
      this.empty = empty;
   }

   /**
    * Returns the required.
    * @return String
    */
   public String getRequired() {
      return required;
   }

   /**
    * Sets the required.
    * @param required The required to set
    */
   public void setRequired(String required) {
      this.required = required;
   }

   /**
    * Returns the phone.
    * @return String
    */
   public String getPhone() {
      return phone;
   }

   /**
    * Sets the phone.
    * @param phone The phone to set
    */
   public void setPhone(String phone) {
      this.phone = phone;
   }

   /**
    * Returns the b.
    * @return String
    */
   public String getByte() {
      return byteCV;
   }

   /**
    * Returns the s.
    * @return String
    */
   public String getShort() {
      return shortCV;
   }

   /**
    * Sets the b.
    * @param b The b to set
    */
   public void setByte(String b) {
      this.byteCV = b;
   }

   /**
    * Sets the s.
    * @param s The s to set
    */
   public void setShort(String s) {
      this.shortCV = s;
   }

   /**
    * Returns the d.
    * @return String
    */
   public String getDouble() {
      return doubleCV;
   }

   /**
    * Returns the f.
    * @return String
    */
   public String getFloat() {
      return floatCV;
   }

   /**
    * Returns the integer.
    * @return String
    */
   public String getInteger() {
      return integer;
   }

   /**
    * Returns the l.
    * @return String
    */
   public String getLong() {
      return longCV;
   }

   /**
    * Sets the d.
    * @param d The d to set
    */
   public void setDouble(String d) {
      this.doubleCV = d;
   }

   /**
    * Sets the f.
    * @param f The f to set
    */
   public void setFloat(String f) {
      this.floatCV = f;
   }

   /**
    * Sets the integer.
    * @param integer The integer to set
    */
   public void setInteger(String integer) {
      this.integer = integer;
   }

   /**
    * Sets the l.
    * @param l The l to set
    */
   public void setLong(String l) {
      this.longCV = l;
   }

   /**
    * Returns the date.
    * @return String
    */
   public String getDate() {
      return date;
   }

   /**
    * Sets the date.
    * @param date The date to set
    */
   public void setDate(String date) {
      this.date = date;
   }

   /**
    * Returns the creditCard.
    * @return String
    */
   public String getCreditCard() {
      return creditCard;
   }

   /**
    * Returns the maxLength.
    * @return String
    */
   public String getMaxLength() {
      return maxLength;
   }

   /**
    * Returns the minLength.
    * @return String
    */
   public String getMinLength() {
      return minLength;
   }

   /**
    * Sets the creditCard.
    * @param creditCard The creditCard to set
    */
   public void setCreditCard(String creditCard) {
      this.creditCard = creditCard;
   }

   /**
    * Sets the maxLength.
    * @param maxLength The maxLength to set
    */
   public void setMaxLength(String maxLength) {
      this.maxLength = maxLength;
   }

   /**
    * Sets the minLength.
    * @param minLength The minLength to set
    */
   public void setMinLength(String minLength) {
      this.minLength = minLength;
   }

   /**
    * @return
    */
   public String getTwoField1() {
      return twoField1;
   }

   /**
    * @return
    */
   public String getTwoField2() {
      return twoField2;
   }

   /**
    * @param string
    */
   public void setTwoField1(String string) {
      this.twoField1 = string;
   }

   /**
    * @param string
    */
   public void setTwoField2(String string) {
      this.twoField2 = string;
   }

}
