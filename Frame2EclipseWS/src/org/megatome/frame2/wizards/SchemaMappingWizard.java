package org.megatome.frame2.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.megatome.frame2.Frame2WSPlugin;
import org.megatome.frame2.model.EventName;
import org.megatome.frame2.model.Frame2ModelException;
import org.megatome.frame2.model.SchemaMapping;

public class SchemaMappingWizard extends BaseFrame2Wizard {
	private SchemaMappingWizardPage1 page;
	
	public SchemaMappingWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	public SchemaMappingWizard(final IStructuredSelection selection) {
		super(selection);
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		this.page = new SchemaMappingWizardPage1(this.selection);
		addPage(this.page);
	}
		
	@Override
	public boolean performFinish() {
		final String schemaLocation = this.page.getSchemaLocation();
		final List<String> selectedEvents = this.page.getSelectedEvents();

		final IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					monitor
							.beginTask(Frame2WSPlugin.getResourceString("SchemaMappingWizard.creatingMessage"), 1); //$NON-NLS-1$
					if (SchemaMappingWizard.this.getFrame2Model() != null) {
						SchemaMapping mapping = new SchemaMapping();
						mapping.setSchemaLocation(schemaLocation);
						EventName[] eventNames = new EventName[selectedEvents.size()];
						int idx = 0;
						for (String event : selectedEvents) {
							EventName en = new EventName();
							en.setValue(event);
							eventNames[idx++] = en;
						}
						mapping.setEventName(eventNames);

						try {
							SchemaMappingWizard.this.getFrame2Model().addSchemaMapping(mapping);
							SchemaMappingWizard.this.persistModel(monitor);
						} catch (Frame2ModelException e) {
							throw new InvocationTargetException(e);
						}
					}
					monitor.worked(1);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (final InterruptedException e) {
			return false;
		} catch (final InvocationTargetException e) {
			final Throwable realException = e.getTargetException();
			MessageDialog
					.openError(
							getShell(),
							Frame2WSPlugin.getResourceString("SchemaMappingWizard.errorTitle"), realException.getMessage()); //$NON-NLS-1$
			return false;
		}
		return true;
	}

}
