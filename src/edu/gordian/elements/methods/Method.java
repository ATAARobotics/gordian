package edu.gordian.elements.methods;

import edu.gordian.values.Value;

public final class Method implements Runnable {

    private final MethodBase base;
    private final Value[] args;

    public Method(MethodBase base, Value[] args) {
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
        base.run(args);
    }
}
