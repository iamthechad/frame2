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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.ui.dialogs.StatusInfo;
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
import org.megatome.frame2.model.EventHandler;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.Frame2Plugin;

public class EventHandlerWizardPage1 extends NewTypeWizardPage {
    private Text handlerNameText;
    private Table initParamTable;
    private TableEditor editor;

    private Button removeButton;

    private ISelection selection;

    private static int addRowValue = 1;

    private IStatus handlerNameStatus;
    private IStatus initParamStatus;
    private StatusInfo badModelStatus = new StatusInfo();
    
    private EventHandler[] definedHandlers = new EventHandler[0];
    
    private static final String PAGE_NAME = Frame2Plugin.getResourceString("EventHandlerWizardPage1.wizardName"); //$NON-NLS-1$

    public EventHandlerWizardPage1(ISelection selection) {
        super(true, PAGE_NAME);
        setTitle(Frame2Plugin.getResourceString("EventHandlerWizardPage1.pageTitle")); //$NON-NLS-1$
        setDescription(Frame2Plugin.getResourceString("EventHandlerWizardPage1.pageDescription")); //$NON-NLS-1$
        this.selection = selection;
    }

    public void createControl(Composite parent) {
        initializeDialogUnits(parent);

        Composite composite = new Composite(parent, SWT.NONE);

        int nColumns = 4;

        GridLayout layout = new GridLayout();
        layout.numColumns = nColumns;
        layout.verticalSpacing = 9;
        composite.setLayout(layout);

        Label label = new Label(composite, SWT.NULL);
        label.setText(Frame2Plugin.getResourceString("EventHandlerWizardPage1.handlerNameLabel")); //$NON-NLS-1$

        handlerNameText = new Text(composite, SWT.BORDER | SWT.SINGLE);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        handlerNameText.setLayoutData(gd);
        handlerNameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });

        createSeparator(composite, nColumns);

        createContainerControls(composite, nColumns);
        createPackageControls(composite, nColumns);
        createTypeNameControls(composite, nColumns);

        createSeparator(composite, nColumns);

        label = new Label(composite, SWT.NULL);
        label.setText(Frame2Plugin.getResourceString("EventHandlerWizardPage1.initParamLabel")); //$NON-NLS-1$
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        label.setLayoutData(gd);

        initParamTable = new Table(composite, SWT.SINGLE | SWT.FULL_SELECTION);
        initParamTable.setLinesVisible(true);
        initParamTable.setHeaderVisible(true);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 4;
        initParamTable.setLayoutData(gd);
        int tableWidth = initParamTable.getClientArea().width;

        TableColumn tc = new TableColumn(initParamTable, SWT.NULL);
        tc.setText(Frame2Plugin.getResourceString("EventHandlerWizardPage1.nameColumn")); //$NON-NLS-1$
        tc.setWidth(200);

        TableColumn tc2 = new TableColumn(initParamTable, SWT.NULL);
        tc2.setText(Frame2Plugin.getResourceString("EventHandlerWizardPage1.valueColumn")); //$NON-NLS-1$
        tc2.setWidth(200);

        editor = new TableEditor(initParamTable);
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;
        initParamTable.addListener(SWT.MouseDown, new Listener() {
            public void handleEvent(Event event) {
                Rectangle clientArea = initParamTable.getClientArea();
                Point pt = new Point(event.x, event.y);
                int index = initParamTable.getTopIndex();
                while (index < initParamTable.getItemCount()) {
                    boolean visible = false;
                    final TableItem item = initParamTable.getItem(index);
                    for (int i = 0; i < initParamTable.getColumnCount(); i++) {
                        Rectangle rect = item.getBounds(i);
                        if (rect.contains(pt)) {
                            final int column = i;
                            final Text text =
                                new Text(initParamTable, SWT.NONE);
                            Listener textListener = new Listener() {
                                public void handleEvent(final Event e) {
                                    switch (e.type) {
                                        case SWT.FocusOut :
                                            item.setText(
                                                column,
                                                text.getText());
                                            text.dispose();
                                            break;
                                        case SWT.Traverse :
                                            switch (e.detail) {
                                                case SWT.TRAVERSE_RETURN :
                                                    item.setText(
                                                        column,
                                                        text.getText());
                                                    //FALL THROUGH
                                                case SWT.TRAVERSE_ESCAPE :
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
                            editor.setEditor(text, item, i);
                            text.setText(item.getText(i));
                            text.selectAll();
                            text.setFocus();
                            removeButton.setEnabled(true);
                            return;
                        }
                        if (!visible && rect.intersects(clientArea)) {
                            visible = true;
                        }
                    }
                    if (!visible)
                        return;
                    index++;
                }
            }
        });

        Button addButton = new Button(composite, SWT.PUSH);
        addButton.setText(Frame2Plugin.getResourceString("EventHandlerWizardPage1.addRowCtl")); //$NON-NLS-1$
        gd = new GridData(GridData.BEGINNING);
        gd.horizontalSpan = 2;
        addButton.setLayoutData(gd);
        addButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                final TableItem item = new TableItem(initParamTable, SWT.NONE);
                String[] initialText =
                    new String[] {
                        Frame2Plugin.getResourceString("EventHandlerWizardPage1.dummyName") + addRowValue, //$NON-NLS-1$
                        Frame2Plugin.getResourceString("EventHandlerWizardPage1.dummyValue") + addRowValue }; //$NON-NLS-1$
                addRowValue++;
                item.setText(initialText);
            }
        });

        removeButton = new Button(composite, SWT.PUSH);
        removeButton.setText(Frame2Plugin.getResourceString("EventHandlerWizardPage1.removeRowCtl")); //$NON-NLS-1$
        gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
        gd.horizontalSpan = 2;
        removeButton.setLayoutData(gd);
        removeButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                int selIndex = initParamTable.getSelectionIndex();
                if (selIndex == -1) {
                    return;
                }

                initParamTable.remove(selIndex);
                removeButton.setEnabled(false);
            }
        });
        removeButton.setEnabled(false);

        initialize();
        dialogChanged();
        setPageComplete(false);
        setControl(composite);
    }

    private void initialize() {
        addRowValue = 1;
        Frame2Model model = ((EventHandlerWizard)getWizard()).getFrame2Model();
        
        if (model != null) {
            definedHandlers = model.getEventHandlers();
        } else {
            setPageComplete(false);
            badModelStatus.setError(Frame2Plugin.getResourceString("EventHandlerWizardPage1.configError")); //$NON-NLS-1$
            doStatusUpdate();
        }

        if (selection != null
            && selection.isEmpty() == false
            && selection instanceof IStructuredSelection) {
            IStructuredSelection ssel = (IStructuredSelection)selection;
            if (ssel.size() > 1)
                return;
            IJavaElement jelem = getInitialJavaElement(ssel);
            initContainerPage(jelem);
            initTypePage(jelem);
        }

        List interfaces = new ArrayList();
        interfaces.add(Frame2Plugin.getResourceString("EventHandlerWizardPage1.eventHandlerInterface")); //$NON-NLS-1$
        setSuperInterfaces(interfaces, true);
    }

    //  ------ validation --------
    private void doStatusUpdate() {
        handlerNameStatus = getHandlerNameStatus();
        initParamStatus = getInitParamStatus();
        
        // status of all used components
        IStatus[] status =
            new IStatus[] {
                badModelStatus,
                handlerNameStatus,
                fContainerStatus,
                fPackageStatus,
                fTypeNameStatus,
                initParamStatus,
                };
                
        updateStatus(status);
    }

    private void dialogChanged() {
        doStatusUpdate();
    }

    private IStatus getHandlerNameStatus() {
        StatusInfo status = new StatusInfo();
        String handlerName = getHandlerName();

        if (handlerName.length() == 0) {
            status.setError(Frame2Plugin.getResourceString("EventHandlerWizardPage1.errorMissingHandlerName")); //$NON-NLS-1$
            return status;
        }

        for (int i = 0; i < definedHandlers.length; i++) {
            if (handlerName.equals(definedHandlers[i].getName())) {
                status.setError(Frame2Plugin.getResourceString("EventHandlerWizardPage1.errorDuplicateHandler")); //$NON-NLS-1$
                return status;
            }
        }

        return status;
    }

    private IStatus getInitParamStatus() {
        StatusInfo status = new StatusInfo();

        int paramCount = initParamTable.getItemCount();
        if (paramCount == 0) {
            return status;
        }

        for (int i = 0; i < paramCount; i++) {
            TableItem item = initParamTable.getItem(i);
            String paramName = item.getText(0);
            if (paramName.length() == 0) {
                status.setError(Frame2Plugin.getResourceString("EventHandlerWizardPage1.errorEmptyParamName")); //$NON-NLS-1$
                return status;
            }
        }

        return status;
    }

    private void updateStatus(String message) {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    public String getHandlerName() {
        return handlerNameText.getText();
    }
    
    public String getHandlerType() {
        String handlerClass = ""; //$NON-NLS-1$
        handlerClass = getPackageText();
        handlerClass += "."; //$NON-NLS-1$
        handlerClass += getTypeName();

        return handlerClass;
    }
    
    public List getInitParams() {
        List initParams = new ArrayList();
        
        int paramCount = initParamTable.getItemCount();
        for (int i = 0; i < paramCount; i++) {
            TableItem item = initParamTable.getItem(i);
            String[] nameValue = new String[] { item.getText(0), item.getText(1) };
            initParams.add(nameValue);
        }
        
        return initParams;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jdt.ui.wizards.NewContainerWizardPage#handleFieldChanged(java.lang.String)
     */
    protected void handleFieldChanged(String fieldName) {
        super.handleFieldChanged(fieldName);

        doStatusUpdate();
    }
    
    protected void createTypeMembers(
        IType newType,
        ImportsManager imports,
        IProgressMonitor monitor)
        throws CoreException {
    
        imports.addImport(Frame2Plugin.getResourceString("EventHandlerWizardPage1.eventHandlerInterface")); //$NON-NLS-1$
        
        StringBuffer handleBuffer = new StringBuffer();
        handleBuffer.append(Frame2Plugin.getResourceString("EventHandlerWizardPage1.public_modifier")); //$NON-NLS-1$
        handleBuffer.append(imports.addImport(Frame2Plugin.getResourceString("EventHandlerWizardPage1.stringImport"))); //$NON-NLS-1$
        handleBuffer.append(Frame2Plugin.getResourceString("EventHandlerWizardPage1.handleMethodStart")); //$NON-NLS-1$
        handleBuffer.append(imports.addImport(Frame2Plugin.getResourceString("EventHandlerWizardPage1.eventClass"))); //$NON-NLS-1$
        handleBuffer.append(Frame2Plugin.getResourceString("EventHandlerWizardPage1.event_name")); //$NON-NLS-1$
        handleBuffer.append(imports.addImport(Frame2Plugin.getResourceString("EventHandlerWizardPage1.contextClass"))); //$NON-NLS-1$
        handleBuffer.append(Frame2Plugin.getResourceString("EventHandlerWizardPage1.context_name")); //$NON-NLS-1$
        handleBuffer.append(imports.addImport(Frame2Plugin.getResourceString("EventHandlerWizardPage1.exceptionClass"))); //$NON-NLS-1$
        handleBuffer.append(Frame2Plugin.getResourceString("EventHandlerWizardPage1.handle_body")); //$NON-NLS-1$
        
        newType.createMethod(handleBuffer.toString(), null, true, monitor);
    
    }

    public void dispose() {
        super.dispose();
        handlerNameText.dispose();
        editor.dispose();
        initParamTable.dispose();
    
        removeButton.dispose();
    }

}