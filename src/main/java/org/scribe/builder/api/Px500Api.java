package org.scribe.builder.api;

import org.scribe.model.Token;

public class Px500Api extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "https://api.500px.com/v1/oauth/authorize?oauth_token=";

    public String getAccessTokenEndpoint() {
        return "https://api.500px.com/v1/oauth/access_token";
    }

    public String getRequestTokenEndpoint() {
        return "https://api.500px.com/v1/oauth/request_token";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }
}