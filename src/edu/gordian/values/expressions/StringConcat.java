package edu.gordian.values.expressions;

import edu.gordian.Strings;
import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianString;

public final class StringConcat extends GordianString {

    public static boolean is(Scope s, String v) {
        return (Strings.contains(v, '+') && v.indexOf('+') > 0 && v.indexOf('+') < v.length() - 1)
                && ((s.toValue(v.substring(0, v.indexOf('+'))) instanceof GordianString)
                || (s.toValue(v.substring(v.indexOf('+') + 1)) instanceof GordianString));
    }

    public static StringConcat valueOf(Scope s, String v) {
        return new StringConcat((s.toValue(v.substring(0, v.indexOf('+')))).getValue().toString(),
                (s.toValue(v.substring(v.indexOf('+') + 1))).getValue().toString());
    }

    public StringConcat(String v1, String v2) {
        super(v1 + v2);
    }
}
