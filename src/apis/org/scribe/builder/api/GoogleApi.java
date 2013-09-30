package org.scribe.builder.api;

import org.scribe.model.Token;
import org.scribe.model.Verb;

public class GoogleApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";

    public String getAccessTokenEndpoint() {
        return "https://www.google.com/accounts/OAuthGetAccessToken";
    }

    public String getRequestTokenEndpoint() {
        return "https://www.google.com/accounts/OAuthGetRequestToken";
    }

    public Verb getAccessTokenVerb() {
        return Verb.GET;
    }

    public Verb getRequestTokenVerb() {
        return Verb.GET;
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }
}
