package edu.ata.script.data;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public class IntegerData extends NumberData {

    public static boolean isType(String data) {
        try {
            java.lang.Integer.valueOf(data);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static Data get(String data) {
        try {
            java.lang.Integer.valueOf(data);
            return new IntegerData(data);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Could not create integer with - " + data);
        }
    }

    public IntegerData(String literalString) {
        super(literalString);
    }

    public double doubleValue() {
        return ((java.lang.Integer) getValue()).doubleValue();
    }

    public Object getValue() {
        return java.lang.Integer.valueOf(getLiteralString());
    }
}