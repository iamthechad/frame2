/*
 * ====================================================================
 * 
 * Frame2 Open Source License
 * 
 * Copyright (c) 2004-2005 Megatome Technologies. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 * include the following acknowlegement: "This product includes software
 * developed by Megatome Technologies." Alternately, this acknowlegement may
 * appear in the software itself, if and wherever such third-party
 * acknowlegements normally appear.
 * 
 * 4. The names "The Frame2 Project", and "Frame2", must not be used to endorse
 * or promote products derived from this software without prior written
 * permission. For written permission, please contact
 * iamthechad@sourceforge.net.
 * 
 * 5. Products derived from this software may not be called "Frame2" nor may
 * "Frame2" appear in their names without prior written permission of Megatome
 * Technologies.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL MEGATOME
 * TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Common Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.megatome.frame2.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;

public class NewEventWizardPage1 extends NewTypeWizardPage {

	private final ISelection selection;
	private SelectionListener radioListener;
	private Button newClassRadio;
	private Button existingClassRadio;
	private Button noClassRadio;
	private Combo existingClassCombo;
	private Text eventNameText;
	// private IStatus eventNameStatus;
	// private IStatus selectedEventStatus;
	private IStatus badModelStatus = ValidationStatus.ok();

	private Frame2Event[] definedEvents = new Frame2Event[0];

	// private boolean initialized = false;

	private static final String PAGE_NAME = Frame2Plugin
			.getResourceString("NewEventWizardPage1.wizardName"); //$NON-NLS-1$
	private static final String NEW_CLASS_RADIO = Frame2Plugin
			.getResourceString("NewEventWizardPage1.newClassRadio"); //$NON-NLS-1$
	private static final String EXISTING_CLASS_RADIO = Frame2Plugin
			.getResourceString("NewEventWizardPage1.existingClassRadio"); //$NON-NLS-1$
	private static final String NO_CLASS_RADIO = Frame2Plugin
			.getResourceString("NewEventWizardPage1.noClassRadio"); //$NON-NLS-1$
	public static final String NEW_CLASS = Frame2Plugin
			.getResourceString("NewEventWizardPage1.newClass_type"); //$NON-NLS-1$
	public static final String EXISTING_CLASS = Frame2Plugin
			.getResourceString("NewEventWizardPage1.existingClass_type"); //$NON-NLS-1$
	public static final String NO_CLASS = Frame2Plugin
			.getResourceString("NewEventWizardPage1.noClass_type"); //$NON-NLS-1$

	public NewEventWizardPage1(final ISelection selection) {
		super(true, PAGE_NAME);
		setTitle(Frame2Plugin
				.getResourceString("NewEventWizardPage1.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getResourceString("NewEventWizardPage1.pageDescription")); //$NON-NLS-1$
		this.selection = selection;
	}

	public void createControl(final Composite parent) {
		initializeDialogUnits(parent);

		final Composite composite = new Composite(parent, SWT.NONE);

		final int nColumns = 4;

		final GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);

		// pick & choose the wanted UI components
		Label label = new Label(composite, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("NewEventWizardPage1.eventNameLabel")); //$NON-NLS-1$

		this.eventNameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.eventNameText.setLayoutData(gd);
		this.eventNameText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		createSeparator(composite, nColumns);

		this.newClassRadio = new Button(composite, SWT.RADIO);
		this.newClassRadio.setText(NEW_CLASS_RADIO);
		this.newClassRadio.addSelectionListener(getRadioListener());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		this.newClassRadio.setLayoutData(gd);
		this.newClassRadio.setSelection(true);

		createContainerControls(composite, nColumns);
		createPackageControls(composite, nColumns);
		createTypeNameControls(composite, nColumns);

		createSeparator(composite, nColumns);

		this.existingClassRadio = new Button(composite, SWT.RADIO);
		this.existingClassRadio.setText(EXISTING_CLASS_RADIO);
		this.existingClassRadio.addSelectionListener(getRadioListener());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		this.existingClassRadio.setLayoutData(gd);

		label = new Label(composite, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("NewEventWizardPage1.eventLabel")); //$NON-NLS-1$

		this.existingClassCombo = new Combo(composite, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.existingClassCombo.setLayoutData(gd);
		this.existingClassCombo.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		createSeparator(composite, nColumns);

		this.noClassRadio = new Button(composite, SWT.RADIO);
		this.noClassRadio.setText(NO_CLASS_RADIO);
		this.noClassRadio.addSelectionListener(getRadioListener());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		this.noClassRadio.setLayoutData(gd);

		createSeparator(composite, nColumns);

		initialize();
		dialogChanged();
		setControl(composite);
		// Dialog.applyDialogFont(composite);
	}

	private void initialize() {
		final Frame2Model model = ((NewEventWizard) getWizard())
				.getFrame2Model();

		this.badModelStatus = ValidationStatus.ok();
		if (model != null) {
			this.definedEvents = model.getEvents();
			final List<String> uniqueEventNames = new ArrayList<String>();
			for (int j = 0; j < this.definedEvents.length; j++) {
				final String eventType = this.definedEvents[j].getType();
				if ((eventType != null) && (!eventType.equals(""))) {//$NON-NLS-1$
					if (!uniqueEventNames.contains(eventType)) {
						uniqueEventNames.add(eventType);
					}
				}
			}

			this.existingClassCombo.setItems(uniqueEventNames
					.toArray(new String[uniqueEventNames.size()]));
		} else {
			setPageComplete(false);
			this.badModelStatus = ValidationStatus
					.error(Frame2Plugin
							.getResourceString("NewEventWizardPage1.errorConfiguration")); //$NON-NLS-1$
		}

		if (this.selection != null && this.selection.isEmpty() == false
				&& this.selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) this.selection;
			if (ssel.size() > 1) {
				return;
			}
			final IJavaElement jelem = getInitialJavaElement(ssel);
			initContainerPage(jelem);
			initTypePage(jelem);
		}

		// initialized = true;
		setSuperClass(
				Frame2Plugin
						.getResourceString("NewEventWizardPage1.commonsValidatorEventSuperClass"), //$NON-NLS-1$
				true);
	}

	void dialogChanged() {
		final String eventType = getNewEventType();

		// eventNameStatus = getEventNameStatus();

		if (eventType.equals(EXISTING_CLASS)) {
			// selectedEventStatus = getSelectedEventStatus();
		}

		doStatusUpdate();
	}

	@Override
	protected void handleFieldChanged(final String fieldName) {
		super.handleFieldChanged(fieldName);

		doStatusUpdate();
	}

	private IStatus getEventNameStatus() {
		final String eventName = getEventName();

		if (eventName.length() == 0) {
			return ValidationStatus
					.error(Frame2Plugin
							.getResourceString("NewEventWizardPage1.errorMissingEventName")); //$NON-NLS-1$
		}

		for (int i = 0; i < this.definedEvents.length; i++) {
			if (eventName.equals(this.definedEvents[i].getName())) {
				return ValidationStatus
						.error(Frame2Plugin
								.getResourceString("NewEventWizardPage1.errorDuplicateEventName")); //$NON-NLS-1$
			}
		}

		return ValidationStatus.ok();
	}

	private IStatus getSelectedEventStatus() {
		final String eventClassType = getEventClassType();

		if (eventClassType.length() == 0) {
			return ValidationStatus
					.error(Frame2Plugin
							.getResourceString("NewEventWizardPage1.errorSelectExisting")); //$NON-NLS-1$
		}

		return ValidationStatus.ok();
	}

	/*
	 * private void updateStatus(String message) { setErrorMessage(message);
	 * setPageComplete(message == null); }
	 */

	private SelectionListener getRadioListener() {
		if (this.radioListener == null) {
			this.radioListener = new SelectionListener() {

				public void widgetDefaultSelected(@SuppressWarnings("unused")
				final SelectionEvent e) {
					// Ignore
				}

				public void widgetSelected(@SuppressWarnings("unused")
				final SelectionEvent e) {
					dialogChanged();
				}
			};
		}

		return this.radioListener;
	}

	// ------ validation --------
	private void doStatusUpdate() {
		// status of all used components
		if ((this.badModelStatus.getMessage() != null)
				&& (!this.badModelStatus.getMessage().equals(""))) { //$NON-NLS-1$
			final IStatus[] status = new IStatus[] { this.badModelStatus };
			updateStatus(status);
			return;
		}
		final String eventType = getNewEventType();
		if (eventType.equals(NEW_CLASS)) {
			final IStatus[] status = new IStatus[] { this.badModelStatus,
					this.fContainerStatus, this.fPackageStatus,
					this.fTypeNameStatus, getEventNameStatus(), };
			// the most severe status will be displayed and the ok button
			// enabled/disabled.
			updateStatus(status);
		} else if (eventType.equals(EXISTING_CLASS)) {
			final IStatus[] status = new IStatus[] { this.badModelStatus,
					getEventNameStatus(), getSelectedEventStatus(), };
			updateStatus(status);
		} else if (eventType.equals(NO_CLASS)) {
			final IStatus[] status = new IStatus[] { this.badModelStatus,
					getEventNameStatus(), };
			updateStatus(status);
		}
	}

	public String getNewEventType() {
		if (this.newClassRadio.getSelection()) {
			return NEW_CLASS;
		} else if (this.existingClassRadio.getSelection()) {
			return EXISTING_CLASS;
		} else {
			return NO_CLASS;
		}
	}

	public String getEventName() {
		return this.eventNameText.getText();
	}

	public String getEventClassType() {
		String eventClass = ""; //$NON-NLS-1$
		if (this.newClassRadio.getSelection()) {
			eventClass = getPackageText();
			eventClass += "."; //$NON-NLS-1$
			eventClass += getTypeName();
		} else if (this.existingClassRadio.getSelection()) {
			eventClass = this.existingClassCombo.getText();
		}

		return eventClass;
	}

	@SuppressWarnings("unused")
	@Override
	protected void createTypeMembers(final IType newType,
			final ImportsManager imports, final IProgressMonitor monitor)
			throws CoreException {

		imports
				.addImport(Frame2Plugin
						.getResourceString("NewEventWizardPage1.commonsValidatorEventSuperClass")); //$NON-NLS-1$

	}

	@Override
	public void dispose() {
		super.dispose();

		this.newClassRadio.dispose();
		this.existingClassRadio.dispose();
		this.noClassRadio.dispose();
		this.existingClassCombo.dispose();
		this.eventNameText.dispose();
	}

}