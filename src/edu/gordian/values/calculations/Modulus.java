package edu.gordian.values.calculations;

public final class Modulus extends Calculation {

    public Modulus(Double first, Double second) {
        super(first, second);
    }

    public Double getNumber(Double f1, Double f2) {
        return Double.valueOf(f1.doubleValue() % f2.doubleValue());
    }
}
