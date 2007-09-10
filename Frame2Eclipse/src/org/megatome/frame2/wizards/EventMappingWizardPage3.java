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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.megatome.frame2.model.Forward;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.Frame2Plugin;

public class EventMappingWizardPage3 extends WizardPage {
	private Combo htmlViewCombo;
	private Combo xmlViewCombo;
	Table rolesTable;
	TableEditor editor;
	private Button addRowButton;
	Button removeRowButton;
	// private ISelection selection;
	// private IProject rootProject;
	private boolean badModel = false;

	private boolean handlersSelected = false;

	private final String noneString = Frame2Plugin
			.getResourceString("EventMappingWizardPage3.noneString"); //$NON-NLS-1$
	static int roleIndex = 1;

	public EventMappingWizardPage3(@SuppressWarnings("unused")
	final ISelection selection) {
		super(Frame2Plugin
				.getResourceString("EventMappingWizardPage3.wizardName")); //$NON-NLS-1$
		setTitle(Frame2Plugin
				.getResourceString("EventMappingWizardPage3.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getResourceString("EventMappingWizardPage3.pageDescription")); //$NON-NLS-1$
		// this.selection = selection;
	}

	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("EventMappingWizardPage3.htmlViewLabel")); //$NON-NLS-1$

		this.htmlViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.htmlViewCombo.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("EventMappingWizardPage3.xmlViewLabel")); //$NON-NLS-1$

