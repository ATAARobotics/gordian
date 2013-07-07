package edu.gordian.values.calculations;

import edu.gordian.values.GordianNumber;

public final class Multiplication extends Calculation {

    public Multiplication(GordianNumber first, GordianNumber second) {
        super(first, second);
    }

    public GordianNumber getNumber(GordianNumber f1, GordianNumber f2) {
        return GordianNumber.valueOf(f1.doubleValue() * f2.doubleValue());
    }
}
