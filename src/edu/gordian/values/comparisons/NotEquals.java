package edu.gordian.values.comparisons;

import edu.gordian.values.Value;

public final class NotEquals extends Comparison {

    public NotEquals(Value first, Value second) {
        super(first, second);
    }

    public boolean get(Object f1, Object f2) {
        return !f1.equals(f2);
    }
}
