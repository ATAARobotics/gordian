package edu.ata.script.data;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public class String extends Data {

    public static boolean isType(java.lang.String data) {
    }

    public static Data get(java.lang.String data) {
    }
    
    public String(java.lang.String literalString) {
        super(literalString);
    }

    public Object getValue() {
        return getLiteralString();
    }
}