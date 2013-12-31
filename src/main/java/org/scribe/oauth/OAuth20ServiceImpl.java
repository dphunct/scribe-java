package org.scribe.oauth;

import java.io.IOException;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;

public class OAuth20ServiceImpl implements OAuthService {
    private static final String VERSION = "2.0";

    private final DefaultApi20 api;
    private final OAuthConfig config;

    /**
     * Default constructor
     * 
     * @param api OAuth2.0 api information
     * @param config OAuth 2.0 configuration param object
     */
    public OAuth20ServiceImpl(final DefaultApi20 api, final OAuthConfig config) {
        this.api = api;
        this.config = config;
    }

    /**
     * {@inheritDoc}
     * @throws IOException 
     */
    public Token getAccessToken(final Token requestToken, final Verifier verifier)
            throws IOException {
        final OAuthRequest request = config.getOAuthRequestCreatorFactory()
                .createAccessTokenRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
        request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
        request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
        request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        if (config.hasScope()) {
            request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
        }
        final Response response = request.send();
        return api.getAccessTokenExtractor().extract(response.getBody());
    }

    /**
     * {@inheritDoc}
     */
    public Token getRequestToken() {
        throw new UnsupportedOperationException(
                "Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
    }

    /**
     * {@inheritDoc}
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     * {@inheritDoc}
     */
    public void signRequest(final Token accessToken, final OAuthRequest request) {
        request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
    }

    /**
     * {@inheritDoc}
     */
    public String getAuthorizationUrl(final Token requestToken) {
        return api.getAuthorizationUrl(config);
    }

    /**
     * {@inheritDoc}
     * @throws IOException 
     */
    public Token getAccessToken(final Token requestToken) throws IOException {
        final OAuthRequest request = config.getOAuthRequestCreatorFactory()
                .createAccessTokenRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
        request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
        request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        if (config.hasScope()) {
            request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
        }
        final Response response = request.send();
        return api.getAccessTokenExtractor().extract(response.getBody());
    }

}
