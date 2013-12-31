package org.scribe.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class OAuthRequestTest {

    private OAuthRequest request;

    @Before
    public void setup() {
        final RequestHttpImpl httpRequest = new RequestHttpImpl(Verb.GET, "http://example.com");
        request = new OAuthRequest(httpRequest);
    }

    @Test
    public void shouldAddOAuthParamters() {
        request.addOAuthParameter(OAuthConstants.TOKEN, "token");
        request.addOAuthParameter(OAuthConstants.NONCE, "nonce");
        request.addOAuthParameter(OAuthConstants.TIMESTAMP, "ts");
        request.addOAuthParameter(OAuthConstants.SCOPE, "feeds");

        assertEquals(4, request.getOauthParameters().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfParameterIsNotOAuth() {
        request.addOAuthParameter("otherParam", "value");
    }
}
