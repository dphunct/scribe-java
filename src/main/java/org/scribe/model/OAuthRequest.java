package org.scribe.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The representation of an OAuth Request is a Bridge to the request object.  The user can provide the underlying request object
 * 
 * @author Duane Musser
 */
public class OAuthRequest implements Request {
    /**
     * 
     */
    private static final String NOT_INTIALIZED = "Not intialized!  Make sure RequestFactortImpl is provided.";
    private static final String OAUTH_PREFIX = "oauth_";
    private final Map/*<String, String>*/oauthParameters;

    private final Request request;

    public OAuthRequest(final Request request) {
        super();
        this.request = request;
        oauthParameters = new HashMap/*<String, String>*/();
    }

    /**
     * Adds an OAuth parameter.
     * 
     * @param key name of the parameter
     * @param name name of the parameter
     * 
     * @throws IllegalArgumentException if the parameter is not an OAuth parameter
     */
    public void addOAuthParameter(final String key, final String value) {
        oauthParameters.put(checkKey(key), value);
    }

    private String checkKey(final String key) {
        if (key.startsWith(OAUTH_PREFIX) || key.equals(OAuthConstants.SCOPE)) {
            return key;
        } else {
            throw new IllegalArgumentException("OAuth parameters must either be '"
                    + OAuthConstants.SCOPE + "' or start with '" + OAUTH_PREFIX + "'");
        }
    }

    /**
     * Returns the {@link Map} containing the key-name pair of parameters.
     * 
     * @return parameters as map
     */
    public Map/*<String, String>*/getOauthParameters() {
        return oauthParameters;
    }

    public String toString() {
        return "@OAuthRequest(" + String.valueOf(getVerb()) + ", " + String.valueOf(getUrl()) + ")";
    }

    /**
     * @see org.scribe.model.Request#addHeader(java.lang.String, java.lang.String)
     */
    public void addHeader(final String key, final String value) {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        request.addHeader(key, value);
    }

    /**
     * @see org.scribe.model.Request#addBodyParameter(java.lang.String, java.lang.String)
     */
    public void addBodyParameter(final String key, final String value) {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        request.addBodyParameter(key, value);
    }

    /**
     * @see org.scribe.model.Request#addQuerystringParameter(java.lang.String, java.lang.String)
     */
    public void addQuerystringParameter(final String key, final String value) {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        request.addQuerystringParameter(key, value);
    }

    /**
     * @see org.scribe.model.Request#getQueryStringParams()
     */
    public ParameterList getQueryStringParams() {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        return request.getQueryStringParams();
    }

    /**
     * @see org.scribe.model.Request#getBodyParams()
     */
    public ParameterList getBodyParams() {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        return request.getBodyParams();
    }

    /**
     * @see org.scribe.model.Request#getUrl()
     */
    public String getUrl() {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        return request.getUrl();
    }

    /**
     * @see org.scribe.model.Request#getVerb()
     */
    public Verb getVerb() {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        return request.getVerb();
    }

    /**
     * @see org.scribe.model.Request#getHeaders()
     */
    public Map getHeaders() {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        return request.getHeaders();
    }

    /**
     * @throws IOException 
     * @see org.scribe.model.Request#send()
     */
    public Response send() throws IOException {
        if (request == null) {
            throw new IllegalAccessError(NOT_INTIALIZED);
        }
        return request.send();
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#addPayload(java.lang.String)
     */
    public void addPayload(final String payload) throws IOException {
        request.addPayload(payload);
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#addPayload(byte[])
     */
    public void addPayload(final byte[] payload) throws IOException {
        request.addPayload(payload);
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getPayload()
     */
    public String getPayload() {
        return request.getPayload();
    }

}
