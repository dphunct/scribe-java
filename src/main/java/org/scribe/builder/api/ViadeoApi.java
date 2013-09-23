package org.scribe.builder.api;

import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

public class ViadeoApi extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://secure.viadeo.com/oauth-provider/authorize2?client_id=%clientId%&redirect_uri=%redirectUri%&response_type=code";
    private static final String SCOPED_PARAMETER = "&scope=";

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }

    public String getAccessTokenEndpoint() {
        return "https://secure.viadeo.com/oauth-provider/access_token2?grant_type=authorization_code";
    }

    public String getAuthorizationUrl(final OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(),
                "Must provide a valid url as callback. Viadeo does not support OOB");
        String result = AUTHORIZE_URL.replace("clientId", config.getApiKey()).replace(
                "redirectUri", OAuthEncoder.encode(config.getCallback()));
        // Append scope if present
        if (config.hasScope()) {
            result = result + SCOPED_PARAMETER + OAuthEncoder.encode(config.getScope());
        }
        return result;
    }
}
