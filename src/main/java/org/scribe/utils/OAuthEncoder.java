package org.scribe.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.scribe.exceptions.OAuthException;

/**
 * @author: Pablo Fernandez
 */
public class OAuthEncoder {
    private static String CHARSET = "UTF-8";
    private static final Map/*<String, String>*/ENCODING_RULES;

    static {
        final Map/*<String, String>*/rules = new HashMap/*<String, String>*/();
        rules.put("*", "%2A");
        rules.put("+", "%20");
        rules.put("%7E", "~");
        ENCODING_RULES = Collections.unmodifiableMap(rules);
    }

    private OAuthEncoder() {
    }

    public static String encode(final String plain) {
        Preconditions.checkNotNull(plain, "Cannot encode null object");
        String encoded = "";
        try {
            encoded = URLEncoder.encode(plain, CHARSET);
        } catch (final UnsupportedEncodingException uee) {
            throw new OAuthException("Charset not found while encoding string: " + CHARSET, uee);
        }
        final Iterator i = ENCODING_RULES.entrySet().iterator();
        while (i.hasNext()) {
            final Map.Entry/*<String, String>*/rule = (Map.Entry) i.next();
            encoded = applyRule(encoded, (String) rule.getKey(), (String) rule.getValue());
        }
        return encoded;
    }

    private static String applyRule(final String encoded, final String toReplace,
            final String replacement) {
        return encoded.replaceAll(Pattern.quote(toReplace), replacement);
    }

    public static String decode(final String encoded) {
        Preconditions.checkNotNull(encoded, "Cannot decode null object");
        try {
            return URLDecoder.decode(encoded, CHARSET);
        } catch (final UnsupportedEncodingException uee) {
            throw new OAuthException("Charset not found while decoding string: " + CHARSET, uee);
        }
    }
}
