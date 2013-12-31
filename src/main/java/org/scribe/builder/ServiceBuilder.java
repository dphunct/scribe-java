package org.scribe.builder;

import java.io.OutputStream;

import org.scribe.builder.api.Api;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequestCreatorFactory;
import org.scribe.model.SignatureType;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.Preconditions;

/**
 * Implementation of the Builder pattern, with a fluent interface that creates a
 * {@link OAuthService}
 * 
 * @author Pablo Fernandez
 *
 */
public class ServiceBuilder {
    private String apiKey;
    private String apiSecret;
    private String callback;
    private Api api;
    private String scope;
    private SignatureType signatureType;
    private OutputStream debugStream;
    private OAuthRequestCreatorFactory requestCreatorFactory;

    /**
     * Default constructor
     */
    public ServiceBuilder() {
        callback = OAuthConstants.OUT_OF_BAND;
        signatureType = SignatureType.Header;
        debugStream = null;
    }

    /**
     * Configures the {@link Api}
     * 
     * @param apiClass the class of one of the existent {@link Api}s on org.scribe.api package
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder provider(final Class/*<? extends Api>*/apiClass) {
        api = createApi(apiClass);
        return this;
    }

    private Api createApi(final Class/*<? extends Api>*/apiClass) {
        Preconditions.checkNotNull(apiClass, "Api class cannot be null");
        final Api api;
        try {
            final Object o = apiClass.newInstance();
            if (o instanceof Api) {
                api = (Api) o;
            } else {
                throw new IllegalArgumentException(String.valueOf(apiClass) + " is not an API");
            }
        } catch (final Exception e) {
            throw new OAuthException("Error while creating the Api object", e);
        }
        return api;
    }

    /**
     * Configures the {@link Api}
     *
     * Overloaded version. Let's you use an instance instead of a class.
     *
     * @param api instance of {@link Api}s
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder provider(final Api api) {
        Preconditions.checkNotNull(api, "Api cannot be null");
        this.api = api;
        return this;
    }

    public ServiceBuilder OAuthRequestCreator(final OAuthRequestCreatorFactory requestCreatorFactory) {
        Preconditions.checkNotNull(requestCreatorFactory, "requestCreatorFactory cannot be null");
        this.requestCreatorFactory = requestCreatorFactory;
        return this;
    }

    /**
     * Adds an OAuth callback url
     * 
     * @param callback callback url. Must be a valid url or 'oob' for out of band OAuth
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder callback(final String callback) {
        Preconditions.checkNotNull(callback, "Callback can't be null");
        this.callback = callback;
        return this;
    }

    /**
     * Configures the api key
     * 
     * @param apiKey The api key for your application
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder apiKey(final String apiKey) {
        Preconditions.checkEmptyString(apiKey, "Invalid Api key");
        this.apiKey = apiKey;
        return this;
    }

    /**
     * Configures the api secret
     * 
     * @param apiSecret The api secret for your application
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder apiSecret(final String apiSecret) {
        Preconditions.checkEmptyString(apiSecret, "Invalid Api secret");
        this.apiSecret = apiSecret;
        return this;
    }

    /**
     * Configures the OAuth scope. This is only necessary in some APIs (like Google's).
     * 
     * @param scope The OAuth scope
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder scope(final String scope) {
        Preconditions.checkEmptyString(scope, "Invalid OAuth scope");
        this.scope = scope;
        return this;
    }

    /**
     * Configures the signature type, choose between header, querystring, etc. Defaults to Header
     *
     * @param scope The OAuth scope
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public ServiceBuilder signatureType(final SignatureType type) {
        Preconditions.checkNotNull(type, "Signature type can't be null");
        signatureType = type;
        return this;
    }

    public ServiceBuilder debugStream(final OutputStream stream) {
        Preconditions.checkNotNull(stream, "debug stream can't be null");
        debugStream = stream;
        return this;
    }

    public ServiceBuilder debug() {
        this.debugStream(System.out);
        return this;
    }

    /**
     * Returns the fully configured {@link OAuthService}
     * 
     * @return fully configured {@link OAuthService}
     */
    public OAuthService build() {
        Preconditions.checkNotNull(api,
                "You must specify a valid api through the provider() method");
        Preconditions.checkEmptyString(apiKey, "You must provide an api key");
        Preconditions.checkEmptyString(apiSecret, "You must provide an api secret");
        final OAuthConfig config = new OAuthConfig(apiKey, apiSecret, callback, signatureType,
                scope, debugStream, requestCreatorFactory);
        return api.createService(config);
    }
}
