package edu.ata.script.data;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public class Boolean extends Data {

    public static boolean isType(java.lang.String data) {
        if(data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false")) {
            return true;
        } else {
            
        }
    }

    public static Data get(java.lang.String data) {
        if(data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false")) {
            return new Boolean(data);
        } else {
            
        }
    }

    public Boolean(java.lang.String literalString) {
        super(literalString);
    }

    public Object getValue() {
        return java.lang.Boolean.valueOf(getLiteralString());
    }
}