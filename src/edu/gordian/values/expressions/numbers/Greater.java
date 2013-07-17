package edu.gordian.values.expressions.numbers;

import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianBoolean;
import edu.gordian.values.gordian.GordianNumber;

public final class Greater extends GordianBoolean {

    public static boolean is(Scope s, String v) {
        try {
            return v.indexOf('>') > 0 && v.indexOf('>') < v.length() - 1
                    && (s.toValue(v.substring(0, v.indexOf('>'))) instanceof GordianNumber)
                    && (s.toValue(v.substring(v.indexOf('>') + 1)) instanceof GordianNumber);
        } catch (Scope.IsNotValue e) {
            // toValue didn't work
            return false;
        }
    }

    public static Greater valueOf(Scope s, String v) {
        return new Greater((GordianNumber) s.toValue(v.substring(0, v.indexOf('>'))),
                (GordianNumber) s.toValue(v.substring(v.indexOf('>') + 1)));
    }

    public Greater(GordianNumber first, GordianNumber second) {
        super(first.doubleValue() > second.doubleValue());
    }
}
