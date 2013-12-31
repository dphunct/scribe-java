package org.scribe.test.helpers;

import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.RequestHttpImpl;
import org.scribe.model.Verb;

public class ObjectMother {

    public static OAuthRequest createSampleOAuthRequest() {
        final RequestHttpImpl httpRequest = new RequestHttpImpl(Verb.GET, "http://example.com");
        final OAuthRequest request = new OAuthRequest(httpRequest);
        request.addOAuthParameter(OAuthConstants.TIMESTAMP, "123456");
        request.addOAuthParameter(OAuthConstants.CONSUMER_KEY, "AS#$^*@&");
        request.addOAuthParameter(OAuthConstants.CALLBACK, "http://example/callback");
        request.addOAuthParameter(OAuthConstants.SIGNATURE, "OAuth-Signature");
        return request;
    }
}
