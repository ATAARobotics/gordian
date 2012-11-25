package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.data.integers.Manipulation;

/**
 * @author Joel Gallant
 */
public class Integer extends Data {

    public static boolean isType(java.lang.String data) {
        try {
            java.lang.Integer.valueOf(data);
            return true;
        } catch (NumberFormatException ex) {
            return Manipulation.isType(data);
        }
    }

    public static Data get(java.lang.String data) {
        try {
            java.lang.Integer.valueOf(data);
            return new Integer(data);
        } catch (NumberFormatException ex) {
            if (Manipulation.isType(data)) {
                return Manipulation.get(data);
            } else {
                throw new RuntimeException("Could not create integer with - " + data);
            }
        }
    }

    public Integer(java.lang.String literalString) {
        super(literalString);
    }

    public Double convert() {
        return (Double) Double.get(get() + "");
    }

    public int get() {
        return ((java.lang.Integer) getValue()).intValue();
    }

    public Object getValue() {
        return java.lang.Integer.valueOf(getLiteralString());
    }
}