package org.scribe.services;

import org.apache.commons.codec.binary.Base64;

public class DatatypeConverterEncoder extends Base64Encoder {

    public String encode(final byte[] bytes) {
        return String.valueOf(Base64.encodeBase64(bytes));
    }

    public String getType() {
        return "DatatypeConverter";
    }
}
