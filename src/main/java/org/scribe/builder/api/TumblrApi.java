package org.scribe.builder.api;

import org.scribe.model.Token;

public class TumblrApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "https://www.tumblr.com/oauth/authorize?oauth_token=";
    private static final String REQUEST_TOKEN_RESOURCE = "http://www.tumblr.com/oauth/request_token";
    private static final String ACCESS_TOKEN_RESOURCE = "http://www.tumblr.com/oauth/access_token";

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
