/**
 * 
 */
package org.scribe.model;

import java.io.IOException;
import java.util.Map;

import org.scribe.exceptions.OAuthException;

/**
 * @author DMusser
 *
 */
public interface Request {

    public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * Execute the request and return a {@link ResponseHttpImpl}
     * 
     * @return Response
     * @throws IOException 
     */
    public Response send() throws IOException;

    /**
     * Add an HTTP Header to the RequestHttpImpl
     * 
     * @param key the header name
     * @param name the header name
     */
    public void addHeader(final String key, final String value);

    /**
     * Add a body Parameter (for POST/ PUT Requests)
     * 
     * @param key the parameter name
     * @param name the parameter name
     */
    public void addBodyParameter(final String key, final String value);

    /**
     * Add a QueryString parameter
     *
     * @param key the parameter name
     * @param name the parameter name
     */
    public void addQuerystringParameter(final String key, final String value);

    /**
     * Add body payload.
     * 
     * This method is used when the HTTP body is not a form-url-encoded string,
     * but another thing. Like for example XML.
     * 
     * Note: The contents are not part of the OAuth signature
     * 
     * @param payload the body of the request
     */
    public void addPayload(final String payload) throws IOException;

    /**
     * Overloaded version for byte arrays
     *
     * @param payload
     */
    public void addPayload(final byte[] payload) throws IOException;

    /**
     * 
     * @return
     */
    public String getPayload();

    /**
     * Get a {@link ParameterList} with the query string parameters.
     * 
     * @return a {@link ParameterList} containing the query string parameters.
     * @throws OAuthException if the request URL is not valid.
     */
    public ParameterList getQueryStringParams();

    /**
     * Obtains a {@link ParameterList} of the body parameters.
     * 
     * @return a {@link ParameterList}containing the body parameters.
     */
    public ParameterList getBodyParams();

    /**
     * Obtains the URL of the HTTP RequestHttpImpl.
     * 
     * @return the original URL of the HTTP RequestHttpImpl
     */
    public String getUrl();

    /**
     * Returns the HTTP Verb
     * 
     * @return the verb
     */
    public Verb getVerb();

    /**
     * Returns the connection headers as a {@link Map}
     * 
     * @return map of headers
     */
    public Map/*<String, String>*/getHeaders();

}