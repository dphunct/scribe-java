package org.scribe.extractors;

import org.apache.regexp.RE;
import org.apache.regexp.REUtil;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * Default implementation of {@RequestTokenExtractor} and {@AccessTokenExtractor}. Conforms to OAuth 1.0a
 *
 * The process for extracting access and request tokens is similar so this class can do both things.
 * 
 * @author Pablo Fernandez
 */
public class TokenExtractorImpl implements RequestTokenExtractor, AccessTokenExtractor {
    private static final String TOKEN_REGEX = "oauth_token=([^&]+)";
    private static final String SECRET_REGEX = "oauth_token_secret=([^&]*)";

    /**
     * {@inheritDoc} 
     */
    public Token extract(final String response) {
        Preconditions.checkEmptyString(response,
                "ResponseHttpImpl body is incorrect. Can't extract a token from an empty string");
        final String token = extract(response, TOKEN_REGEX);
        final String secret = extract(response, SECRET_REGEX);
        return new Token(token, secret, response);
    }

    private String extract(final String response, final String p) {
        final RE re = REUtil.createRE(p);

        if (re.match(response) && re.getMatchFlags() >= 1) {
            return OAuthEncoder.decode(re.getParen(1));
        } else {
            throw new OAuthException(
                    "ResponseHttpImpl body is incorrect. Can't extract token and secret from this: '"
                            + response + "'", null);
        }
    }
}
