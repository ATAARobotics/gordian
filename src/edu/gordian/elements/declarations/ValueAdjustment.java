package edu.gordian.elements.declarations;

import edu.gordian.scopes.Scope;
import edu.gordian.values.Value;
import edu.gordian.values.gordian.GordianNumber;

public final class ValueAdjustment implements Runnable {

    private final Scope scope;
    private final String key;
    private final double i;

    public ValueAdjustment(Scope scope, String key, double i) {
        if (scope == null) {
            throw new NullPointerException("Scope is null");
        }
        if (key == null) {
            throw new NullPointerException("Key is null");
        }
        this.scope = scope;
        this.key = key;
        this.i = i;
    }

    public void run() {
        double value;
        try {
            Value v = scope.getVariable(key);
            if (v.getValue() instanceof GordianNumber) {
                value = ((GordianNumber) v.getValue()).doubleValue();
            } else {
                value = 0;
            }
        } catch (NullPointerException ex) {
            value = 0;
        }
        value += i;
        scope.setVariable(key, GordianNumber.valueOf(value));
    }
}
