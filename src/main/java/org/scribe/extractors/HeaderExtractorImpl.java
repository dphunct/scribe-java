package org.scribe.extractors;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.scribe.exceptions.OAuthParametersMissingException;
import org.scribe.model.OAuthRequest;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * Default implementation of {@link HeaderExtractor}. Conforms to OAuth 1.0a
 * 
 * @author Pablo Fernandez
 *
 */
public class HeaderExtractorImpl implements HeaderExtractor {
    private static final String PARAM_SEPARATOR = ", ";
    private static final String PREAMBLE = "OAuth ";
    public static final int ESTIMATED_PARAM_LENGTH = 20;

    /**
     * {@inheritDoc}
     */
    public String extract(final OAuthRequest request) {
        checkPreconditions(request);
        final Map/*<String, String>*/parameters = request.getOauthParameters();
        final StringBuilder header = new StringBuilder(parameters.size() * ESTIMATED_PARAM_LENGTH);
        header.append(PREAMBLE);
        final Iterator i = parameters.entrySet().iterator();
        while (i.hasNext()) {
            final Map.Entry/*<String, String>*/entry = (Entry) i.next();
            if (header.length() > PREAMBLE.length()) {
                header.append(PARAM_SEPARATOR);
            }
            final StringBuffer buffer = new StringBuffer();
            buffer.append(entry.getKey());
            buffer.append("=\"");
            buffer.append(OAuthEncoder.encode((String) entry.getValue()));
            buffer.append("\"");
            header.append(buffer.toString());
        }
        return header.toString();
    }

    private void checkPreconditions(final OAuthRequest request) {
        Preconditions.checkNotNull(request, "Cannot extract a header from a null object");

        if (request.getOauthParameters() == null || request.getOauthParameters().size() <= 0) {
            throw new OAuthParametersMissingException(request);
        }
    }

}
