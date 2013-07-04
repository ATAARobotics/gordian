package edu.gordian.elements.declarations;

import edu.gordian.scopes.Scope;
import edu.gordian.values.StaticValue;
import edu.gordian.values.Value;

public final class ValueAdjustment implements Runnable, Value {

    private final Scope scope;
    private final String key;
    private final int i;

    public ValueAdjustment(Scope scope, String key, int i) {
        this.scope = scope;
        this.key = key;
        this.i = i;
    }

    public void run() {
        getValue();
    }

    public Object getValue() {
        Value v = scope.getVariable(key);
        int value = (v == null || !(v.getValue() instanceof Double)
                ? 0 : ((Double) v.getValue()).intValue()) + i;
        scope.setVariable(key, new StaticValue(Double.valueOf(value)));
        return Double.valueOf(value);
    }

    public String toString() {
        return getValue().toString();
    }
}
