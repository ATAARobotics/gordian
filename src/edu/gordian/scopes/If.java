package edu.gordian.scopes;

import edu.gordian.Scope;

public class If extends Scope {

    private final String condition;

    public If(String condition, Scope scope) {
        super(scope);
        this.condition = condition;
    }

    public void run(String script) throws Exception {
        if (((Boolean) toValue(condition).getValue()).booleanValue()) {
            super.run(script);
        }
    }
}
