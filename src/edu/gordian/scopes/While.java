package edu.gordian.scopes;

import edu.gordian.Scope;

public class While extends Scope {

    private final String condition;

    public While(String condition, Scope scope) {
        super(scope);
        this.condition = condition;
    }

    public void run(String script) throws Exception {
        while (((Boolean) toValue(condition).getValue()).booleanValue()) {
            super.run(script);
        }
    }
}
