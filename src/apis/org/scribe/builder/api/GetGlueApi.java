package org.scribe.builder.api;

import org.scribe.model.Token;

public class GetGlueApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "http://getglue.com/oauth/authorize?oauth_token=";
    private static final String REQUEST_TOKEN_RESOURCE = "https://api.getglue.com/oauth/request_token";
    private static final String ACCESS_TOKEN_RESOURCE = "https://api.getglue.com/oauth/access_token";

    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_RESOURCE;
    }

    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_RESOURCE;
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

}
