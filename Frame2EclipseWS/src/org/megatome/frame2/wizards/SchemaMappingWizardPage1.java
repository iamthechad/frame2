package org.megatome.frame2.wizards;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;
import org.megatome.frame2.Frame2WSPlugin;
import org.megatome.frame2.model.Frame2Event;
import org.megatome.frame2.model.Frame2Model;
import org.megatome.frame2.model.SchemaMapping;

public class SchemaMappingWizardPage1 extends WizardPage {
	private ISelection selection;
	
	private Label schemaLocationLabel;
	private Text schemaLocationText;
	private Button schemaLocationBrowse;
	
	private Button addButton;
	private Button removeButton;
	private Table availableEventsTable;
	private Table selectedEventsTable;
	
	private IProject rootProject;
	private boolean badModel = false;
	
	public SchemaMappingWizardPage1(final ISelection selection) {
		super(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.wizardName")); //$NON-NLS-1$
		setTitle(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.wizardTitle")); //$NON-NLS-1$
		setDescription(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.wizardDescription")); //$NON-NLS-1$
		this.selection = selection;
	}

	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 5;
		layout.verticalSpacing = 9;
		
		this.schemaLocationLabel = new Label(container, SWT.NULL);
		this.schemaLocationLabel.setText(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.schemaLocationLabel")); //$NON-NLS-1$
		GridData gd = new GridData();
		this.schemaLocationLabel.setLayoutData(gd);

		this.schemaLocationText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		gd.horizontalSpan = 3;
		this.schemaLocationText.setLayoutData(gd);
		this.schemaLocationText.addModifyListener(new ModifyListener() {
			public void modifyText(@SuppressWarnings("unused")
			final ModifyEvent e) {
				dialogChanged();
			}
		});

		this.schemaLocationBrowse = new Button(container, SWT.PUSH);
		this.schemaLocationBrowse.setText(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.browseButtonText")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalAlignment = SWT.END;
		this.schemaLocationBrowse.setLayoutData(gd);
		this.schemaLocationBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				handleBrowse();
			}
		});
		
		this.availableEventsTable = new Table(container, SWT.SINGLE
				| SWT.FULL_SELECTION);
		this.availableEventsTable.setHeaderVisible(true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		this.availableEventsTable.setLayoutData(gd);
		this.availableEventsTable
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(@SuppressWarnings("unused")
					final SelectionEvent e) {
						enableMovementButtons();
					}
				});

		TableColumn tc = new TableColumn(this.availableEventsTable, SWT.NULL);
		tc.setText(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.availableEventsHeader")); //$NON-NLS-1$
		tc.setWidth(200);

		final Composite buttonContainer = new Composite(container, SWT.NULL);
		final RowLayout rl = new RowLayout();
		rl.type = SWT.VERTICAL;
		rl.pack = false;
		buttonContainer.setLayout(rl);

		this.addButton = new Button(buttonContainer, SWT.PUSH);
		this.addButton.setImage(getImage(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.rightArrowImage"))); //$NON-NLS-1$
		this.addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				addEventToSelected();
			}
		});

		this.removeButton = new Button(buttonContainer, SWT.PUSH);
		this.removeButton.setImage(getImage(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.leftArrowImage"))); //$NON-NLS-1$
		this.removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				removeEventFromSelected();
			}
		});

		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalSpan = 1;
		buttonContainer.setLayoutData(gd);

