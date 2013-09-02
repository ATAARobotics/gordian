package edu.gordian.values;

import language.value.Value;

public final class GordianNull implements Value {

    private static final GordianNull NULL = new GordianNull();

    public static GordianNull get() {
        return NULL;
    }

    private GordianNull() {
    }

    public boolean equals(Object obj) {
        return obj instanceof GordianNull;
    }

    public String toString() {
        return "null";
    }

    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
