package edu.gordian.scopes;

import edu.gordian.Scope;

public class For extends Scope {

    private final int condition;

    public For(String condition, Scope scope) {
        super(scope);
        this.condition = ((Double) toValue(condition).getValue()).intValue();
    }

    public void run(String script) throws Exception {
        for (int x = 0; x < condition; x++) {
            super.run(script);
        }
    }
}
