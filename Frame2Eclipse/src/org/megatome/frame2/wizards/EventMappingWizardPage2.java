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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.EventHandler;
import org.megatome.frame2.model.Frame2Model;

public class EventMappingWizardPage2 extends WizardPage {
	private Table availableHandlersTable;
	private Table selectedHandlersTable;
	private Button addSingleButton;
	private Button addAllButton;
	private Button removeSingleButton;
	private Button removeAllButton;
	private Button moveUpButton;
	private Button moveDownButton;
	private boolean badModel = false;

	public EventMappingWizardPage2() {
		super(Frame2Plugin
				.getString("EventMappingWizardPage2.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin
				.getString("EventMappingWizardPage2.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getString("EventMappingWizardPage2.pageDescription")); //$NON-NLS-1$
	}

	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 6;
		layout.verticalSpacing = 9;

		this.availableHandlersTable = new Table(container, SWT.SINGLE
				| SWT.FULL_SELECTION);
		this.availableHandlersTable.setHeaderVisible(true);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		this.availableHandlersTable.setLayoutData(gd);
		this.availableHandlersTable
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(@SuppressWarnings("unused")
					final SelectionEvent e) {
						enableMovementButtons();
					}
				});

		TableColumn tc = new TableColumn(this.availableHandlersTable, SWT.NULL);
		tc.setText(Frame2Plugin
				.getString("EventMappingWizardPage2.availableColumn")); //$NON-NLS-1$
		tc.setWidth(200);

		final Composite buttonContainer = new Composite(container, SWT.NULL);
		final RowLayout rl = new RowLayout();
		rl.type = SWT.VERTICAL;
		rl.pack = false;
		buttonContainer.setLayout(rl);

		this.addSingleButton = new Button(buttonContainer, SWT.PUSH);
		this.addSingleButton.setImage(getImage(Frame2Plugin
				.getString("EventMappingWizardPage2.arrowRightImage"))); //$NON-NLS-1$
		this.addSingleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				addHandlerToSelected();
			}
		});

		this.removeSingleButton = new Button(buttonContainer, SWT.PUSH);
		this.removeSingleButton.setImage(getImage(Frame2Plugin
				.getString("EventMappingWizardPage2.arrowLeftImage"))); //$NON-NLS-1$
		this.removeSingleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				removeHandlerFromSelected();
			}
		});

		this.addAllButton = new Button(buttonContainer, SWT.PUSH);
		this.addAllButton
				.setImage(getImage(Frame2Plugin
						.getString("EventMappingWizardPage2.doubleArrowRightImage"))); //$NON-NLS-1$
		this.addAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				addAllHandlersToSelected();
			}
		});

		this.removeAllButton = new Button(buttonContainer, SWT.PUSH);
		this.removeAllButton
				.setImage(getImage(Frame2Plugin
						.getString("EventMappingWizardPage2.doubleArrowLeftImage"))); //$NON-NLS-1$
		this.removeAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				removeAllHandlersFromSelected();
			}
		});

		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalSpan = 1;
		buttonContainer.setLayoutData(gd);

		this.selectedHandlersTable = new Table(container, SWT.SINGLE
				| SWT.FULL_SELECTION);
		this.selectedHandlersTable.setHeaderVisible(true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		this.selectedHandlersTable.setLayoutData(gd);
		this.selectedHandlersTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				enableMovementButtons();
			}
		});

		tc = new TableColumn(this.selectedHandlersTable, SWT.NULL);
		tc.setText(Frame2Plugin
				.getString("EventMappingWizardPage2.selectedColumn")); //$NON-NLS-1$
		tc.setWidth(200);

		final Composite buttonContainer2 = new Composite(container, SWT.NULL);
		final RowLayout rl2 = new RowLayout();
		rl2.type = SWT.VERTICAL;
		rl2.pack = false;
		rl2.spacing = 6;
		buttonContainer2.setLayout(rl2);

		this.moveUpButton = new Button(buttonContainer2, SWT.PUSH);
		this.moveUpButton.setImage(getImage(Frame2Plugin
				.getString("EventMappingWizardPage2.arrowUpImage"))); //$NON-NLS-1$
		this.moveUpButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				moveSelectedHandler(true);
			}
		});

		this.moveDownButton = new Button(buttonContainer2, SWT.PUSH);
		this.moveDownButton.setImage(getImage(Frame2Plugin
				.getString("EventMappingWizardPage2.arrowDownImage"))); //$NON-NLS-1$
		this.moveDownButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				moveSelectedHandler(false);
			}
		});

		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalSpan = 1;
		buttonContainer2.setLayoutData(gd);

		this.addSingleButton.setEnabled(false);
		this.addAllButton.setEnabled(false);
		this.removeSingleButton.setEnabled(false);
		this.removeAllButton.setEnabled(false);

		this.moveUpButton.setEnabled(false);
		this.moveDownButton.setEnabled(false);

		initialize();
		enableMovementButtons();
		setControl(container);
	}

	private void initialize() {
		final Frame2Model model = ((EventMappingWizard) getWizard())
				.getFrame2Model();

		if (model != null) {
			final EventHandler[] handlers = model.getEventHandlers();
			for (int i = 0; i < handlers.length; i++) {
				final TableItem item = new TableItem(
						this.availableHandlersTable, SWT.NULL);
				item.setText(handlers[i].getName());
			}
		} else {
			setPageComplete(false);
			this.badModel = true;
			updateStatus(Frame2Plugin
					.getString("EventMappingWizardPage2.errorConfig")); //$NON-NLS-1$
		}
	}

	private void setHandlerPageValid() {
		final IWizardPage page = getNextPage();

		if ((page != null) && (page instanceof EventMappingWizardPage3)) {
			final EventMappingWizardPage3 viewPage = (EventMappingWizardPage3) page;
			viewPage.setHandlersSelected(this.selectedHandlersTable
					.getItemCount() > 0);
			// Force wizard to update enabled/disabled buttons
			if (!this.badModel) {
				setPageComplete(true);
			}
		}
	}

	void enableMovementButtons() {
		final int availSel = this.availableHandlersTable.getSelectionIndex();
		final int selectedSel = this.selectedHandlersTable.getSelectionIndex();

		this.addSingleButton.setEnabled(availSel != -1);
		this.removeSingleButton.setEnabled(selectedSel != -1);

		this.addAllButton
				.setEnabled(this.availableHandlersTable.getItemCount() > 0);
		this.removeAllButton.setEnabled(this.selectedHandlersTable
				.getItemCount() > 0);

		this.moveUpButton.setEnabled(selectedSel > 0);
		this.moveDownButton
				.setEnabled((selectedSel != -1)
						&& (selectedSel < this.selectedHandlersTable
								.getItemCount() - 1));
	}

	void addHandlerToSelected() {
		final int selIndex = this.availableHandlersTable.getSelectionIndex();

		if (selIndex == -1) {
			return;
		}

		moveItemToTable(this.availableHandlersTable,
				this.selectedHandlersTable, selIndex);
		enableMovementButtons();
		setHandlerPageValid();
	}

	void removeHandlerFromSelected() {
		final int selIndex = this.selectedHandlersTable.getSelectionIndex();

		if (selIndex == -1) {
			return;
		}

		moveItemToTable(this.selectedHandlersTable,
				this.availableHandlersTable, selIndex);
		enableMovementButtons();
		setHandlerPageValid();
	}

	void addAllHandlersToSelected() {
		final int itemCount = this.availableHandlersTable.getItemCount();

		if (itemCount == 0) {
			return;
		}

		for (int i = 0; i < itemCount; i++) {
			moveItemToTable(this.availableHandlersTable,
					this.selectedHandlersTable, 0);
		}
		enableMovementButtons();
		setHandlerPageValid();
	}

	void removeAllHandlersFromSelected() {
		final int itemCount = this.selectedHandlersTable.getItemCount();

		if (itemCount == 0) {
			return;
		}

		for (int i = 0; i < itemCount; i++) {
			moveItemToTable(this.selectedHandlersTable,
					this.availableHandlersTable, 0);
		}
		enableMovementButtons();
		setHandlerPageValid();
	}

	private void moveItemToTable(final Table srcTable, final Table destTable,
			final int index) {
		final TableItem item = srcTable.getItem(index);
		final TableItem newItem = new TableItem(destTable, SWT.NULL);
		newItem.setText(item.getText());
		srcTable.remove(index);
	}

	void moveSelectedHandler(final boolean moveUp) {
		final int selIndex = this.selectedHandlersTable.getSelectionIndex();

		if (selIndex == -1) {
			return;
		}

		int newIndex = -1;
		if (moveUp) {
			if (selIndex == 0) {
				return;
			}

			newIndex = selIndex - 1;
		} else {
			if (selIndex == (this.selectedHandlersTable.getItemCount() - 1)) {
				return;
			}

			newIndex = selIndex + 1;
		}

		final TableItem item1 = this.selectedHandlersTable.getItem(selIndex);
		final TableItem item2 = this.selectedHandlersTable.getItem(newIndex);
		final String text1 = item1.getText();
		final String text2 = item2.getText();
		item1.setText(text2);
		item2.setText(text1);
		this.selectedHandlersTable.select(newIndex);
		enableMovementButtons();
	}

	private void updateStatus(final String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public List<String> getSelectedHandlers() {
		final List<String> selected = new ArrayList<String>();

		final int selCount = this.selectedHandlersTable.getItemCount();
		for (int i = 0; i < selCount; i++) {
			final TableItem item = this.selectedHandlersTable.getItem(i);
			selected.add(item.getText());
		}

		return selected;
	}

	private Image getImage(final String imageName) {
		final String iconPath = Frame2Plugin
				.getString("EventMappingWizardPage2.iconDir"); //$NON-NLS-1$
		ImageDescriptor id = null;
		try {
			final Frame2Plugin plugin = Frame2Plugin.getDefault();
			final URL installURL = plugin.getBundle().getEntry("/"); //$NON-NLS-1$
			final URL url = new URL(installURL, iconPath + imageName);
			id = ImageDescriptor.createFromURL(url);
		} catch (final MalformedURLException e) {
			// Ignore
		}

		return (id != null) ? id.createImage() : null;
	}

	@Override
	public void dispose() {
		super.dispose();

		this.availableHandlersTable.dispose();
		this.selectedHandlersTable.dispose();
		this.addSingleButton.dispose();
		this.addAllButton.dispose();
		this.removeSingleButton.dispose();
		this.removeAllButton.dispose();
		this.moveUpButton.dispose();
		this.moveDownButton.dispose();
	}

}