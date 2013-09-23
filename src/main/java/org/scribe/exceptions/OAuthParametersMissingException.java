package org.scribe.exceptions;

import org.scribe.model.OAuthRequest;

/**
 * Specialized exception that represents a missing OAuth parameter. 
 * 
 * @author Pablo Fernandez
 */
public class OAuthParametersMissingException extends OAuthException {

    private static final long serialVersionUID = 1745308760111976671L;

    /**
     * Default constructor.
     * 
     * @param request OAuthRequest that caused the error
     */
    public OAuthParametersMissingException(final OAuthRequest request) {
        super("Could not find oauth parameters in request: " + String.valueOf(request) + ". "
                + "OAuth parameters must be specified with the addOAuthParameter() method");
    }
}
