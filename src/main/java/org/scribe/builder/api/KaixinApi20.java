package org.scribe.builder.api;

import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;

/**
 * Kaixin(http://www.kaixin001.com/) open platform api based on OAuth 2.0.
 */
public class KaixinApi20 extends DefaultApi20 {

    private static final String AUTHORIZE_URL = "http://api.kaixin001.com/oauth2/authorize?client_id=%clientId%&redirect_uri=%redirectUri%&response_type=code";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=";

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }

    public String getAccessTokenEndpoint() {
        return "https://api.kaixin001.com/oauth2/access_token?grant_type=authorization_code";
    }

    public String getAuthorizationUrl(final OAuthConfig config) {
        String result = AUTHORIZE_URL.replace("clientId", config.getApiKey()).replace(
                "%redirectUri%", OAuthEncoder.encode(config.getCallback()));
        // Append scope if present
        if (config.hasScope()) {
            result = SCOPED_AUTHORIZE_URL + OAuthEncoder.encode(config.getScope());
        }
        return result;
    }
}
