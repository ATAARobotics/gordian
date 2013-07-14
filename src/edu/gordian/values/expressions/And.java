package edu.gordian.values.expressions;

import edu.gordian.Strings;
import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianBoolean;

public final class And extends GordianBoolean {

    public static boolean is(Scope s, String v) {
        return Strings.contains(v, "&&") && Strings.lastIndexOf(v, "&&") > 0 && Strings.lastIndexOf(v, "&&") < v.length() - 2
                && (s.toValue(v.substring(0, Strings.lastIndexOf(v, "&&"))) instanceof GordianBoolean)
                && (s.toValue(v.substring(Strings.lastIndexOf(v, "&&") + 2)) instanceof GordianBoolean);
    }

    public static And valueOf(Scope s, String v) {
        return new And(((GordianBoolean) s.toValue(v.substring(0, Strings.lastIndexOf(v, "&&")))).booleanValue()
                && ((GordianBoolean) s.toValue(v.substring(Strings.lastIndexOf(v, "&&") + 2))).booleanValue());
    }

    public And(boolean value) {
        super(value);
    }
}
