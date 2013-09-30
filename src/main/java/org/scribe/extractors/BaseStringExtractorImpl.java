package org.scribe.extractors;

import org.scribe.exceptions.OAuthParametersMissingException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.ParameterList;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * Default implementation of {@link BaseStringExtractor}. Conforms to OAuth 1.0a
 * 
 * @author Pablo Fernandez
 *
 */
public class BaseStringExtractorImpl implements BaseStringExtractor {

    private static final String AMPERSAND_SEPARATOR = "&";

    /**
     * {@inheritDoc}
     */
    public String extract(final OAuthRequest request) {
        checkPreconditions(request);
        final String verb = OAuthEncoder.encode(request.getVerb().name());
        final String url = OAuthEncoder.encode(request.getUrl());
        final String params = getSortedAndEncodedParams(request);
        final StringBuffer buffer = new StringBuffer();
        buffer.append(verb);
        buffer.append(AMPERSAND_SEPARATOR);
        buffer.append(url);
        buffer.append(AMPERSAND_SEPARATOR);
        buffer.append(params);
        return buffer.toString();
    }

    private String getSortedAndEncodedParams(final OAuthRequest request) {
        final ParameterList params = new ParameterList();
        params.addAll(request.getQueryStringParams());
        params.addAll(request.getBodyParams());
        params.addAll(new ParameterList(request.getOauthParameters()));
        return params.sort().asOauthBaseString();
    }

    private void checkPreconditions(final OAuthRequest request) {
        Preconditions.checkNotNull(request, "Cannot extract base string from null object");

        if (request.getOauthParameters() == null || request.getOauthParameters().size() <= 0) {
            throw new OAuthParametersMissingException(request);
        }
    }
}
