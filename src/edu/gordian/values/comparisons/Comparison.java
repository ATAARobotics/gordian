package edu.gordian.values.comparisons;

import edu.gordian.values.Value;

public abstract class Comparison implements Value {

    private final Value first, second;

    public Comparison(Value first, Value second) {
        if (first == null) {
            throw new NullPointerException("First value is null");
        }
        if (second == null) {
            throw new NullPointerException("Second value is null");
        }
        this.first = first;
        this.second = second;
    }

    public final Object getValue() {
        return Boolean.valueOf(get(first.getValue(), second.getValue()));
    }

    public abstract boolean get(Object f1, Object f2);

    public String toString() {
        return getValue().toString();
    }
}
