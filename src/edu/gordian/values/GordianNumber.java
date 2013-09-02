package edu.gordian.values;

import language.value.Value;

public final class GordianNumber implements Value {

    private final double val;

    public static GordianNumber toNumber(String s) {
        double val;
        try {
            val = Double.parseDouble(s);
            return new GordianNumber(val);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public GordianNumber(int val) {
        this.val = val;
    }

    public GordianNumber(long val) {
        this.val = val;
    }

    public GordianNumber(double val) {
        this.val = val;
    }

    public int getInt() {
        return (int) val;
    }

    public long getLong() {
        return (long) val;
    }

    public double getDouble() {
        return val;
    }

    public boolean isInt() {
        return val % 1.0 == 0;
    }

    public boolean isDouble() {
        return !isInt();
    }

    public boolean equals(Object obj) {
        if (obj instanceof GordianNumber) {
            return val == ((GordianNumber) obj).val;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.val) ^ (Double.doubleToLongBits(this.val) >>> 32));
        return hash;
    }

    public String toString() {
        if(isInt()) {
            return String.valueOf(getInt());
        } else {
            return String.valueOf(getDouble());
        }
    }
}
