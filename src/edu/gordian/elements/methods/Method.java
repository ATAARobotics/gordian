package edu.gordian.elements.methods;

import edu.gordian.Element;
import edu.gordian.Scope;
import edu.gordian.values.Value;

public class Method implements Element {

    private final MethodBase base;
    private final Value[] args;

    public Method(MethodBase base, Value[] args) {
        this.base = base;
        this.args = args;
    }

    public Method(MethodBase base, Scope scope, String[] args) {
        this(base, scope.toValues(args));
    }

    public void run() {
        base.run(args);
    }
}
