package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.data.doubles.Calculation;

/**
 * @author Joel Gallant
 */
public class DoubleData extends NumberData {

    public static boolean isType(String data) {
        try {
            java.lang.Double.valueOf(data);
            return true;
        } catch (NumberFormatException ex) {
            return Calculation.isType(data);
        }
    }

    public static Data get(String data) {
        try {
            java.lang.Double.valueOf(data);
            return new DoubleData(data);
        } catch (NumberFormatException ex) {
            if (Calculation.isType(data)) {
                return Calculation.get(data);
            } else {
                throw new RuntimeException("Could not create double with - " + data);
            }
        }
    }

    public double doubleValue() {
        return ((java.lang.Double) getValue()).doubleValue();
    }

    public DoubleData(String literalString) {
        super(literalString);
    }

    public Object getValue() {
        return java.lang.Double.valueOf(getLiteralString());
    }
}