package edu.gordian.values.comparisons.numbers;

import edu.gordian.values.GordianNumber;
import edu.gordian.values.Value;
import edu.gordian.values.comparisons.Comparison;

abstract class NumberComparison extends Comparison {

    public NumberComparison(Value first, Value second) {
        super(first, second);
    }

    public final boolean get(Object f1, Object f2) {
        return get(((GordianNumber) f1).doubleValue(), ((GordianNumber) f2).doubleValue());
    }

    public abstract boolean get(double f1, double f2);
}
