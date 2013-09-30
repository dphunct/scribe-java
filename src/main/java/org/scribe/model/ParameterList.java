package org.scribe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * @author: Pablo Fernandez
 */
public class ParameterList {
    private static final String QUERY_STRING_SEPARATOR = "?";
    private static final String PARAM_SEPARATOR = "&";
    private static final String PAIR_SEPARATOR = "=";
    private static final String EMPTY_STRING = "";

    private final List/*<Parameter>*/params;

    public ParameterList() {
        params = new ArrayList/*<Parameter>*/();
    }

    ParameterList(final List/*<Parameter>*/params) {
        this.params = new ArrayList/*<Parameter>*/(params);
    }

    public ParameterList(final Map/*<String, String>*/map) {
        this();
        final Iterator i = map.entrySet().iterator();
        while (i.hasNext()) {
            final Map.Entry/*<String, String>*/entry = (Entry) i.next();
            params.add(new Parameter((String) entry.getKey(), (String) entry.getValue()));
        }
    }

    public void add(final String key, final String value) {
        params.add(new Parameter(key, value));
    }

    public String appendTo(String url) {
        Preconditions.checkNotNull(url, "Cannot append to null URL");
        final String queryString = asFormUrlEncodedString();
        if (EMPTY_STRING.equals(queryString)) {
            return url;
        } else {
            url += url.indexOf(QUERY_STRING_SEPARATOR) < 0 ? QUERY_STRING_SEPARATOR
                    : PARAM_SEPARATOR;
            url += queryString;
            return url;
        }
    }

    public String asOauthBaseString() {
        return OAuthEncoder.encode(asFormUrlEncodedString());
    }

    public String asFormUrlEncodedString() {
        if (params.size() == 0) {
            return EMPTY_STRING;
        }

        final StringBuffer builder = new StringBuffer();
        final Iterator i = params.iterator();
        while (i.hasNext()) {
            final Parameter p = (Parameter) i.next();
            builder.append('&').append(p.asUrlEncodedPair());
        }
        return builder.toString().substring(1);
    }

    public void addAll(final ParameterList other) {
        params.addAll(other.params);
    }

    public void addQuerystring(final String queryString) {
        if (queryString != null && queryString.length() > 0) {
            final String[] vals = StringUtils.split(queryString, PARAM_SEPARATOR);
            for (int i = 0; i < vals.length; i++) {
                final String param = vals[i];
                final String pair[] = StringUtils.split(param, PAIR_SEPARATOR);
                final String key = OAuthEncoder.decode(pair[0]);
                final String value = pair.length > 1 ? OAuthEncoder.decode(pair[1]) : EMPTY_STRING;
                params.add(new Parameter(key, value));
            }
        }
    }

    public boolean contains(final Parameter param) {
        return params.contains(param);
    }

    public int size() {
        return params.size();
    }

    public ParameterList sort() {
        final ParameterList sorted = new ParameterList(params);
        Collections.sort(sorted.params);
        return sorted;
    }
}
