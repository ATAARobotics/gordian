package edu.gordian.values.adjustments;

import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianBoolean;

public final class ReversedBoolean extends GordianBoolean {

    public static boolean is(Scope s, String v) {
        return v.startsWith("!") && (s.toValue(v.substring(1)) instanceof GordianBoolean);
    }

    public static ReversedBoolean valueOf(Scope s, String v) {
        return new ReversedBoolean(!((GordianBoolean) s.toValue(v.substring(1))).booleanValue());
    }

    public ReversedBoolean(boolean value) {
        super(value);
    }
}
