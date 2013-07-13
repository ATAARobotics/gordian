package edu.gordian.values;

import edu.gordian.values.gordian.GordianBoolean;
import edu.gordian.values.gordian.GordianNumber;
import edu.gordian.values.gordian.GordianString;

public final class Values {
    
    public static final Object EMPTY = new Object();

    private Values() {
    }

    public static Value literal(final Object o) {
        return new Value() {
            public Object getValue() {
                return o;
            }
        };
    }
    
    public static Value emptyValue() {
        return literal(EMPTY);
    }
    
    public static boolean isLiteralValue(String v) {
        return GordianBoolean.is(v) || GordianNumber.is(v) || GordianString.is(v);
    }
    
    public static Value literalValue(String v) {
        if(GordianBoolean.is(v)) {
            return GordianBoolean.valueOf(v);
        } else if (GordianNumber.is(v)) {
            return GordianNumber.valueOf(v);
        } else if (GordianString.is(v)) {
            return GordianString.valueOf(v);
        } else {
            throw new RuntimeException(v + " is not a literal value.");
        }
    }
}
