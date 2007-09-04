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
package org.megatome.frame2.front;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.Globals;
import org.megatome.frame2.errors.Errors;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.front.config.ViewType;
import org.megatome.frame2.introspector.IntrospectorException;
import org.megatome.frame2.introspector.IntrospectorFactory;
import org.megatome.frame2.log.Logger;
import org.megatome.frame2.log.LoggerFactory;
import org.megatome.frame2.validator.CommonsValidatorException;

/**
 * A request models the execution of an HTTP request through the Event and the
 * EventHandlers. It is a primary delegate of RequestProcessor, and brings
 * together the data and logic necessary for processing the request.
 */
public class HttpRequestProcessor extends RequestProcessorBase {
    protected ServletContext servletContext;

    protected HttpServletRequest request;

    private HttpServletResponse response;

    private Map<String, Object> requestParams;

    private Exception fileUploadException = null;

    public static final String CONTENT_TYPE = "Content-type"; //$NON-NLS-1$

    private static final String MULTIPART = "multipart/"; //$NON-NLS-1$

    private static final Logger LOGGER = LoggerFactory.instance(HttpRequestProcessor.class.getName());

    /**
     * Constructor for HttpRequestProcessor.
     */
    public HttpRequestProcessor(Configuration config, ServletContext context,
            HttpServletRequest request, HttpServletResponse response) {
        super(config);
        this.servletContext = context;
        this.context = new ContextImpl();
        this.request = request;
        this.response = response;
        this.requestParams = getRequestParameterMap(request);
    }

    /**
     * Get the event that is associated with the request in the configuration.
     * The config entry:
     * 
     * <pre>
     * &lt;event name=&quot;displayUsers&quot; type=&quot;test.org.megatome.app.user.DisplayUsers&quot;/&gt;
     * </pre>
     * 
     * matches the event name "displayUsers" to a class. A request to
     * <code>http://somehost/webapp/displayUsers.f2</code> will invoke the
     * DisplayUsers class.
     * @return The event that is associated with the request in the
     *         configuration.
     */
    public Event getEvent() throws ConfigException {
        String eventName = getEventName(this.request.getServletPath());
        Event event = getConfig().getEventProxy(eventName).getEvent();
        if (event != null) {
            event.setEventName(eventName);
        }
        return event;

    }

    private String getEventName(String servletPath) {
        String result = null;

        if (servletPath != null) {
            int point = servletPath.indexOf("."); //$NON-NLS-1$
            int lastSlash = servletPath.lastIndexOf("/"); //$NON-NLS-1$

            if (point != -1 && lastSlash != -1) {
                result = servletPath.substring(lastSlash + 1, point);
            } else {
                result = servletPath.substring(lastSlash + 1);
            }
        }

        return result;
    }

    /**
     * Method mapRequestToEvent.
     * @param event
     * @return String
     */
    boolean mapRequestToEvent(Event event, boolean validate)
            throws IntrospectorException, CommonsValidatorException {

        IntrospectorFactory.instance().mapProperties(this.requestParams, event);

        return validateMappedEvent(validate, event);
    }
    
    boolean mapAttributesToEvent(Map<String, Object> attributes, Event event, boolean validate)
            throws IntrospectorException, CommonsValidatorException {

        IntrospectorFactory.instance().mapProperties(attributes, event);

        return validateMappedEvent(validate, event);
    }
    
    private boolean validateMappedEvent(boolean validate, Event event) throws CommonsValidatorException {
    	boolean passed = true;

        if (validate && (event != null)) {
            try {
                passed = event.validate(this.errors);
            } catch (NoClassDefFoundError e) {
                // Bug Fix: 917752
                // Detect missing CommonsValidator and respond appropriately
                // Error is thrown only when plugin is specified
                PluginProxy cvp = getConfig().getPluginProxy(
                        "CommonsValidatorPlugin"); //$NON-NLS-1$
                if (cvp != null) {
                    LOGGER.warn("Cannot validate event", e); //$NON-NLS-1$
                    throw new CommonsValidatorException(
                            "CommonsValidator missing from classpath, but specified in configuration"); //$NON-NLS-1$
                }
            }
        }

        // always put error in request.
        this.request.setAttribute(Globals.ERRORS, this.errors);
        return passed;
    }

    /**
     * Method getContext.
     */
    @Override
	protected ContextWrapper getContextWrapper() {
        return this.context;
    }

    void forwardTo(String view) throws ServletException, IOException {
        this.servletContext.getRequestDispatcher(view).forward(this.request, this.response);
    }

    void redirectTo(String view) throws IOException {
        String encodedURL = encodeRedirectURL(view);

        this.response.sendRedirect(encodedURL);
    }

    private String encodeRedirectURL(String view) {
        StringBuffer buf = new StringBuffer(view);
        String[] redirectAttrs = this.context.getRedirectAttributes();
        int len = redirectAttrs.length;

        if (len > 0) {
            buf.append("?"); //$NON-NLS-1$
        }

        for (int i = 0; i < len; i++) {
            buf.append(getParam(i, redirectAttrs));

            if ((i + 1) < len) {
                buf.append("&"); //$NON-NLS-1$
            }
        }

        return buf.toString();
    }

