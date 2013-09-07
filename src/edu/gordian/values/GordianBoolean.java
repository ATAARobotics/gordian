package edu.gordian.values;

import edu.gordian.Method;
import edu.gordian.Variable;
import edu.gordian.scopes.EmptyClass;
import language.scope.Scope;
import language.value.Value;

public final class GordianBoolean extends EmptyClass implements Value {

    private final boolean val;

    public GordianBoolean(final boolean val) {
        super(new Method[]{
                new Method("reverse") {
                    public Value run(Scope current, Value[] args) {
                        return new GordianBoolean(!val);
                    }
                }
            }, new Variable[0]);
        this.val = val;
    }

    public static GordianBoolean toBoolean(String s) {
        return new GordianBoolean(s.equalsIgnoreCase("true"));
    }

    public boolean get() {
        return val;
    }

    public String toString() {
        return String.valueOf(val);
    }

    public boolean equals(Object obj) {
        if (obj instanceof GordianBoolean) {
            return ((GordianBoolean) obj).val == val;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.val ? 1 : 0);
        return hash;
    }
}
