package edu.gordian.values;

import edu.first.util.Strings;
import edu.gordian.Method;
import edu.gordian.Variable;
import edu.gordian.scopes.EmptyInstance;
import language.scope.Scope;
import language.value.Value;

public final class GordianString extends EmptyInstance implements Value {

    private final String val;

    public GordianString(final String val) {
        super(new Method[]{
            new Method("charat") {
                public Value run(Scope current, Value[] args) {
                    return new GordianString("" + val.charAt(((GordianNumber) args[0]).getInt()));
                }
            }, new Method("indexof") {
                public Value run(Scope current, Value[] args) {
                    return new GordianNumber(val.indexOf(args[0].toString()));
                }
            }, new Method("length") {
                public Value run(Scope current, Value[] args) {
                    return new GordianNumber(val.length());
                }
            }
        }, new Variable[0]);
        this.val = Strings.replaceAll(Strings.replaceAll(Strings.replaceAll(val,
                "\\\"", "\""), "\\t", "\t"), "\\n", "\n");
    }

    public boolean equals(Object obj) {
        if (obj instanceof GordianString) {
            return ((GordianString) obj).val.equals(val);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.val.hashCode();
        return hash;
    }

    public String toString() {
        return val;
    }
}
