package edu.gordian.elements.methods;

import edu.gordian.values.ReturningMethodBase;

import edu.gordian.scopes.Scope;
import edu.gordian.values.Value;

public final class ReturningMethod implements Runnable {

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
        base.runFor(args);
    }
}
