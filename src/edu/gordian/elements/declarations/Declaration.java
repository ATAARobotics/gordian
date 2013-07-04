package edu.gordian.elements.declarations;

import edu.gordian.scopes.Scope;
import edu.gordian.values.Value;

public final class Declaration implements Runnable, Value {

    private final Scope scope;
    private final String key;
    private final String value;

    public Declaration(Scope scope, String key, String value) {
        this.scope = scope;
        this.key = key;
        this.value = value;
    }

    public void run() {
        getValue();
    }

    public Object getValue() {
        Value val = scope.toValue(value);
        scope.setVariable(key, val);
        return val.getValue();
    }

    public String toString() {
        return getValue().toString();
    }
}
