package org.scribe.builder.api;

import org.scribe.model.Token;
import org.scribe.model.Verb;

/**
 * @author Arieh "Vainolo" Bibliowicz
 * @see http://apidocs.mendeley.com/home/authentication
 */
public class MendeleyApi extends DefaultApi10a {

    private static final String AUTHORIZE_URL = "http://api.mendeley.com/oauth/authorize?oauth_token=";

    public String getRequestTokenEndpoint() {
        return "http://api.mendeley.com/oauth/request_token/";
    }

    public String getAccessTokenEndpoint() {
        return "http://api.mendeley.com/oauth/access_token/";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    public Verb getAccessTokenVerb() {
        return Verb.GET;
    }

    public Verb getRequestTokenVerb() {
        return Verb.GET;
    }
}
