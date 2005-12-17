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
package org.megatome.frame2.front;

import java.io.IOException;
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
    protected ServletContext _servletContext;

    protected HttpServletRequest _request;

    private HttpServletResponse _response;

    private Map requestParams;

    private Exception fileUploadException = null;

    public static final String CONTENT_TYPE = "Content-type";

    private static final String MULTIPART = "multipart/";

    private Logger getLogger() {
        return LoggerFactory.instance(HttpRequestProcessor.class.getName());
    }

    /**
     * Constructor for HttpRequestProcessor.
     */
    public HttpRequestProcessor(Configuration config, ServletContext context,
            HttpServletRequest request, HttpServletResponse response) {
        super(config);
        this._servletContext = context;
        this.context = new ContextImpl();
        this._request = request;
        this._response = response;
        this.requestParams = getRequestParameterMap(_request);
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
        String eventName = getEventName(_request.getServletPath());
        Event event = getConfig().getEventProxy(eventName).getEvent();
        if (event != null) {
            event.setName(eventName);
        }
        return event;

    }

    private String getEventName(String servletPath) {
        String result = null;

        if (servletPath != null) {
            int point = servletPath.indexOf(".");
            int lastSlash = servletPath.lastIndexOf("/");

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

        IntrospectorFactory.instance().mapProperties(requestParams, event);

        boolean passed = true;

        if ((validate) && (event != null)) {
            try {
                passed &= event.validate(errors);
            } catch (NoClassDefFoundError e) {
                // Bug Fix: 917752
                // Detect missing CommonsValidator and respond appropriately
                // Error is thrown only when plugin is specified
                PluginProxy cvp = getConfig().getPluginProxy(
                        "CommonsValidatorPlugin");
                if (cvp != null) {
                    getLogger().warn("Cannot validate event", e);
                    throw new CommonsValidatorException(
                            "CommonsValidator missing from classpath, but specified in configuration");
                }
            }
        }

        // always put error in request.
        _request.setAttribute(Globals.ERRORS, errors);
        return passed;
    }

    /**
     * Method getContext.
     */
    protected ContextWrapper getContextWrapper() {
        return context;
    }

    void forwardTo(String view) throws ServletException, IOException {
        _servletContext.getRequestDispatcher(view).forward(_request, _response);
    }

    void redirectTo(String view) throws IOException {
        String encodedURL = encodeRedirectURL(view);

        _response.sendRedirect(encodedURL);
    }

    private String encodeRedirectURL(String view) {
        StringBuffer buf = new StringBuffer(view);
        String[] redirectAttrs = context.getRedirectAttributes();
        int len = redirectAttrs.length;

        if (len > 0) {
            buf.append("?");
        }

        for (int i = 0; i < len; i++) {
            buf.append(getParam(i, redirectAttrs));

            if ((i + 1) < len) {
                buf.append("&");
            }
        }

        return buf.toString();
    }

    private StringBuffer getParam(int index, String[] redirectAttrs) {
        String attrKey = redirectAttrs[index];
        StringBuffer param = new StringBuffer().append(attrKey).append("=")
                .append(context.getRequestAttribute(attrKey));

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
        getLogger().debug("In HttpRequestProcessor processRequest()");
        String view = null;
        ForwardProxy result = null;
        try {
            String eventName = getEventName(_request.getServletPath());
            Event event = getEvent();

            if (requestParams == null) {
                Frame2Exception e = null;
                if (fileUploadException != null) {
                    e = new Frame2Exception(fileUploadException);
                    fileUploadException = null;
                } else {
                    e = new Frame2Exception(
                            "File Upload Error: There was an error parsing file upload parameters.");
                }
                throw e;
            }

            if (!isUserAuthorizedForEvent(eventName)) {
                String login = _request.getRemoteUser();

                login = (login != null) ? login : "";
                throw new AuthorizationException("User " + login
                        + " not authorized for mapping " + eventName);
            }
            //requestParams = getRequestParameterMap(_request);

            //if (isCancelRequest(_request)) {
            if (isCancelRequest(requestParams)) {
                result = getConfig().cancelViewFor(eventName,
                        Configuration.HTML_TOKEN);

                if (result.isEventType()) {
                    result = callHandlers(result.getPath(), getConfig()
                            .getEventProxy(result.getPath()).getEvent(),
                            ViewType.HTML);
                }
                view = result.getPath();

            } else {
                if (mapRequestToEvent(event, getConfig().validateFor(eventName))) {
                    result = callHandlers(eventName, event, ViewType.HTML);
                    view = result.getPath();
                } else {
                    getContextWrapper().setRequestAttribute(eventName, event);
                    view = getConfig().inputViewFor(eventName,
                            configResourceType());
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
                result = callHandlers(exception.getPath(), getConfig()
                        .getEventProxy(exception.getPath()).getEvent(),
                        ViewType.HTML);
                view = result.getPath();
            } else {
                view = exception.getPath();
            }
        }
        resolveForward(result, view);
        return null;
    }

    /**
     * HTTPRequestProcessor only generates a log message for this method.
     * @see org.megatome.frame2.front.RequestProcessor#preProcess()
     */
    public void preProcess() {
        getLogger().debug("In HttpRequestProcessor preProcess()");
    }

    /**
     * HTTPRequestProcessor only generates a log message for this method.
     * @see org.megatome.frame2.front.RequestProcessor#postProcess()
     */
    public void postProcess() {
        getLogger().debug("In HttpRequestProcessor postProcess()");
    }

    private void resolveForward(ForwardProxy result, String view)
            throws ServletException, IOException {
        if ((result != null) && result.isRedirect()) {
            redirectTo(view);
        } else {
            forwardTo(view);
        }
    }

    /**
     * Release resources held by the request processor.
     * @see org.megatome.frame2.front.RequestProcessor#release()
     */
    public void release() {
        _servletContext = null;
        _request = null;
        _response = null;
        errors = null; // NIT: this is a little off, there appears to be a
                        // hand-off

        // from the processor and the request object...
    }

    protected String configResourceType() {
        return Configuration.HTML_TOKEN;
    }

    protected boolean isUserAuthorizedForEvent(String event) throws ConfigException {
        boolean result = false;

        String[] roles = getConfig().rolesfor(event);

        if (roles.length > 0) {
            for (int i = 0; (i < roles.length) && !result; i++) {
                result = _request.isUserInRole(roles[i]);
            }
        } else {
            result = true;
        }

        return result;
    }

    private Map getRequestParameterMap(HttpServletRequest request) {
        Map parameters = new HashMap();

        if (isMultipartRequest(request)) {

            try {
                Class.forName("org.apache.commons.fileupload.DiskFileUpload");
            } catch (ClassNotFoundException e) {
                fileUploadException = new Frame2Exception(
                        "The Commons FileUpload library is missing."
                                + " It is required to process file uploads.");
                getLogger().severe("File Upload Error", fileUploadException);
                return null;
            }

            try {

                // Had to move this to a separate class to avoid NoClassDef
                // errors
                // when trying to instantiate this class when the file upload
                // library is missing
                parameters = FileUploadSupport.processMultipartRequest(request);
            } catch (Frame2Exception e) {
                fileUploadException = e;
                return null;
            }
        } else {
            parameters.putAll(request.getParameterMap());
        }
        return parameters;
    }

    private final boolean isMultipartRequest(final HttpServletRequest request) {
        String contentType = request.getHeader(CONTENT_TYPE);
        if ((contentType == null) || (!contentType.startsWith(MULTIPART))) {
            return false;
        }
        return true;
    }

    private class ContextImpl implements ContextWrapper {
        private Map _initParms;

        private Set _redirectAttrs = new TreeSet();

        public ServletContext getServletContext() {
            return _servletContext;
        }

        public Object getRequestAttribute(String key) {
            return _request.getAttribute(key);
        }

        public String[] getRedirectAttributes() {
            return (String[])_redirectAttrs.toArray(new String[0]);
        }

        public Errors getRequestErrors() {
            return errors;
        }

        public Object getSessionAttribute(String key) {
            return _request.getSession().getAttribute(key);
        }

        public void setRequestAttribute(String key, Object value) {
            _request.setAttribute(key, value);
        }

        public void setRequestAttribute(String key, Object value,
                boolean redirectAttr) {
            if (redirectAttr) {
                _redirectAttrs.add(key);
            } else {
                _redirectAttrs.remove(key);
            }

            setRequestAttribute(key, value);
        }

        public void setSessionAttribute(String key, Object value) {
            _request.getSession().setAttribute(key, value);
        }

        public void removeRequestAttribute(String key) {
            _request.removeAttribute(key);
            _redirectAttrs.remove(key);
        }

        public void removeSessionAttribute(String key) {
            _request.getSession().removeAttribute(key);
        }

        public String getInitParameter(String key) {
            String result = null;

            if (_initParms != null) {
                result = (String)_initParms.get(key);
            }

            return result;
        }

        public void setInitParameters(Map initParms) {
            _initParms = initParms;
        }
    }
}