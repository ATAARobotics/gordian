package edu.gordian.values;

import com.sun.squawk.util.MathUtils;

public final class GordianNumber {

    private final double value;

    public GordianNumber(double value) {
        this.value = value;
    }

    public static GordianNumber valueOf(double d) {
        return new GordianNumber(d);
    }
    
    public static GordianNumber valueOf(String d) throws NumberFormatException {
        return new GordianNumber(Double.parseDouble(d));
    }

    public double doubleValue() {
        return value;
    }

    public int intValue() {
        return (int) MathUtils.round(value);
    }

    public boolean isInt() {
        return value % 1.0 == 0;
    }

    public String toString() {
        if(isInt()) {
            return String.valueOf(intValue());
        } else {
            return String.valueOf(value);
        }
    }
}
