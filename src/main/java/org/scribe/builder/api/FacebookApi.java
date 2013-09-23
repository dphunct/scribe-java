package org.scribe.builder.api;

import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

public class FacebookApi extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://www.facebook.com/dialog/oauth?client_id=%clientId%&redirect_uri=%redirectUri%";
    private static final String SCOPED_PARAMETER = "&scope=";

    public String getAccessTokenEndpoint() {
        return "https://graph.facebook.com/oauth/access_token";
    }

    public String getAuthorizationUrl(final OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(),
                "Must provide a valid url as callback. Facebook does not support OOB");

        // Append scope if present
        String result = AUTHORIZE_URL.replace("%clientId%", config.getApiKey()).replace(
                "redirectUri", OAuthEncoder.encode(config.getCallback()));
        if (config.hasScope()) {
            result = result + SCOPED_PARAMETER + config.getScope();
        }
        return result;
    }
}
