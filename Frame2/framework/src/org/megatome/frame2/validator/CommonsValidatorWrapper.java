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
package org.megatome.frame2.validator;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.megatome.frame2.Globals;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.xml.sax.SAXException;

public class CommonsValidatorWrapper {

	// public static final String RULES_FILE = "commons-validator-rules.xml";
	// public static final String MAPPINGS_FILE =
	// "commons-validation-mappings.xml";
	// public static final String ERRORS_KEY =
	// "org.megatome.frame2.errors.Errors";

	static String cvFilePath = "/WEB-INF/commonsvalidator"; //$NON-NLS-1$
	// static String cvRulesFile = Globals.RULES_FILE;
	static String cvMappingsFile = Globals.MAPPINGS_FILE;

	static ValidatorResources validatorResources;

	private static Logger LOGGER = LoggerFactory
			.instance(CommonsValidatorWrapper.class.getName());

	private CommonsValidatorWrapper() {
		// not public
	}

	public static void load(ServletContext context)
			throws CommonsValidatorException {

		// validatorResources = new ValidatorResources();

		String filePath = cvFilePath + Globals.FORWARD_SLASH + cvMappingsFile;
		InputStream in = context.getResourceAsStream(filePath);
		if (in == null) {
			// validatorResources = null;
			throw new CommonsValidatorException("invalid filePath: " + filePath); //$NON-NLS-1$
		}
		try {
			validatorResources = new ValidatorResources(in);
		} catch (IOException e) {
			validatorResources = null;
			throw new CommonsValidatorException(e);
		} catch (SAXException e) {
			validatorResources = null;
			throw new CommonsValidatorException(e);
		}

		/*
		 * List<String> fileNames = new ArrayList<String>();
		 * fileNames.add(cvRulesFile); fileNames.add(cvMappingsFile);
		 * 
		 * for (int i = 0; i < fileNames.size(); i++) { String filePath =
		 * cvFilePath + Globals.FORWARD_SLASH + fileNames.get(i); InputStream in =
		 * context.getResourceAsStream(filePath); if (in == null) {
		 * validatorResources = null; throw new
		 * CommonsValidatorException("invalid filePath: " + filePath);
		 * //$NON-NLS-1$ } try {
		 * ValidatorResourcesInitializer.initialize(validatorResources, in); }
		 * catch (IOException e) { validatorResources = null; throw new
		 * CommonsValidatorException(e); } }
		 */

	}

	public static ValidatorResources getValidatorResources() {
		return validatorResources;
	}

	/**
	 * Returns the filePath.
	 * 
	 * @return String
	 */
	public static String getFilePath() {
		return cvFilePath;
	}

	/**
	 * Sets the filePath.
	 * 
	 * @param filePath
	 *            The filePath to set
	 */
	public static void setFilePath(String filePath) {
		cvFilePath = filePath;
	}

	/**
	 * Method to be called by the CommonsValidatorEvent class. signature will be
	 * modified.
	 */
	public static void validate(String beanName, Object o, Errors errors) {
		try {
			Validator validator = new Validator(validatorResources, beanName);
			// add the name bean to the validator as a resource
			// for the validations to be performed on.
			validator.setParameter(Validator.BEAN_PARAM, o);
			validator.setParameter(Globals.ERRORS_KEY, errors);

			validator.validate();
		} catch (ValidatorException e) {
			LOGGER.info("Error validating HttpEvent " + beanName + " : " + e); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Error validating: " + e.getMessage()); //$NON-NLS-1$
		}

	}

	/**
	 * Returns the mappingsFile.
	 * 
	 * @return String
	 */
	public static String getMappingsFile() {
		return cvMappingsFile;
	}

	/**
	 * Returns the rulesFile.
	 * 
	 * @return String
	 */
	/*
	 * public static String getRulesFile() { return cvRulesFile; }
	 */

	/**
	 * Sets the mappingsFile.
	 * 
	 * @param mappingsFile
	 *            The mappingsFile to set
	 */
	public static void setMappingsFile(String mappingsFile) {
		cvMappingsFile = mappingsFile;
	}

	/**
	 * Sets the rulesFile.
	 * 
	 * @param rulesFile
	 *            The rulesFile to set
	 */
	/*
	 * public static void setRulesFile(String rulesFile) { cvRulesFile =
	 * rulesFile; }
	 */

	/**
	 * Release the validatorResources object.
	 */
	public static void release() {
		validatorResources = null;
	}

}
