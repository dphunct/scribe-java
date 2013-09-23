package org.scribe.builder.api;

import org.scribe.model.Token;

public class SinaWeiboApi extends DefaultApi10a {
    private static final String REQUEST_TOKEN_URL = "http://api.t.sina.com.cn/oauth/request_token";
    private static final String ACCESS_TOKEN_URL = "http://api.t.sina.com.cn/oauth/access_token";
    private static final String AUTHORIZE_URL = "http://api.t.sina.com.cn/oauth/authorize?oauth_token=";

    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_URL;
    }

    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_URL;
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }
}
