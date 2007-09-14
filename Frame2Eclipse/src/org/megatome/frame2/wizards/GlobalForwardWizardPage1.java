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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;

public class GlobalForwardWizardPage1 extends WizardPage {
	private Text nameText;
	Text htmlResourceText;
	Text xmlResourceText;
	Text xmlResponderText;
	private Label nameLabel;
	private Label typeLabel;
	Label htmlResourceLabel;
	Label xmlResourceLabel;
	Label xmlResponderLabel;
	Label eventLabel;
	Combo eventCombo;

	private Button htmlResourceRadio;
	private Button xmlResourceRadio;
	private Button xmlResponderRadio;
	private Button eventRadio;

	Button htmlResourceBrowse;

	private SelectionListener radioListener;

	private IProject rootProject;

	private boolean badModel = false;

	public GlobalForwardWizardPage1(final IProject currentProject) {
		super(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.pageDescription")); //$NON-NLS-1$
		this.rootProject = currentProject;
	}

	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		this.nameLabel = new Label(container, SWT.NULL);
		this.nameLabel.setText(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.nameCtl")); //$NON-NLS-1$

		this.nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.nameText.setLayoutData(gd);
		this.nameText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.nameLabel.setEnabled(false);
		this.nameText.setEnabled(false);

		this.typeLabel = new Label(container, SWT.NULL);
		this.typeLabel.setText(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.typeCtl")); //$NON-NLS-1$

		this.typeLabel.setEnabled(false);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.typeLabel.setLayoutData(gd);

		this.htmlResourceRadio = new Button(container, SWT.RADIO);
		this.htmlResourceRadio
				.setText(Frame2Plugin
						.getResourceString("GlobalForwardWizardPage1.htmlResource_type")); //$NON-NLS-1$
		this.htmlResourceRadio.setSelection(true);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.htmlResourceRadio.setLayoutData(gd);
		this.htmlResourceRadio.addSelectionListener(getRadioListener());

		this.htmlResourceLabel = new Label(container, SWT.NULL);
		this.htmlResourceLabel.setText(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.pathCtl")); //$NON-NLS-1$

		this.htmlResourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		this.htmlResourceText.setLayoutData(gd);
		this.htmlResourceText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.htmlResourceBrowse = new Button(container, SWT.PUSH);
		this.htmlResourceBrowse.setText(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.browseCtl")); //$NON-NLS-1$
		gd = new GridData(GridData.END);
		this.htmlResourceBrowse.setLayoutData(gd);
		this.htmlResourceBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				handleBrowse();
			}
		});

		this.htmlResourceRadio.setEnabled(false);
		this.htmlResourceLabel.setEnabled(false);
		this.htmlResourceText.setEnabled(false);
		this.htmlResourceBrowse.setEnabled(false);

		this.xmlResourceRadio = new Button(container, SWT.RADIO);
		this.xmlResourceRadio
				.setText(Frame2Plugin
						.getResourceString("GlobalForwardWizardPage1.xmlResource_type")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.xmlResourceRadio.setLayoutData(gd);
		this.xmlResourceRadio.addSelectionListener(getRadioListener());

		this.xmlResourceLabel = new Label(container, SWT.NULL);
		this.xmlResourceLabel.setText(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.pathCtl")); //$NON-NLS-1$

		this.xmlResourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.xmlResourceText.setLayoutData(gd);
		this.xmlResourceText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.xmlResourceRadio.setEnabled(false);
		this.xmlResourceLabel.setEnabled(false);
		this.xmlResourceText.setEnabled(false);

		this.xmlResponderRadio = new Button(container, SWT.RADIO);
		this.xmlResponderRadio
				.setText(Frame2Plugin
						.getResourceString("GlobalForwardWizardPage1.xmlResponder_type")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.xmlResponderRadio.setLayoutData(gd);
		this.xmlResponderRadio.addSelectionListener(getRadioListener());

		this.xmlResponderLabel = new Label(container, SWT.NULL);
		this.xmlResponderLabel.setText(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.pathCtl")); //$NON-NLS-1$

		this.xmlResponderText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.xmlResponderText.setLayoutData(gd);
		this.xmlResponderText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.xmlResponderRadio.setEnabled(false);
		this.xmlResponderLabel.setEnabled(false);
		this.xmlResponderText.setEnabled(false);

		this.eventRadio = new Button(container, SWT.RADIO);
		this.eventRadio.setText(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.event_type")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.eventRadio.setLayoutData(gd);
		this.eventRadio.addSelectionListener(getRadioListener());

		this.eventLabel = new Label(container, SWT.NULL);
		this.eventLabel.setText(Frame2Plugin
				.getResourceString("GlobalForwardWizardPage1.pathCtl")); //$NON-NLS-1$

		this.eventCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.eventCombo.setLayoutData(gd);
		this.eventCombo.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.eventRadio.setEnabled(false);
		this.eventLabel.setEnabled(false);
		this.eventCombo.setEnabled(false);

		initialize();
		// dialogChanged();
		setPageComplete(false);
		setControl(container);
	}

	private void initialize() {
		final Frame2Model model = ((GlobalForwardWizard) getWizard())
				.getFrame2Model();

		if (model != null) {
			this.nameLabel.setEnabled(true);
			this.nameText.setEnabled(true);

			this.typeLabel.setEnabled(true);
			this.htmlResourceRadio.setEnabled(true);
			this.xmlResourceRadio.setEnabled(true);
			this.xmlResponderRadio.setEnabled(true);
			this.eventRadio.setEnabled(true);

			this.htmlResourceText.setEnabled(true);
			this.htmlResourceBrowse.setEnabled(true);

			final Frame2Event[] events = model.getEvents();
			for (int i = 0; i < events.length; i++) {
				this.eventCombo.add(events[i].getName());
			}
		} else {
			setPageComplete(false);
			this.badModel = true;
			dialogChanged();
		}
	}

	void handleBrowse() {
		final IResource[] forwardElements = findForwardResources();
		final ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(
				getShell(), forwardElements);
		dialog.setInitialSelections(forwardElements);
		if (dialog.open() == Window.OK) {
			final Object[] results = dialog.getResult();
			if (results.length == 1) {
				this.htmlResourceText
						.setText("/" + ((IFile) results[0]).getProjectRelativePath().toString()); //$NON-NLS-1$
				dialogChanged();
			}
		}
	}

	private IResource[] findForwardResources() {
		final List<IResource> filteredResources = new ArrayList<IResource>();

		filterResources(this.rootProject, filteredResources);

		IResource[] allFiltered = new IResource[0];
		allFiltered = filteredResources.toArray(allFiltered);

		return allFiltered;
	}

	private void filterResources(final IContainer container,
			final List<IResource> resourceList) {
		IResource[] members;
		try {
			members = container.members();
		} catch (final CoreException e) {
			return;
		}

		for (int i = 0; i < members.length; i++) {
			if (members[i] instanceof IContainer) {
				filterResources((IContainer) members[i], resourceList);
			}

			final String fileExt = members[i].getFileExtension();
			if ((fileExt != null)
					&& (fileExt
							.equalsIgnoreCase(Frame2Plugin
									.getResourceString("GlobalForwardWizardPage1.jsp_file_type")) || //$NON-NLS-1$
							fileExt
									.equalsIgnoreCase(Frame2Plugin
											.getResourceString("GlobalForwardWizardPage1.htm_file_type")) || //$NON-NLS-1$
					fileExt
							.equalsIgnoreCase(Frame2Plugin
									.getResourceString("GlobalForwardWizardPage1.html_file_type")))) { //$NON-NLS-1$
				resourceList.add(members[i]);
			}
		}
	}

	private SelectionListener getRadioListener() {
		if (this.radioListener == null) {
			this.radioListener = new SelectionListener() {
				public void widgetDefaultSelected(@SuppressWarnings("unused")
				final SelectionEvent e) {
					// Ignored
				}

				public void widgetSelected(final SelectionEvent e) {
					final Button source = (Button) e.getSource();
					final String buttonText = source.getText();

					if (buttonText
							.equals(Frame2Plugin
									.getResourceString("GlobalForwardWizardPage1.htmlResource_type"))) { //$NON-NLS-1$
						GlobalForwardWizardPage1.this.htmlResourceLabel
								.setEnabled(true);
						GlobalForwardWizardPage1.this.htmlResourceText
								.setEnabled(true);
						GlobalForwardWizardPage1.this.htmlResourceBrowse
								.setEnabled(true);

						GlobalForwardWizardPage1.this.xmlResourceLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.xmlResourceText
								.setEnabled(false);

						GlobalForwardWizardPage1.this.xmlResponderLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.xmlResponderText
								.setEnabled(false);

						GlobalForwardWizardPage1.this.eventLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.eventCombo
								.setEnabled(false);
					} else if (buttonText
							.equals(Frame2Plugin
									.getResourceString("GlobalForwardWizardPage1.xmlResource_type"))) { //$NON-NLS-1$
						GlobalForwardWizardPage1.this.htmlResourceLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.htmlResourceText
								.setEnabled(false);
						GlobalForwardWizardPage1.this.htmlResourceBrowse
								.setEnabled(false);

						GlobalForwardWizardPage1.this.xmlResourceLabel
								.setEnabled(true);
						GlobalForwardWizardPage1.this.xmlResourceText
								.setEnabled(true);

						GlobalForwardWizardPage1.this.xmlResponderLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.xmlResponderText
								.setEnabled(false);

						GlobalForwardWizardPage1.this.eventLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.eventCombo
								.setEnabled(false);
					} else if (buttonText
							.equals(Frame2Plugin
									.getResourceString("GlobalForwardWizardPage1.xmlResponder_type"))) { //$NON-NLS-1$
						GlobalForwardWizardPage1.this.htmlResourceLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.htmlResourceText
								.setEnabled(false);
						GlobalForwardWizardPage1.this.htmlResourceBrowse
								.setEnabled(false);

						GlobalForwardWizardPage1.this.xmlResourceLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.xmlResourceText
								.setEnabled(false);

						GlobalForwardWizardPage1.this.xmlResponderLabel
								.setEnabled(true);
						GlobalForwardWizardPage1.this.xmlResponderText
								.setEnabled(true);

						GlobalForwardWizardPage1.this.eventLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.eventCombo
								.setEnabled(false);
					} else if (buttonText
							.equals(Frame2Plugin
									.getResourceString("GlobalForwardWizardPage1.event_type"))) { //$NON-NLS-1$
						GlobalForwardWizardPage1.this.htmlResourceLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.htmlResourceText
								.setEnabled(false);
						GlobalForwardWizardPage1.this.htmlResourceBrowse
								.setEnabled(false);

						GlobalForwardWizardPage1.this.xmlResourceLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.xmlResourceText
								.setEnabled(false);

						GlobalForwardWizardPage1.this.xmlResponderLabel
								.setEnabled(false);
						GlobalForwardWizardPage1.this.xmlResponderText
								.setEnabled(false);

						GlobalForwardWizardPage1.this.eventLabel
								.setEnabled(true);
						GlobalForwardWizardPage1.this.eventCombo
								.setEnabled(true);
					}

					dialogChanged();
				}
			};
		}

		return this.radioListener;
	}

	void dialogChanged() {
		if (this.badModel) {
			updateStatus(Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.configError")); //$NON-NLS-1$
			return;
		}

		final String name = getForwardName();
		final String type = getForwardType();
		final String path = getForwardPath();

		if (name.length() == 0) {
			updateStatus(Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.missingNameMessage")); //$NON-NLS-1$
			return;
		}
		if (type.length() == 0) {
			updateStatus(Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.missingTypeMessage")); //$NON-NLS-1$
			return;
		}
		if (path.length() == 0) {
			updateStatus(Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.missingPathMessage")); //$NON-NLS-1$
			return;
		}

		if (!validateForwardName()) {
			return;
		}

		if (!validateForwardPath()) {
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(final String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getForwardName() {
		return this.nameText.getText();
	}

	public String getForwardType() {
		if (this.htmlResourceRadio.getSelection()) {
			return Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.htmlResource_type"); //$NON-NLS-1$
		} else if (this.xmlResourceRadio.getSelection()) {
			return Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.xmlResource_type"); //$NON-NLS-1$
		} else if (this.xmlResponderRadio.getSelection()) {
			return Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.xmlResponder_type"); //$NON-NLS-1$
		} else if (this.eventRadio.getSelection()) {
			return Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.event_internal_type"); //$NON-NLS-1$
		}

		return ""; //$NON-NLS-1$
	}

	public String getForwardPath() {
		if (this.htmlResourceRadio.getSelection()) {
			return this.htmlResourceText.getText();
		} else if (this.xmlResourceRadio.getSelection()) {
			return this.xmlResourceText.getText();
		} else if (this.xmlResponderRadio.getSelection()) {
			return this.xmlResponderText.getText();
		} else if (this.eventRadio.getSelection()) {
			return this.eventCombo.getText();
		}

		return ""; //$NON-NLS-1$
	}

	private boolean validateForwardName() {
		final String forwardName = getForwardName();
		final Forward[] allForwards = ((GlobalForwardWizard) getWizard())
				.getFrame2Model().getGlobalForwards();
		for (int i = 0; i < allForwards.length; i++) {
			if (forwardName.equals(allForwards[i].getName())) {
				updateStatus(Frame2Plugin
						.getResourceString("GlobalForwardWizardPage1.errorDuplicateName")); //$NON-NLS-1$
				return false;
			}
		}

		return true;
	}

	private boolean validateForwardPath() {
		final String forwardType = getForwardType();
		final String forwardPath = getForwardPath();

		if (forwardType
				.equals(Frame2Plugin
						.getResourceString("GlobalForwardWizardPage1.htmlResource_type"))) { //$NON-NLS-1$

			if (forwardPath.indexOf(Frame2Plugin
					.getResourceString("GlobalForwardWizardPage1.uriPrefix")) != -1) { //$NON-NLS-1$
				return true;
			}

			final IResource filePath = this.rootProject.findMember(forwardPath);
			if (filePath != null) {
				if (!filePath.exists() || (!(filePath instanceof IFile))) {
					updateStatus(Frame2Plugin
							.getResourceString("GlobalForwardWizardPage1.missingForwardFile")); //$NON-NLS-1$
					return false;
				}
			} else {
				updateStatus(Frame2Plugin
						.getResourceString("GlobalForwardWizardPage1.missingForwardFile")); //$NON-NLS-1$
				return false;
			}

		} else if (forwardType.equals(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.xmlResponder_type"))) { //$NON-NLS-1$
			IJavaProject javaProject = JavaCore.create(this.rootProject);
			
			try {
				IType type = javaProject.findType(forwardPath);
				if (type == null) {
					updateStatus(Frame2Plugin
							.getResourceString("GlobalForwardWizardPage1.missingResponderFile")); //$NON-NLS-1$
					return false;
				}
				
				SearchPattern pattern = SearchPattern.createPattern(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.responderClassName"), IJavaSearchConstants.TYPE, IJavaSearchConstants.IMPLEMENTORS, SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE); //$NON-NLS-1$
				IJavaSearchScope scope = SearchEngine.createHierarchyScope(type);
				
				MatchSearchRequestor requestor = new MatchSearchRequestor();
				
				SearchEngine searchEngine = new SearchEngine();
			    searchEngine.search(pattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, scope, requestor, null);

				if (!requestor.hadMatch()) {
					updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.notImplementResponderMessage")); //$NON-NLS-1$
					return false;
				}
			} catch (JavaModelException e) {
				updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.validateResponderError")); //$NON-NLS-1$
				return false;
			} catch (CoreException e) {
				updateStatus(Frame2Plugin.getResourceString("GlobalForwardWizardPage1.validateResponderError")); //$NON-NLS-1$
				return false;
			}
		}

		return true;
	}

	@Override
	public void dispose() {
		super.dispose();
		this.nameText.dispose();
		this.htmlResourceText.dispose();
		this.xmlResourceText.dispose();
		this.xmlResponderText.dispose();
		this.nameLabel.dispose();
		this.typeLabel.dispose();
		this.htmlResourceLabel.dispose();
		this.xmlResourceLabel.dispose();
		this.xmlResponderLabel.dispose();
		this.eventLabel.dispose();
		this.eventCombo.dispose();

		this.htmlResourceRadio.dispose();
		this.xmlResourceRadio.dispose();
		this.xmlResponderRadio.dispose();
		this.eventRadio.dispose();

		this.htmlResourceBrowse.dispose();
	}
	
	static class MatchSearchRequestor extends SearchRequestor {
		private boolean matched = false;
		@Override
		@SuppressWarnings("unused")
		public void acceptSearchMatch(SearchMatch match)
				throws CoreException {
			this.matched = true;
		}
		
		public boolean hadMatch() {
			return this.matched;
		}
	}

}