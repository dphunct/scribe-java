package org.scribe.extractors;

import org.apache.regexp.RE;
import org.apache.regexp.REUtil;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * Default implementation of {@AccessTokenExtractor}. Conforms to OAuth 2.0
 *
 */
public class TokenExtractor20Impl implements AccessTokenExtractor {
    private static final String TOKEN_REGEX = "access_token=([^&]+)";
    private static final String EMPTY_SECRET = "";

    /**
     * {@inheritDoc} 
     */
    public Token extract(final String response) {
        Preconditions.checkEmptyString(response,
                "ResponseHttpImpl body is incorrect. Can't extract a token from an empty string");
        final RE re = REUtil.createRE(TOKEN_REGEX);
        if (re.match(response)) {
            final String token = OAuthEncoder.decode(re.getParen(1));
            return new Token(token, EMPTY_SECRET, response);
        } else {
            throw new OAuthException(
                    "ResponseHttpImpl body is incorrect. Can't extract a token from this: '"
                            + response + "'", null);
        }
    }
}
