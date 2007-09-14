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
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;

public class EventHandlerWizardPage2 extends WizardPage {
	private Text nameText;
	Text htmlResourceText;
	Text xmlResourceText;
	private Label nameLabel;
	private Label typeLabel;
	Label htmlResourceLabel;
	Label xmlResourceLabel;
	Label eventLabel;
	Combo eventCombo;

	private Button htmlResourceRadio;
	private Button xmlResourceRadio;
	private Button xmlResponderRadio;
	private Button eventRadio;
	private Button addButton;

	Button htmlResourceBrowse;

	private Table forwardTable;
	Button removeButton;

	private SelectionListener radioListener;

	private IProject htmlResourceProject;

	private boolean badModel = false;

	private String[] localForwardNames = null;

	public EventHandlerWizardPage2() {
		super(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.pageDescription")); //$NON-NLS-1$
	}

	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 4;
		// layout.verticalSpacing = 9;
		this.nameLabel = new Label(container, SWT.NULL);
		this.nameLabel.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.nameLabel")); //$NON-NLS-1$

		this.nameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
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
				.getResourceString("EventHandlerWizardPage2.typeLabel")); //$NON-NLS-1$

		this.typeLabel.setEnabled(false);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		this.typeLabel.setLayoutData(gd);

		this.htmlResourceRadio = new Button(container, SWT.RADIO);
		this.htmlResourceRadio
				.setText(Frame2Plugin
						.getResourceString("EventHandlerWizardPage2.htmlResource_type")); //$NON-NLS-1$
		this.htmlResourceRadio.setSelection(true);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		this.htmlResourceRadio.setLayoutData(gd);
		this.htmlResourceRadio.addSelectionListener(getRadioListener());

