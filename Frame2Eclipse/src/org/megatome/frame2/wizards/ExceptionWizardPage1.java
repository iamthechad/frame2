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
/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.megatome.frame2.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2Exception;
import org.megatome.frame2.model.Frame2Model;

public class ExceptionWizardPage1 extends WizardPage {
	private Text requestKeyText;
	private Text typeText;
	private Combo htmlViewCombo;
	private Combo xmlViewCombo;

	private boolean badModel = false;

	private Frame2Exception[] definedExceptions = new Frame2Exception[0];

	private static String noneString = Frame2Plugin
			.getResourceString("ExceptionWizardPage1.noneString"); //$NON-NLS-1$

	public ExceptionWizardPage1() {
		super(Frame2Plugin.getResourceString("ExceptionWizardPage1.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin
				.getResourceString("ExceptionWizardPage1.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getResourceString("ExceptionWizardPage1.pageDescription")); //$NON-NLS-1$
	}

	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("ExceptionWizardPage1.requestKeyLabel")); //$NON-NLS-1$

		this.requestKeyText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		this.requestKeyText.setLayoutData(gd);
		this.requestKeyText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("ExceptionWizardPage1.exceptionTypeLabel")); //$NON-NLS-1$

		this.typeText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.typeText.setLayoutData(gd);
		this.typeText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("ExceptionWizardPage1.htmlViewLabel")); //$NON-NLS-1$

		this.htmlViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.htmlViewCombo.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("ExceptionWizardPage1.xmlViewLabel")); //$NON-NLS-1$

		this.xmlViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.xmlViewCombo.setLayoutData(gd);

		initialize();
		// dialogChanged();
		setPageComplete(false);
		this.htmlViewCombo.setText(noneString);
		this.xmlViewCombo.setText(noneString);

		this.htmlViewCombo.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.xmlViewCombo.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.requestKeyText.setFocus();

		setControl(container);
	}

	private void initialize() {
		final Frame2Model model = ((ExceptionWizard) getWizard())
				.getFrame2Model();

		if (model != null) {
			this.htmlViewCombo.add(noneString);
			this.xmlViewCombo.add(noneString);

			final Forward[] forwards = model.getGlobalForwards();
			for (int i = 0; i < forwards.length; i++) {
				final String forwardType = forwards[i].getType();
				if (forwardType
						.equals(Frame2Plugin
								.getResourceString("ExceptionWizardPage1.htmlResource_type"))) { //$NON-NLS-1$
					this.htmlViewCombo.add(forwards[i].getName());
				} else if (forwardType
						.equals(Frame2Plugin
								.getResourceString("ExceptionWizardPage1.xmlResource_type")) || //$NON-NLS-1$
						forwardType
								.equals(Frame2Plugin
										.getResourceString("ExceptionWizardPage1.xmlResponse_type"))) { //$NON-NLS-1$
					this.xmlViewCombo.add(forwards[i].getName());
				} else if (forwardType
						.equals(Frame2Plugin
								.getResourceString("ExceptionWizardPage1.event_internal_type"))) { //$NON-NLS-1$
					this.htmlViewCombo.add(forwards[i].getName());
					this.xmlViewCombo.add(forwards[i].getName());
				}
			}

			this.htmlViewCombo.setText(noneString);
			this.xmlViewCombo.setText(noneString);

			this.definedExceptions = model.getExceptions();
		} else {
			setPageComplete(false);
			this.badModel = true;
			dialogChanged();
		}
	}

	void dialogChanged() {
		if (this.badModel) {
			updateStatus(Frame2Plugin
					.getResourceString("ExceptionWizardPage1.errorConfig")); //$NON-NLS-1$
			return;
		}

		final String requestKey = getRequestKey();
		final String exceptionType = getExceptionType();
		final String htmlView = getHTMLView();
		final String xmlView = getXMLView();

		if (requestKey.length() == 0) {
			updateStatus(Frame2Plugin
					.getResourceString("ExceptionWizardPage1.errorMissingRequestKey")); //$NON-NLS-1$
			return;
		}
		if (exceptionType.length() == 0) {
			updateStatus(Frame2Plugin
					.getResourceString("ExceptionWizardPage1.errorMissingExceptionType")); //$NON-NLS-1$
			return;
		}
		if (isDuplicateType()) {
			updateStatus(Frame2Plugin
					.getResourceString("ExceptionWizardPage1.errorDuplicateHandler")); //$NON-NLS-1$
			return;
		}

		if ((htmlView.length() == 0) && (xmlView.length() == 0)) {
			updateStatus(Frame2Plugin
					.getResourceString("ExceptionWizardPage1.errorMissingView")); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}

	private boolean isDuplicateType() {
		final String exceptionType = getExceptionType();

		for (int i = 0; i < this.definedExceptions.length; i++) {
			if (this.definedExceptions[i].getType().equals(exceptionType)) {
				return true;
			}
		}

		return false;
	}

	private void updateStatus(final String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getRequestKey() {
		return this.requestKeyText.getText();
	}

	public String getExceptionType() {
		return this.typeText.getText();
	}

	public String getHTMLView() {
		final String viewText = this.htmlViewCombo.getText();
		if (viewText.equals(noneString)) {
			return ""; //$NON-NLS-1$
		}

		return viewText;
	}

	public String getXMLView() {
		final String viewText = this.xmlViewCombo.getText();
		if (viewText.equals(noneString)) {
			return ""; //$NON-NLS-1$
		}

		return viewText;
	}

	@Override
	public void dispose() {
		super.dispose();

		this.requestKeyText.dispose();
		this.typeText.dispose();
		this.htmlViewCombo.dispose();
		this.xmlViewCombo.dispose();
	}

}