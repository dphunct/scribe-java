package org.scribe.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.scribe.exceptions.OAuthException;
import org.scribe.utils.StreamUtils;

/**
 * Represents an HTTP ResponseHttpImpl.
 * 
 * @author Pablo Fernandez
 */
public class ResponseHttpImpl implements Response {

    private int code;
    private String message;
    private String body;
    private InputStream stream;
    private Map/*<String, String>*/headers;

    ResponseHttpImpl(final HttpURLConnection connection) throws IOException {
        try {
            connection.connect();
            code = connection.getResponseCode();
            message = connection.getResponseMessage();
            headers = parseHeaders(connection);
            stream = isSuccessful() ? connection.getInputStream() : connection.getErrorStream();
        } catch (final UnknownHostException e) {
            throw new OAuthException("The IP address of a host could not be determined.", e);
        }
    }

    private String parseBodyContents() {
        body = StreamUtils.getStreamContents(getStream());
        return body;
    }

    private Map/*<String, String>*/parseHeaders(final HttpURLConnection conn) {
        final Map/*<String, String>*/headers = new HashMap/*<String, String>*/();
        final Iterator i = conn.getHeaderFields().keySet().iterator();
        while (i.hasNext()) {
            final String key = (String) i.next();
            headers.put(key, ((Collection) conn.getHeaderFields().get(key)).toArray()[0]);
        }
        return headers;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Response#isSuccessful()
     */
    public boolean isSuccessful() {
        return getCode() >= 200 && getCode() < 400;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Response#getBody()
     */
    public String getBody() {
        return body != null ? body : parseBodyContents();
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Response#getStream()
     */
    public InputStream getStream() {
        return stream;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Response#getCode()
     */
    public int getCode() {
        return code;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Response#getMessage()
     */
    public String getMessage() {
        return message;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Response#getHeaders()
     */
    public Map/*<String, String>*/getHeaders() {
        return headers;
    }

    /* (non-Javadoc)
     * @see org.scribe.model.Response#getHeader(java.lang.String)
     */
    public String getHeader(final String name) {
        return (String) headers.get(name);
    }

}