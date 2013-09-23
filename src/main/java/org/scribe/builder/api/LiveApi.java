package org.scribe.builder.api;

import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;

public class LiveApi extends DefaultApi20 {

    private static final String AUTHORIZE_URL = "https://oauth.live.com/authorize?client_id=%clientId%&redirect_uri=%responseUri%&response_type=code";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=";

    public String getAccessTokenEndpoint() {
        return "https://oauth.live.com/token?grant_type=authorization_code";
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

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }
}