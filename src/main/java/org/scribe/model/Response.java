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
 * Represents an HTTP Response.
 * 
 * @author Pablo Fernandez
 */
public class Response {

    private int code;
    private String message;
    private String body;
    private InputStream stream;
    private Map/*<String, String>*/headers;

    Response(final HttpURLConnection connection) throws IOException {
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

    public boolean isSuccessful() {
        return getCode() >= 200 && getCode() < 400;
    }

    /**
     * Obtains the HTTP Response body
     * 
     * @return response body
     */
    public String getBody() {
        return body != null ? body : parseBodyContents();
    }

    /**
     * Obtains the meaningful stream of the HttpUrlConnection, either inputStream
     * or errorInputStream, depending on the status code
     * 
     * @return input stream / error stream
     */
    public InputStream getStream() {
        return stream;
    }

    /**
     * Obtains the HTTP status code
     * 
     * @return the status code
     */
    public int getCode() {
        return code;
    }

    /**
     * Obtains the HTTP status message.
     * Returns <code>null</code> if the message can not be discerned from the response (not valid HTTP)
     * 
     * @return the status message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Obtains a {@link Map} containing the HTTP Response Headers
     * 
     * @return headers
     */
    public Map/*<String, String>*/getHeaders() {
        return headers;
    }

    /**
     * Obtains a single HTTP Header name, or null if undefined
     * 
     * @param name the header name.
     * 
     * @return header name or null.
     */
    public String getHeader(final String name) {
        return (String) headers.get(name);
    }

}