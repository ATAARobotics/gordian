package edu.gordian.values.expressions;

import edu.gordian.Strings;
import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianBoolean;

public final class Or extends GordianBoolean {

    public static boolean is(Scope s, String v) {
        try {
            return Strings.contains(v, "||") && Strings.lastIndexOf(v, "||") > 0 && Strings.lastIndexOf(v, "||") < v.length() - 2
                    && (s.toValue(v.substring(0, Strings.lastIndexOf(v, "||"))) instanceof GordianBoolean)
                    && (s.toValue(v.substring(Strings.lastIndexOf(v, "||") + 2)) instanceof GordianBoolean);
        } catch (Scope.IsNotValue e) {
            // toValue didn't work
            return false;
        }
    }

    public static Or valueOf(Scope s, String v) {
        return new Or(((GordianBoolean) s.toValue(v.substring(0, Strings.lastIndexOf(v, "||")))).booleanValue()
                || ((GordianBoolean) s.toValue(v.substring(Strings.lastIndexOf(v, "||") + 2))).booleanValue());
    }

    public Or(boolean value) {
        super(value);
    }
}
