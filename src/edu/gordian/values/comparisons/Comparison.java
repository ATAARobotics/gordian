package edu.gordian.values.comparisons;

import edu.gordian.values.Value;

public abstract class Comparison implements Value {

    private final Value first, second;

    public Comparison(Value first, Value second) {
        this.first = first;
        this.second = second;
    }

    public Object getValue() {
        return Boolean.valueOf(get(first.getValue(), second.getValue()));
    }

    public abstract boolean get(Object f1, Object f2);

    public String toString() {
        return getValue().toString();
    }
}
