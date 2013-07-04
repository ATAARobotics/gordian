package edu.gordian.values.comparisons.numbers;

import edu.gordian.values.Value;

public final class GreaterOrEqual extends NumberComparison {

    public GreaterOrEqual(Value first, Value second) {
        super(first, second);
    }

    public boolean get(double f1, double f2) {
        return f1 >= f2;
    }
}
