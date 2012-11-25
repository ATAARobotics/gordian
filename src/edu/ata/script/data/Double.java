package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.data.doubles.Calculation;

/**
 * @author Joel Gallant
 */
public class Double extends Data {

    public static boolean isType(java.lang.String data) {
        try {
            java.lang.Double.valueOf(data);
            return true;
        } catch (NumberFormatException ex) {
            return Calculation.isType(data);
        }
    }

    public static Data get(java.lang.String data) {
        try {
            java.lang.Double.valueOf(data);
            return new Double(data);
        } catch (NumberFormatException ex) {
            if (Calculation.isType(data)) {
                return Calculation.get(data);
            } else {
                throw new RuntimeException("Could not create double with - " + data);
            }
        }
    }

    public double get() {
        return ((java.lang.Double) getValue()).doubleValue();
    }

    public Double(java.lang.String literalString) {
        super(literalString);
    }

    public Object getValue() {
        return java.lang.Double.valueOf(getLiteralString());
    }
}