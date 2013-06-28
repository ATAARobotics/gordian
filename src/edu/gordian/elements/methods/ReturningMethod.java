package edu.gordian.elements.methods;

import edu.gordian.values.ReturningMethodBase;
import edu.gordian.Element;
import edu.gordian.Scope;
import edu.gordian.values.Value;

public class ReturningMethod implements Element {

    private final ReturningMethodBase base;
    private final Value[] args;

    public ReturningMethod(ReturningMethodBase base, Value[] args) {
        this.base = base;
        this.args = args;
    }

    public ReturningMethod(ReturningMethodBase base, Scope scope, String[] args) {
        this(base, scope.toValues(args));
    }

    public void run() {
        base.run(args);
    }
}
