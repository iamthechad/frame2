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
package org.megatome.frame2.validator;

import java.util.Date;

import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;
import org.megatome.frame2.errors.Error;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;

public class CommonsFieldValidator {
   
   /**
    * Constructor for CommonsFieldValidator.
    */
   public CommonsFieldValidator() {
      super();
   }
   
   protected static Logger getLogger() {
      return LoggerFactory.instance(CommonsFieldValidator.class.getName());
   }
   
   protected static void addError(
      ValidatorAction va,
      Errors errors,
      Field field) {
      addError(va, errors, field, null, null);
   }
   
   protected static void addError(
      ValidatorAction va,
      Errors errors,
      Field field,
      Object validatorErrorValue) {
      addError(va, errors, field, validatorErrorValue, null);
   }
   
   protected static void addError(
      ValidatorAction va,
      Errors errors,
      Field field,
      Object validatorErrorValue1,
      Object validatorErrorValue2) {
      Arg arg = field.getArg0();
      String validatorKey = va.getMsg();
      if (arg != null) {
         String fieldMsgKey = arg.getKey();
         if (fieldMsgKey != null) {
            Error fieldMsg = new Error(fieldMsgKey);
            Error validateError =
               new Error(
                  validatorKey,
                  fieldMsg,
                  validatorErrorValue1,
                  validatorErrorValue2);
            errors.add(validateError);
         }
      }
   }
   
   public static boolean validateEmail(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateEmail(value, va, errors, field);
   }
   
   public static boolean validateEmail(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      // If value is null, then don't validate email.  This allows the email field to be optional on the input.
      if (value == null) {
         return true;
      }
      if (!GenericValidator.isEmail(value)) {
         addError(va, errors, field);
         return false;
      } else {
         return true;
      }
   }
   
   public static boolean validateRequired(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateRequired(value, va, errors, field);
   }
   
   public static boolean validateRequired(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (GenericValidator.isBlankOrNull(value)) {
         addError(va, errors, field);
         return false;
      } else {
         return true;
      }
   }
   
   public static boolean mask(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return mask(value, va, errors, field);
   }
   
