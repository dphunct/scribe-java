package org.scribe.builder.api;

import org.apache.commons.lang.StringUtils;
import org.apache.regexp.RE;
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
        String string = StringUtils.replace(AUTHORIZE_URL, "%clientId%", config.getApiKey());
        string = StringUtils.replace(string, "%redirectUri%",
                OAuthEncoder.encode(config.getCallback()));
        return string;
    }

    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    public AccessTokenExtractor getAccessTokenExtractor() {
        return new AccessTokenExtractor() {

            public Token extract(final String response) {
                Preconditions
                        .checkEmptyString(response,
                                "ResponseHttpImpl body is incorrect. Can't extract a token from an empty string");

                final String regex = "\"access_token\"\\s*:\\s*\"([^&\"]+)\"";
                final RE re = new RE(regex);
                if (re.match(response)) {
                    final String token = OAuthEncoder.decode(re.getParen(1));
                    return new Token(token, "", response);
                } else {
                    throw new OAuthException(
                            "ResponseHttpImpl body is incorrect. Can't extract a token from this: '"
                                    + response + "'", null);
                }
            }
        };
    }
}