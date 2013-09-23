package org.scribe.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author: Pablo Fernandez
 */
public class MapUtils {
    private MapUtils() {
    }

    public static/*<K,V>*/String toString(final Map/*<K,V>*/map) {
        if (map == null) {
            return "";
        }
        if (map.isEmpty()) {
            return "{}";
        }

        final StringBuilder result = new StringBuilder();

        final Iterator i = map.entrySet().iterator();
        while (i.hasNext()) {
            final Map.Entry/*<K,V>*/entry = (Entry) i.next();
            result.append(", " + String.valueOf(entry.getKey()) + " -> "
                    + String.valueOf(entry.getValue()) + " ");
        }
        return "{" + result.substring(1) + "}";
    }
}
