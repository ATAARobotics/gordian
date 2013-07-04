package edu.gordian.values.calculations;

import edu.gordian.values.Value;

abstract class Calculation implements Value {

    private Double first, second;

    public Calculation(Double first, Double second) {
        this.first = first;
        this.second = second;
    }

    public Object getValue() {
        return getNumber(first, second);
    }

    public abstract Double getNumber(Double f1, Double f2);

    public String toString() {
        return getValue().toString();
    }
}
