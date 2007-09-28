package org.megatome.frame2.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
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
import org.megatome.frame2.Frame2WSPlugin;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.util.StatusFactory;

public class SoapFrontControllerWizardPage1 extends NewTypeWizardPage {
	private static final String UNDERSCORE = "_"; //$NON-NLS-1$
	private static final String DOLLAR_SIGN = "$"; //$NON-NLS-1$
	private static final String RPAREN = ")"; //$NON-NLS-1$
	private static final String LPAREN = "("; //$NON-NLS-1$

	private IStructuredSelection selection;

	private IStatus badModelStatus = null;
	
	Table rolesTable;
	TableEditor editor;
	private Button addRowButton;
	Button removeRowButton;
	static int roleIndex = 1;

	public SoapFrontControllerWizardPage1(final IStructuredSelection selection) {
		super(true, Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.wizardName")); //$NON-NLS-1$
		setTitle(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.wizardTitle")); //$NON-NLS-1$
		setDescription(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.wizardDescription")); //$NON-NLS-1$
		this.selection = selection;
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		final Composite composite = new Composite(parent, SWT.NONE);

		final int nColumns = 4;

		final GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);

		createContainerControls(composite, nColumns);
		createPackageControls(composite, nColumns);
		createTypeNameControls(composite, nColumns);

		createSeparator(composite, nColumns);
		
		// Table? 
		Label label = new Label(composite, SWT.NULL);
		label.setText(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNamesLabel")); //$NON-NLS-1$
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 4;
		label.setLayoutData(gd);
		
		this.rolesTable = new Table(composite, SWT.SINGLE | SWT.FULL_SELECTION);
		this.rolesTable.setHeaderVisible(true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 4;
		this.rolesTable.setLayoutData(gd);
		//this.rolesTable.set

		final TableColumn tc = new TableColumn(this.rolesTable, SWT.NULL);
		tc.setText(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNamesColumn")); //$NON-NLS-1$
		tc.setWidth(200);

		this.editor = new TableEditor(this.rolesTable);
		this.editor.horizontalAlignment = SWT.LEFT;
		this.editor.grabHorizontal = true;
		this.rolesTable.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(final Event event) {
				final Rectangle clientArea = SoapFrontControllerWizardPage1.this.rolesTable
						.getClientArea();
				final Point pt = new Point(event.x, event.y);
				int index = SoapFrontControllerWizardPage1.this.rolesTable
						.getTopIndex();
				while (index < SoapFrontControllerWizardPage1.this.rolesTable
						.getItemCount()) {
					boolean visible = false;
					final TableItem item = SoapFrontControllerWizardPage1.this.rolesTable
							.getItem(index);
					for (int i = 0; i < SoapFrontControllerWizardPage1.this.rolesTable
							.getColumnCount(); i++) {
						final Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							final Text text = new Text(
									SoapFrontControllerWizardPage1.this.rolesTable,
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
							SoapFrontControllerWizardPage1.this.editor.setEditor(text,
									item, i);
							text.setText(item.getText(i));
							text.selectAll();
							text.setFocus();
							SoapFrontControllerWizardPage1.this.removeRowButton
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

		this.addRowButton = new Button(composite, SWT.PUSH);
		this.addRowButton.setText(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.addMethodButton")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalSpan = 2;
		this.addRowButton.setLayoutData(gd);
		this.addRowButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				final TableItem item = new TableItem(
						SoapFrontControllerWizardPage1.this.rolesTable, SWT.NULL);
				item
						.setText(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodName") + roleIndex++); //$NON-NLS-1$
				dialogChanged();
			}
		});

		this.removeRowButton = new Button(composite, SWT.PUSH);
		this.removeRowButton.setText(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.removeMethodButton")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.horizontalSpan = 2;
		this.removeRowButton.setLayoutData(gd);
		this.removeRowButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				final int selIndex = SoapFrontControllerWizardPage1.this.rolesTable
						.getSelectionIndex();
				if (selIndex != -1) {
					SoapFrontControllerWizardPage1.this.rolesTable.remove(selIndex);
				}

				SoapFrontControllerWizardPage1.this.removeRowButton.setEnabled(false);
				dialogChanged();
			}
		});
		this.removeRowButton.setEnabled(false);

		initialize();
		dialogChanged();
		setControl(composite);
		// Dialog.applyDialogFont(composite);
	}

	private void initialize() {
		final Frame2Model model = ((SoapFrontControllerWizard) getWizard())
				.getFrame2Model();

		this.badModelStatus = StatusFactory.ok();
		if (model == null) {
			setPageComplete(false);
			this.badModelStatus = StatusFactory.error(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.configError")); //$NON-NLS-1$
		}

		if (this.selection != null && !this.selection.isEmpty()) {
			if (this.selection.size() > 1) {
				return;
			}
			final IJavaElement jelem = getInitialJavaElement(this.selection);
			initContainerPage(jelem);
			initTypePage(jelem);
		}

		// initialized = true;
		setSuperClass(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.soapFrontControllerClass"), true); //$NON-NLS-1$
	}

	@Override
	protected void handleFieldChanged(final String fieldName) {
		super.handleFieldChanged(fieldName);

		doStatusUpdate();
	}

	void dialogChanged() {
		// What needs to be here?
		doStatusUpdate();
	}

	private void doStatusUpdate() {
		// status of all used components
		if ((this.badModelStatus.getMessage() != null)
				&& (!this.badModelStatus.getMessage().equals(""))) { //$NON-NLS-1$
			final IStatus[] status = new IStatus[] { this.badModelStatus };
			updateStatus(status);
			return;
		}
		final IStatus[] status = new IStatus[] { this.badModelStatus,
				this.fContainerStatus, this.fPackageStatus,
				this.fTypeNameStatus};
		IStatus[] methodStatus = getMethodNameStatus();
		final IStatus[] fullStatus = new IStatus[status.length + methodStatus.length];
		System.arraycopy(status, 0, fullStatus, 0, status.length);
		System.arraycopy(methodStatus, 0, fullStatus, status.length, methodStatus.length);
		// the most severe status will be displayed and the ok button
		// enabled/disabled.
		updateStatus(methodStatus);
	}
	
	private IStatus[] getMethodNameStatus() {
		List<String> methodNames = getMethodNames();
		if (methodNames.isEmpty()) {
			return new IStatus[] { StatusFactory.error(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.noMethodsError")) }; //$NON-NLS-1$
		}
		
		List<IStatus> statusItems = new ArrayList<IStatus>();
		String regex = Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameRegex"); //$NON-NLS-1$
		for (String text : methodNames) {
			if (text.contains(LPAREN) || text.contains(RPAREN)) {
				statusItems.add(StatusFactory.error(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameMsgPre") + text + Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameMsgParensPost"))); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (!Pattern.matches(regex, text)) {
				statusItems.add(StatusFactory.error(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameMsgPre") + text + Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameMsgLegalPost"))); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (text.startsWith(DOLLAR_SIGN)) {
				statusItems.add(StatusFactory.warning(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameDollarStartMessage"))); //$NON-NLS-1$
			}
			if (text.startsWith(UNDERSCORE)) {
				statusItems.add(StatusFactory.warning(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameUnderscoreStartMsg"))); //$NON-NLS-1$
			}
			if (text.contains(DOLLAR_SIGN)) {
				statusItems.add(StatusFactory.warning(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameContainDollarMsg"))); //$NON-NLS-1$
			}
		}
		
		if (!statusItems.isEmpty()) {
			return statusItems.toArray(new IStatus[statusItems.size()]);
		}
		
		return new IStatus[] { StatusFactory.ok() };
	}
	
	public List<String> getMethodNames() {
		List<String> methodNames = new ArrayList<String>();
		TableItem[] items = this.rolesTable.getItems();
		for (TableItem item : items) {
			methodNames.add(item.getText());
		}
		
		return methodNames;
	}
	
	@Override
	protected void createTypeMembers(final IType newType,
			final ImportsManager imports, final IProgressMonitor monitor)
			throws CoreException {

		imports.addImport(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.soapFrontControllerClass")); //$NON-NLS-1$
		imports.addImport(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.elementClass")); //$NON-NLS-1$
		
		String epsMethodContent = Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.eventPkgSpecMethod"); //$NON-NLS-1$
		newType.createMethod(epsMethodContent, null, false, monitor);
		
		final List<String> methodNames = ((SoapFrontControllerWizard) getWizard())
		.getMethodNames();
		final String content = Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.elementWrapperMethod"); //$NON-NLS-1$
		for (String name : methodNames) {
			newType.createMethod(content.replaceAll(Frame2WSPlugin.getResourceString("SoapFrontControllerWizardPage1.methodNameToken"), name), null, false, monitor); //$NON-NLS-1$
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		this.rolesTable.dispose();
		this.editor.dispose();
		this.addRowButton.dispose();
		this.removeRowButton.dispose();
	}

}
