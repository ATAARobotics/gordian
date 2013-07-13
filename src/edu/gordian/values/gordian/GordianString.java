package edu.gordian.values.gordian;

import edu.gordian.values.Value;

public class GordianString implements Value {

    private final String value;

    public static boolean is(String s) {
        return s.startsWith("\"") && s.endsWith("\"");
    }

    public GordianString(String value) {
        this.value = value.substring(1, value.length() - 2);
    }

    public static GordianString valueOf(String s) {
        return new GordianString(s);
    }

    public final String stringValue() {
        return value;
    }

    public final Object getValue() {
        return value;
    }

    public final String toString() {
        return value;
    }
}
