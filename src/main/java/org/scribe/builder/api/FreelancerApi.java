package org.scribe.builder.api;

import org.scribe.model.Token;
import org.scribe.model.Verb;

public class FreelancerApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "http://www.freelancer.com/users/api-token/auth.php?oauth_token=";

    public String getAccessTokenEndpoint() {
        return "http://api.freelancer.com/RequestAccessToken/requestAccessToken.xml?";
    }

    public String getRequestTokenEndpoint() {
        return "http://api.freelancer.com/RequestRequestToken/requestRequestToken.xml";
    }

    public Verb getAccessTokenVerb() {
        return Verb.GET;
    }

    public Verb getRequestTokenVerb() {
        return Verb.GET;
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    public static class Sandbox extends FreelancerApi {
        private static final String SANDBOX_AUTHORIZATION_URL = "http://www.sandbox.freelancer.com/users/api-token/auth.php";

        public String getRequestTokenEndpoint() {
            return "http://api.sandbox.freelancer.com/RequestRequestToken/requestRequestToken.xml";
        }

        public String getAccessTokenEndpoint() {
            return "http://api.sandbox.freelancer.com/RequestAccessToken/requestAccessToken.xml?";
        }

        public String getAuthorizationUrl(final Token requestToken) {
            return SANDBOX_AUTHORIZATION_URL + "?oauth_token=" + requestToken.getToken();
        }
    }
}
