/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004-2005 Megatome Technologies.  All rights
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.model.EventHandler;
import org.megatome.frame2.model.Frame2Model;

public class EventHandlerWizardPage1 extends NewTypeWizardPage {
	private Text handlerNameText;
	Table initParamTable;
	TableEditor editor;

	Button removeButton;

	private final ISelection selection;

	static int addRowValue = 1;

	private IStatus handlerNameStatus;
	private IStatus initParamStatus;
	private IStatus badModelStatus = ValidationStatus.ok();

	private EventHandler[] definedHandlers = new EventHandler[0];

	private static final String PAGE_NAME = Frame2Plugin
			.getResourceString("EventHandlerWizardPage1.wizardName"); //$NON-NLS-1$

	public EventHandlerWizardPage1(final ISelection selection) {
		super(true, PAGE_NAME);
		setTitle(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.pageTitle")); //$NON-NLS-1$
		setDescription(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.pageDescription")); //$NON-NLS-1$
		this.selection = selection;
	}

	public void createControl(final Composite parent) {
		initializeDialogUnits(parent);

		final Composite composite = new Composite(parent, SWT.NONE);

		final int nColumns = 4;

		final GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		layout.verticalSpacing = 9;
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.handlerNameLabel")); //$NON-NLS-1$

		this.handlerNameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		this.handlerNameText.setLayoutData(gd);
		this.handlerNameText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		createSeparator(composite, nColumns);

		createContainerControls(composite, nColumns);
		createPackageControls(composite, nColumns);
		createTypeNameControls(composite, nColumns);

		createSeparator(composite, nColumns);

		label = new Label(composite, SWT.NULL);
		label.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.initParamLabel")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		label.setLayoutData(gd);

		this.initParamTable = new Table(composite, SWT.SINGLE
				| SWT.FULL_SELECTION);
		this.initParamTable.setLinesVisible(true);
		this.initParamTable.setHeaderVisible(true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 4;
		this.initParamTable.setLayoutData(gd);
		// int tableWidth = initParamTable.getClientArea().width;

		final TableColumn tc = new TableColumn(this.initParamTable, SWT.NULL);
		tc.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.nameColumn")); //$NON-NLS-1$
		tc.setWidth(200);

		final TableColumn tc2 = new TableColumn(this.initParamTable, SWT.NULL);
		tc2.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.valueColumn")); //$NON-NLS-1$
		tc2.setWidth(200);

		this.editor = new TableEditor(this.initParamTable);
		this.editor.horizontalAlignment = SWT.LEFT;
		this.editor.grabHorizontal = true;
		this.initParamTable.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(final Event event) {
				final Rectangle clientArea = EventHandlerWizardPage1.this.initParamTable
						.getClientArea();
				final Point pt = new Point(event.x, event.y);
				int index = EventHandlerWizardPage1.this.initParamTable
						.getTopIndex();
				while (index < EventHandlerWizardPage1.this.initParamTable
						.getItemCount()) {
					boolean visible = false;
					final TableItem item = EventHandlerWizardPage1.this.initParamTable
							.getItem(index);
					for (int i = 0; i < EventHandlerWizardPage1.this.initParamTable
							.getColumnCount(); i++) {
						final Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							final Text text = new Text(
									EventHandlerWizardPage1.this.initParamTable,
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
							EventHandlerWizardPage1.this.editor.setEditor(text,
									item, i);
							text.setText(item.getText(i));
							text.selectAll();
							text.setFocus();
							EventHandlerWizardPage1.this.removeButton
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

		final Button addButton = new Button(composite, SWT.PUSH);
		addButton.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.addRowCtl")); //$NON-NLS-1$
		gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		addButton.setLayoutData(gd);
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				final TableItem item = new TableItem(
						EventHandlerWizardPage1.this.initParamTable, SWT.NONE);
				final String[] initialText = new String[] {
						Frame2Plugin
								.getResourceString("EventHandlerWizardPage1.dummyName") + addRowValue, //$NON-NLS-1$
						Frame2Plugin
								.getResourceString("EventHandlerWizardPage1.dummyValue") + addRowValue }; //$NON-NLS-1$
				addRowValue++;
				item.setText(initialText);
			}
		});

		this.removeButton = new Button(composite, SWT.PUSH);
		this.removeButton.setText(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.removeRowCtl")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.horizontalSpan = 2;
		this.removeButton.setLayoutData(gd);
		this.removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				final int selIndex = EventHandlerWizardPage1.this.initParamTable
						.getSelectionIndex();
				if (selIndex == -1) {
					return;
				}

				EventHandlerWizardPage1.this.initParamTable.remove(selIndex);
				EventHandlerWizardPage1.this.removeButton.setEnabled(false);
			}
		});
		this.removeButton.setEnabled(false);

		initialize();
		dialogChanged();
		setPageComplete(false);
		setControl(composite);
	}

	private void initialize() {
		addRowValue = 1;
		final Frame2Model model = ((EventHandlerWizard) getWizard())
				.getFrame2Model();

		if (model != null) {
			this.definedHandlers = model.getEventHandlers();
		} else {
			setPageComplete(false);
			this.badModelStatus = ValidationStatus.error(Frame2Plugin
					.getResourceString("EventHandlerWizardPage1.configError")); //$NON-NLS-1$
			doStatusUpdate();
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

		final List<String> interfaces = new ArrayList<String>();
		interfaces
				.add(Frame2Plugin
						.getResourceString("EventHandlerWizardPage1.eventHandlerInterface")); //$NON-NLS-1$
		setSuperInterfaces(interfaces, true);
	}

	// ------ validation --------
	private void doStatusUpdate() {
		this.handlerNameStatus = getHandlerNameStatus();
		this.initParamStatus = getInitParamStatus();

		// status of all used components
		final IStatus[] status = new IStatus[] { this.badModelStatus,
				this.handlerNameStatus, this.fContainerStatus,
				this.fPackageStatus, this.fTypeNameStatus,
				this.initParamStatus, };

		updateStatus(status);
	}

	void dialogChanged() {
		doStatusUpdate();
	}

	private IStatus getHandlerNameStatus() {
		// IStatus status = new Status();
		final String handlerName = getHandlerName();

		if (handlerName.length() == 0) {
			return ValidationStatus
					.error(Frame2Plugin
							.getResourceString("EventHandlerWizardPage1.errorMissingHandlerName")); //$NON-NLS-1$
		}

		for (int i = 0; i < this.definedHandlers.length; i++) {
			if (handlerName.equals(this.definedHandlers[i].getName())) {
				return ValidationStatus
						.error(Frame2Plugin
								.getResourceString("EventHandlerWizardPage1.errorDuplicateHandler")); //$NON-NLS-1$
			}
		}

		return ValidationStatus.ok();
	}

	private IStatus getInitParamStatus() {
		final int paramCount = this.initParamTable.getItemCount();
		if (paramCount == 0) {
			return ValidationStatus.ok();
		}

		for (int i = 0; i < paramCount; i++) {
			final TableItem item = this.initParamTable.getItem(i);
			final String paramName = item.getText(0);
			if (paramName.length() == 0) {
				return ValidationStatus
						.error(Frame2Plugin
								.getResourceString("EventHandlerWizardPage1.errorEmptyParamName")); //$NON-NLS-1$
			}
		}

		return ValidationStatus.ok();
	}

	/*
	 * private void updateStatus(String message) { setErrorMessage(message);
	 * setPageComplete(message == null); }
	 */

	public String getHandlerName() {
		return this.handlerNameText.getText();
	}

	public String getHandlerType() {
		String handlerClass = ""; //$NON-NLS-1$
		handlerClass = getPackageText();
		handlerClass += "."; //$NON-NLS-1$
		handlerClass += getTypeName();

		return handlerClass;
	}

	public List<String[]> getInitParams() {
		final List<String[]> initParams = new ArrayList<String[]>();

		final int paramCount = this.initParamTable.getItemCount();
		for (int i = 0; i < paramCount; i++) {
			final TableItem item = this.initParamTable.getItem(i);
			final String[] nameValue = new String[] { item.getText(0),
					item.getText(1) };
			initParams.add(nameValue);
		}

		return initParams;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.wizards.NewContainerWizardPage#handleFieldChanged(java.lang.String)
	 */
	@Override
	protected void handleFieldChanged(final String fieldName) {
		super.handleFieldChanged(fieldName);

		doStatusUpdate();
	}

	@Override
	protected void createTypeMembers(final IType newType,
			final ImportsManager imports, final IProgressMonitor monitor)
			throws CoreException {

		imports
				.addImport(Frame2Plugin
						.getResourceString("EventHandlerWizardPage1.eventHandlerInterface")); //$NON-NLS-1$

		final StringBuffer handleBuffer = new StringBuffer();
		handleBuffer.append(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.public_modifier")); //$NON-NLS-1$
		handleBuffer.append(imports.addImport(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.stringImport"))); //$NON-NLS-1$
		handleBuffer
				.append(Frame2Plugin
						.getResourceString("EventHandlerWizardPage1.handleMethodStart")); //$NON-NLS-1$
		handleBuffer.append(imports.addImport(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.eventClass"))); //$NON-NLS-1$
		handleBuffer.append(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.event_name")); //$NON-NLS-1$
		handleBuffer.append(imports.addImport(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.contextClass"))); //$NON-NLS-1$
		handleBuffer.append(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.context_name")); //$NON-NLS-1$
		handleBuffer.append(imports.addImport(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.exceptionClass"))); //$NON-NLS-1$
		handleBuffer.append(Frame2Plugin
				.getResourceString("EventHandlerWizardPage1.handle_body")); //$NON-NLS-1$

		newType.createMethod(handleBuffer.toString(), null, true, monitor);

	}

	@Override
	public void dispose() {
		super.dispose();
		this.handlerNameText.dispose();
		this.editor.dispose();
		this.initParamTable.dispose();

		this.removeButton.dispose();
	}

}