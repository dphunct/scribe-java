package org.scribe.exceptions;

/**
 * Specialized exception that represents a problem in the signature
 * 
 * @author Pablo Fernandez
 */
public class OAuthSignatureException extends OAuthException {
    private static final long serialVersionUID = 1L;
    private static final String MSG = "Error while signing string: ";

    /**
     * Default constructor
     * 
     * @param stringToSign plain string that gets signed (HMAC-SHA, etc)
     * @param e original exception
     */
    public OAuthSignatureException(final String stringToSign, final Exception e) {
        super(MSG + String.valueOf(stringToSign), e);
    }

}
