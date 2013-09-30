package org.scribe.extractors;

import org.apache.regexp.RE;
import org.apache.regexp.REUtil;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.utils.Preconditions;

public class JsonTokenExtractor implements AccessTokenExtractor {
    private static final String ACCESS_TOKEN_PATTERN = "\"access_token\":\\s*\"(\\S*?)\"";

    public Token extract(final String response) {

        Preconditions.checkEmptyString(response,
                "Cannot extract a token from a null or empty String");
        final RE accessTokenPattern = REUtil.createRE(ACCESS_TOKEN_PATTERN);
        if (accessTokenPattern.match(response)) {
            return new Token(accessTokenPattern.getParen(1), "", response);
        } else {
            throw new OAuthException("Cannot extract an acces token. ResponseHttpImpl was: "
                    + response);
        }
    }
}