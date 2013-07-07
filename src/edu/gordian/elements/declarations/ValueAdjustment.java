package edu.gordian.elements.declarations;

import edu.gordian.scopes.Scope;
import edu.gordian.values.StaticValue;
import edu.gordian.values.Value;
import edu.gordian.values.GordianNumber;

public final class ValueAdjustment implements Runnable, Value {

    private final Scope scope;
    private final String key;
    private final double i;

    public ValueAdjustment(Scope scope, String key, double i) {
        if(scope == null) {
            throw new NullPointerException("Scope is null");
        }
        if(key == null) {
            throw new NullPointerException("Key is null");
        }
        this.scope = scope;
        this.key = key;
        this.i = i;
    }

    public void run() {
        getValue();
    }

    public Object getValue() {
        Value v = scope.getVariable(key);
        double value = (v == null || !(v.getValue() instanceof GordianNumber)
                ? 0 : ((GordianNumber) v.getValue()).doubleValue()) + i;
        scope.setVariable(key, new StaticValue(GordianNumber.valueOf(value)));
        return GordianNumber.valueOf(value);
    }

    public String toString() {
        return getValue().toString();
    }
}
