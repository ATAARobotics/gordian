package edu.gordian.values.calculations;

import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianNumber;

public final class Division extends GordianNumber {

    public static boolean is(Scope s, String v) {
        return (v.lastIndexOf('/') > 0 && v.lastIndexOf('/') < v.length() - 1)
                && (s.toValue(v.substring(0, v.lastIndexOf('/'))) instanceof GordianNumber)
                && (s.toValue(v.substring(v.lastIndexOf('/') + 1)) instanceof GordianNumber);
    }

    public static Division valueOf(Scope s, String v) {
        return new Division(((GordianNumber) s.toValue(v.substring(0, v.lastIndexOf('/')))),
                ((GordianNumber) s.toValue(v.substring(v.lastIndexOf('/') + 1))));
    }

    public Division(GordianNumber first, GordianNumber second) {
        super(first.doubleValue() / second.doubleValue());
    }
}
