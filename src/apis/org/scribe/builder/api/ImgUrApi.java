package org.scribe.builder.api;

import org.scribe.model.Token;

/**
 * OAuth API for ImgUr
 *
 * @author David Wursteisen
 * @see <a href="http://api.imgur.com/#authapi">ImgUr API</a>
 */
public class ImgUrApi extends DefaultApi10a {

    public String getRequestTokenEndpoint() {
        return "https://api.imgur.com/oauth/request_token";
    }

    public String getAccessTokenEndpoint() {
        return "https://api.imgur.com/oauth/access_token";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return "https://api.imgur.com/oauth/authorize?oauth_token=" + requestToken.getToken();
    }
}
