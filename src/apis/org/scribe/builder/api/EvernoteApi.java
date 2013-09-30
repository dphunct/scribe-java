package org.scribe.builder.api;

import org.scribe.model.Token;

public class EvernoteApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "https://www.evernote.com/OAuth.action?oauth_token=";

    public String getRequestTokenEndpoint() {
        return "https://www.evernote.com/oauth";
    }

    public String getAccessTokenEndpoint() {
        return "https://www.evernote.com/oauth";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    public static class Sandbox extends EvernoteApi {
        private static final String SANDBOX_URL = "https://sandbox.evernote.com";

        public String getRequestTokenEndpoint() {
            return SANDBOX_URL + "/oauth";
        }

        public String getAccessTokenEndpoint() {
            return SANDBOX_URL + "/oauth";
        }

        public String getAuthorizationUrl(final Token requestToken) {
            return SANDBOX_URL + "/OAuth.action?oauth_token=" + requestToken.getToken();
        }
    }
}
