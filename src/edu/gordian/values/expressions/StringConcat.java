package edu.gordian.values.expressions;

import edu.gordian.Strings;
import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianString;

public final class StringConcat extends GordianString {

    public static boolean is(Scope s, String v) {
        try {
            return (Strings.contains(v, '+') && v.lastIndexOf('+') > 0 && v.lastIndexOf('+') < v.length() - 1)
                    && ((s.toValue(v.substring(0, v.lastIndexOf('+'))) instanceof GordianString)
                    || (s.toValue(v.substring(v.lastIndexOf('+') + 1)) instanceof GordianString));
        } catch (Scope.IsNotValue e) {
            // toValue didn't work
            return false;
        }
    }

    public static StringConcat valueOf(Scope s, String v) {
        return new StringConcat((s.toValue(v.substring(0, v.lastIndexOf('+')))).getValue().toString(),
                (s.toValue(v.substring(v.lastIndexOf('+') + 1))).getValue().toString());
    }

    public StringConcat(String v1, String v2) {
        super(v1 + v2);
    }
}
