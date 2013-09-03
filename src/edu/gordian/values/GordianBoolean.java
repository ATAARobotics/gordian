package edu.gordian.values;

import language.value.Value;

public final class GordianBoolean implements Value {

    private final boolean val;

    public GordianBoolean(boolean val) {
        this.val = val;
    }
    
    public static GordianBoolean toBoolean(String s) {
        return new GordianBoolean(s.equalsIgnoreCase("true"));
    }

    public boolean get() {
        return val;
    }

    public String toString() {
        return String.valueOf(val);
    }

    public boolean equals(Object obj) {
        if (obj instanceof GordianBoolean) {
            return ((GordianBoolean) obj).val == val;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.val ? 1 : 0);
        return hash;
    }
}
