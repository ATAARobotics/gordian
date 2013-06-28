package edu.gordian.values;

import edu.gordian.Scope;

public class ReturningMethod implements Value {

    private final Object value;

    public ReturningMethod(ReturningMethodBase base, Value[] args) {
        this.value = base.run(args);
    }

    public ReturningMethod(ReturningMethodBase base, Scope scope, String[] args) {
        this(base, scope.toValues(args));
    }

    public Object getValue() {
        return value;
    }

    public String toString() {
        return value.toString();
    }
}