		this.selectedEventsTable = new Table(container, SWT.SINGLE
				| SWT.FULL_SELECTION);
		this.selectedEventsTable.setHeaderVisible(true);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		this.selectedEventsTable.setLayoutData(gd);
		this.selectedEventsTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(@SuppressWarnings("unused")
			final SelectionEvent e) {
				enableMovementButtons();
			}
		});

		tc = new TableColumn(this.selectedEventsTable, SWT.NULL);
		tc.setText(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.selectedEventsHeader")); //$NON-NLS-1$
		tc.setWidth(200);
		
		initialize();
		// dialogChanged();
		setPageComplete(false);
		setControl(container);
	}
	
	private void initialize() {
		final Frame2Model model = ((SchemaMappingWizard) getWizard())
				.getFrame2Model();

		if (model != null) {
			final Frame2Event[] events = model.getEvents();
			for (int i = 0; i < events.length; i++) {
				final TableItem item = new TableItem(
						this.availableEventsTable, SWT.NULL);
				item.setText(events[i].getName());
			}
		} else {
			setPageComplete(false);
			this.badModel = true;
			
			this.schemaLocationLabel.setEnabled(false);
			this.schemaLocationText.setEnabled(false);
			this.schemaLocationBrowse.setEnabled(false);
			
			this.availableEventsTable.setEnabled(false);
			this.selectedEventsTable.setEnabled(false);
			this.addButton.setEnabled(false);
			this.removeButton.setEnabled(false);
			
			dialogChanged();
		}

		if (this.selection != null && this.selection.isEmpty() == false
				&& this.selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) this.selection;
			if (ssel.size() > 1) {
				return;
			}

			final Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				this.rootProject = ((IResource) obj).getProject();
			}
		}
	}
	
	void dialogChanged() {
		if (this.badModel) {
			updateStatus(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.badModelStatus")); //$NON-NLS-1$
			return;
		}

		final String location = getSchemaLocation();

		if (location.length() == 0) {
			updateStatus(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.noLocationMessage")); //$NON-NLS-1$
			return;
		}
		
		final Frame2Model model = ((SchemaMappingWizard) getWizard()).getFrame2Model();
		SchemaMapping[] mappings = model.getSchemaMappings();
		for (SchemaMapping mapping : mappings) {
			if (location.equalsIgnoreCase(mapping.getSchemaLocation())) {
				updateStatus(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.locationUsedMessage")); //$NON-NLS-1$
				return;
			}
		}
		
		if (getSelectedEvents().isEmpty()) {
			updateStatus(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.noEventsMessage")); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}
	
	void handleBrowse() {
		final IResource[] schemaElements = findSchemaResources();
		final ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(
				getShell(), schemaElements);
		dialog.setInitialSelections(schemaElements);
		if (dialog.open() == Window.OK) {
			final Object[] results = dialog.getResult();
			if (results.length == 1) {
				this.schemaLocationText
						.setText("/" + ((IFile) results[0]).getProjectRelativePath().toString()); //$NON-NLS-1$
				dialogChanged();
			}
		}
	}
	
	void addEventToSelected() {
		final int selIndex = this.availableEventsTable.getSelectionIndex();

		if (selIndex == -1) {
			return;
		}

		moveItemToTable(this.availableEventsTable,
				this.selectedEventsTable, selIndex);
		enableMovementButtons();
		dialogChanged();
	}

	void removeEventFromSelected() {
		final int selIndex = this.selectedEventsTable.getSelectionIndex();

		if (selIndex == -1) {
			return;
		}

		moveItemToTable(this.selectedEventsTable,
				this.availableEventsTable, selIndex);
		enableMovementButtons();
		dialogChanged();
	}
	
	private void moveItemToTable(final Table srcTable, final Table destTable,
			final int index) {
		final TableItem item = srcTable.getItem(index);
		final TableItem newItem = new TableItem(destTable, SWT.NULL);
		newItem.setText(item.getText());
		srcTable.remove(index);
	}
	
	void enableMovementButtons() {
		final int availSel = this.availableEventsTable.getSelectionIndex();
		final int selectedSel = this.selectedEventsTable.getSelectionIndex();

		this.addButton.setEnabled(availSel != -1);
		this.removeButton.setEnabled(selectedSel != -1);
	}
	
	public List<String> getSelectedEvents() {
		final List<String> selected = new ArrayList<String>();

		final int selCount = this.selectedEventsTable.getItemCount();
		for (int i = 0; i < selCount; i++) {
			final TableItem item = this.selectedEventsTable.getItem(i);
			selected.add(item.getText());
		}

		return selected;
	}
	
	private IResource[] findSchemaResources() {
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
			if ((fileExt != null) && (fileExt.equalsIgnoreCase(Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.xsd_extension")))) { //$NON-NLS-1$
				resourceList.add(members[i]);
			}
		}
	}
	
	public String getSchemaLocation() {
		return this.schemaLocationText.getText();
	}
	
	private void updateStatus(final String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	private Image getImage(final String imageName) {
		final String iconPath = Frame2WSPlugin.getResourceString("SchemaMappingWizardPage1.iconsLocation"); //$NON-NLS-1$
		ImageDescriptor id = null;
		try {
			final Frame2WSPlugin plugin = Frame2WSPlugin.getDefault();
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
		this.schemaLocationLabel.dispose();
		this.schemaLocationText.dispose();
		this.schemaLocationBrowse.dispose();
		
		if (this.addButton != null) {
			this.addButton.dispose();
		}
		if (this.removeButton != null) {
			this.removeButton.dispose();
		}
		this.availableEventsTable.dispose();
		this.selectedEventsTable.dispose();
	}

}
