package edu.gordian.values.expressions;

import edu.gordian.Strings;
import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianBoolean;

public final class NotEquals extends GordianBoolean {

    public static boolean is(String v) {
        return Strings.contains(v, "!=") && Strings.lastIndexOf(v, "!=") > 0 && Strings.lastIndexOf(v, "!=") < v.length() - 2;
    }

    public static Equals valueOf(Scope s, String v) {
        return new Equals(!s.toValue(v.substring(0, Strings.lastIndexOf(v, "!="))).getValue().
                equals(s.toValue(v.substring(Strings.lastIndexOf(v, "!=") + 2)).getValue()));
    }

    public NotEquals(boolean value) {
        super(value);
    }
}