   public static boolean mask(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return true;
      }
      String mask = field.getVarValue("mask");
      if (mask == null) {
         return true;
      }
      try {
         if (!GenericValidator.matchRegexp(value, mask)) {
            addError(va, errors, field);
            return false;
         } else {
            return true;
         }
      } catch (Exception e) {
         // TODO Waiting for Jim's response on what to do in this case...
         getLogger().info("mask = " + mask + " caused an exception:" + e);
      }
      return true;
   }
   
   public static Byte validateByte(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateByte(value, va, errors, field);
   }
   
   public static Byte validateByte(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return null;
      }
      Byte result = null;
      result = GenericTypeValidator.formatByte(value);
      if (result == null) {
         addError(va, errors, field);
      }
      return result;
   }
   
   public static Short validateShort(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateShort(value, va, errors, field);
   }
   
   public static Short validateShort(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return null;
      }
      Short result = null;
      result = GenericTypeValidator.formatShort(value);
      if (result == null) {
         addError(va, errors, field);
      }
      return result;
   }
   
   public static Integer validateInteger(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateInteger(value, va, errors, field);
   }
   
   public static Integer validateInteger(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return null;
      }
      Integer result = null;
      result = GenericTypeValidator.formatInt(value);
      if (result == null) {
         addError(va, errors, field);
      }
      return result;
   }
   
   public static Long validateLong(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateLong(value, va, errors, field);
   }
   
   public static Long validateLong(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return null;
      }
      Long result = null;
      result = GenericTypeValidator.formatLong(value);
      if (result == null) {
         addError(va, errors, field);
      }
      return result;
   }
   
   public static Float validateFloat(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateFloat(value, va, errors, field);
   }
   
   public static Float validateFloat(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return null;
      }
      Float result = null;
      result = GenericTypeValidator.formatFloat(value);
      if (result == null) {
         addError(va, errors, field);
      }
      return result;
   }
   
   public static Double validateDouble(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateDouble(value, va, errors, field);
   }
   
   public static Double validateDouble(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return null;
      }
      Double result = null;
      result = GenericTypeValidator.formatDouble(value);
      if (result == null) {
         addError(va, errors, field);
      }
      return result;
   }
   
   public static Date validateDate(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateDate(value, va, errors, field);
   }
   
   public static Date validateDate(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return null;
      }
      String datePattern = field.getVarValue("datePattern");
      String datePatternStrict = field.getVarValue("datePatternStrict");
      Date result = null;
      try {
         if (datePattern != null && datePattern.length() > 0) {
            result = GenericTypeValidator.formatDate(value, datePattern, false);
         } else if (
            datePatternStrict != null && datePatternStrict.length() > 0) {
            result =
               GenericTypeValidator.formatDate(value, datePatternStrict, true);
         } else {
            return null;
         }
      } catch (Exception e) {
         getLogger().info(
            "Date validation failed for value = " + value + " exception: " + e);
      }
      if (result == null) {
         addError(va, errors, field);
      }
      return result;
   }
   
   public static boolean validateIntRange(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateIntRange(value, va, errors, field);
   }
   
   public static boolean validateIntRange(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return true;
      }
      String sMin = field.getVarValue("min");
      if (sMin == null) {
         return true;
      }
      String sMax = field.getVarValue("max");
      if (sMax == null) {
         return true;
      }
      try {
         int integer = Integer.parseInt(value);
         int min = Integer.parseInt(sMin);
         int max = Integer.parseInt(sMax);
         if (!GenericValidator.isInRange(integer, min, max)) {
            addError(va, errors, field, sMin, sMax);
            return false;
         }
      } catch (Exception e) {
         addError(va, errors, field, sMin, sMax);
         return false;
      }
      return true;
   }
   
   public static boolean validateFloatRange(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateFloatRange(value, va, errors, field);
   }
   
   public static boolean validateFloatRange(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return true;
      }
      String sMin = field.getVarValue("min");
      if (sMin == null) {
         return true;
      }
      String sMax = field.getVarValue("max");
      if (sMax == null) {
         return true;
      }
      try {
         float aFloat = Float.parseFloat(value);
         float min = Float.parseFloat(sMin);
         float max = Float.parseFloat(sMax);
         if (!GenericValidator.isInRange(aFloat, min, max)) {
            addError(va, errors, field, sMin, sMax);
            return false;
         }
      } catch (Exception e) {
         addError(va, errors, field, sMin, sMax);
         return false;
      }
      return true;
   }
   
   public static boolean validateDoubleRange(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateDoubleRange(value, va, errors, field);
   }
   
   public static boolean validateDoubleRange(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return true;
      }
      String sMin = field.getVarValue("min");
      if (sMin == null) {
         return true;
      }
      String sMax = field.getVarValue("max");
      if (sMax == null) {
         return true;
      }
      try {
         double aDouble = Double.parseDouble(value);
         double min = Double.parseDouble(sMin);
         double max = Double.parseDouble(sMax);
         if (!GenericValidator.isInRange(aDouble, min, max)) {
            addError(va, errors, field, sMin, sMax);
            return false;
         }
      } catch (Exception e) {
         addError(va, errors, field, sMin, sMax);
         return false;
      }
      return true;
   }
   
   public static Long validateCreditCard(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateCreditCard(value, va, errors, field);
   }
   
   public static Long validateCreditCard(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return null;
      }
      Long result = null;
      result = GenericTypeValidator.formatCreditCard(value);
      if (result == null) {
         addError(va, errors, field);
      }
      return result;
   }
   
   public static boolean validateMinLength(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateMinLength(value, va, errors, field);
   }
   
   public static boolean validateMinLength(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return true;
      }
      String sMinLength = field.getVarValue("minlength");
      if (sMinLength == null) {
         return true;
      }
      if (value != null) {
         try {
            int min = Integer.parseInt(sMinLength);
            if (!GenericValidator.minLength(value, min)) {
               addError(va, errors, field, Integer.toString(min));
               return false;
            }
         } catch (Exception e) {
            addError(va, errors, field);
            return false;
         }
      }
      return true;
   }
   
   public static boolean validateMaxLength(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      return validateMaxLength(value, va, errors, field);
   }
   
   public static boolean validateMaxLength(
      String value,
      ValidatorAction va,
      Errors errors,
      Field field) {
      if (value == null) {
         return true;
      }
      String sMaxLength = field.getVarValue("maxlength");
      if (sMaxLength == null) {
         return true;
      }
      if (value != null) {
         try {
            int max = Integer.parseInt(sMaxLength);
            if (!GenericValidator.maxLength(value, max)) {
               addError(va, errors, field, Integer.toString(max));
               return false;
            }
         } catch (Exception e) {
            addError(va, errors, field);
            return false;
         }
      }
      return true;
   }
   
   /**
    * Validate the contents of two fields to verify that they match. This
    * is useful for comparing a password and a confirmation value, 
    * for instance.
    * @param bean
    * @param va
    * @param errors
    * @param field
    * @return
    */
   public static boolean validateTwoFields(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      String sProperty2 = field.getVarValue("secondProperty");
      String value2 = ValidatorUtil.getValueAsString(bean, sProperty2);
      if (!GenericValidator.isBlankOrNull(value)) {
         try {
            if (!value.equals(value2)) {
               Arg arg = field.getArg1();
               Error fieldMsg = null;
               if (arg != null) {
                  String fieldMsgKey = arg.getKey();
                  if (fieldMsgKey != null) {
                     fieldMsg = new Error(fieldMsgKey);
                  }
               }
               addError(va, errors, field, fieldMsg);
               return false;
            }
         } catch (Exception e) {
            addError(va, errors, field, null);
            return false;
         }
      }
      return true;
   }

   public static final String FIELD_TEST_NULL = "NULL";
   public static final String FIELD_TEST_NOTNULL = "NOTNULL";
   public static final String FIELD_TEST_EQUAL = "EQUAL";

   public static boolean validateRequiredIf(
      Object bean,
      ValidatorAction va,
      Errors errors,
      Field field) {
      String value = ValidatorUtil.getValueAsString(bean, field.getProperty());
      boolean required = false;
      int i = 0;
      String fieldJoin = "AND";
      if (!GenericValidator.isBlankOrNull(field.getVarValue("fieldJoin"))) {
         fieldJoin = field.getVarValue("fieldJoin");
      }
      if (fieldJoin.equalsIgnoreCase("AND")) {
         required = true;
      }
      while (!GenericValidator
         .isBlankOrNull(field.getVarValue("field[" + i + "]"))) {
         String dependProp = field.getVarValue("field[" + i + "]");
         String dependTest = field.getVarValue("fieldTest[" + i + "]");
         String dependTestValue = field.getVarValue("fieldValue[" + i + "]");
         String dependIndexed = field.getVarValue("fieldIndexed[" + i + "]");
         if (dependIndexed == null) {
            dependIndexed = "false";
         }
         String dependVal = null;
         boolean thisRequired = false;
         if (field.isIndexed() && dependIndexed.equalsIgnoreCase("true")) {
            String key = field.getKey();
            if ((key.indexOf("[") > -1) && (key.indexOf("]") > -1)) {
               String ind = key.substring(0, key.indexOf(".") + 1);
               dependProp = ind + dependProp;
            }
         }
         dependVal = ValidatorUtil.getValueAsString(bean, dependProp);
         if (dependTest.equals(FIELD_TEST_NULL)) {
            if ((dependVal != null) && (dependVal.length() > 0)) {
               thisRequired = false;
            } else {
               thisRequired = true;
            }
         }
         if (dependTest.equals(FIELD_TEST_NOTNULL)) {
            if ((dependVal != null) && (dependVal.length() > 0)) {
               thisRequired = true;
            } else {
               thisRequired = false;
            }
         }
         if (dependTest.equals(FIELD_TEST_EQUAL)) {
            thisRequired = dependTestValue.equalsIgnoreCase(dependVal);
         }
         if (fieldJoin.equalsIgnoreCase("AND")) {
            required = required && thisRequired;
         } else {
            required = required || thisRequired;
         }
         i++;
      }
      if (required) {
         if (GenericValidator.isBlankOrNull(value)) {
            addError(va, errors, field, null);
            return false;
         } else {
            return true;
         }
      }
      return true;
   }
}
