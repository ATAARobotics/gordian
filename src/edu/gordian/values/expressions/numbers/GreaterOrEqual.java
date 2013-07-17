package edu.gordian.values.expressions.numbers;

import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianBoolean;
import edu.gordian.values.gordian.GordianNumber;

public final class GreaterOrEqual extends GordianBoolean {

    public static boolean is(Scope s, String v) {
        try {
            return v.indexOf(">=") > 0 && v.indexOf(">=") < v.length() - 2
                    && (s.toValue(v.substring(0, v.indexOf(">="))) instanceof GordianNumber)
                    && (s.toValue(v.substring(v.indexOf(">=") + 2)) instanceof GordianNumber);
        } catch (Scope.IsNotValue e) {
            // toValue didn't work
            return false;
        }
    }

    public static GreaterOrEqual valueOf(Scope s, String v) {
        return new GreaterOrEqual((GordianNumber) s.toValue(v.substring(0, v.indexOf(">="))),
                (GordianNumber) s.toValue(v.substring(v.indexOf(">=") + 2)));
    }

    public GreaterOrEqual(GordianNumber first, GordianNumber second) {
        super(first.doubleValue() >= second.doubleValue());
    }
}
