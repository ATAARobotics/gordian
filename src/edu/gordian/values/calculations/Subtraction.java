package edu.gordian.values.calculations;

public final class Subtraction extends Calculation {

    public Subtraction(Double first, Double second) {
        super(first, second);
    }

    public Double getNumber(Double f1, Double f2) {
        return Double.valueOf(f1.doubleValue() - f2.doubleValue());
    }
}
