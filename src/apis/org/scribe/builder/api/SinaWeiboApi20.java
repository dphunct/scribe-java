package org.scribe.builder.api;

import org.apache.commons.lang.StringUtils;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Verb;
import org.scribe.utils.OAuthEncoder;

/**
 * SinaWeibo OAuth 2.0 api.
 */
public class SinaWeiboApi20 extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?client_id=%clientId%&redirect_uri=%redirectUri%&response_type=code";
    private static final String SCOPED_PARAMETER = "&scope=";

    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }

    public String getAccessTokenEndpoint() {
        return "https://api.weibo.com/oauth2/access_token?grant_type=authorization_code";
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
