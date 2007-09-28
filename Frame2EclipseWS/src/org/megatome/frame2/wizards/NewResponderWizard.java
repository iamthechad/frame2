package org.megatome.frame2.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.megatome.frame2.Frame2WSPlugin;

public class NewResponderWizard extends BaseFrame2Wizard {
	private NewClassWizardPage page;

	public NewResponderWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	public NewResponderWizard(final IStructuredSelection selection) {
		super(selection);
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		this.page = new NewClassWizardPage();
		this.page.init(this.selection);
		this.page.addSuperInterface(Frame2WSPlugin.getResourceString("NewResponderWizard.responderClass")); //$NON-NLS-1$
		this.page.setDescription(Frame2WSPlugin.getResourceString("NewResponderWizard.wizardDescription")); //$NON-NLS-1$
		this.page.setTitle(Frame2WSPlugin.getResourceString("NewResponderWizard.wizardTitle")); //$NON-NLS-1$
		addPage(this.page);
	}
	
	@Override
	public String getFrame2WizardTitle() {
		return Frame2WSPlugin.getResourceString("NewResponderWizard.windowTitle"); //$NON-NLS-1$
	}

	@Override
	public boolean performFinish() {
		final IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
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
			MessageDialog.openError(getShell(), Frame2WSPlugin.getResourceString("NewResponderWizard.errorTitle"), realException //$NON-NLS-1$
					.getMessage());
			return false;
		}
		return true;
	}

	void doFinish(final IProgressMonitor monitor)
			throws CoreException {

		monitor.beginTask(Frame2WSPlugin.getResourceString("NewResponderWizard.creatingResponderStatus"), 3); //$NON-NLS-1$
		try {
			this.page.createType(monitor);
		} catch (final InterruptedException e1) {
			throwCoreException(Frame2WSPlugin.getResourceString("NewResponderWizard.createError") + e1.getMessage()); //$NON-NLS-1$
		}
		final IType type = this.page.getCreatedType();
		final ICompilationUnit icu = type.getCompilationUnit();
		IResource resource = null;
		if (icu != null) {
			resource = icu.getCorrespondingResource();
		}
		
		IFile file = null;
		if (resource != null) {
			file = (IFile)resource.getAdapter(IFile.class);
		}
		monitor.worked(1);
		if (file != null) {
			monitor.setTaskName(Frame2WSPlugin.getResourceString("NewResponderWizard.openingFileStatus")); //$NON-NLS-1$
			final IFile f = file;
			getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					final IWorkbenchPage iwpage = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					try {
						IDE.openEditor(iwpage, f, true);
					} catch (final PartInitException e) {
						// Ignore
					}
				}
			});
			monitor.worked(1);
		}
	}
}
