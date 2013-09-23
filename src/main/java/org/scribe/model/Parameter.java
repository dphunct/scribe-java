package org.scribe.model;

import org.scribe.utils.OAuthEncoder;

/**
 * @author: Pablo Fernandez
 */
public class Parameter implements Comparable/*<Parameter>*/
{
    private final String key;
    private final String value;

    public Parameter(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String asUrlEncodedPair() {
        return OAuthEncoder.encode(key).concat("=").concat(OAuthEncoder.encode(value));
    }

    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Parameter)) {
            return false;
        }

        final Parameter otherParam = (Parameter) other;
        return otherParam.key.equals(key) && otherParam.value.equals(value);
    }

    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }

    public int compareTo(final Object o) {
        if (o instanceof Parameter) {
            return compareTo((Parameter) o);
        }
        return 1;
    }

    public int compareTo(final Parameter parameter) {
        final int keyDiff = key.compareTo(parameter.key);

        return keyDiff != 0 ? keyDiff : value.compareTo(parameter.value);
    }
}
