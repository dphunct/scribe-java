package org.scribe.builder.api;

import org.scribe.model.Token;
import org.scribe.model.Verb;

public class SapoApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "https://id.sapo.pt/oauth/authorize?oauth_token=";
    private static final String ACCESS_URL = "https://id.sapo.pt/oauth/access_token";
    private static final String REQUEST_URL = "https://id.sapo.pt/oauth/request_token";

    public String getAccessTokenEndpoint() {
        return ACCESS_URL;
    }

    public String getRequestTokenEndpoint() {
        return REQUEST_URL;
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    public Verb getRequestTokenVerb() {
        return Verb.GET;
    }

    public Verb getAccessTokenVerb() {
        return Verb.GET;
    }
}