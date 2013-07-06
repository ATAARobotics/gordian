package edu.gordian.elements.methods;

import edu.gordian.values.ReturningMethodBase;
import edu.gordian.values.Value;

public final class ReturningMethod implements Runnable {

    private final ReturningMethodBase base;
    private final Value[] args;

    public ReturningMethod(ReturningMethodBase base, Value[] args) {
        if (base == null) {
            throw new NullPointerException("Base given was null");
        }
        if (args == null) {
            throw new NullPointerException("Args given was null");
        }
        this.base = base;
        this.args = args;
    }

    public void run() {
        base.runFor(args);
    }
}
