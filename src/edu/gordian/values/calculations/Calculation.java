package edu.gordian.values.calculations;

import edu.gordian.values.GordianNumber;
import edu.gordian.values.Value;

abstract class Calculation implements Value {

    private GordianNumber first, second;

    public Calculation(GordianNumber first, GordianNumber second) {
        if (first == null) {
            throw new NullPointerException("First number is null");
        }
        if (second == null) {
            throw new NullPointerException("Second number is null");
        }
        this.first = first;
        this.second = second;
    }

    public final Object getValue() {
        return getNumber(first, second);
    }

    public abstract GordianNumber getNumber(GordianNumber f1, GordianNumber f2);

    public String toString() {
        return getValue().toString();
    }
}
