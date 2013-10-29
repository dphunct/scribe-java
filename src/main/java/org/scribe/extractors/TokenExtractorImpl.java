package org.scribe.extractors;

import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

public class TokenExtractorImpl implements RequestTokenExtractor, AccessTokenExtractor {

    /**
     * {@inheritDoc} 
     */
    public Token extract(final String response) {
        Preconditions.checkEmptyString(response,
                "ResponseHttpImpl body is incorrect. Can't extract a token from an empty string");
        final String token = extract(response, "oauth_token");
        final String secret = extract(response, "oauth_token_secret");
        return new Token(token, secret, response);
    }

    private static String extract(final String response, final String param) {
        final int start = response.indexOf(param + "=");
        if (start >= 0) {
            int end = response.indexOf("&", start);
            if (end < 0) {
                end = response.length() - 1;
            }
            return OAuthEncoder.decode(response.substring(start + param.length() + 1, end));
        }
        throw new OAuthException(
                "ResponseHttpImpl body is incorrect. Can't extract a token from this: '" + response
                        + "'", null);
    }

}
