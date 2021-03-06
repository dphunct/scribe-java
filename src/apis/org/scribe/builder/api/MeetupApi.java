package org.scribe.builder.api;

import org.scribe.model.Token;

/**
 * OAuth access to the Meetup.com API.
 * For more information visit http://www.meetup.com/api
 */
public class MeetupApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "http://www.meetup.com/authenticate?oauth_token=";

    public String getRequestTokenEndpoint() {
        return "http://api.meetup.com/oauth/request/";
    }

    public String getAccessTokenEndpoint() {
        return "http://api.meetup.com/oauth/access/";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }
}
