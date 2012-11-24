package edu.ata.script.data;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public class Boolean extends Data {

    public static boolean isType(java.lang.String data) {
    }

    public static Data get(java.lang.String data) {
    }

    public Boolean(java.lang.String literalString) {
        super(literalString);
    }

    protected Object getValue() {
        return java.lang.Boolean.valueOf(getLiteralString());
    }
}