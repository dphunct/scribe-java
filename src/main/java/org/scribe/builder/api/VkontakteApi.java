package org.scribe.builder.api;

import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * @author Boris G. Tsirkin <mail@dotbg.name>
 * @since 20.4.2011
 */
public class VkontakteApi extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://oauth.vk.com/authorize?client_id=%clientId%&redirect_uri=%redirectUri%&response_type=code";
    private static final String SCOPED_PARAMETER = "&scope=";

    public String getAccessTokenEndpoint() {
        return "https://api.vkontakte.ru/oauth/access_token";
    }

    public String getAuthorizationUrl(final OAuthConfig config) {
        Preconditions.checkValidUrl(config.getCallback(),
                "Valid url is required for a callback. Vkontakte does not support OOB");
        String result = AUTHORIZE_URL.replace("clientId", config.getApiKey()).replace(
                "redirectUri", OAuthEncoder.encode(config.getCallback()));
        // Append scope if present
        if (config.hasScope()) {
            result = result + SCOPED_PARAMETER + OAuthEncoder.encode(config.getScope());
        }
        return result;
    }

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new JsonTokenExtractor();
    }
}
