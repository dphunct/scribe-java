package org.scribe.builder.api;

import org.scribe.model.Token;

public class VimeoApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "http://vimeo.com/oauth/authorize?oauth_token=";

    public String getAccessTokenEndpoint() {
        return "http://vimeo.com/oauth/access_token";
    }

    public String getRequestTokenEndpoint() {
        return "http://vimeo.com/oauth/request_token";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }
}
