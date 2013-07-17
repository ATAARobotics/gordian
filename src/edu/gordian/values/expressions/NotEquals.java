package edu.gordian.values.expressions;

import edu.gordian.Strings;
import edu.gordian.scopes.Scope;
import edu.gordian.values.gordian.GordianBoolean;

public final class NotEquals extends GordianBoolean {

    public static boolean is(Scope s, String v) {
        int[] i = Strings.allIndexesOf(v, "!=");
        for (int x = 0; x < i.length; x++) {
            int index = i[x];
            try {
                return index > 0 && index < v.length() - 2
                        && s.toValue(v.substring(0, index)).getValue().getClass()
                        .equals(s.toValue(v.substring(index + 2)).getValue().getClass());
            } catch (Scope.IsNotValue e) {
                // toValue didn't work
            }
        }
        return false;
    }

    public static NotEquals valueOf(Scope s, String v) {
        int[] i = Strings.allIndexesOf(v, "!=");
        for (int x = 0; x < i.length; x++) {
            int index = i[x];
            try {
                return new NotEquals(!s.toValue(v.substring(0, index)).getValue().
                        equals(s.toValue(v.substring(index + 2)).getValue()));
            } catch (Exception e) {
                // toValue or casting didn't work
            }
        }
        throw new Scope.IsNotValue(v);
    }

    public NotEquals(boolean value) {
        super(value);
    }
}
