package org.apache.shiro.web.filter.authc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Simple Filter that, upon receiving a request, will immediately log-out the currently executing
 * {@link #getSubject(javax.servlet.ServletRequest, javax.servlet.ServletResponse) subject}
 * and then redirect them to a configured {@link #getRedirectUrl() redirectUrl}.
 *
 * @since 1.2
 */
public class LogoutFilter extends AdviceFilter {

    /**
     * The default redirect URL to where the user will be redirected after logout.  The value is {@code "/"}, Shiro's
     * representation of the web application's context root.
     */
    public static final String DEFAULT_REDIRECT_URL = "/";

    /**
     * The URL to where the user will be redirected after logout.
     */
    private String redirectUrl = DEFAULT_REDIRECT_URL;

    /**
     * Acquires the currently executing {@link #getSubject(javax.servlet.ServletRequest, javax.servlet.ServletResponse) subject},
     * a potentially Subject or request-specific
     * {@link #getRedirectUrl(javax.servlet.ServletRequest, javax.servlet.ServletResponse, org.apache.shiro.subject.Subject) redirectUrl},
     * and redirects the end-user to that redirect url.
     *
     * @param request  the incoming ServletRequest
     * @param response the outgoing ServletResponse
     * @return {@code false} always as typically no further interaction should be done after user logout.
     * @throws Exception if there is any error.
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        subject.logout();
        issueRedirect(request, response, redirectUrl);
        return false;
    }

    /**
     * Returns the currently executing {@link Subject}.  This implementation merely defaults to calling
     * {@code SecurityUtils.}{@link org.apache.shiro.SecurityUtils#getSubject() getSubject()}, but can be overridden
     * by subclasses for different retrieval strategies.
     *
     * @param request  the incoming Servlet request
     * @param response the outgoing Servlet response
     * @return the currently executing {@link Subject}.
     */
    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }

    /**
     * Issues an HTTP redirect to the specified URL after subject logout.  This implementation simply calls
     * {@code WebUtils.}{@link WebUtils#issueRedirect(javax.servlet.ServletRequest, javax.servlet.ServletResponse, String) issueRedirect(request,response,redirectUrl)}.
     *
     * @param request  the incoming Servlet request
     * @param response the outgoing Servlet response
     * @param redirectUrl the URL to where the browser will be redirected immediately after Subject logout.
     * @throws Exception if there is any error.
     */
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
        WebUtils.issueRedirect(request, response, redirectUrl);
    }

    /**
     * Returns the redirect URL to send the user after logout.  This default implementation ignores the arguments and
     * returns the static configured {@link #getRedirectUrl() redirectUrl} property, but this method may be overridden
     * by subclasses to dynamically construct the URL based on the request or subject if necessary.
     * <p/>
     * Note: the Subject is <em>not</em> yet logged out at the time this method is invoked.  You may access the Subject's
     * session if one is available and if necessary.
     * <p/>
     * Tip: if you need to access the Subject's session, consider using the
     * {@code Subject.}{@link Subject#getSession(boolean) getSession(false)} method to ensure a new session isn't created unnecessarily.
     * If a session would be created, it will be immediately stopped after logout, not providing any value and
     * unnecessarily taxing session infrastructure/resources.
     *
     * @param request the incoming Servlet request
     * @param response the outgoing ServletResponse
     * @param subject the not-yet-logged-out currently executing Subject
     * @return the redirect URL to send the user after logout.
     */
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        return getRedirectUrl();
    }

    /**
     * Returns the URL to where the user will be redirected after logout.  Default is the web application's context
     * root, i.e. {@code "/"}
     *
     * @return the URL to where the user will be redirected after logout.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the URL to where the user will be redirected after logout.  Default is the web application's context
     * root, i.e. {@code "/"}
     *
     * @param redirectUrl the url to where the user will be redirected after logout
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
