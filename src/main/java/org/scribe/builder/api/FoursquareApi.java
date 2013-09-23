package org.scribe.builder.api;

import org.scribe.model.Token;

public class FoursquareApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "http://foursquare.com/oauth/authorize?oauth_token=%s";

    public String getAccessTokenEndpoint() {
        return "http://foursquare.com/oauth/access_token";
    }

    public String getRequestTokenEndpoint() {
        return "http://foursquare.com/oauth/request_token";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }
}
