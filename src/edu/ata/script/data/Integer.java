package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.data.integer.Manipulation;

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
            return Manipulation.get(data);
        }
    }
    public Integer(java.lang.String literalString) {
        super(literalString);
    }

    protected Object getValue() {
        return java.lang.Integer.valueOf(getLiteralString());
    }
}