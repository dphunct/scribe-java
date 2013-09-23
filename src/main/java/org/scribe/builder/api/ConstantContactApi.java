package org.scribe.builder.api;

import org.scribe.model.Token;

public class ConstantContactApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "https://oauth.constantcontact.com/ws/oauth/confirm_access?oauth_token=";

    public String getAccessTokenEndpoint() {
        return "https://oauth.constantcontact.com/ws/oauth/access_token";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    public String getRequestTokenEndpoint() {
        return "https://oauth.constantcontact.com/ws/oauth/request_token";
    }
}
