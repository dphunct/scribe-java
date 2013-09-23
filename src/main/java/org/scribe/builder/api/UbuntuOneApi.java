package org.scribe.builder.api;

import org.scribe.model.Token;
import org.scribe.services.PlaintextSignatureService;
import org.scribe.services.SignatureService;

/**
 * @author Julio Gutierrez
 * 
 *         Sep 6, 2012
 */
public class UbuntuOneApi extends DefaultApi10a {

    private static final String AUTHORIZE_URL = "https://one.ubuntu.com/oauth/authorize/?oauth_token=";

    public String getAccessTokenEndpoint() {
        return "https://one.ubuntu.com/oauth/access/";
    }

    public String getAuthorizationUrl(final Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    public String getRequestTokenEndpoint() {
        return "https://one.ubuntu.com/oauth/request/";
    }

    public SignatureService getSignatureService() {
        return new PlaintextSignatureService();
    }

}
