package org.scribe.builder.api;

import org.apache.commons.lang.StringUtils;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;

/**
 * Renren(http://www.renren.com/) OAuth 2.0 based api.
 */
public class RenrenApi extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://graph.renren.com/oauth/authorize?client_id=%clientId%&redirect_uri=%redirectUri%&response_type=code";
    private static final String SCOPED_PARAMETER = "&scope=";

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }

    public String getAccessTokenEndpoint() {
        return "https://graph.renren.com/oauth/token?grant_type=authorization_code";
    }

    public String getAuthorizationUrl(final OAuthConfig config) {
        String result = StringUtils.replace(AUTHORIZE_URL, "clientId", config.getApiKey());
        StringUtils.replace(result, "redirectUri", OAuthEncoder.encode(config.getCallback()));
        // Append scope if present
        if (config.hasScope()) {
            result = result + SCOPED_PARAMETER + OAuthEncoder.encode(config.getScope());
        }
        return result;
    }
}