		this.xmlViewCombo = new Combo(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		this.xmlViewCombo.setLayoutData(gd);

		// Sep
		label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		label.setLayoutData(gd);

		this.rolesTable = new Table(container, SWT.SINGLE | SWT.FULL_SELECTION);
		this.rolesTable.setHeaderVisible(true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		this.rolesTable.setLayoutData(gd);

		final TableColumn tc = new TableColumn(this.rolesTable, SWT.NULL);
		tc.setText(Frame2Plugin
				.getResourceString("EventMappingWizardPage3.userRolesColumn")); //$NON-NLS-1$
		tc.setWidth(200);

		this.editor = new TableEditor(this.rolesTable);
		this.editor.horizontalAlignment = SWT.LEFT;
		this.editor.grabHorizontal = true;
		this.rolesTable.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(final Event event) {
				final Rectangle clientArea = EventMappingWizardPage3.this.rolesTable
						.getClientArea();
				final Point pt = new Point(event.x, event.y);
				int index = EventMappingWizardPage3.this.rolesTable
						.getTopIndex();
				while (index < EventMappingWizardPage3.this.rolesTable
						.getItemCount()) {
					boolean visible = false;
					final TableItem item = EventMappingWizardPage3.this.rolesTable
							.getItem(index);
					for (int i = 0; i < EventMappingWizardPage3.this.rolesTable
							.getColumnCount(); i++) {
						final Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							final Text text = new Text(
									EventMappingWizardPage3.this.rolesTable,
									SWT.NONE);
							final Listener textListener = new Listener() {
								@SuppressWarnings("fallthrough")
								public void handleEvent(final Event e) {
									switch (e.type) {
									case SWT.FocusOut:
										item.setText(column, text.getText());
										text.dispose();
										break;
									case SWT.Traverse:
										switch (e.detail) {
										case SWT.TRAVERSE_RETURN:
											item
													.setText(column, text
															.getText());
											// FALL THROUGH
										case SWT.TRAVERSE_ESCAPE:
											text.dispose();
											e.doit = false;
										}
										break;
									}
									dialogChanged();
								}
							};
							text.addListener(SWT.FocusOut, textListener);
							text.addListener(SWT.Traverse, textListener);
							EventMappingWizardPage3.this.editor.setEditor(text,
									item, i);
							text.setText(item.getText(i));
							text.selectAll();
							text.setFocus();
							EventMappingWizardPage3.this.removeRowButton
									.setEnabled(true);
							return;
						}
						if (!visible && rect.intersects(clientArea)) {
							visible = true;
						}
					}
					if (!visible) {
						return;
					}
					index++;
				}
			}
		});

		this.addRowButton = new Button(container, SWT.PUSH);
		this.addRowButton.setText(Frame2Plugin
				.getResourceString("EventMappingWizardPage3.addRowCtl")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 2;
		this.addRowButton.setLayoutData(gd);
		this.addRowButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				final TableItem item = new TableItem(
						EventMappingWizardPage3.this.rolesTable, SWT.NULL);
				item
						.setText(Frame2Plugin
								.getResourceString("EventMappingWizardPage3.dummyRole") + roleIndex++); //$NON-NLS-1$
			}
		});

		this.removeRowButton = new Button(container, SWT.PUSH);
		this.removeRowButton.setText(Frame2Plugin
				.getResourceString("EventMappingWizardPage3.removeRowCtl")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.horizontalSpan = 1;
		this.removeRowButton.setLayoutData(gd);
		this.removeRowButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				final int selIndex = EventMappingWizardPage3.this.rolesTable
						.getSelectionIndex();
				if (selIndex != -1) {
					EventMappingWizardPage3.this.rolesTable.remove(selIndex);
				}

				EventMappingWizardPage3.this.removeRowButton.setEnabled(false);
			}
		});
		this.removeRowButton.setEnabled(false);

		initialize();

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

		setPageComplete(this.handlersSelected);
		dialogChanged();
		setControl(container);
	}

	private void initialize() {
		roleIndex = 1;
		final Frame2Model model = ((EventMappingWizard) getWizard())
				.getFrame2Model();

		if (model != null) {
			this.htmlViewCombo.add(this.noneString);
			this.xmlViewCombo.add(this.noneString);

			final Forward[] forwards = model.getGlobalForwards();
			for (int i = 0; i < forwards.length; i++) {
				final String forwardType = forwards[i].getType();
				if (forwardType
						.equals(Frame2Plugin
								.getResourceString("EventMappingWizardPage3.htmlResource_type"))) { //$NON-NLS-1$
					this.htmlViewCombo.add(forwards[i].getName());
				} else if (forwardType
						.equals(Frame2Plugin
								.getResourceString("EventMappingWizardPage3.xmlResource_type")) || //$NON-NLS-1$
						forwardType
								.equals(Frame2Plugin
										.getResourceString("EventMappingWizardPage3.xmlResponse_type"))) { //$NON-NLS-1$
					this.xmlViewCombo.add(forwards[i].getName());
				} else if (forwardType
						.equals(Frame2Plugin
								.getResourceString("EventMappingWizardPage3.event_internal_type"))) { //$NON-NLS-1$
					this.htmlViewCombo.add(forwards[i].getName());
					this.xmlViewCombo.add(forwards[i].getName());
				}
			}

			this.htmlViewCombo.setText(this.noneString);
			this.xmlViewCombo.setText(this.noneString);
		} else {
			setPageComplete(false);
			this.badModel = true;
			dialogChanged();
		}
	}

	void dialogChanged() {
		if (this.badModel) {
			updateStatus(Frame2Plugin
					.getResourceString("EventMappingWizardPage3.errorConfig")); //$NON-NLS-1$
			return;
		}

		final String htmlView = getHTMLView();
		final String xmlView = getXMLView();

		if ((htmlView.length() == 0) && (xmlView.length() == 0)
				&& (!this.handlersSelected)) {
			updateStatus(Frame2Plugin
					.getResourceString("EventMappingWizardPage3.errorMissingInformation")); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(final String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getHTMLView() {
		final String htmlView = this.htmlViewCombo.getText();
		if (htmlView.equals(this.noneString)) {
			return ""; //$NON-NLS-1$
		}

		return htmlView;
	}

	public String getXMLView() {
		final String xmlView = this.xmlViewCombo.getText();
		if (xmlView.equals(this.noneString)) {
			return ""; //$NON-NLS-1$
		}

		return xmlView;
	}

	public List<String> getSecurityRoles() {
		final List<String> roles = new ArrayList<String>();

		final int roleCount = this.rolesTable.getItemCount();
		for (int i = 0; i < roleCount; i++) {
			final TableItem item = this.rolesTable.getItem(i);
			roles.add(item.getText());
		}

		return roles;
	}

	public void setHandlersSelected(final boolean selected) {
		this.handlersSelected = selected;
		dialogChanged();
	}

	@Override
	public void dispose() {
		super.dispose();

		this.htmlViewCombo.dispose();
		this.xmlViewCombo.dispose();
		this.editor.dispose();
		this.rolesTable.dispose();
		this.addRowButton.dispose();
		this.removeRowButton.dispose();
	}

}