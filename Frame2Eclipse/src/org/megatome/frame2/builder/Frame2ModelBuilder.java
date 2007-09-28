package org.megatome.frame2.builder;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.megatome.frame2.Frame2Plugin;
import org.megatome.frame2.util.Frame2EntityResolver;
import org.megatome.frame2.util.PluginLogger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class Frame2ModelBuilder extends IncrementalProjectBuilder {

	class Frame2DeltaVisitor implements IResourceDeltaVisitor {
		public boolean visit(IResourceDelta delta) {
			IResource resource = delta.getResource();
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				// handle added resource
				checkXML(resource);
				break;
			case IResourceDelta.REMOVED:
				// handle removed resource
				break;
			case IResourceDelta.CHANGED:
				// handle changed resource
				checkXML(resource);
				break;
			}
			//return true to continue visiting children.
			return true;
		}
	}

	class Frame2ResourceVisitor implements IResourceVisitor {
		public boolean visit(IResource resource) {
			checkXML(resource);
			//return true to continue visiting children.
			return true;
		}
	}

	class XMLErrorHandler extends DefaultHandler {
		
		private IFile file;
		private EntityResolver resolver = new Frame2EntityResolver();

		public XMLErrorHandler(IFile file) {
			this.file = file;
		}

		private void addMarker(SAXParseException e, int severity) {
			Frame2ModelBuilder.this.addMarker(this.file, e.getMessage(), e
					.getLineNumber(), severity);
		}

		@Override
		public void error(SAXParseException exception) {
			addMarker(exception, IMarker.SEVERITY_ERROR);
		}

		@Override
		public void fatalError(SAXParseException exception) {
			addMarker(exception, IMarker.SEVERITY_ERROR);
		}

		@Override
		public void warning(SAXParseException exception) {
			addMarker(exception, IMarker.SEVERITY_WARNING);
		}

		@Override
		public InputSource resolveEntity(String publicId, String systemId)
				throws IOException, SAXException {
			return this.resolver.resolveEntity(publicId, systemId);
		}
	}

	public static final String BUILDER_ID = "org.megatome.frame2.Frame2Builder"; //$NON-NLS-1$

	private static final String MARKER_TYPE = "org.megatome.frame2.frame2ConfigProblem"; //$NON-NLS-1$

	private SAXParserFactory parserFactory;

	void addMarker(IFile file, String message, int lineNumber,
			int severity) {
		try {
			int markerLineNumber = lineNumber;
			IMarker marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			if (markerLineNumber == -1) {
				markerLineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, markerLineNumber);
		} catch (CoreException e) {
			PluginLogger.error("Error adding marker", e); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IProject[] build(int kind, @SuppressWarnings("unused")
	Map args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	void checkXML(IResource resource) {
		if (resource instanceof IFile && resource.getName().endsWith(Frame2Plugin.getString("Frame2ModelBuilder.frame2ConfigFile"))) { //$NON-NLS-1$
			IFile file = (IFile) resource;
			deleteMarkers(file);
			XMLErrorHandler reporter = new XMLErrorHandler(file);
			try {
				getParser().parse(file.getContents(), reporter);
			} catch (Exception e) {
				PluginLogger.error("Error Parsing Frame2 Config", e); //$NON-NLS-1$
			}
		}
	}

	private void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
			PluginLogger.error("Error Removing Markers", ce); //$NON-NLS-1$
		}
	}

	protected void fullBuild(@SuppressWarnings("unused")
	final IProgressMonitor monitor) {
		try {
			getProject().accept(new Frame2ResourceVisitor());
		} catch (CoreException e) {
			PluginLogger.error("Error Processing Frame2 Config", e); //$NON-NLS-1$
		}
	}

	private SAXParser getParser() throws ParserConfigurationException,
			SAXException {
		if (this.parserFactory == null) {
			this.parserFactory = SAXParserFactory.newInstance();
			this.parserFactory.setValidating(true);
		}
		return this.parserFactory.newSAXParser();
	}

	protected void incrementalBuild(IResourceDelta delta,
			@SuppressWarnings("unused")
			IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new Frame2DeltaVisitor());
	}
}
