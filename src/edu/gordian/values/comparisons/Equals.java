package edu.gordian.values.comparisons;

import edu.gordian.values.Value;

public final class Equals extends Comparison {

    public Equals(Value first, Value second) {
        super(first, second);
    }

    public boolean get(Object f1, Object f2) {
        return f1.equals(f2);
    }
}
