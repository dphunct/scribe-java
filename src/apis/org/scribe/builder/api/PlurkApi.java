package org.scribe.builder.api;

import org.scribe.model.Token;

public class PlurkApi extends DefaultApi10a {
    private static final String REQUEST_TOKEN_URL = "http://www.plurk.com/OAuth/request_token";
    private static final String AUTHORIZE_URL = "http://www.plurk.com/OAuth/authorize?oauth_token=";
    private static final String ACCESS_TOKEN_URL = "http://www.plurk.com/OAuth/access_token";

    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_URL;
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_URL;
    }

    public static class Mobile extends PlurkApi {
        private static final String AUTHORIZE_URL = "http://www.plurk.com/m/authorize?oauth_token=";

        public String getAuthorizationUrl(final Token requestToken) {
            return AUTHORIZE_URL + requestToken.getToken();
        }
    }
}
