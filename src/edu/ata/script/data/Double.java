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
            return Calculation.get(data);
        }
    }

    public double get() {
        return ((java.lang.Double)getValue()).doubleValue();
    }

    public Double(java.lang.String literalString) {
        super(literalString);
    }

    protected Object getValue() {
        return java.lang.Double.valueOf(getLiteralString());
    }
}