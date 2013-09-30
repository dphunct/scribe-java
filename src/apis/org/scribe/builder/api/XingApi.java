package org.scribe.builder.api;

import org.scribe.model.Token;

public class XingApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "https://api.xing.com/v1/authorize?oauth_token=";

    public String getAccessTokenEndpoint() {
        return "https://api.xing.com/v1/access_token";
    }

    public String getRequestTokenEndpoint() {
        return "https://api.xing.com/v1/request_token";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

}