    private StringBuffer getParam(int index, String[] redirectAttrs) {
        String attrKey = redirectAttrs[index];
        StringBuffer param = new StringBuffer().append(attrKey).append("=") //$NON-NLS-1$
                .append(this.context.getRequestAttribute(attrKey));

        return param;
    }

    /**
     * Process the HTTP request. Processing an HTTP request involves the
     * following steps:
     * <ol>
     * <li>Populate the event from request parameters via introspection</li>
     * <li>Step through all event handlers, passing in the populated event for
     * each</li>
     * <li>Forward to the appropriate location, based on returns from handlers
     * </li>
     * </ol>
     * @return Null
     * @throws Throwable
     * @see org.megatome.frame2.front.RequestProcessor#processRequest()
     */
    public Object processRequest() throws Throwable {
        LOGGER.debug("In HttpRequestProcessor processRequest()"); //$NON-NLS-1$
        //String view = null;
        //ForwardProxy result = null;
        ViewProxy proxy = null;
        try {
            String eventName = getEventName(this.request.getServletPath());
            Event event = getEvent();

            if (this.requestParams == null) {
                Frame2Exception e = null;
                if (this.fileUploadException != null) {
                    e = new Frame2Exception(this.fileUploadException);
                    this.fileUploadException = null;
                } else {
                    e = new Frame2Exception(
                            "File Upload Error: There was an error parsing file upload parameters."); //$NON-NLS-1$
                }
                throw e;
            }

            if (!isUserAuthorizedForEvent(eventName)) {
                String login = this.request.getRemoteUser();

                login = (login != null) ? login : ""; //$NON-NLS-1$
                throw new AuthorizationException("User " + login //$NON-NLS-1$
                        + " not authorized for mapping " + eventName); //$NON-NLS-1$
            }

            if (isCancelRequest(this.requestParams)) {
                ForwardProxy result = getConfig().cancelViewFor(eventName,
                        Configuration.HTML_TOKEN);

                // TODO This assumes that nothing gets forwarded to the cancel page
                if (result.isEventType()) {
                    result = callHandlers(result.getPath(), getConfig()
                            .getEventProxy(result.getPath()).getEvent(),
                            ViewType.HTML);
                }
                String view = result.getPath();
                proxy = new ViewProxy(result, view);
            } else {
                if (mapRequestToEvent(event, getConfig().validateFor(eventName))) {
                    ForwardProxy result = callHandlers(eventName, event, ViewType.HTML);
                    proxy = callAndMapHandlers(result);
                } else {
                	proxy = new ViewProxy(getValidationFailedView(event, eventName));
                }
            }
        } catch (Throwable ex) {
            ExceptionProxy exception = getConfig().resolveException(ex,
                    configResourceType(), ViewType.HTML);
            if (exception == null) {
                throw ex;
            }

            String keyRequest = exception.getKey();
            getContextWrapper().setRequestAttribute(keyRequest, ex);

            if (exception.isEventType()) {
            	// TODO This assumes that nothing gets forwarded to the exception page
                ForwardProxy result = callHandlers(exception.getPath(), getConfig()
                        .getEventProxy(exception.getPath()).getEvent(),
                        ViewType.HTML);
                String view = result.getPath();
                proxy = new ViewProxy(result, view);
            } else {
                proxy = new ViewProxy(exception.getPath());
            }
        }
        resolveForward(proxy);
        return null;
    }
    
    private ViewProxy callAndMapHandlers(ForwardProxy proxy) throws Exception {
    	boolean valid = true;
    	ForwardProxy result = proxy;
        while (result.isEventType()) {
        	// Map URI params into event, then callHandlers again
        	Event next = getConfig().getEventProxy(result.getPath()).getEvent();
        	if (getContextWrapper().hasResponseURIAttributes()) {
        		valid = mapAttributesToEvent(getContextWrapper().getResponseURIAttributes(), next, getConfig().validateFor(next.getEventName()));
        	}
        	if (valid) {
        		result = callHandlers(result.getPath(), next, ViewType.HTML);
        	} else {
        		return new ViewProxy(result, getValidationFailedView(next, next.getEventName()));
        	}
        }
    	getContextWrapper().clearResponseURIAttributes();
    	return new ViewProxy(result, result.getPath());
    }
    
    private String getValidationFailedView(final Event event, final String eventName) throws Exception {
    	getContextWrapper().setRequestAttribute(eventName, event);
        return getConfig().inputViewFor(eventName,
                configResourceType());
    }

    /**
     * HTTPRequestProcessor only generates a log message for this method.
     * @see org.megatome.frame2.front.RequestProcessor#preProcess()
     */
    public void preProcess() {
        LOGGER.debug("In HttpRequestProcessor preProcess()"); //$NON-NLS-1$
    }

