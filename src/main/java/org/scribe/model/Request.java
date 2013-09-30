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
     * Returns the complete url (host + resource + encoded querystring parameters).
     *
     * @return the complete url.
     */
    //    public String getCompleteUrl();

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
    //    public void addPayload(final String payload);

    /**
     * Overloaded version for byte arrays
     *
     * @param payload
     */
    //    public void addPayload(final byte[] payload);

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
     * Returns the URL without the port and the query string part.
     * 
     * @return the OAuth-sanitized URL
     */
    //    public String getSanitizedUrl();

    /**
     * Returns the body of the request
     * 
     * @return form encoded string
     * @throws OAuthException if the charset chosen is not supported
     */
    public String getBodyContents();

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

    /**
     * Returns the connection charset. Defaults to {@link Charset} defaultCharset if not set
     *
     * @return charset
     */
    //    public String getCharset();

    /**
     * Set the charset of the body of the request
     *
     * @param charsetName name of the charset of the request
     */
    //    public void setCharset(final String charsetName);

    /**
     * Sets whether the underlying Http Connection is persistent or not.
     *
     * @see http://download.oracle.com/javase/1.5.0/docs/guide/net/http-keepalive.html
     * @param connectionKeepAlive
     */
    //    public void setConnectionKeepAlive(final boolean connectionKeepAlive);

    /**
     * Sets whether the underlying Http Connection follows redirects or not.
     *
     * Defaults to true (follow redirects)
     *
     * @see http://docs.oracle.com/javase/6/docs/api/java/net/HttpURLConnection.html#setInstanceFollowRedirects(boolean)
     * @param followRedirects
     */
    //    public void setFollowRedirects(final boolean followRedirects);

}