		this.htmlResourceLabel = new Label(container, SWT.NULL);
		this.htmlResourceLabel.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.pathLabel")); //$NON-NLS-1$

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
				.getResourceString("EventHandlerWizardPage2.browseCtl")); //$NON-NLS-1$
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
		this.xmlResourceRadio.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.xmlResource_type")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.xmlResourceRadio.setLayoutData(gd);
		this.xmlResourceRadio.addSelectionListener(getRadioListener());

		this.xmlResponderRadio = new Button(container, SWT.RADIO);
		this.xmlResponderRadio
				.setText(Frame2Plugin
						.getResourceString("EventHandlerWizardPage2.xmlResponder_type")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.xmlResponderRadio.setLayoutData(gd);
		this.xmlResponderRadio.addSelectionListener(getRadioListener());

		this.xmlResourceLabel = new Label(container, SWT.NULL);
		this.xmlResourceLabel.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.pathLabel")); //$NON-NLS-1$

		this.xmlResourceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.xmlResourceText.setLayoutData(gd);
		this.xmlResourceText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.xmlResourceRadio.setEnabled(false);
		this.xmlResponderRadio.setEnabled(false);
		this.xmlResourceLabel.setEnabled(false);
		this.xmlResourceText.setEnabled(false);

		this.eventRadio = new Button(container, SWT.RADIO);
		this.eventRadio.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.event_type")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		this.eventRadio.setLayoutData(gd);
		this.eventRadio.addSelectionListener(getRadioListener());

		this.eventLabel = new Label(container, SWT.NULL);
		this.eventLabel.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.pathLabel")); //$NON-NLS-1$

		this.eventCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
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

		this.addButton = new Button(container, SWT.PUSH);
		this.addButton.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.addToTableCtl")); //$NON-NLS-1$
		gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		this.addButton.setLayoutData(gd);
		this.addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				addForwardToTable();
			}
		});
		this.addButton.setEnabled(false);

		this.removeButton = new Button(container, SWT.PUSH);
		this.removeButton.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage2.removeCtl")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.horizontalSpan = 2;
		this.removeButton.setLayoutData(gd);
		this.removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				removeForwardFromTable();
			}
		});
		this.removeButton.setEnabled(false);

		this.forwardTable = new Table(container, SWT.SINGLE
				| SWT.FULL_SELECTION);
		this.forwardTable.setLinesVisible(true);
		this.forwardTable.setHeaderVisible(true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 4;
		gd.verticalSpan = 3;
		this.forwardTable.setLayoutData(gd);

		final TableColumn tc1 = new TableColumn(this.forwardTable, SWT.NULL);
		tc1
				.setText(Frame2Plugin
						.getResourceString("EventHandlerWizardPage2.forwardNameColumn")); //$NON-NLS-1$
		tc1.setWidth(120);

		final TableColumn tc2 = new TableColumn(this.forwardTable, SWT.NULL);
		tc2
				.setText(Frame2Plugin
						.getResourceString("EventHandlerWizardPage2.forwardTypeColumn")); //$NON-NLS-1$
		tc2.setWidth(120);

		final TableColumn tc3 = new TableColumn(this.forwardTable, SWT.NULL);
		tc3
				.setText(Frame2Plugin
						.getResourceString("EventHandlerWizardPage2.forwardPathColumn")); //$NON-NLS-1$
		tc3.setWidth(120);

		this.forwardTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				EventHandlerWizardPage2.this.removeButton.setEnabled(true);
			}

			public void widgetDefaultSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				// Ignore
			}
		});

		initialize();
		// dialogChanged();
		setPageComplete(true);
		setControl(container);
	}

	private void initialize() {
		final Frame2Model model = ((EventHandlerWizard) getWizard())
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

	void addForwardToTable() {
		final String forwardName = getForwardName();
		final String forwardType = getForwardType();
		final String forwardPath = getForwardPath();

		final TableItem item = new TableItem(this.forwardTable, SWT.NONE);
		final String[] itemData = new String[] { forwardName, forwardType,
				forwardPath, };
		item.setText(itemData);

		this.nameText.setText(""); //$NON-NLS-1$
		this.htmlResourceText.setText(""); //$NON-NLS-1$
		this.xmlResourceText.setText(""); //$NON-NLS-1$

		if (this.localForwardNames == null) {
			this.localForwardNames = new String[] { forwardName };
		} else {
			final String[] newForwardNames = new String[this.localForwardNames.length + 1];
			System.arraycopy(this.localForwardNames, 0, newForwardNames, 0,
					this.localForwardNames.length);
			newForwardNames[newForwardNames.length - 1] = forwardName;
			this.localForwardNames = newForwardNames;
		}

		this.addButton.setEnabled(false);
	}

	void removeForwardFromTable() {
		final int selIndex = this.forwardTable.getSelectionIndex();
		if (selIndex == -1) {
			this.removeButton.setEnabled(false);
			return;
		}

		final String forwardName = this.forwardTable.getItem(selIndex).getText(
				0);
		this.forwardTable.remove(selIndex);

		int nameIndex = -1;
		for (int i = 0; i < this.localForwardNames.length; i++) {
			if (this.localForwardNames[i].equals(forwardName)) {
				nameIndex = i;
			}
		}

		if (nameIndex == -1) {
			return;
		}

		final String[] newForwards = new String[this.localForwardNames.length - 1];
		System.arraycopy(this.localForwardNames, 0, newForwards, 0, nameIndex);
		System.arraycopy(this.localForwardNames, nameIndex + 1, newForwards,
				nameIndex, this.localForwardNames.length - nameIndex - 1);
		this.localForwardNames = newForwards;

		this.removeButton.setEnabled(false);
	}

	void handleBrowse() {
		final IResource[] forwardElements = findForwardResources();
		final ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(
				getShell(), forwardElements);
		dialog.setInitialSelections(forwardElements);
		if (dialog.open() == Window.OK) {
			final Object[] results = dialog.getResult();
			if (results.length == 1) {
				final IFile f = (IFile) results[0];
				this.htmlResourceText
						.setText("/" + f.getProjectRelativePath().toString()); //$NON-NLS-1$
				this.htmlResourceProject = f.getProject();
				dialogChanged();
			}
		}
	}

	private IResource[] findForwardResources() {
		final List<IResource> filteredResources = new ArrayList<IResource>();

		final IWorkspace myWorkspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceRoot myRoot = myWorkspace.getRoot();

		final IProject[] allProjects = myRoot.getProjects();
		for (int i = 0; i < allProjects.length; i++) {
			if (allProjects[i].isOpen()) {
				filterResources(allProjects[i], filteredResources);
			}
		}

		// filterResources(rootProject, filteredResources);

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
									.getResourceString("EventHandlerWizardPage2.jsp_file_type")) //$NON-NLS-1$
							|| fileExt
									.equalsIgnoreCase(Frame2Plugin
											.getResourceString("EventHandlerWizardPage2.htm_file_type")) //$NON-NLS-1$
					|| fileExt
							.equalsIgnoreCase(Frame2Plugin
									.getResourceString("EventHandlerWizardPage2.html_file_type")))) { //$NON-NLS-1$
				resourceList.add(members[i]);
			}
		}
	}

	private SelectionListener getRadioListener() {
		if (this.radioListener == null) {
			this.radioListener = new SelectionListener() {
				public void widgetDefaultSelected(@SuppressWarnings("unused")
				final SelectionEvent e) {
					// Ignore
				}

				public void widgetSelected(final SelectionEvent e) {
					final Button source = (Button) e.getSource();
					final String buttonText = source.getText();

					if (buttonText
							.equals(Frame2Plugin
									.getResourceString("EventHandlerWizardPage2.htmlResource_type"))) { //$NON-NLS-1$
						EventHandlerWizardPage2.this.htmlResourceLabel
								.setEnabled(true);
						EventHandlerWizardPage2.this.htmlResourceText
								.setEnabled(true);
						EventHandlerWizardPage2.this.htmlResourceBrowse
								.setEnabled(true);

						EventHandlerWizardPage2.this.xmlResourceLabel
								.setEnabled(false);
						EventHandlerWizardPage2.this.xmlResourceText
								.setEnabled(false);

						EventHandlerWizardPage2.this.eventLabel
								.setEnabled(false);
						EventHandlerWizardPage2.this.eventCombo
								.setEnabled(false);
					} else if (buttonText
							.equals(Frame2Plugin
									.getResourceString("EventHandlerWizardPage2.xmlResource_type"))) { //$NON-NLS-1$
						EventHandlerWizardPage2.this.htmlResourceLabel
								.setEnabled(false);
						EventHandlerWizardPage2.this.htmlResourceText
								.setEnabled(false);
						EventHandlerWizardPage2.this.htmlResourceBrowse
								.setEnabled(false);

						EventHandlerWizardPage2.this.xmlResourceLabel
								.setEnabled(true);
						EventHandlerWizardPage2.this.xmlResourceText
								.setEnabled(true);

						EventHandlerWizardPage2.this.eventLabel
								.setEnabled(false);
						EventHandlerWizardPage2.this.eventCombo
								.setEnabled(false);
					} else if (buttonText
							.equals(Frame2Plugin
									.getResourceString("EventHandlerWizardPage2.xmlResponder_type"))) { //$NON-NLS-1$
						EventHandlerWizardPage2.this.htmlResourceLabel
								.setEnabled(false);
						EventHandlerWizardPage2.this.htmlResourceText
								.setEnabled(false);
						EventHandlerWizardPage2.this.htmlResourceBrowse
								.setEnabled(false);

						EventHandlerWizardPage2.this.xmlResourceLabel
								.setEnabled(true);
						EventHandlerWizardPage2.this.xmlResourceText
								.setEnabled(true);

						EventHandlerWizardPage2.this.eventLabel
								.setEnabled(false);
						EventHandlerWizardPage2.this.eventCombo
								.setEnabled(false);
					} else if (buttonText
							.equals(Frame2Plugin
									.getResourceString("EventHandlerWizardPage2.event_type"))) { //$NON-NLS-1$
						EventHandlerWizardPage2.this.htmlResourceLabel
								.setEnabled(false);
						EventHandlerWizardPage2.this.htmlResourceText
								.setEnabled(false);
						EventHandlerWizardPage2.this.htmlResourceBrowse
								.setEnabled(false);

						EventHandlerWizardPage2.this.xmlResourceLabel
								.setEnabled(false);
						EventHandlerWizardPage2.this.xmlResourceText
								.setEnabled(false);

						EventHandlerWizardPage2.this.eventLabel
								.setEnabled(true);
						EventHandlerWizardPage2.this.eventCombo
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
			setErrorMessage(Frame2Plugin
					.getResourceString("EventHandlerWizardPage2.errorConfig")); //$NON-NLS-1$
			setPageComplete(false);
			return;
		}

		final String name = getForwardName();
		final String type = getForwardType();
		final String path = getForwardPath();

		if ((name.length() == 0) || (type.length() == 0)
				|| (path.length() == 0) || (!validateForwardName())
				|| (!validateForwardPath())) {
			this.addButton.setEnabled(false);
			return;
		}

		this.addButton.setEnabled(true);
	}

	private String getForwardName() {
		return this.nameText.getText();
	}

	private String getForwardType() {
		if (this.htmlResourceRadio.getSelection()) {
			return Frame2Plugin
					.getResourceString("EventHandlerWizardPage2.htmlResource_type"); //$NON-NLS-1$
		} else if (this.xmlResourceRadio.getSelection()) {
			return Frame2Plugin
					.getResourceString("EventHandlerWizardPage2.xmlResource_type"); //$NON-NLS-1$
		} else if (this.xmlResponderRadio.getSelection()) {
			return Frame2Plugin
					.getResourceString("EventHandlerWizardPage2.xmlResponder_type"); //$NON-NLS-1$
		} else if (this.eventRadio.getSelection()) {
			return Frame2Plugin
					.getResourceString("EventHandlerWizardPage2.event_internal_type"); //$NON-NLS-1$
		}

		return ""; //$NON-NLS-1$
	}

	private String getForwardPath() {
		if (this.htmlResourceRadio.getSelection()) {
			return this.htmlResourceText.getText();
		} else if (this.xmlResourceRadio.getSelection()) {
			return this.xmlResourceText.getText();
		} else if (this.xmlResponderRadio.getSelection()) {
			return this.xmlResourceText.getText();
		} else if (this.eventRadio.getSelection()) {
			return this.eventCombo.getText();
		}

		return ""; //$NON-NLS-1$
	}

	private boolean validateForwardName() {
		final String forwardName = getForwardName();

		if (this.localForwardNames == null) {
			return true;
		}

		for (int i = 0; i < this.localForwardNames.length; i++) {
			if (this.localForwardNames[i].equals(forwardName)) {
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
						.getResourceString("EventHandlerWizardPage2.htmlResource_type"))) { //$NON-NLS-1$
			if (forwardPath.indexOf(Frame2Plugin
					.getResourceString("EventHandlerWizardPage2.uri_prefix")) != -1) { //$NON-NLS-1$
				return true;
			}

			if (this.htmlResourceProject != null) {
				final IResource filePath = this.htmlResourceProject
						.findMember(forwardPath);
				if (filePath != null) {
					if (!filePath.exists() || (!(filePath instanceof IFile))) {
						return false;
					}
				} else {
					return false;
				}
			} else {
				final IWorkspace myWorkspace = ResourcesPlugin.getWorkspace();
				final IWorkspaceRoot myRoot = myWorkspace.getRoot();

				final IProject[] allProjects = myRoot.getProjects();
				for (int i = 0; i < allProjects.length; i++) {
					final IResource filePath = allProjects[i]
							.findMember(forwardPath);
					if (filePath != null) {
						if (filePath.exists() && (filePath instanceof IFile)) {
							return true;
						}
					}
				}

				return false;
			}
		}

		return true;
	}

	public List<String[]> getLocalForwards() {
		final List<String[]> localForwards = new ArrayList<String[]>();

		final int forwardCount = this.forwardTable.getItemCount();

		for (int i = 0; i < forwardCount; i++) {
			final TableItem item = this.forwardTable.getItem(i);
			final String[] forwardValues = new String[] { item.getText(0),
					item.getText(1), item.getText(2), };
			localForwards.add(forwardValues);
		}

		return localForwards;
	}

	@Override
	public void dispose() {
		super.dispose();
		this.nameText.dispose();
		this.htmlResourceText.dispose();
		this.xmlResourceText.dispose();
		this.nameLabel.dispose();
		this.typeLabel.dispose();
		this.htmlResourceLabel.dispose();
		this.xmlResourceLabel.dispose();
		this.eventLabel.dispose();
		this.eventCombo.dispose();

		this.htmlResourceRadio.dispose();
		this.xmlResourceRadio.dispose();
		this.xmlResponderRadio.dispose();
		this.eventRadio.dispose();
		this.addButton.dispose();

		this.htmlResourceBrowse.dispose();

		this.forwardTable.dispose();
		this.removeButton.dispose();
	}

}