    /**
     * HTTPRequestProcessor only generates a log message for this method.
     * @see org.megatome.frame2.front.RequestProcessor#postProcess()
     */
    public void postProcess() {
        LOGGER.debug("In HttpRequestProcessor postProcess()"); //$NON-NLS-1$
    }

    private void resolveForward(final ViewProxy proxy)
            throws ServletException, IOException {
        //if ((result != null) && result.isRedirect()) {
    	if (proxy.isRedirect()) {
            redirectTo(proxy.getView());
        } else {
            forwardTo(proxy.getView());
        }
    }

    /**
     * Release resources held by the request processor.
     * @see org.megatome.frame2.front.RequestProcessor#release()
     */
    @Override
	public void release() {
        this.servletContext = null;
        this.request = null;
        this.response = null;
        this.errors = null; // NIT: this is a little off, there appears to be a
                        // hand-off

        // from the processor and the request object...
    }

    @Override
	protected String configResourceType() {
        return Configuration.HTML_TOKEN;
    }

    @Override
	protected boolean isUserAuthorizedForEvent(String event) throws ConfigException {
        boolean result = false;

        String[] roles = getConfig().rolesfor(event);

        if (roles.length > 0) {
            for (int i = 0; (i < roles.length) && !result; i++) {
                result = this.request.isUserInRole(roles[i]);
            }
        } else {
            result = true;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
	private Map<String, Object> getRequestParameterMap(HttpServletRequest rq) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        if (isMultipartRequest(rq)) {

            try {
                Class.forName("org.apache.commons.fileupload.DiskFileUpload"); //$NON-NLS-1$
            } catch (ClassNotFoundException e) {
                this.fileUploadException = new Frame2Exception(
                        "The Commons FileUpload library is missing." //$NON-NLS-1$
                                + " It is required to process file uploads."); //$NON-NLS-1$
                LOGGER.severe("File Upload Error", this.fileUploadException); //$NON-NLS-1$
                return null;
            }

            try {

                // Had to move this to a separate class to avoid NoClassDef
                // errors
                // when trying to instantiate this class when the file upload
                // library is missing
                parameters = FileUploadSupport.processMultipartRequest(rq);
            } catch (Frame2Exception e) {
                this.fileUploadException = e;
                return null;
            }
        } else {
            parameters.putAll(rq.getParameterMap());
        }
        return parameters;
    }

    private final boolean isMultipartRequest(final HttpServletRequest rq) {
        String contentType = rq.getHeader(CONTENT_TYPE);
        if ((contentType == null) || (!contentType.startsWith(MULTIPART))) {
            return false;
        }
        return true;
    }

    class ContextImpl implements ContextWrapper {
        private Map<String, String> initParms;

        private Set<String> redirectAttrs = new TreeSet<String>();
        private Map<String,Object> responseAttrs = new HashMap<String,Object>();

        public ServletContext getServletContext() {
            return HttpRequestProcessor.this.servletContext;
        }

        public Object getRequestAttribute(String key) {
            return HttpRequestProcessor.this.request.getAttribute(key);
        }

        public String[] getRedirectAttributes() {
            return this.redirectAttrs.toArray(new String[0]);
        }

        public Errors getRequestErrors() {
            return HttpRequestProcessor.this.errors;
        }

        public Object getSessionAttribute(String key) {
            return HttpRequestProcessor.this.request.getSession().getAttribute(key);
        }

        public void setRequestAttribute(String key, Object value) {
            HttpRequestProcessor.this.request.setAttribute(key, value);
        }

        public void setRequestAttribute(String key, Object value,
                boolean redirectAttr) {
            if (redirectAttr) {
                this.redirectAttrs.add(key);
            } else {
                this.redirectAttrs.remove(key);
            }

            setRequestAttribute(key, value);
        }

        public void setSessionAttribute(String key, Object value) {
            HttpRequestProcessor.this.request.getSession().setAttribute(key, value);
        }

        public void removeRequestAttribute(String key) {
            HttpRequestProcessor.this.request.removeAttribute(key);
            this.redirectAttrs.remove(key);
        }

        public void removeSessionAttribute(String key) {
            HttpRequestProcessor.this.request.getSession().removeAttribute(key);
        }

        public String getInitParameter(String key) {
            String result = null;

            if (this.initParms != null) {
                result = this.initParms.get(key);
            }

            return result;
        }

        public void setInitParameters(Map<String, String> initParms) {
            this.initParms = initParms;
        }

		@Override
		public void addResponseURIAttribute(String key, Object value) {
			this.responseAttrs.put(key, value);
		}

		@Override
		public Map<String, Object> getResponseURIAttributes() {
			return Collections.unmodifiableMap(this.responseAttrs);
		}

		@Override
		public boolean hasResponseURIAttributes() {
			return (!this.responseAttrs.isEmpty());
		}

		@Override
		public void clearResponseURIAttributes() {
			this.responseAttrs.clear();
		}
    }
}