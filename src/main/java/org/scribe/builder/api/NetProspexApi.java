package org.scribe.builder.api;

import org.scribe.model.Token;

public class NetProspexApi extends DefaultApi10a {
    private static final String REQUEST_TOKEN_URL = "https://api.netprospex.com/1.0/oauth/request-token";
    private static final String ACCESS_TOKEN_URL = "https://api.netprospex.com/1.0/oauth/access-token";
    private static final String AUTHORIZE_URL = "https://api.netprospex.com/1.0/oauth/authorize?oauth_token=";

    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_URL;
    }

    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_URL;
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }
}
