package org.scribe.builder.api;

import org.scribe.model.Token;

/**
 * OAuth API for Skyrock.
 *
 * @author Nicolas Quiénot
 * @see <a href="http://www.skyrock.com/developer/">Skyrock.com API</a>
 */
public class SkyrockApi extends DefaultApi10a {
    private static final String API_ENDPOINT = "https://api.skyrock.com/v2";
    private static final String REQUEST_TOKEN_RESOURCE = "/oauth/initiate";
    private static final String AUTHORIZE_URL = "/oauth/authorize?oauth_token=";
    private static final String ACCESS_TOKEN_RESOURCE = "/oauth/token";

    public String getAccessTokenEndpoint() {
        return API_ENDPOINT + ACCESS_TOKEN_RESOURCE;
    }

    public String getRequestTokenEndpoint() {
        return API_ENDPOINT + REQUEST_TOKEN_RESOURCE;
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return API_ENDPOINT + AUTHORIZE_URL + requestToken.getToken();
    }
}
