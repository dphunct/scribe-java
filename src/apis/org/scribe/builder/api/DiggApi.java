package org.scribe.builder.api;

import org.scribe.model.Token;

public class DiggApi extends DefaultApi10a {

    private static final String AUTHORIZE_URL = "http://digg.com/oauth/authorize?oauth_token=";
    private static final String BASE_URL = "http://services.digg.com/oauth/";

    public String getRequestTokenEndpoint() {
        return BASE_URL + "request_token";
    }

    public String getAccessTokenEndpoint() {
        return BASE_URL + "access_token";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

}
