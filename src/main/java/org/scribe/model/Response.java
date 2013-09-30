/**
 * 
 */
package org.scribe.model;

import java.io.InputStream;
import java.util.Map;

/**
 * @author DMusser
 *
 */
public interface Response {

    public boolean isSuccessful();

    /**
     * Obtains the HTTP ResponseHttpImpl body
     * 
     * @return response body
     */
    public String getBody();

    /**
     * Obtains the meaningful stream of the HttpUrlConnection, either inputStream
     * or errorInputStream, depending on the status code
     * 
     * @return input stream / error stream
     */
    public InputStream getStream();

    /**
     * Obtains the HTTP status code
     * 
     * @return the status code
     */
    public int getCode();

    /**
     * Obtains the HTTP status message.
     * Returns <code>null</code> if the message can not be discerned from the response (not valid HTTP)
     * 
     * @return the status message
     */
    public String getMessage();

    /**
     * Obtains a {@link Map} containing the HTTP ResponseHttpImpl Headers
     * 
     * @return headers
     */
    public Map/*<String, String>*/getHeaders();

    /**
     * Obtains a single HTTP Header name, or null if undefined
     * 
     * @param name the header name.
     * 
     * @return header name or null.
     */
    public String getHeader(final String name);

}