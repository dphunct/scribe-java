/**
 * 
 */
package org.scribe.model;

/**
 * @author DMusser
 *
 */
public abstract class RequestFactory {

    public abstract Request createRequest(Verb verb, String url);

}
