package org.scribe.builder.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.scribe.model.Token;

public class LinkedInApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authenticate?oauth_token=";
    private static final String REQUEST_TOKEN_URL = "https://api.linkedin.com/uas/oauth/requestToken";

    private final Set/*<String>*/scopes;

    public LinkedInApi() {
        scopes = new HashSet();//.emptySet();
    }

    public LinkedInApi(final Set/*<String>*/scopes) {
        this.scopes = Collections.unmodifiableSet(scopes);
    }

    public String getAccessTokenEndpoint() {
        return "https://api.linkedin.com/uas/oauth/accessToken";
    }

    public String getRequestTokenEndpoint() {
        return scopes.isEmpty() ? REQUEST_TOKEN_URL : REQUEST_TOKEN_URL + "?scope="
                + scopesAsString();
    }

    private String scopesAsString() {
        final StringBuilder builder = new StringBuilder();
        final Iterator i = scopes.iterator();
        while (i.hasNext()) {
            final String scope = (String) i.next();
            builder.append("+" + scope);
        }
        return builder.substring(1);
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    public static LinkedInApi withScopes(final String[] scopes) {
        final Set/*<String>*/scopeSet = new HashSet/*<String>*/(Arrays.asList(scopes));
        return new LinkedInApi(scopeSet);
    }

}
