package org.scribe.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The representation of an OAuth HttpRequest.
 * 
 * Adds OAuth-related functionality to the {@link Request}  
 * 
 * @author Pablo Fernandez
 */
public class OAuthRequest extends Request {
    private static final String OAUTH_PREFIX = "oauth_";
    private final Map/*<String, String>*/oauthParameters;

    /**
     * Default constructor.
     * 
     * @param verb Http verb/method
     * @param url resource URL
     */
    public OAuthRequest(final Verb verb, final String url) {
        super(verb, url);
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
}
