package edu.gordian.values.expressions.numbers;

import edu.gordian.Strings;
import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianBoolean;
import edu.gordian.values.gordian.GordianNumber;

public final class Less extends GordianBoolean {

    public static boolean is(String v) {
        return Strings.contains(v, '<') && v.indexOf('<') > 0 && v.indexOf('<') < v.length() - 1;
    }

    public static Less valueOf(Scope s, String v) {
        return new Less((GordianNumber) s.toValue(v.substring(0, v.indexOf('<'))),
                (GordianNumber) s.toValue(v.substring(v.indexOf('<') + 1)));
    }

    public Less(GordianNumber first, GordianNumber second) {
        super(first.doubleValue() < second.doubleValue());
    }
}
