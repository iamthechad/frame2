package org.megatome.frame2.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.megatome.frame2.Frame2WSPlugin;

public class SoapFrontControllerWizard extends BaseFrame2Wizard {
	private SoapFrontControllerWizardPage1 page;
	
	private List<String> methodNames = new ArrayList<String>();

	public SoapFrontControllerWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	public SoapFrontControllerWizard(IStructuredSelection selection) {
		super(selection);
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		this.page = new SoapFrontControllerWizardPage1(this.selection);
		addPage(this.page);
	}

	@Override
	public String getFrame2WizardTitle() {
		return Frame2WSPlugin.getResourceString("SoapFrontControllerWizard.windowTitle"); //$NON-NLS-1$
	}
	
	List<String> getMethodNames() {
		return this.methodNames;
	}

	@Override
	public boolean performFinish() {
		final String containerName = this.page.getPackageFragmentRootText();
		this.methodNames = new ArrayList<String>(this.page.getMethodNames());
		final IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(containerName, monitor);
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
			MessageDialog.openError(getShell(), Frame2WSPlugin.getResourceString("SoapFrontControllerWizard.error"), realException //$NON-NLS-1$
					.getMessage());
			return false;
		}
		return true;
	}

	void doFinish(final String containerName, final IProgressMonitor monitor)
			throws CoreException {

		// create a sample file
		monitor.beginTask(Frame2WSPlugin.getResourceString("SoapFrontControllerWizard.creatingClassStatus"), 3); //$NON-NLS-1$
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException(Frame2WSPlugin.getResourceString("SoapFrontControllerWizard.containerMsgPre") + containerName + Frame2WSPlugin.getResourceString("SoapFrontControllerWizard.containerMsgPost")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		final IContainer container = (IContainer) resource;

		try {
			this.page.createType(monitor);
		} catch (final InterruptedException e1) {
			throwCoreException(Frame2WSPlugin.getResourceString("SoapFrontControllerWizard.classCreateError") + e1.getMessage()); //$NON-NLS-1$
		}
		final IType type = this.page.getCreatedType();
		final IPath typePath = type.getPath();
		final IPath containerPath = container.getFullPath();

		final int count = typePath.matchingFirstSegments(containerPath);
		final IPath newTypePath = typePath.removeFirstSegments(count);

		final IFile file = container.getFile(newTypePath);
		monitor.worked(1);
		monitor.setTaskName(Frame2WSPlugin.getResourceString("SoapFrontControllerWizard.openingFileStatus")); //$NON-NLS-1$
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				final IWorkbenchPage iwpage = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(iwpage, file, true);
				} catch (final PartInitException e) {
					// Ignore
				}
			}
		});
		monitor.worked(1);
	}

}
