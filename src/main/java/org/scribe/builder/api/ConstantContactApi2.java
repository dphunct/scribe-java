package org.scribe.builder.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

public class ConstantContactApi2 extends DefaultApi20 {
    private static final String AUTHORIZE_URL = "https://oauth2.constantcontact.com/oauth2/oauth/siteowner/authorize?client_id=%clienId%&response_type=code&redirect_uri=%redirectUri%";

    public String getAccessTokenEndpoint() {
        return "https://oauth2.constantcontact.com/oauth2/oauth/token?grant_type=authorization_code";
    }

    public String getAuthorizationUrl(final OAuthConfig config) {
        return AUTHORIZE_URL.replace("%clientId%", config.getApiKey()).replace("%redirectUri%",
                OAuthEncoder.encode(config.getCallback()));
    }

    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new AccessTokenExtractor() {

            public Token extract(final String response) {
                Preconditions.checkEmptyString(response,
                        "Response body is incorrect. Can't extract a token from an empty string");

                final String regex = "\"access_token\"\\s*:\\s*\"([^&\"]+)\"";
                final Matcher matcher = Pattern.compile(regex).matcher(response);
                if (matcher.find()) {
                    final String token = OAuthEncoder.decode(matcher.group(1));
                    return new Token(token, "", response);
                } else {
                    throw new OAuthException(
                            "Response body is incorrect. Can't extract a token from this: '"
                                    + response + "'", null);
                }
            }
        };
    }
}