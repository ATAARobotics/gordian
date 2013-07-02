package edu.gordian.scopes;

import edu.gordian.Scope;

public class For extends Scope {

    private final String condition;

    public For(String condition, Scope scope) {
        super(scope);
        this.condition = condition;
    }

    public void run(String script) throws Exception {
        for (int x = 0; x < ((Double) toValue(condition).getValue()).intValue(); x++) {
            super.run(script);
        }
    }
}
