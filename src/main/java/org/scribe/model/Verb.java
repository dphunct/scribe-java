package org.scribe.model;

/**
 * An enumeration containing the most common HTTP Verbs.
 * 
 * @author Pablo Fernandez
 */
public class Verb {

    public static final Verb GET = new Verb("GET");
    public static final Verb POST = new Verb("POST");
    public static final Verb PUT = new Verb("PUT");
    public static final Verb DELETE = new Verb("DELETE");
    public static final Verb HEAD = new Verb("HEAD");
    public static final Verb OPTIONS = new Verb("OPTIONS");
    public static final Verb TRACE = new Verb("TRACE");
    public static final Verb PATCH = new Verb("PATCH");

    final String name;

    /**
     * @param string
     */
    public Verb(final String string) {
        super();
        name = string;
    }

    public String name() {
        return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Verb other = (Verb) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return name;
    }

}
