package org.scribe.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.scribe.exceptions.OAuthException;

/**
 * Represents an HTTP RequestHttpImpl object
 */
public class RequestHttpImpl implements Request {

    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private final String url;
    private final Verb verb;
    private final ParameterList querystringParams;
    private final ParameterList bodyParams;
    private final Map/*<String, String>*/headers;
    private String charset;
    private String content = null;

    private HttpURLConnection connection;

    /**
     * Creates a new Http RequestHttpImpl
     * 
     * @param verb Http Verb (GET, POST, etc)
     * @param url url with optional querystring parameters.
     */
    public RequestHttpImpl(final Verb verb, final String url) {
        super();
        this.verb = verb;
        this.url = url;
        querystringParams = new ParameterList();
        bodyParams = new ParameterList();
        headers = new HashMap/*<String, String>*/();
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getCompleteUrl()
     */
    public String getCompleteUrl() {
        return querystringParams.appendTo(url);
    }

    void addHeaders(final HttpURLConnection conn) {
        final Iterator i = headers.keySet().iterator();
        while (i.hasNext()) {
            final String key = (String) i.next();
            conn.setRequestProperty(key, (String) headers.get(key));
        }
    }

    public void addPayload(final byte[] content) throws IOException {
        addPayload(new String(content));
    }

    public void addPayload(final String content) throws IOException {
        this.content = content;
    }

    public String getPayload() {
        return content;
    }

    /**
     * TODO implement
     */
    public Response send() throws IOException {
        connection.setRequestProperty(CONTENT_LENGTH, String.valueOf(content.length()));

        // Set default content type if none is set.
        if (connection.getRequestProperty(CONTENT_TYPE) == null) {
            connection.setRequestProperty(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
        }
        connection.setDoOutput(true);
        //        connection.getOutputStream().write(content);
        final Response response = new ResponseHttpImpl(connection);
        return response;
        //        return null;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#addHeader(java.lang.String, java.lang.String)
     */
    public void addHeader(final String key, final String value) {
        headers.put(key, value);
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#addBodyParameter(java.lang.String, java.lang.String)
     */
    public void addBodyParameter(final String key, final String value) {
        bodyParams.add(key, value);
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#addQuerystringParameter(java.lang.String, java.lang.String)
     */
    public void addQuerystringParameter(final String key, final String value) {
        querystringParams.add(key, value);
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getQueryStringParams()
     */
    public ParameterList getQueryStringParams() {
        try {
            final ParameterList result = new ParameterList();
            final String queryString = new URL(url).getQuery();
            result.addQuerystring(queryString);
            result.addAll(querystringParams);
            return result;
        } catch (final MalformedURLException mue) {
            throw new OAuthException("Malformed URL", mue);
        }
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getBodyParams()
     */
    public ParameterList getBodyParams() {
        return bodyParams;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getUrl()
     */
    public String getUrl() {
        return url;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getSanitizedUrl()
     */
    public String getSanitizedUrl() {
        return StringUtils.replace(StringUtils.replace(url, "\\?.*", ""), "\\:\\d{4}", "");
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getVerb()
     */
    public Verb getVerb() {
        return verb;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getHeaders()
     */
    public Map/*<String, String>*/getHeaders() {
        return headers;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#getCharset()
     */
    public String getCharset() {
        return charset == null ? Charset.defaultCharset().name() : charset;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Request#toString()
     */
    public String toString() {
        return "@RequestHttpImpl(" + String.valueOf(getVerb()) + " " + getUrl() + ")";
    }

    /**
     * @return
     */
    public String getBodyContents() {
        return getPayload();
    }

}
