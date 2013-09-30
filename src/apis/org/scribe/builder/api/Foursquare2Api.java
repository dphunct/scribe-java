package org.scribe.builder.api;

import org.apache.commons.lang.StringUtils;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

public class Foursquare2Api extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://foursquare.com/oauth2/authenticate?client_id=%clientId%&response_type=code&redirect_uri=%redirectUri%";

    public String getAccessTokenEndpoint() {
        return "https://foursquare.com/oauth2/access_token?grant_type=authorization_code";
    }

    public String getAuthorizationUrl(final OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(),
                "Must provide a valid url as callback. Foursquare2 does not support OOB");
        final String result = StringUtils.replace(AUTHORIZE_URL, "clientId", config.getApiKey());
        StringUtils.replace(result, "redirectUri", OAuthEncoder.encode(config.getCallback()));
        return result;
    }

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }
}
