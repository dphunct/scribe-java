package org.scribe.oauth;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.services.Base64Encoder;
import org.scribe.utils.MapUtils;

/**
 * OAuth 1.0a implementation of {@link OAuthService}
 *
 * @author Pablo Fernandez
 */
public class OAuth10aServiceImpl implements OAuthService {
    private static final String VERSION = "1.0";

    protected final OAuthConfig config;
    protected final DefaultApi10a api;

    /**
     * Default constructor
     *
     * @param api OAuth1.0a api information
     * @param config OAuth 1.0a configuration param object
     */
    public OAuth10aServiceImpl(final DefaultApi10a api, final OAuthConfig config) {
        this.api = api;
        this.config = config;
    }

    public Token getRequestToken() throws IOException {
        return getRequestToken(null);
    }

    public Token getRequestToken(final Map parameters) throws IOException {
        config.log("obtaining request token from " + api.getRequestTokenEndpoint());
        final OAuthRequest request = config
                .getOAuthRequestCreatorFactory()
                .createRequestTokenRequest(api.getRequestTokenVerb(), api.getRequestTokenEndpoint());
        if (parameters != null && parameters.size() > 0) {
            final Iterator i = parameters.keySet().iterator();
            while (i.hasNext()) {
                final String key = (String) i.next();
                final String value = (String) parameters.get(key);
                if (value != null) {
                    request.addQuerystringParameter(key, value);
                }
            }
        }
        config.log("setting oauth_callback to " + config.getCallback());
        request.addOAuthParameter(OAuthConstants.CALLBACK, config.getCallback());
        addOAuthParams(request, OAuthConstants.EMPTY_TOKEN);
        appendSignature(request);

        config.log("sending request...");
        final Response response = request.send();
        final String body = response.getBody();

        config.log("response status code: " + response.getCode());
        config.log("response body: " + body);
        return api.getRequestTokenExtractor().extract(body);
    }

    protected void addOAuthParams(final OAuthRequest request, final Token token) {
        request.addOAuthParameter(OAuthConstants.TIMESTAMP, api.getTimestampService()
                .getTimestampInSeconds());
        request.addOAuthParameter(OAuthConstants.NONCE, api.getTimestampService().getNonce());
        request.addOAuthParameter(OAuthConstants.CONSUMER_KEY, config.getApiKey());
        request.addOAuthParameter(OAuthConstants.SIGN_METHOD, api.getSignatureService()
                .getSignatureMethod());
        request.addOAuthParameter(OAuthConstants.VERSION, getVersion());
        if (config.hasScope()) {
            request.addOAuthParameter(OAuthConstants.SCOPE, config.getScope());
        }
        request.addOAuthParameter(OAuthConstants.SIGNATURE, getSignature(request, token));

        config.log("appended additional OAuth parameters: "
                + MapUtils.toString(request.getOauthParameters()));
    }

    /**
     * {@inheritDoc}
     */
    public Token getAccessToken(final Token requestToken, final Verifier verifier) {
        config.log("obtaining access token from " + api.getAccessTokenEndpoint());
        final OAuthRequest request = config.getOAuthRequestCreatorFactory()
                .createAccessTokenRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        request.addOAuthParameter(OAuthConstants.TOKEN, requestToken.getToken());
        if (verifier != null) {
            request.addOAuthParameter(OAuthConstants.VERIFIER, verifier.getValue());
        }
        config.log("setting token to: " + requestToken + " and verifier to: " + verifier);
        addOAuthParams(request, requestToken);
        appendSignature(request);
        try {
            final Response response = request.send();
            return api.getAccessTokenExtractor().extract(response.getBody());
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Token getAccessToken(final Token requestToken) {
        return getAccessToken(requestToken, null);
    }

    /**
     * {@inheritDoc}
     */
    public void signRequest(final Token token, final OAuthRequest request) {
        config.log("signing request: " + request.getUrl());

        // Do not append the token if empty. This is for two legged OAuth calls.
        if (!token.isEmpty()) {
            request.addOAuthParameter(OAuthConstants.TOKEN, token.getToken());
        }
        config.log("setting token to: " + token);
        addOAuthParams(request, token);
        appendSignature(request);
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
    public String getAuthorizationUrl(final Token requestToken) {
        return api.getAuthorizationUrl(requestToken);
    }

    protected String getSignature(final OAuthRequest request, final Token token) {
        config.log("generating signature...");
        config.log("using base64 encoder: " + Base64Encoder.type());
        final String baseString = api.getBaseStringExtractor().extract(request);
        final String signature = api.getSignatureService().getSignature(baseString,
                config.getApiSecret(), token.getSecret());

        config.log("base string is: " + baseString);
        config.log("signature is: " + signature);
        return signature;
    }

    protected void appendSignature(final OAuthRequest request) {
        if (SignatureType.Header.equals(config.getSignatureType())) {
            config.log("using Http Header signature");

            final String oauthHeader = api.getHeaderExtractor().extract(request);
            request.addHeader(OAuthConstants.HEADER, oauthHeader);
        } else if (SignatureType.QueryString.equals(config.getSignatureType())) {
            config.log("using Querystring signature");
            final Iterator i = request.getOauthParameters().entrySet().iterator();
            while (i.hasNext()) {
                final Map.Entry/*<String, String>*/entry = (Map.Entry) i.next();
                request.addQuerystringParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

}
