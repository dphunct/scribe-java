package org.scribe.extractors;

import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

public class TokenExtractor20Impl implements AccessTokenExtractor {
    private static final String PARAM = "access_token";
    private static final String EMPTY_SECRET = "";

    /**
     * {@inheritDoc} 
     */
    public Token extract(final String response) {
        Preconditions.checkEmptyString(response,
                "ResponseHttpImpl body is incorrect. Can't extract a token from an empty string");

        final int start = response.indexOf(PARAM + "=");
        if (start >= 0) {
            int end = response.indexOf("&", start);
            if (end < 0) {
                end = response.length() - 1;
            }
            final String token = OAuthEncoder.decode(response.substring(start + PARAM.length() + 1,
                    end));
            return new Token(token, EMPTY_SECRET, response);
        }

        throw new OAuthException(
                "ResponseHttpImpl body is incorrect. Can't extract a token from this: '" + response
                        + "'", null);
    }
}
